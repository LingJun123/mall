package com.mateEase.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
// 订单
public class Order {
    private int order_id;
    private int prod_id;
    private String user_name;
    private int order_count;
    private Date order_date;
    private float price;
}
