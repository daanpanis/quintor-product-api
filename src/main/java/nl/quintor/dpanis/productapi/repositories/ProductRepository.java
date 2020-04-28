package nl.quintor.dpanis.productapi.repositories;

import nl.quintor.dpanis.productapi.entities.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductRepository extends CrudRepository<Product, Long> {

    @Query("SELECT p FROM Product p WHERE p.name LIKE %?1%")
    List<Product> searchName(String searchQuery);

}
