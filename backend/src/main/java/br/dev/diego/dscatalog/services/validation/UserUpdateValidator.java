package br.dev.diego.dscatalog.services.validation;

import br.dev.diego.dscatalog.controllers.dto.UserUpdateDto;
import br.dev.diego.dscatalog.controllers.exceptions.CustomFieldError;
import br.dev.diego.dscatalog.entities.User;
import br.dev.diego.dscatalog.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserUpdateValidator implements ConstraintValidator<UserUpdateValid, UserUpdateDto> {

    @Autowired
    private UserRepository repository;
    
    @Autowired
    private HttpServletRequest req;

    @Override
    public void initialize(UserUpdateValid ann) {
        ConstraintValidator.super.initialize(ann);
    }

    @Override
    public boolean isValid(UserUpdateDto dto, ConstraintValidatorContext context) {

        List<CustomFieldError> list = new ArrayList<>();
        String[] requestUri = req.getRequestURI().split("/");
        int id = Integer.parseInt(requestUri[2]);

        Optional<User> user = repository.findByEmail(dto.getEmail());
        if(user.isPresent() && id != user.get().getId()) {
            list.add(new CustomFieldError("email", "Email already in use."));
        }


        for (CustomFieldError cfe : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(cfe.getFieldMessage()).addPropertyNode(cfe.getFieldName()).addConstraintViolation();
        }

        return list.isEmpty();
    }
}
