package nl.quintor.dpanis.productapi.services.implementations;

import com.google.common.base.Preconditions;
import nl.quintor.dpanis.productapi.entities.Category;
import nl.quintor.dpanis.productapi.entities.Product;
import nl.quintor.dpanis.productapi.exceptions.NotFoundException;
import nl.quintor.dpanis.productapi.exceptions.ProductExistsException;
import nl.quintor.dpanis.productapi.models.ProductModel;
import nl.quintor.dpanis.productapi.repositories.CategoryRepository;
import nl.quintor.dpanis.productapi.repositories.ProductRepository;
import nl.quintor.dpanis.productapi.services.ProductService;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

@Service
@Primary
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Product byId(Long id) {
        Preconditions.checkNotNull(id);
        return checkProductExists(id);
    }

    @Override
    public Product create(ProductModel model) {
        Preconditions.checkNotNull(model);
        checkProductName(model.getName());

        Product product = new Product();
        product.setCategory(checkCategoryExists(model.getCategory()));
        modelToProduct(model, product);
        productRepository.save(product);
        return product;
    }

    @Override
    public Product update(Long id, ProductModel model) {
        Preconditions.checkNotNull(id);
        Preconditions.checkNotNull(model);
        Product product = checkProductExists(id);
        if (!product.getName().equals(model.getName())) {
            checkProductName(model.getName());
        }
        if (!product.getCategory().getId().equals(model.getCategory())) {
            product.setCategory(checkCategoryExists(id));
        }
        modelToProduct(model, product);
        productRepository.save(product);
        return product;
    }

    @Override
    public Product delete(Long id) {
        Preconditions.checkNotNull(id);
        Product product = checkProductExists(id);
        productRepository.delete(product);
        return product;
    }

    private void checkProductName(String name) {
        if (productRepository.findByName(name).isPresent()) {
            throw new ProductExistsException("A product by this name already exists");
        }
    }

    private Product checkProductExists(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new NotFoundException("No product found by that id"));
    }

    private Category checkCategoryExists(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new NotFoundException("No category found by that id"));
    }

    private void modelToProduct(ProductModel model, Product product) {
        product.setName(model.getName());
        product.setDescription(model.getDescription());
        product.setStock(model.getStock());
        product.setPrice(model.getPrice());
    }
}
