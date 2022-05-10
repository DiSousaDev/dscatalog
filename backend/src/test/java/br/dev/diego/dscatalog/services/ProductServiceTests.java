package br.dev.diego.dscatalog.services;

import br.dev.diego.dscatalog.controllers.dto.ProductDto;
import br.dev.diego.dscatalog.controllers.dto.ProductInsertDto;
import br.dev.diego.dscatalog.controllers.dto.ProductUpdateDto;
import br.dev.diego.dscatalog.entities.Product;
import br.dev.diego.dscatalog.mother.ProductMother;
import br.dev.diego.dscatalog.repositories.ProductRepository;
import br.dev.diego.dscatalog.services.exceptions.DataNotFoundException;
import br.dev.diego.dscatalog.services.exceptions.DatabaseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class ProductServiceTests {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    private long existingId;
    private long nonExistingId;
    private long productWithEntity;
    private PageImpl<Product> page;
    private Product product;

    @BeforeEach
    void setUp() {
        existingId = 1L;
        nonExistingId = 1000L;
        productWithEntity = 2L;
        product = ProductMother.getProduct();
        page = new PageImpl<>(List.of(product));

        when(productRepository.findAll(any(Pageable.class))).thenReturn(page);
        when(productRepository.save(any())).thenReturn(product);
        when(productRepository.findById(existingId)).thenReturn(Optional.of(product));
        when(productRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        doNothing().when(productRepository).deleteById(existingId);
        doThrow(EmptyResultDataAccessException.class).when(productRepository).deleteById(nonExistingId);
        doThrow(DataIntegrityViolationException.class).when(productRepository).deleteById(productWithEntity);

    }

    @Test
    void findAllPagedShouldReturnPage() {

        Pageable pageable = PageRequest.of(0,10);
        Page<ProductDto> result = productService.findAllPaged(pageable);

        assertNotNull(result);
        verify(productRepository).findAll(pageable);

    }

    @Test
    void findByIdShouldReturnProductDtoWhenIdExists() {

        ProductDto productDto = productService.findById(existingId);
        assertNotNull(productDto);
        verify(productRepository).findById(existingId);

    }

    @Test
    void findByIdShouldThrowDataNotFoundExceptionWhenIdDoesntExists() {

        assertThrows(DataNotFoundException.class, () -> {
            productService.findById(nonExistingId);
        });

        verify(productRepository).findById(nonExistingId);

    }

    @Test
    void updateShouldReturnProductDtoWhenIdExists() {

        ProductDto productDto = productService.update(existingId, ProductMother.getProductUpdatedDto());
        assertNotNull(productDto);

        verify(productRepository).findById(existingId);
        verify(productRepository).save(any());

    }

    @Test
    void updateShouldThrowDataNotFoundExceptionWhenIdDoesntExists() {

        assertThrows(DataNotFoundException.class, () -> {
            productService.update(nonExistingId, ProductMother.getProductUpdatedDto());
        });

        verify(productRepository).findById(nonExistingId);

    }

    @Test
    void deleteShouldNothingWhenIdExists() {

        assertDoesNotThrow(() -> {
            productService.deleteById(existingId);
        });
        verify(productRepository, times(1)).deleteById(existingId);

    }

    @Test
    void deleteShouldThrowDataNotFoundExceptionWhenIdDoesntExists() {

        assertThrows(DataNotFoundException.class, () -> {
            productService.deleteById(nonExistingId);
        });

        verify(productRepository, times(1)).deleteById(nonExistingId);

    }

    @Test
    void deleteShouldThrowDatabaseExceptionWhenObjectHaveEntityAssociated() {

        assertThrows(DatabaseException.class, () -> {
            productService.deleteById(productWithEntity);
        });

        verify(productRepository, times(1)).deleteById(productWithEntity);

    }

}