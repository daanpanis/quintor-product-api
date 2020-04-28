package nl.quintor.dpanis.productapi.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Category extends BaseEntity {

    @OneToMany(mappedBy = "category")
    @JsonIgnore
    private Set<Product> products;

}
