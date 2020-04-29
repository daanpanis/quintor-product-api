package nl.quintor.dpanis.productapi.services.implementations;

import com.google.common.base.Preconditions;
import nl.quintor.dpanis.productapi.entities.Category;
import nl.quintor.dpanis.productapi.exceptions.CategoryExistsException;
import nl.quintor.dpanis.productapi.exceptions.NotFoundException;
import nl.quintor.dpanis.productapi.models.CategoryModel;
import nl.quintor.dpanis.productapi.repositories.CategoryRepository;
import nl.quintor.dpanis.productapi.services.CategoryService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository repository;

    public CategoryServiceImpl(CategoryRepository repository) {
        this.repository = repository;
    }

    @Override
    public Category byId(Long id) {
        return null;
    }

    @Override
    public Category create(CategoryModel model) {
        Preconditions.checkNotNull(model);
        if (repository.findByName(model.getName()).isPresent()) {
            throw new CategoryExistsException("a category with this name already exists");
        }

        Category category = new Category();
        category.setName(model.getName());
        category.setDescription(model.getDescription());
        repository.save(category);
        return category;
    }

    @Override
    public Category update(Long id, CategoryModel model) {
        Preconditions.checkNotNull(id);
        Preconditions.checkNotNull(model);
        Category category = checkCategoryExists(id);
        if (category.getName().equals(model.getName()) && category.getDescription().equals(model.getDescription())) {
            return category;
        }

        if (!category.getName().equals(model.getName()) && repository.findByName(model.getName()).isPresent()) {
            throw new CategoryExistsException("a category with this name already exists");
        }
        category.setName(model.getName());
        category.setDescription(model.getDescription());
        repository.save(category);
        return category;
    }

    @Override
    public Category delete(Long id) {
        Preconditions.checkNotNull(id);
        Category category = checkCategoryExists(id);
        repository.delete(category);
        return category;
    }

    private Category checkCategoryExists(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("No category found by this id"));
    }
}
