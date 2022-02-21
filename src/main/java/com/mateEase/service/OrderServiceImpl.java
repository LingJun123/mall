package com.mateEase.service;

import com.mateEase.mapper.OrderMapper;
import com.mateEase.pojo.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    OrderMapper orderMapper;

    @Override
    public List<Order> queryAllOrderByName(String user_name) {
        return orderMapper.queryAllOrderByName(user_name);
    }

    @Override
    public Order queryOrder(Order order) {
        return orderMapper.queryOrder(order);
    }

    @Override
    public int insertOrder(Order order) {
        return orderMapper.insertOrder(order);
    }
}