package nl.quintor.dpanis.productapi.repositories;

import nl.quintor.dpanis.productapi.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    @Query
    User findByEmail(String email);

}
