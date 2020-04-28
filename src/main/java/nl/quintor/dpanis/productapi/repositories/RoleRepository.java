package nl.quintor.dpanis.productapi.repositories;

import nl.quintor.dpanis.productapi.entities.Role;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {

    Role findByName(String name);

    @Query("SELECT r FROM Role r WHERE defaultRole = true")
    Role findDefaultRole();

}
