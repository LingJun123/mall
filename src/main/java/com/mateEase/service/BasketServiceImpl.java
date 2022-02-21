package com.mateEase.service;

import com.mateEase.mapper.BasketMapper;
import com.mateEase.pojo.Basket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BasketServiceImpl implements BasketService{
    @Autowired
    BasketMapper basketMapper;
    @Autowired
    OrderService orderService;

    @Override
    public List<Basket> queryBasketByName(String user_name) {
        return basketMapper.queryBasketByName(user_name);
    }

    @Override
    public Basket queryBasketByNameID(Basket basket) {
        return basketMapper.queryBasketByNameID(basket);
    }

    @Override
    public int clearBasketByName(String name) {
        return basketMapper.clearBasketByName(name);
    }

    @Override
    public int insertBasket(Basket basket) {
        return basketMapper.insertBasket(basket);
    }

    @Override
    public int repeatAddBasket(Basket basket) {
        return basketMapper.repeatAddBasket(basket);
    }
}
