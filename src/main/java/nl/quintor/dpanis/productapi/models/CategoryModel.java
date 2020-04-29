package nl.quintor.dpanis.productapi.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CategoryModel {

    @NotNull
    @Size(min = 2, max = 100)
    private String name;

    @NotNull()
    @Size(min = 5, max = 3000)
    private String description;

}
