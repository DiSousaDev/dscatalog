package br.dev.diego.dscatalog.repositories;

import br.dev.diego.dscatalog.entities.Product;
import br.dev.diego.dscatalog.mother.ProductMother;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ProductRepositoryTests {

    @Autowired
    private ProductRepository repository;

    private long existingId;
    private long nonExistingId;
    private long countTotalProducts;

    @BeforeEach
    void setUp() {
        existingId = 1L;
        nonExistingId = 1000L;
        countTotalProducts = 25L;
    }

    @Test
    void deleteShouldDeleteObjectWhenIdExists() {

        repository.deleteById(existingId);

        Optional<Product> result = repository.findById(existingId);
        assertTrue(result.isEmpty());
    }

    @Test
    void deleteShouldThrowEmptyResultDataAccessExceptionWhenIdDoesNotExist() {

        assertThrows(EmptyResultDataAccessException.class, () -> {
            repository.deleteById(nonExistingId);
        });
    }

    @Test
    void saveShouldPersistWithAutoIncrementWhenIdIsNull() {
        Product product = ProductMother.getNewProduct();
        product.setId(null);
        Product newProduct = repository.save(product);
        assertNotNull(newProduct);
        assertEquals(countTotalProducts + 1, newProduct.getId());
    }

    @Test
    void findByIdShouldReturnNonEmptyOptionalWhenIdExists() {
        Optional<Product> product = repository.findById(existingId);
        assertTrue(product.isPresent());
        assertEquals(existingId, product.get().getId());
    }

    @Test
    void findByIdShouldReturnEmptyOptionalWhenIdDoesNotExists() {
        Optional<Product> product = repository.findById(nonExistingId);
        assertTrue(product.isEmpty());
    }

}