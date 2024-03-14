package com.dwbi.common.ai.config;

import io.github.briqt.spark4j.SparkClient;
import io.github.briqt.spark4j.constant.SparkApiVersion;
import io.github.briqt.spark4j.exception.SparkException;
import io.github.briqt.spark4j.model.SparkMessage;
import io.github.briqt.spark4j.model.SparkSyncChatResponse;
import io.github.briqt.spark4j.model.request.SparkRequest;
import io.github.briqt.spark4j.model.response.SparkTextUsage;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @hundune~
 * @version1.0
 */
@Service
public class AiManager {
    SparkClient sparkClient = new SparkClient();
// 设置认证信息
    AiManager(){
        sparkClient.appid="48575dde";
        sparkClient.apiKey="8b84ededeedd16a3e7d4b40298d18de2";
        sparkClient.apiSecret="MTYwOTgxNjEwNjNlNGM3MDg2MWU2ZTdm";
    }
    public String doAnalysis(String message){

        List<SparkMessage> messages=new ArrayList<>();
        messages.add(SparkMessage.systemContent("你是一个数据分析师和前端开发专家，接下来我会按照以下固定格式给你提供内容：\n" +
                "分析需求：\n" +
                        "{数据分析的需求或者目标}\n" +
                        "原始数据：\n" +
                        "{数据分析的需求或者目标}\n" +
                        "请根据这两部分内容，按照以下止指定格式输出内容（此外不需要输出任何多余的开通、结尾、注释）\n" +
                        "【【【【【\n" +
                        "{先生成前端 Echarts V5 的 option 配置对象 JSON 代码，合理地将数据进行可视化，不要生成任何多余地内容，比如注释}\n" +
                        "【【【【【\n" +
                        "{然后生成上面数据的分析结论，不要生成多余地注释}"));
        messages.add(SparkMessage.userContent(message));
        // 构造请求
        SparkRequest sparkRequest=SparkRequest.builder()
        // 消息列表S
                .messages(messages)
        // 模型回答的tokens的最大长度,非必传，默认为2048。
        // V1.5取值为[1,4096]
        // V2.0取值为[1,8192]
        // V3.0取值为[1,8192]
                .maxTokens(8192)
        // 核采样阈值。用于决定结果随机性,取值越高随机性越强即相同的问题得到的不同答案的可能性越高 非必传,取值为[0,1],默认为0.5
                .temperature(0.2)
        // 指定请求版本，默认使用最新3.0版本
                .apiVersion(SparkApiVersion.V3_0)
                .build();

        try {
            // 同步调用
            SparkSyncChatResponse chatResponse = sparkClient.chatSync(sparkRequest);
            SparkTextUsage textUsage = chatResponse.getTextUsage();
            System.out.println("\n回答：" + chatResponse.getContent());
            System.out.println("\n提问tokens：" + textUsage.getPromptTokens()
                    + "，回答tokens：" + textUsage.getCompletionTokens()
                    + "，总消耗tokens：" + textUsage.getTotalTokens());

            return chatResponse.getContent();
        } catch (SparkException e) {
            System.out.println("发生异常了：" + e.getMessage());
        }
        return null;
    }

    public String doChangeType(String textTaskType,String message){
        List<SparkMessage> messages=new ArrayList<>();
        messages.add(SparkMessage.systemContent("请使用"+textTaskType+"语法对下面文章格式化"));
        messages.add(SparkMessage.userContent(message));
        // 构造请求
        SparkRequest sparkRequest=SparkRequest.builder()
                // 消息列表S
                .messages(messages)
                // 模型回答的tokens的最大长度,非必传，默认为2048。
                // V1.5取值为[1,4096]
                // V2.0取值为[1,8192]
                // V3.0取值为[1,8192]
                .maxTokens(8192)
                // 核采样阈值。用于决定结果随机性,取值越高随机性越强即相同的问题得到的不同答案的可能性越高 非必传,取值为[0,1],默认为0.5
                .temperature(0.2)
                // 指定请求版本，默认使用最新3.0版本
                .apiVersion(SparkApiVersion.V3_0)
                .build();

        try {
            // 同步调用
            SparkSyncChatResponse chatResponse = sparkClient.chatSync(sparkRequest);
            SparkTextUsage textUsage = chatResponse.getTextUsage();
            System.out.println("\n回答：" + chatResponse.getContent());
            System.out.println("\n提问tokens：" + textUsage.getPromptTokens()
                    + "，回答tokens：" + textUsage.getCompletionTokens()
                    + "，总消耗tokens：" + textUsage.getTotalTokens());

            return chatResponse.getContent();
        } catch (SparkException e) {
            System.out.println("发生异常了：" + e.getMessage());
        }
        return null;
    }

}
