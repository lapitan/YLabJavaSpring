package com.edu.ulab.app.entity;


import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Set;


@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "person", schema = "ulab_edu")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence")
    @SequenceGenerator(name = "sequence", sequenceName = "sequence", allocationSize = 100)
    private Long id;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private int age;

//    @Column(nullable = false)
//    private int count;

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL)
    private List<Book> bookSet;
}
