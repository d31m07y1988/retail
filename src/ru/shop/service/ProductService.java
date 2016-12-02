package ru.shop.service;

import ru.shop.model.Product;

import java.util.List;

/**
 * Created by Ramil on 02.12.2016.
 */
public interface ProductService {

    Product fetch(long id);
    List<Product> fetchAll();
    List<Product> findByName(String productName);
    void update(Product product);
    Boolean sell(long id, int count);
}
