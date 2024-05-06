package dev.guedes.mongodbdemo.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;

/**
 * @author João Guedes
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Account {

    @BsonId
    ObjectId id;

    String email;

    String password;

}
