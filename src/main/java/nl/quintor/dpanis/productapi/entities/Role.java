package nl.quintor.dpanis.productapi.entities;

import lombok.*;

import javax.persistence.*;
import java.util.Collection;

/**
 * Credits go to: https://www.baeldung.com/role-and-privilege-for-spring-security-registration
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Role extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String name;
    @Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean defaultRole;
    @ManyToMany(mappedBy = "roles")
    private Collection<User> users;
    @ManyToMany
    @JoinTable(
            name = "roles_privileges",
            joinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "privilege_id", referencedColumnName = "id"))
    private Collection<Privilege> privileges;

}
