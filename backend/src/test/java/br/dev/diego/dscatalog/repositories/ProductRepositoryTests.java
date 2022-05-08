package br.dev.diego.dscatalog.repositories;

import br.dev.diego.dscatalog.entities.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ProductRepositoryTests {

    @Autowired
    private ProductRepository repository;

    @Test
    void deleteShouldDeleteObjectWhenIdExists() {
        long existingId = 1L;
        repository.deleteById(existingId);

        Optional<Product> result = repository.findById(existingId);
        assertTrue(result.isEmpty());
    }

}