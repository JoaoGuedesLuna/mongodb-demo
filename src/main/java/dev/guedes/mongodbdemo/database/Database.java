package dev.guedes.mongodbdemo.database;

import com.mongodb.*;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import dev.guedes.mongodbdemo.exception.DatabaseException;
import dev.guedes.mongodbdemo.exception.PropertiesException;
import dev.guedes.mongodbdemo.model.Account;
import dev.guedes.mongodbdemo.model.Item;
import dev.guedes.mongodbdemo.model.Order;
import dev.guedes.mongodbdemo.model.Product;
import lombok.Cleanup;
import lombok.experimental.UtilityClass;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * @author João Guedes
 */
@UtilityClass
public class Database {

    private static MongoDatabase database;
    private static final Properties properties;

    static {
        String propertiesPath = System.getProperty("user.dir").concat("\\src\\main\\resources\\application.properties");
        try {
            @Cleanup FileInputStream fileInputStream = new FileInputStream(propertiesPath);
            properties = new Properties();
            properties.load(fileInputStream);
        } catch (IOException e) {
            throw new PropertiesException(e.getMessage());
        }
    }

    public static MongoDatabase getInstance() throws DatabaseException {
        if (database == null) {
            final String DATABASE_NAME = properties.getProperty("data.mongodb.database");
            Database.database = Database.getClient().getDatabase(DATABASE_NAME);
        }
        return Database.database;
    }

    private static MongoClient getClient() throws DatabaseException {
        try {
            final String URI = properties.getProperty("data.mongodb.uri");
            ServerApi serverApi = ServerApi.builder()
                    .version(ServerApiVersion.V1)
                    .build();
            PojoCodecProvider codecProvider = PojoCodecProvider.builder()
                    .register(
                            Account.class,
                            Order.class,
                            Item.class,
                            Product.class
                    )
                    .build();
            CodecRegistry registry = CodecRegistries.fromRegistries(
                    MongoClientSettings.getDefaultCodecRegistry(),
                    CodecRegistries.fromProviders(codecProvider)
            );
            MongoClientSettings settings = MongoClientSettings.builder()
                    .serverApi(serverApi)
                    .codecRegistry(registry)
                    .applyConnectionString(new ConnectionString(URI))
                    .build();
            return MongoClients.create(settings);
        } catch (MongoException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

}
