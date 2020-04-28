package nl.quintor.dpanis.productapi.entities;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.Collection;
import java.util.Set;

/**
 * Credits go to: https://www.baeldung.com/role-and-privilege-for-spring-security-registration
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Privilege extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String name;

    @ManyToMany(mappedBy = "privileges")
    private Set<Role> roles;
}
