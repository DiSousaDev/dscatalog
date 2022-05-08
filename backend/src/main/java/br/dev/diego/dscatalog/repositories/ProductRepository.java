package br.dev.diego.dscatalog.repositories;

import br.dev.diego.dscatalog.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
