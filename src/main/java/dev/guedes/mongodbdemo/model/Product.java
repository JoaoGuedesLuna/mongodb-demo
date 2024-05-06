package dev.guedes.mongodbdemo.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;
import java.math.BigDecimal;

/**
 * @author João Guedes
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Product {

    @BsonId
    ObjectId id;

    String name;

    String description;

    BigDecimal price;

}
