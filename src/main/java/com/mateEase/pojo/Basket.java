package com.mateEase.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
// 购物车
public class Basket {
    private int basket_id;
    private int prod_id;
    private String user_name;    // 买家用户名
    private int basket_count;
    private Date basket_date;
}
