package com.mateEase.service;

import com.mateEase.pojo.Product;

import java.util.List;

public interface ProductService {
    List<Product> queryAllProduct();
    Product queryProductByID(int id);
    int insertProduct(Product product);
    int deleteProductByID(int id);
    int updateProduct(Product product);
    List<Product> queryProductByName(String name);
}
