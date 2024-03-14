package com.dwbi.chart.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dwbi.chart.mapper.ChartMapper;
import com.dwbi.chart.api.model.entity.Chart;
import com.dwbi.chart.service.ChartService;
import org.springframework.stereotype.Service;

/**
* @author 86182
* @description 针对表【chart(图表信息表)】的数据库操作Service实现
* @createDate 2023-12-23 20:41:15
*/
@Service
public class ChartServiceImpl extends ServiceImpl<ChartMapper, Chart>
    implements ChartService{

}




