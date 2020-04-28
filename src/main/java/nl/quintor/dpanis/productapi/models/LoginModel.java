package nl.quintor.dpanis.productapi.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoginModel {

    @NotNull
    @Email
    private String email;
    @NotNull
    @Min(1)
    private String password;

}
