package br.dev.diego.dscatalog.services;

import br.dev.diego.dscatalog.controllers.dto.CategoryDto;
import br.dev.diego.dscatalog.controllers.dto.CategoryInsertDto;
import br.dev.diego.dscatalog.entities.Category;
import br.dev.diego.dscatalog.repositories.CategoryRepository;
import br.dev.diego.dscatalog.services.exceptions.DataNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
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
        return new CategoryDto(repository.findById(id).orElseThrow(() -> new DataNotFoundException(
                "Categoria não encontrada id: " + id + " entity: " + Category.class.getName())));
    }

    @Transactional
    public CategoryDto save(CategoryInsertDto category) {
        return new CategoryDto(repository.save(new Category(null, category.getName())));
    }


}
