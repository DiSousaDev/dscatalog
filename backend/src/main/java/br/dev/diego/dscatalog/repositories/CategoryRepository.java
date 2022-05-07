package br.dev.diego.dscatalog.repositories;

import br.dev.diego.dscatalog.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
