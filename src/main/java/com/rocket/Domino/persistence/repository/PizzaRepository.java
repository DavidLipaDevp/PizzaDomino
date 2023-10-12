package com.rocket.Domino.persistence.repository;

import com.rocket.Domino.persistence.entity.PizzaEntity;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface PizzaRepository extends ListCrudRepository<PizzaEntity, Integer> {
    List<PizzaEntity> findAllByAvailableTrueOrderByPrice();
    PizzaEntity findAllByAvailableTrueAndNameIgnoreCase(String name);
    List<PizzaEntity> findAllByAvailableTrueAndDescriptionContainingIgnoreCase(String ingrediente);
    List<PizzaEntity> findAllByAvailableTrueAndDescriptionNotContainingIgnoreCase(String ingrediente);
    int countByVeganTrue();

}
