package nl.quintor.dpanis.productapi.entities;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    @Column(nullable = false, unique = true)
    @Size(min = 5, max = 50)
    private String name;

    @NotBlank
    @Column(nullable = false)
    @Size(min = 5, max = 500)
    private String description;

    @Column(nullable = false)
    @Min(0)
    private int stock;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Category category;

}
