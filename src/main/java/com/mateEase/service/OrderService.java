package com.mateEase.service;

import com.mateEase.pojo.Order;

import java.util.List;

public interface OrderService {
    List<Order> queryAllOrderByName(String user_name);
    Order queryOrder(Order order);
    int insertOrder(Order order);
}
