package dev.guedes.mongodbdemo.repository.impl;

import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import dev.guedes.mongodbdemo.exception.DatabaseException;
import dev.guedes.mongodbdemo.model.Order;
import dev.guedes.mongodbdemo.repository.OrderRepository;
import lombok.NonNull;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * @author João Guedes
 */
public class OrderRepositoryImpl implements OrderRepository {

    private final MongoCollection<Order> collection;

    public OrderRepositoryImpl(@NonNull MongoDatabase database) {
        this.collection = database.getCollection("orders", Order.class);
    }

    @Override
    public void save(Order order) throws DatabaseException {
        if (order.getId() == null) {
            this.insert(order);
        } else {
            this.update(order);
        }
    }

    private void insert(Order order) throws DatabaseException {
        try {
            this.collection.insertOne(order);
        } catch (MongoException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    private void update(Order order) throws DatabaseException {
        try {
            Bson filter = Filters.eq("_id", order.getId());
            Bson update = Updates.combine(
                    Updates.set("date", order.getDate()),
                    Updates.set("items", order.getItems()),
                    Updates.set("account_id", order.getAccountId())
            );
            this.collection.updateOne(filter, update);
        } catch (MongoException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public Optional<Order> findById(ObjectId id) throws DatabaseException {
        try {
            Bson filter = Filters.eq("_id", id);
            return Optional.ofNullable(this.find(filter));
        } catch (MongoException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    private Order find(Bson filter) {
        return this.collection.find(filter).first();
    }

    @Override
    public List<Order> findAllByAccountId(ObjectId accountId) throws DatabaseException {
        try {
            Bson filter  = Filters.eq("account_id", accountId);
            return this.findAll(filter);
        } catch (MongoException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    private List<Order> findAll(Bson filter) {
        return StreamSupport.stream(this.collection.find(filter).spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Order order) throws DatabaseException {
        try {
            Bson filter = Filters.eq("_id", order.getId());
            this.collection.deleteOne(filter);
        } catch (MongoException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

}
