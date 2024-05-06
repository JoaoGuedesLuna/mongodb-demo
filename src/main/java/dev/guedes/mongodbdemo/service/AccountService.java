package dev.guedes.mongodbdemo.service;

import dev.guedes.mongodbdemo.exception.DatabaseException;
import dev.guedes.mongodbdemo.model.Account;
import dev.guedes.mongodbdemo.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.mindrot.jbcrypt.BCrypt;
import java.util.Optional;

/**
 * @author João Guedes
 */
@RequiredArgsConstructor
public class AccountService implements AccountRepository {

    private final AccountRepository accountRepository;

    public void save(Account account) throws DatabaseException {
        String hashedPassword = BCrypt.hashpw(account.getPassword(), BCrypt.gensalt());
        account.setPassword(hashedPassword);
        this.accountRepository.save(account);
    }
    
    public Optional<Account> findById(ObjectId id) throws DatabaseException {
        return this.accountRepository.findById(id);
    }
    
    public Optional<Account> findByEmail(String email) throws DatabaseException {
        return this.accountRepository.findByEmail(email);
    }

    public void delete(Account account) throws DatabaseException {
        this.accountRepository.delete(account);
    }
    
}
