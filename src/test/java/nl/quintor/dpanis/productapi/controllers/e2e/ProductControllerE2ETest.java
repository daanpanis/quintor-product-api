package nl.quintor.dpanis.productapi.controllers.e2e;

import nl.quintor.dpanis.productapi.repositories.ProductRepository;
import nl.quintor.dpanis.productapi.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductControllerE2ETest {

    @Autowired
    ProductService service;
    @Autowired
    ProductRepository repository;

}
