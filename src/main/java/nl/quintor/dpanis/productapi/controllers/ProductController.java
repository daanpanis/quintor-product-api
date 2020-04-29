package nl.quintor.dpanis.productapi.controllers;

import nl.quintor.dpanis.productapi.entities.Product;
import nl.quintor.dpanis.productapi.exceptions.NotFoundException;
import nl.quintor.dpanis.productapi.models.ProductModel;
import nl.quintor.dpanis.productapi.repositories.ProductRepository;
import nl.quintor.dpanis.productapi.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductRepository productRepository;
    private final ProductService productService;

    @Autowired
    public ProductController(ProductRepository productRepository, ProductService productService) {
        this.productRepository = productRepository;
        this.productService = productService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('product.list')")
    @ResponseStatus(HttpStatus.OK)
    public Iterable<Product> getAll() {
        return this.productRepository.findAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('product.get')")
    @ResponseStatus(HttpStatus.OK)
    public Product getById(@PathVariable Long id) {
        return productService.byId(id);
    }

    @PutMapping()
    @PreAuthorize("hasAuthority('product.create')")
    @ResponseStatus(HttpStatus.CREATED)
    public Product create(@Valid @RequestBody ProductModel model) {
        return productService.create(model);
    }

    @PostMapping("/{id}")
    @PreAuthorize("hasAuthority('product.update')")
    @ResponseStatus(HttpStatus.OK)
    public Product update(@PathVariable Long id, @Valid @RequestBody ProductModel model) {
        return productService.update(id, model);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('product.delete')")
    @ResponseStatus(HttpStatus.OK)
    public Product delete(@PathVariable Long id) {
        return productService.delete(id);
    }
}
