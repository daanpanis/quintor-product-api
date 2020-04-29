package nl.quintor.dpanis.productapi.services;

import nl.quintor.dpanis.productapi.entities.Product;
import nl.quintor.dpanis.productapi.models.ProductModel;
import org.springframework.stereotype.Service;

@Service
public interface ProductService {

    Product byId(Long id);

    Product create(ProductModel model);

    Product update(Long id, ProductModel model);

    Product delete(Long id);

}
