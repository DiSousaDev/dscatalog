package br.dev.diego.dscatalog.repositories;

import br.dev.diego.dscatalog.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

}
