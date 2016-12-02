package ru.shop.service;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.shop.model.Product;

import java.util.List;

/**
 * Created by Ramil on 02.12.2016.
 */
public class ContextTest {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("ApplicationContext.xml");
        ProductService productService = (ProductService) applicationContext.getBean("ProductService");

        List<Product> products = productService.fetchAll();
        List<Product> specificSearch = productService.findByName("тетрадь в");
        System.out.println(specificSearch);

        //System.out.println(productService.fetch(1));

        System.out.println(productService.sell(1, 1001));
        specificSearch = productService.findByName("тетрадь в");
        System.out.println(specificSearch);

        System.out.println(productService.sell(1, 1000));
        specificSearch = productService.findByName("тетрадь в");
        System.out.println(specificSearch);
    }
}
