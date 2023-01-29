package br.dev.diego.dscatalog.controllers.dto;

import br.dev.diego.dscatalog.entities.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

public class UserDto {

    private Long id;

    @NotBlank(message = "Campo obrigatório.")
    private String firstName;
    private String lastName;

    @Email(message = "E-mail inválido.")
    private String email;

    private final Set<RoleDto> roles = new HashSet<>();

    public UserDto() {
    }

    public UserDto(Long id, String firstName, String lastName, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public UserDto(User entity) {
        id = entity.getId();
        firstName = entity.getFirstName();
        lastName = entity.getLastName();
        email = entity.getEmail();
        entity.getRoles().forEach(role -> this.roles.add(new RoleDto(role.getId(), role.getAuthority())));
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public Set<RoleDto> getRoles() {
        return roles;
    }
}
