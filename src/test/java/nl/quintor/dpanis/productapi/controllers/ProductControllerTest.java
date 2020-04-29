package nl.quintor.dpanis.productapi.controllers;

import nl.quintor.dpanis.productapi.entities.Product;
import nl.quintor.dpanis.productapi.exceptions.NotFoundException;
import nl.quintor.dpanis.productapi.repositories.ProductRepository;
import nl.quintor.dpanis.productapi.services.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @Mock
    ProductRepository repository;

    @Mock
    ProductService service;

    ProductController controller;

    @BeforeEach
    void setup() {
        this.controller = new ProductController(repository, service);
    }

    @Test
    void getAllSuccess() {
        when(repository.findAll()).thenReturn(Arrays.asList(new Product(), new Product()));
        Iterable<Product> products = controller.getAll();
        assertNotNull(products);
        assertTrue(products.iterator().hasNext());
        verify(repository, times(1)).findAll();
    }

    @Test
    void getByIdSuccess() {
        Product product = new Product();
        product.setId(1L);
        when(repository.findById(product.getId())).thenReturn(Optional.of(product));
        Product retrieved = controller.getById(product.getId());
        assertNotNull(retrieved);
        assertEquals(product.getId(), retrieved.getId());
        verify(repository, times(1)).findById(product.getId());
    }

    @Test
    void getByIdNotFound() {
        when(repository.findById(any())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> controller.getById(1L));
        verify(repository, times(1)).findById(any());
    }
}