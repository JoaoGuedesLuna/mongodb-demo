package dev.guedes.mongodbdemo.repository.impl;

import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import dev.guedes.mongodbdemo.exception.DatabaseException;
import dev.guedes.mongodbdemo.model.Account;
import dev.guedes.mongodbdemo.repository.AccountRepository;
import lombok.NonNull;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import java.util.Optional;

/**
 * @author João Guedes
 */
public class AccountRepositoryImpl implements AccountRepository {

    private final MongoCollection<Account> collection;

    public AccountRepositoryImpl(@NonNull MongoDatabase database) {
        this.collection = database.getCollection("accounts", Account.class);
    }

    @Override
    public void save(Account account) throws DatabaseException {
        if (account.getId() == null) {
            this.insert(account);
        } else {
            this.update(account);
        }
    }

    private void insert(Account account) throws DatabaseException {
        try {
            this.collection.insertOne(account);
        } catch (MongoException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    private void update(Account account) throws DatabaseException {
        try {
            Bson filter = Filters.eq("_id", account.getId());
            Bson update = Updates.combine(
                    Updates.set("email", account.getEmail()),
                    Updates.set("password", account.getPassword())
            );
            this.collection.updateOne(filter, update);
        } catch (MongoException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public Optional<Account> findById(ObjectId id) throws DatabaseException {
        try {
            Bson filter = Filters.eq("_id", id);
            return Optional.ofNullable(this.find(filter));
        } catch (MongoException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public Optional<Account> findByEmail(String email) throws DatabaseException {
        try {
            Bson filter = Filters.eq("email", email);
            return Optional.ofNullable(this.find(filter));
        } catch (MongoException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    private Account find(Bson filter) {
        return this.collection.find(filter).first();
    }

    @Override
    public void delete(Account account) throws DatabaseException {
        try {
            Bson filter = Filters.eq("_id", account.getId());
            this.collection.deleteOne(filter);
        } catch (MongoException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

}
