package com.edu.ulab.app.entity;



import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;


@Entity
@Data
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany(mappedBy = "person",cascade = CascadeType.ALL)
    private List<Book> books;

    @NotNull
    @NotBlank
    private String fullName;

    @NotNull
    @NotBlank
    private String title;

    @Min(18)
    private int age;
}
