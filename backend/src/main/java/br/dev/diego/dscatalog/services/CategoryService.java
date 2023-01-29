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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoryService {

    public static final String ENTITY = " entity: ";
    @Autowired
    CategoryRepository repository;

    @Transactional(readOnly = true)
    public Page<CategoryDto> findAllPaged(Pageable pageable) {
        return repository.findAll(pageable).map(CategoryDto::new);
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

    public void deleteById(Long id) {
        try{
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new DataNotFoundException("Categoria não encontrada id: " + id + ENTITY + Category.class.getName());
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Impossivel excluir. Possui entidades relacionadas id: " + id + ENTITY + Category.class.getName());
        }

    }

    private Category findCategoryById(Long id) {
        return repository.findById(id).orElseThrow(() -> new DataNotFoundException(
                "Categoria não encontrada id: " + id + ENTITY + Category.class.getName()));
    }

    private void updateCategory(Category cat, CategoryUpdateDto catUpdate) {
        cat.setName(catUpdate.getName());
    }

}
