package br.dev.diego.dscatalog.services;

import br.dev.diego.dscatalog.controllers.dto.CategoryDto;
import br.dev.diego.dscatalog.controllers.dto.CategoryInsertDto;
import br.dev.diego.dscatalog.controllers.dto.CategoryUpdateDto;
import br.dev.diego.dscatalog.entities.Category;
import br.dev.diego.dscatalog.repositories.CategoryRepository;
import br.dev.diego.dscatalog.services.exceptions.DataNotFoundException;
import br.dev.diego.dscatalog.services.exceptions.DatabaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository repository;

    @Transactional(readOnly = true)
    public List<CategoryDto> findAll() {
        return repository.findAll().stream().map(CategoryDto::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CategoryDto findById(Long id) {
        return new CategoryDto(findCategoryById(id));
    }

    @Transactional
    public CategoryDto save(CategoryInsertDto category) {
        return new CategoryDto(repository.save(new Category(null, category.getName())));
    }

    @Transactional
    public CategoryDto update(Long id, CategoryUpdateDto category) {
        Category cat = findCategoryById(id);
        updateCategory(cat, category);
        return new CategoryDto(cat);
    }

    @Transactional
    public void deleteById(Long id) {
        try{
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new DataNotFoundException("Categoria não encontrada id: " + id + " entity: " + Category.class.getName());
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Impossivel excluir. Possui entidades relacionadas id: " + id + " entity: " + Category.class.getName());
        }

    }

    private Category findCategoryById(Long id) {
        return repository.findById(id).orElseThrow(() -> new DataNotFoundException(
                "Categoria não encontrada id: " + id + " entity: " + Category.class.getName()));
    }

    private void updateCategory(Category cat, CategoryUpdateDto catUpdate) {
        cat.setName(catUpdate.getName());
    }

}
