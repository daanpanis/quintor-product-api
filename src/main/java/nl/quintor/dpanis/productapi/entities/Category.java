package nl.quintor.dpanis.productapi.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Category extends BaseEntity {

    @Column(nullable = false, unique = true)
    @Size(min = 2, max = 100)
    private String name;

    @Column(nullable = false)
    @Size(min = 5, max = 3000)
    private String description;

    @OneToMany(mappedBy = "category")
    @JsonIgnore
    private Set<Product> products;

}
