package nl.quintor.dpanis.productapi.services;

import nl.quintor.dpanis.productapi.entities.CartItem;
import org.springframework.data.repository.CrudRepository;

public interface CartItemRepository extends CrudRepository<CartItem, Long> {



}
