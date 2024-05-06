package dev.guedes.mongodbdemo.repository.impl;

import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import dev.guedes.mongodbdemo.exception.DatabaseException;
import dev.guedes.mongodbdemo.model.Product;
import dev.guedes.mongodbdemo.repository.ProductRepository;
import lombok.NonNull;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * @author João Guedes
 */
public class ProductRepositoryImpl implements ProductRepository {

    private final MongoCollection<Product> collection;

    public ProductRepositoryImpl(@NonNull MongoDatabase database) {
        this.collection = database.getCollection("products", Product.class);
    }

    @Override
    public void save(Product product) throws DatabaseException {
        if (product.getId() == null) {
            this.insert(product);
        } else {
            this.update(product);
        }
    }

    private void insert(Product product) throws DatabaseException {
        try {
            this.collection.insertOne(product);
        } catch (MongoException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    private void update(Product product) throws DatabaseException {
        try {
            Bson filter = Filters.eq("_id", product.getId());
            Bson update = Updates.combine(
                    Updates.set("name", product.getName()),
                    Updates.set("description", product.getDescription()),
                    Updates.set("price", product.getPrice())
            );
            this.collection.updateOne(filter, update);
        } catch (MongoException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public Optional<Product> findById(ObjectId id) throws DatabaseException {
        try {
            Bson filter = Filters.eq("_id", id);
            return Optional.ofNullable(this.find(filter));
        } catch (MongoException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    private Product find(Bson filter) {
        return this.collection.find(filter).first();
    }

    @Override
    public List<Product> findAll() throws DatabaseException {
        try {
           return this.findAll(Filters.empty());
        } catch (MongoException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public List<Product> findAllByNameContaining(String name) throws DatabaseException {
        try {
            Bson filter  = Filters.regex("name", ".*" + name + ".*", "i");
            return this.findAll(filter);
        } catch (MongoException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public List<Product> findAllByNameStartingWithAndPriceBetween(String name, BigDecimal minPrice, BigDecimal maxPrice) throws DatabaseException {
        try {
            Bson filter  = Filters.and(
                    Filters.regex("name", "^".concat(name), "i"),
                    Filters.and(
                            Filters.gte("price", minPrice),
                            Filters.lte("price", maxPrice)
                    )
            );
            return this.findAll(filter);
        } catch (MongoException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    private List<Product> findAll(Bson filter) {
        return StreamSupport.stream(this.collection.find(filter).spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Product product) throws DatabaseException {
        try {
            Bson filter = Filters.eq("_id", product.getId());
            this.collection.deleteOne(filter);
        } catch (MongoException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

}
