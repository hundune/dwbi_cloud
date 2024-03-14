package com.dwbi.chart.bi.mq;

import com.dwbi.common.mq.constant.BiMqConstant;
import com.rabbitmq.client.Channel;
import com.dwbi.common.common.ErrorCode;
import com.dwbi.common.constant.ChartConstant;
import com.dwbi.common.exception.BusinessException;
import com.dwbi.common.ai.config.AiManager;
import com.dwbi.chart.api.model.entity.Chart;
import com.dwbi.chart.service.ChartService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @hundune~
 * @version1.0
 */
@Component
@Slf4j
public class BiMessageConsumer {
    @Resource
    private ChartService chartService;

    @Resource
    private AiManager aiManager;

    //指定程序监听的消息队列和确认机制

    @SneakyThrows
    @RabbitListener(queues = {BiMqConstant.BI_QUEUE_NAME},ackMode = "MANUAL")
    public void receiveMessage(String message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long deliverTag){

        if(StringUtils.isBlank(message)){
            channel.basicNack(deliverTag,false,false);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"消息为空");
        }
        long chartId = Long.parseLong(message);

        Chart chart = chartService.getById(chartId);
        if(chart == null){
            channel.basicNack(deliverTag,false,false);
            chart.setStatus(ChartConstant.FAILED);
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR,"图表为空");
        }
        // 先修改图表任务状态为 “执行中”，完成后修改为“已完成”；执行失败后，修改为“失败”，记录任务失败信息
        Chart updateChart = new Chart();
        updateChart.setId(chart.getId());
        updateChart.setStatus("running");
        boolean b = chartService.updateById(updateChart);
        if (!b) {
            channel.basicNack(deliverTag,false,false);
            chart.setStatus(ChartConstant.FAILED);
            handleChartUpdateError(chart.getId(), "更新图表执行中状态失败");
            return;
        }

        String result = aiManager.doAnalysis(buildUserInput(chart));
        result = result.replaceAll("】", "【");
        String[] splits = result.split("【【【【【");
        //校验,结果应该有 3
        if (splits.length < 3) {
            channel.basicNack(deliverTag,false,false);
            chart.setStatus(ChartConstant.FAILED);
            handleChartUpdateError(chart.getId(), "AI 生成错误");
            return;
        }
        String genChart = splits[1].trim();
        String genResult = splits[2].trim();
        Chart updateChartResult = new Chart();
        updateChartResult.setGenChart(genChart);
        updateChartResult.setGenResult(genResult);
        updateChartResult.setId(chart.getId());
        updateChartResult.setStatus("succeed");
        boolean updateResult = chartService.updateById(updateChartResult);
        if (!updateResult) {
            channel.basicNack(deliverTag,false,false);
            chart.setStatus(ChartConstant.FAILED);
            handleChartUpdateError(chart.getId(), "更新图表成功状态失败");
        }
        channel.basicAck(deliverTag,false);
    }
    private void handleChartUpdateError(long chartId, String execMessage) {
        Chart updateChartResult = new Chart();
        updateChartResult.setId(chartId);
        updateChartResult.setStatus("failed");
        updateChartResult.setExecMessage(execMessage);
        boolean updateResult = chartService.updateById(updateChartResult);
        if (!updateResult) {
            log.info("更新图表失败状态失败");
        }
    }

    private String buildUserInput(Chart chart){
        String goal = chart.getGoal();
        String chartType = chart.getChartType();;
        String csvData = chart.getChartData();

        StringBuffer userInput = new StringBuffer();
        String userGoal = goal;
        if (chartType != null) {
            userGoal += ",请使用" + chartType;
        }
        userInput.append("\n")
                .append("分析需求：")
                .append("\n")
                .append("{")
                .append(userGoal)
                .append("}")
                .append("\n");
        userInput.append("原始数据:").append("\n").append(csvData);
        return userInput.toString();
    }
}
