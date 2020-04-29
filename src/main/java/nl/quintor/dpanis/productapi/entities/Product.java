package nl.quintor.dpanis.productapi.entities;

import lombok.*;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Collection;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Product extends BaseEntity {

    @NotBlank
    @Column(nullable = false, unique = true)
    @Size(min = 5, max = 100)
    private String name;

    @NotBlank
    @Column(nullable = false)
    @Size(min = 5, max = 3000)
    private String description;

    @DecimalMin(value = "0.01")
    @Column(nullable = false, scale = 2)
    private Double price;

    @Column(nullable = false)
    @Min(0)
    private int stock;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Category category;

    @OneToMany(targetEntity = CartItem.class, mappedBy = "product", fetch = FetchType.LAZY)
    private Collection<CartItem> cart;

}
