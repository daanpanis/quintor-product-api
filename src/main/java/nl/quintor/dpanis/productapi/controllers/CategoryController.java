package nl.quintor.dpanis.productapi.controllers;

import nl.quintor.dpanis.productapi.entities.Category;
import nl.quintor.dpanis.productapi.exceptions.NotFoundException;
import nl.quintor.dpanis.productapi.models.CategoryModel;
import nl.quintor.dpanis.productapi.repositories.CategoryRepository;
import nl.quintor.dpanis.productapi.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/category")
public class CategoryController {

    private final CategoryRepository repository;
    private final CategoryService service;

    @Autowired
    public CategoryController(CategoryRepository repository, CategoryService service) {
        this.repository = repository;
        this.service = service;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('category.list')")
    @ResponseStatus(HttpStatus.OK)
    public Iterable<Category> getAll() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('category.get')")
    @ResponseStatus(HttpStatus.OK)
    public Category getById(@PathVariable Long id) {
        return service.byId(id);
    }

    @PutMapping
    @PreAuthorize("hasAuthority('category.create')")
    @ResponseStatus(HttpStatus.CREATED)
    public Category create(@RequestBody @Valid CategoryModel model) {
        return service.create(model);
    }

    @PostMapping("/{id}")
    @PreAuthorize("hasAuthority('category.update')")
    @ResponseStatus(HttpStatus.OK)
    public Category update(@PathVariable Long id, @RequestBody @Valid CategoryModel model) {
        return service.update(id, model);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('category.delete')")
    @ResponseStatus(HttpStatus.OK)
    public Category delete(@PathVariable Long id) {
        return service.delete(id);
    }
}
