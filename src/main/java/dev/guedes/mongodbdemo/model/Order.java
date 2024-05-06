package dev.guedes.mongodbdemo.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author João Guedes
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Order {

    @BsonId
    ObjectId id;

    LocalDateTime date;

    List<Item> items;

    @BsonProperty("account_id")
    ObjectId accountId;

}
