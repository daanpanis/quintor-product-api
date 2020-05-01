package nl.quintor.dpanis.productapi.repositories;

import nl.quintor.dpanis.productapi.entities.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, CartItem.CartItemId> {

    @Query
    Collection<CartItem> findByIdUserId(Long userId);

    @Query
    Optional<CartItem> findByIdUserIdAndIdProductId(Long userId, Long productId);

}
