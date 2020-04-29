package nl.quintor.dpanis.productapi.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nl.quintor.dpanis.productapi.validation.FieldsValueMatch;

import javax.validation.constraints.*;

@AllArgsConstructor
@NoArgsConstructor
@FieldsValueMatch(
        field = "password",
        fieldMatch = "confirmPassword",
        message = "Passwords don't match"
)
@Getter
@Setter
public class RegistrationModel {

    @NotNull
    @Size(min = 2, max = 50)
    private String firstName;

    @NotNull
    @Size(min = 2, max = 50)
    private String lastName;

    @NotNull
    @NotBlank
    @Email
    private String email;

    @NotNull
    @Pattern(
            regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$",
            message = "Password must be at least 8 characters and must container a letter, a number and a special character"
    )
    private String password;

    @NotNull
    private String confirmPassword;
}
