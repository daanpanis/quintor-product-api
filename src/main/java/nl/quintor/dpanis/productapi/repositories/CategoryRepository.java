package nl.quintor.dpanis.productapi.repositories;

import nl.quintor.dpanis.productapi.entities.Category;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Long> {

    @Query
    Optional<Category> findByName(String name);

}
