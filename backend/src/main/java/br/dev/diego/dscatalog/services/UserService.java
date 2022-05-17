package br.dev.diego.dscatalog.services;

import br.dev.diego.dscatalog.controllers.dto.UserDto;
import br.dev.diego.dscatalog.controllers.dto.UserInsertDto;
import br.dev.diego.dscatalog.controllers.dto.UserUpdateDto;
import br.dev.diego.dscatalog.entities.User;
import br.dev.diego.dscatalog.repositories.RoleRepository;
import br.dev.diego.dscatalog.repositories.UserRepository;
import br.dev.diego.dscatalog.services.exceptions.DataNotFoundException;
import br.dev.diego.dscatalog.services.exceptions.DatabaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private static Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    UserRepository repository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) {
        Optional<User> user = repository.findByEmail(email);

        if(user.isEmpty()) {
            logger.error("User not found: " + email);
            throw new UsernameNotFoundException("Email not found.");
        }
        logger.info("User found: " + user);
        return user.get();
    }

    @Transactional(readOnly = true)
    public Page<UserDto> findAllPaged(Pageable pageable) {
        return repository.findAll(pageable).map(UserDto::new);
    }

    @Transactional(readOnly = true)
    public UserDto findById(Long id) {
        User user = findUserById(id);
        return new UserDto(user);
    }

    @Transactional
    public UserDto save(UserInsertDto userInsertDto) {
        User user = new User();
        copyDtoToEntity(userInsertDto, user);
        user = repository.saveAndFlush(user);
        return new UserDto(user);
    }

    @Transactional
    public UserDto update(Long id, UserUpdateDto userUpdateDto) {
        User prod = findUserById(id);
        copyDtoToEntity(userUpdateDto, prod);
        return new UserDto(repository.save(prod));
    }

    public void deleteById(Long id) {
        try{
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new DataNotFoundException("Usuário não encontrado id: " + id + " entity: " + User.class.getName());
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Impossivel excluir. Possui entidades relacionadas id: " + id + " entity: " + User.class.getName());
        }

    }

    private User findUserById(Long id) {
        return repository.findById(id).orElseThrow(() -> new DataNotFoundException(
                "Usuario não encontrado id: " + id + " entity: " + User.class.getName()));
    }

    private void copyDtoToEntity(UserDto userDto, User user) {
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());

        user.getRoles().clear();
        userDto.getRoles().forEach(role -> user.getRoles().add(roleRepository.getById(role.getId())));

        if(userDto instanceof UserInsertDto) {
            user.setPassword(passwordEncoder.encode(((UserInsertDto) userDto).getPassword()));
        }


    }

}
