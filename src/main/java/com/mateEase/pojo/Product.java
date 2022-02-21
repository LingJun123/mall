package com.mateEase.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
// 商品表
public class Product {
    private int prod_id;
    private String prod_name;
    private String prod_pic;
    private String user_name;   // 卖家用户名
    private int status;
    private String brief;
    private String content;
    private float price;
    private int count;
    private Date rec_time;
    private Date update_time;
}
