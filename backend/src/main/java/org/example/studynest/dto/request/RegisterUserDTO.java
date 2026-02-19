package org.example.studynest.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class RegisterUserDTO {
    @NotBlank
    @Size(min = 3, max = 30,
            message = "Username can be between 3 to 30 characters")
    private String username;

    @NotBlank
    @Size(min = 8,
            message = "Password can not be shorter than 8 characters")
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*\\d).+$",
            message = "Password must contain at least one capital letter and one digit"
    )
    private String password;

    @NotBlank
    @Email(message = "Wrong email format")
    private String email;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
