package product;

import common.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
@ToString(callSuper = true)
public class Product extends BaseEntity<UUID> implements Serializable {
    private String model;
    private String name;
    private UUID categories_id;
    private double price;
    private String description;
    private Status status;

    public Product(UUID uuid, String model, String name, UUID categories_id, double price, String description, Status status) {
        super(uuid);
        this.model = model;
        this.name = name;
        this.categories_id = categories_id;
        this.price = price;
        this.description = description;
        this.status = status;
    }
}
