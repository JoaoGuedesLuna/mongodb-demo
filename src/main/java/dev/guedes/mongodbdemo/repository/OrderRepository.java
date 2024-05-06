package dev.guedes.mongodbdemo.repository;

import dev.guedes.mongodbdemo.exception.DatabaseException;
import dev.guedes.mongodbdemo.model.Order;
import org.bson.types.ObjectId;
import java.util.List;
import java.util.Optional;

/**
 * @author João Guedes
 */
public interface OrderRepository {

    void save(Order order) throws DatabaseException;

    Optional<Order> findById(ObjectId id) throws DatabaseException;

    List<Order> findAllByAccountId(ObjectId accountId) throws DatabaseException;

    void delete(Order order) throws DatabaseException;

}
