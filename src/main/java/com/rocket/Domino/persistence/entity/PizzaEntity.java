package com.rocket.Domino.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "pizza")
@Getter
@Setter
@NoArgsConstructor
public class PizzaEntity {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name = "id_pizza",nullable = false)
    private Integer idPizza;

    @Column(nullable = false,length = 30,unique = true)
    private String name;

    @Column(nullable = false,length = 150)
    private String description;

    @Column(nullable = false,columnDefinition = "Decimal(5,2)")
    private Double price;

    @Column(columnDefinition = "SMALLINT")
    private Boolean vegetarian;
    @Column(columnDefinition = "SMALLINT")
    private Boolean vegan;
    @Column(columnDefinition = "SMALLINT",nullable = false)
    private Boolean available;

}