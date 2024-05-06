package dev.guedes.mongodbdemo.repository;

import dev.guedes.mongodbdemo.exception.DatabaseException;
import dev.guedes.mongodbdemo.model.Account;
import org.bson.types.ObjectId;
import java.util.Optional;

/**
 * @author João Guedes
 */
public interface AccountRepository {

    void save(Account account) throws DatabaseException;

    Optional<Account> findById(ObjectId id) throws DatabaseException;

    Optional<Account> findByEmail(String email) throws DatabaseException;

    void delete(Account account) throws DatabaseException;

}
