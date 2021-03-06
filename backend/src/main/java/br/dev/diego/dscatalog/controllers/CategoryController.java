package br.dev.diego.dscatalog.controllers;

import br.dev.diego.dscatalog.controllers.dto.CategoryDto;
import br.dev.diego.dscatalog.controllers.dto.CategoryInsertDto;
import br.dev.diego.dscatalog.controllers.dto.CategoryUpdateDto;
import br.dev.diego.dscatalog.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/categories")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @GetMapping
    public ResponseEntity<Page<CategoryDto>> findAll(Pageable pageable) {
        Page<CategoryDto> page = categoryService.findAllPaged(pageable);
        return ResponseEntity.ok().body(page);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CategoryDto> findById(@PathVariable Long id) {
        CategoryDto obj = categoryService.findById(id);
        return ResponseEntity.ok().body(obj);
    }

    @PostMapping
    public ResponseEntity<CategoryDto> save(@RequestBody CategoryInsertDto category) {
        CategoryDto obj = categoryService.save(category);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).body(obj);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDto> update(@PathVariable Long id, @RequestBody CategoryUpdateDto category) {
        CategoryDto obj = categoryService.update(id, category);
        return ResponseEntity.ok().body(obj);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id){
        categoryService.deleteById(id);
        return ResponseEntity.noContent().build();
    }


}
