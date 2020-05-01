package nl.quintor.dpanis.productapi.services.implementations;

import nl.quintor.dpanis.productapi.services.CartService;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService {
    @Override
    public boolean delete(Long userId, Long productId) {
        return false;
    }

    @Override
    public void deleteAll(Long userId, Long productId) {

    }
}
