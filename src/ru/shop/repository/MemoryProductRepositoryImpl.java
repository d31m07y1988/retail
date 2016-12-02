package ru.shop.repository;

import ru.shop.model.Product;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Created by Ramil on 02.12.2016.
 */
public class MemoryProductRepositoryImpl implements ProductRepository {

    private static Map<Long, Product> repository = new ConcurrentHashMap<>();

    static {
        repository.put(0L, new Product(0, "тетрадь в клетку", BigDecimal.valueOf(2.5), 1000));
        repository.put(1L, new Product(1, "тетрадь в линейку", BigDecimal.valueOf(2.5), 1000));
        repository.put(2L, new Product(2, "общая тетрадь", BigDecimal.valueOf(25), 65));
        repository.put(3L, new Product(3, "тетрадка", BigDecimal.valueOf(2), 600));
        repository.put(4L, new Product(4, "тетрадь с голограммой", BigDecimal.valueOf(45), 80));
        repository.put(5L, new Product(5, "ручка", BigDecimal.valueOf(5), 1000));
        repository.put(6L, new Product(6, "карандаш", BigDecimal.valueOf(4), 100));
        repository.put(7L, new Product(7, "карандаш НВ", BigDecimal.valueOf(4.8), 100));
        repository.put(8L, new Product(8, "карандаш СВ", BigDecimal.valueOf(5.5), 200));
        repository.put(9L, new Product(9, "карандаш ННВ", BigDecimal.valueOf(5.5), 500));
        repository.put(10L, new Product(10, "автоматический карандаш", BigDecimal.valueOf(30), 15));
    }


    @Override
    public Product get(long id) {
        return repository.get(id);
    }

    @Override
    public List<Product> getAll() {
        return new ArrayList<>(repository.values());
    }

    @Override
    public List<Product> findByName(String productName) {
        return repository.values().stream().filter(p -> p.getName().toLowerCase().contains(productName.toLowerCase())).collect(Collectors.toList());
    }

    @Override
    public void update(Product product) {
        Product dbProduct = get(product.getId());
        dbProduct.setCount(product.getCount());
        dbProduct.setName(product.getName());
        dbProduct.setPrice(product.getPrice());
    }
}
