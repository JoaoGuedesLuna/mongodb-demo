package dev.guedes.mongodbdemo.service;

import dev.guedes.mongodbdemo.database.Database;
import dev.guedes.mongodbdemo.repository.AccountRepository;
import dev.guedes.mongodbdemo.repository.OrderRepository;
import dev.guedes.mongodbdemo.repository.ProductRepository;
import dev.guedes.mongodbdemo.repository.impl.AccountRepositoryImpl;
import dev.guedes.mongodbdemo.repository.impl.OrderRepositoryImpl;
import dev.guedes.mongodbdemo.repository.impl.ProductRepositoryImpl;
import lombok.SneakyThrows;

/**
 * @author João Guedes
 */
public class ServiceFactory {

    @SneakyThrows
    public static AccountService createAccountService() {
        AccountRepository accountRepository = new AccountRepositoryImpl(Database.getInstance());
        return new AccountService(accountRepository);
    }

    @SneakyThrows
    public static ProductService createProductService() {
        ProductRepository productRepository = new ProductRepositoryImpl(Database.getInstance());
        return new ProductService(productRepository);
    }

    @SneakyThrows
    public static OrderService createOrderService() {
        OrderRepository orderRepository = new OrderRepositoryImpl(Database.getInstance());
        return new OrderService(orderRepository);
    }

}
