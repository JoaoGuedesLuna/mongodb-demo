package dev.guedes.mongodbdemo.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.bson.codecs.pojo.annotations.BsonProperty;
import java.math.BigDecimal;

/**
 * @author João Guedes
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Item {

    int quantity;

    @BsonProperty("unit_price")
    BigDecimal unitPrice;

    Product product;

}
