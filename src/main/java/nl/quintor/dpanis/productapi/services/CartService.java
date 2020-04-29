package nl.quintor.dpanis.productapi.services;

import org.springframework.stereotype.Service;

@Service
public interface CartService {

    boolean delete(Long userId, Long productId);

    void deleteAll(Long userId, Long productId);

}
