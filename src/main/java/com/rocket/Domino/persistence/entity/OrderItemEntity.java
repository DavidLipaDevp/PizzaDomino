package com.rocket.Domino.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "orden_item")
    @IdClass(OrderItemId.class)
    @Getter
    @Setter
    @NoArgsConstructor
    public class OrderItemEntity {

        @Id
        @Column(name = "id_order",nullable = false)
        private Integer idOrder;

        @Id
        //@GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id_item",nullable = false)
        private Integer idItem;


        @Column(name = "id_pizza",nullable = false)
        private Integer idPizza;

        @Column(nullable = false,columnDefinition = "DECIMAL(2,1)")
        private Double quantity;

        @Column(nullable = false,columnDefinition = "DECIMAL(5,2)")
        private Double price;


        @ManyToOne
        @JoinColumn(name = "id_order",referencedColumnName = "id_order",insertable = false,updatable = false)
        //permite omitir el llamado infinito json ignores
        @JsonIgnore
        private OrderEntity order;

        @OneToOne
        @JoinColumn(name="id_pizza",referencedColumnName = "id_pizza",insertable = false,updatable = false)
        private PizzaEntity pizza;



    }