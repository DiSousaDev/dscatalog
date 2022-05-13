package br.dev.diego.dscatalog.services;

import br.dev.diego.dscatalog.controllers.dto.ProductDto;
import br.dev.diego.dscatalog.repositories.ProductRepository;
import br.dev.diego.dscatalog.services.exceptions.DataNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
class ProducrServiceIT {

    @Autowired
    private ProductService service;

    @Autowired
    private ProductRepository repository;

    private Long existingId;
    private Long nonExistingId;
    private Long countTotalProducts;

    @BeforeEach
    void setUp() {
        existingId= 1L;
        nonExistingId= 1000L;
        countTotalProducts= 25L;
    }

    @Test
    void findAllPagedShouldReturnPageWhenPage0Size10() {

        Pageable pageable = PageRequest.of(0,10);
        Page<ProductDto> result = service.findAllPaged(pageable);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(0, result.getNumber());
        assertEquals(10, result.getSize());
        assertEquals(countTotalProducts, result.getTotalElements());

    }

    @Test
    void findAllPagedShouldReturnEmptyPageWhenPageDoesntExist() {

        Pageable pageable = PageRequest.of(50,10);
        Page<ProductDto> result = service.findAllPaged(pageable);

        assertNotNull(result);
        assertTrue(result.isEmpty());

    }

    @Test
    void findAllPagedShouldReturnSortedPageWhenSortByName() {

        Pageable pageable = PageRequest.of(0,10, Sort.by("name"));
        Page<ProductDto> result = service.findAllPaged(pageable);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals("Macbook Pro", result.getContent().get(0).getName());
        assertEquals("PC Gamer", result.getContent().get(1).getName());
        assertEquals("PC Gamer Alfa", result.getContent().get(2).getName());

    }


    @Test
    void deleteShouldDeleteResourceWhenIdExists() {

        service.deleteById(existingId);
        assertEquals(countTotalProducts - 1, repository.count());

    }

    @Test
    void deleteShouldThrowDataNotFoundExceptionWhenIdDoesntExists() {

        assertThrows(DataNotFoundException.class, () -> {
            service.deleteById(nonExistingId);
        });

    }

}
