package com.dwbi.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dwbi.user.mapper.OrdersMapper;
import com.dwbi.user.api.model.entity.Orders;
import com.dwbi.user.service.OrdersService;
import org.springframework.stereotype.Service;

/**
* @author 86182
* @description 针对表【orders(充值订单表)】的数据库操作Service实现
* @createDate 2024-02-19 14:49:10
*/
@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders>
    implements OrdersService{

}




