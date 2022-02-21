package com.mateEase.service;

import com.mateEase.mapper.ProductMapper;
import com.mateEase.pojo.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService{
    @Autowired
    ProductMapper productMapper;

    @Override
    public List<Product> queryAllProduct() {
        return productMapper.queryAllProduct();
    }

    @Override
    public Product queryProductByID(int id) {
        return productMapper.queryProductByID(id);
    }

    @Override
    public int insertProduct(Product product) {
        return productMapper.insertProduct(product);
    }

    @Override
    public int deleteProductByID(int id) {
        return productMapper.deleteProductByID(id);
    }

    @Override
    public int updateProduct(Product product) {
        return productMapper.updateProduct(product);
    }

    @Override
    public List<Product> queryProductByName(String name) {
        return productMapper.queryProductByName(name);
    }
}
