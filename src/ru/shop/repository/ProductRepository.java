package ru.shop.repository;

import ru.shop.model.Product;

import java.util.List;

/**
 * Created by Ramil on 02.12.2016.
 */
public interface ProductRepository {

    Product get(long id);
    List<Product> getAll();
    List<Product> findByName(String productName);
    void update(Product product);

}
