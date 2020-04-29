package nl.quintor.dpanis.productapi.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductModel {

    @NotNull
    @NotBlank
    @Size(min = 2, max = 100)
    private String name;

    @NotNull
    @NotBlank
    @Size(min = 5, max = 3000)
    private String description;

    @NotNull
    @DecimalMin(value = "0.01", message = "Price must be at least 1 cent")
    @DecimalMax("10000000")
    private Double price;

    @NotNull
    @Min(0)
    private int stock;

    @NotNull
    private Long category;

}
