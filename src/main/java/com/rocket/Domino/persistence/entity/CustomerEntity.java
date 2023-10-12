package com.rocket.Domino.persistence.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name ="customer")
@Getter
@Setter
@NoArgsConstructor
public class CustomerEntity {

    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_customer",nullable = false,length = 15)
    private String idCustomer;

    @Column(nullable = false,length = 60)
    private String name;

    @Column(nullable = false,length = 100)
    private String address;

    @Column(nullable = false,unique = true,length = 50)
    private String email;

    @Column(nullable = false,length = 20)
    private String phoneNumber;
}