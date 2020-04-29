package nl.quintor.dpanis.productapi.services;

import nl.quintor.dpanis.productapi.entities.Category;
import nl.quintor.dpanis.productapi.models.CategoryModel;
import org.springframework.stereotype.Service;

@Service
public interface CategoryService {

    Category byId(Long id);

    Category create(CategoryModel model);

    Category update(Long id, CategoryModel model);

    Category delete(Long id);
}
