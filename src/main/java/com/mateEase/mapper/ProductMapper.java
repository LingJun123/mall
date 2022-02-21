package com.mateEase.mapper;

import com.mateEase.pojo.Product;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ProductMapper {
    // 查询所有产品
    List<Product> queryAllProduct();
    Product queryProductByID(int id);
    int insertProduct(Product product);
    int deleteProductByID(int id);
    int updateProduct(Product product);
    List<Product> queryProductByName(String name);
}
