package ru.shop.service;

import ru.shop.model.Product;
import ru.shop.repository.ProductRepository;

import java.util.List;

/**
 * Created by Ramil on 02.12.2016.
 */
public class ProductServiceImpl implements ProductService {

    ProductRepository repository;

    public void setRepository(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public Product fetch(long id) {
        return repository.get(id);
    }

    @Override
    public List<Product> fetchAll() {
        return repository.getAll();
    }

    @Override
    public List<Product> findByName(String productName) {
        return repository.findByName(productName);
    }

    @Override
    public void update(Product product) {
        repository.update(product);
    }

    @Override
    public boolean sell(long id, int count) {
        Product oldProduct = fetch(id);
        if (oldProduct != null && oldProduct.getCount() - count >= 0) {
            oldProduct.setCount(oldProduct.getCount() - count);
            update(oldProduct);
            return true;
        }
        return false;
    }
}
