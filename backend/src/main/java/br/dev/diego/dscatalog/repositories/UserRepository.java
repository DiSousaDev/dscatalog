package br.dev.diego.dscatalog.repositories;

import br.dev.diego.dscatalog.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
