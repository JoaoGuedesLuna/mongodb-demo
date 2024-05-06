package dev.guedes.mongodbdemo.service;

import dev.guedes.mongodbdemo.exception.DatabaseException;
import dev.guedes.mongodbdemo.model.Product;
import dev.guedes.mongodbdemo.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * @author João Guedes
 */
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public void save(Product product) throws DatabaseException {
        this.productRepository.save(product);
    }

    public Optional<Product> findById(ObjectId id) throws DatabaseException {
        return this.productRepository.findById(id);
    }

    public List<Product> findAll() throws DatabaseException {
        return this.productRepository.findAll();
    }

    public List<Product> findAllByNameContaining(String name) throws DatabaseException {
        return this.productRepository.findAllByNameContaining(name);
    }

    public List<Product> findAllByNameStartingWithAndPriceBetween(String name, BigDecimal minPrice, BigDecimal maxPrice) throws DatabaseException {
        return this.productRepository.findAllByNameStartingWithAndPriceBetween(name, minPrice, maxPrice);
    }

    public void delete(Product product) throws DatabaseException {
        this.productRepository.delete(product);
    }

}
