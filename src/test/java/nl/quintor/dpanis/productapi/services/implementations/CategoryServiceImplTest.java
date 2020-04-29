package nl.quintor.dpanis.productapi.services.implementations;

import nl.quintor.dpanis.productapi.entities.Category;
import nl.quintor.dpanis.productapi.exceptions.CategoryExistsException;
import nl.quintor.dpanis.productapi.exceptions.NotFoundException;
import nl.quintor.dpanis.productapi.models.CategoryModel;
import nl.quintor.dpanis.productapi.repositories.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {

    @Mock
    CategoryRepository repository;

    CategoryServiceImpl service;

    @BeforeEach
    void setup() {
        service = new CategoryServiceImpl(repository);
    }

    @Test
    void testCreateSuccess() {
        when(repository.findByName(any())).thenReturn(Optional.empty());
        CategoryModel model = defaultModel();
        Category category = service.create(model);
        assertNotNull(category);
        assertEquals(model.getName(), category.getName());
        assertEquals(model.getDescription(), category.getDescription());
        verify(repository, times(1)).findByName(model.getName());
        verify(repository, times(1)).save(any());
    }

    @Test
    void testCreateNullModel() {
        assertThrows(NullPointerException.class, () -> service.create(null));
        verify(repository, times(0)).findByName(any());
        verify(repository, times(0)).save(any());
    }

    @Test
    void testCreateNameExists() {
        when(repository.findByName(any())).thenReturn(Optional.of(new Category()));
        assertThrows(CategoryExistsException.class, () -> service.create(defaultModel()));
        verify(repository, times(1)).findByName(any());
        verify(repository, times(0)).save(any());
    }

    @Test
    void testUpdateNewValuesSuccess() {
        when(repository.findById(any())).thenReturn(Optional.of(new Category("", "", null)));
        when(repository.findByName(any())).thenReturn(Optional.empty());
        CategoryModel model = defaultModel();
        Category category = service.update(1L, model);
        assertNotNull(category);
        assertEquals(model.getName(), category.getName());
        assertEquals(model.getDescription(), category.getDescription());
        verify(repository, times(1)).findById(any());
        verify(repository, times(1)).findByName(any());
        verify(repository, times(1)).save(any());
    }

    @Test
    void testUpdateSameValues() {
        CategoryModel model = defaultModel();
        when(repository.findById(any())).thenReturn(Optional.of(new Category(model.getName(), model.getDescription(), null)));
        when(repository.findByName(any())).thenReturn(Optional.empty());

    }

    @Test
    void testDeleteSuccess() {
        when(repository.findById(any())).thenReturn(Optional.of(new Category()));
        assertNotNull(service.delete(1L));
        verify(repository, times(1)).findById(any());
        verify(repository, times(1)).delete(any());
    }

    @Test
    void testDeleteNullId() {
        assertThrows(NullPointerException.class, () -> service.delete(null));
        verify(repository, times(0)).findById(any());
        verify(repository, times(0)).delete(any());
    }

    @Test
    void testDeleteNotExists() {
        when(repository.findById(any())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> service.delete(1L));
        verify(repository, times(1)).findById(any());
        verify(repository, times(0)).delete(any());
    }

    private CategoryModel defaultModel() {
        CategoryModel model = new CategoryModel();
        model.setName("Test Category");
        model.setDescription("Test category description");
        return model;
    }
}