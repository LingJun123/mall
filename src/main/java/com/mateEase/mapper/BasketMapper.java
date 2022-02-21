package com.mateEase.mapper;

import com.mateEase.pojo.Basket;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface BasketMapper {
    // 查询用户购物车中所有商品
    List<Basket> queryBasketByName(String user_name);

    // 查询用户名和商品ID对应的购物车记录
    Basket queryBasketByNameID(Basket basket);

    // 当用户清空购物车时，删除该用户购物车中商品
    int clearBasketByName(String name);

    // 新增一条记录
    int insertBasket(Basket basket);
    // 修改购物车信息
    // 点击购买，则对应商品数量加1
    int repeatAddBasket(Basket basket);
}
