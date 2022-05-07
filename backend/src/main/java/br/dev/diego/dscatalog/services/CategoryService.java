package br.dev.diego.dscatalog.services;

import br.dev.diego.dscatalog.controllers.dto.CategoryDto;
import br.dev.diego.dscatalog.repositories.CategoryRepository;
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

}
