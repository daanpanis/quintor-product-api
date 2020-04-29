package nl.quintor.dpanis.productapi.entities;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Table(
        indexes = {
                @Index(name = "CART_ITEM_USER_PRODUCT_INDEX", unique = true, columnList = "user_id,product_id"),
                @Index(name = "CART_ITEM_USER_INDEX", columnList = "user_id")
        }
)

public class CartItem extends BaseEntity {

    @ManyToOne
    @Column(name = "user_id")
    private User user;
    @ManyToOne
    @Column(name = "product_id")
    private Product product;

    @Min(0)
    private int amount;

}
