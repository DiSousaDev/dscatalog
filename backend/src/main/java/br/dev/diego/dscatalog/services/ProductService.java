package br.dev.diego.dscatalog.services;

import br.dev.diego.dscatalog.controllers.dto.ProductDto;
import br.dev.diego.dscatalog.controllers.dto.ProductInsertDto;
import br.dev.diego.dscatalog.controllers.dto.ProductUpdateDto;
import br.dev.diego.dscatalog.entities.Category;
import br.dev.diego.dscatalog.entities.Product;
import br.dev.diego.dscatalog.repositories.ProductRepository;
import br.dev.diego.dscatalog.services.exceptions.DataNotFoundException;
import br.dev.diego.dscatalog.services.exceptions.DatabaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    public static final String ENTITY = " entity: ";
    @Autowired
    ProductRepository repository;

    @Transactional(readOnly = true)
    public Page<ProductDto> findAllPaged(Long categoryId, String name, Pageable pageable) {
        List<Category> cat = (categoryId == 0) ? null : List.of(new Category(categoryId, null));
        Page<Product> products = repository.find(cat, name.trim(), pageable);
        repository.findProductsWithCategory(products.getContent());
        return products.map(product -> new ProductDto(product, product.getCategories()));
    }

    @Transactional(readOnly = true)
    public ProductDto findById(Long id) {
        Product product = findProductById(id);
        return new ProductDto(product, product.getCategories());
    }

    @Transactional
    public ProductDto save(ProductInsertDto productInsertDto) {
        Product product = new Product();
        copyDtoToEntity(productInsertDto, product);
        return new ProductDto(repository.save(product));
    }

    @Transactional
    public ProductDto update(Long id, ProductUpdateDto productUpdateDto) {
        Product prod = findProductById(id);
        copyDtoToEntity(productUpdateDto, prod);
        return new ProductDto(repository.save(prod));
    }

    public void deleteById(Long id) {
        try{
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new DataNotFoundException("Produto não encontrado id: " + id + ENTITY + Product.class.getName());
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Impossivel excluir. Possui entidades relacionadas id: " + id + ENTITY + Product.class.getName());
        }

    }

    private Product findProductById(Long id) {
        return repository.findById(id).orElseThrow(() -> new DataNotFoundException(
                "Produto não encontrado id: " + id + ENTITY + Product.class.getName()));
    }

    private void copyDtoToEntity(ProductDto productDto, Product product) {
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setImgUrl(productDto.getImgUrl());
        product.setDate(productDto.getDate());

        product.getCategories().clear();
        product.getCategories().addAll(productDto.getCategories().stream().map(categoryDto -> new Category(categoryDto.getId())).collect(Collectors.toList()));

    }

}
