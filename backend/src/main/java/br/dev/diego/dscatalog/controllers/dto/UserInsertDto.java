package br.dev.diego.dscatalog.controllers.dto;

import br.dev.diego.dscatalog.services.validation.UserInsertValid;

@UserInsertValid
public class UserInsertDto extends UserDto {

    private String password;

    public UserInsertDto() {
        super();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
