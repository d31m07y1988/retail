package ru.shop.model;

import java.math.BigDecimal;

/**
 * Created by Ramil on 02.12.2016.
 */
public class Product {
    private long id;
    private String name;
    private BigDecimal price;
    private int count;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public int getCount() {
        return count;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Product(long id, String name, BigDecimal price, int count) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.count = count;
    }

    @Override
    public String toString() {
        return "арт." + id +"\t"+ name + "\t" + price + "руб.\t" + count+"шт.";
    }
}
