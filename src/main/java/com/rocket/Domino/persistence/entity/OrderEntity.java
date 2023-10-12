package com.rocket.Domino.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "pizza_order")
@Getter
@Setter
@NoArgsConstructor
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_order",nullable = false)
    private Integer idOrder;

    @Column(name = "id_customer",nullable = false,length = 15)
    private String idCustomer;

    @Column(nullable = false,columnDefinition = "TIMESTAMP")
    private LocalDateTime date;

    @Column(nullable = false,columnDefinition = "DECIMAL(6,2)")
    private Double total;

    @Column(nullable = false,columnDefinition = "CHAR(1)")
    private String method;

    @Column(name = "additional_notes", length = 200)
    private String additionalNotes;

    /*
        OneToMany: LAZY
        ManyToOne: EAGER
        ManyToMany: LAZY
        OneToOne: EAGER
    */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_customer",referencedColumnName = "id_customer",insertable = false,updatable = false)
    //En este caso ocultamos los datos del customer usando lazy
    @JsonIgnore
    private CustomerEntity customer;
    //EAGER se sube con la clase
    @OneToMany(mappedBy = "order",fetch = FetchType.EAGER)
    private List<OrderItemEntity> items;
}