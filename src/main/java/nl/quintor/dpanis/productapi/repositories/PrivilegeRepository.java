package nl.quintor.dpanis.productapi.repositories;

import nl.quintor.dpanis.productapi.entities.Privilege;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrivilegeRepository extends CrudRepository<Privilege, Long> {

    Privilege findByName(String name);
}
