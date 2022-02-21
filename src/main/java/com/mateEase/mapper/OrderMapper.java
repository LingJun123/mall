package com.mateEase.mapper;

import com.mateEase.pojo.Order;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface OrderMapper {
    List<Order> queryAllOrderByName(String user_name);
    Order queryOrder(Order order);
    int insertOrder(Order order);
}
