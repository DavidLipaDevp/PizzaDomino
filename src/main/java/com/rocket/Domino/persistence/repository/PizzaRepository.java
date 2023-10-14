package com.rocket.Domino.persistence.repository;

import com.rocket.Domino.persistence.entity.PizzaEntity;
import com.rocket.Domino.service.dto.UpdatePizzaPriceDto;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PizzaRepository extends ListCrudRepository<PizzaEntity, Integer> {
    List<PizzaEntity> findAllByAvailableTrueOrderByPrice();
    //find ALL/TOP/FIRST
    Optional<PizzaEntity> findFirstByAvailableTrueAndNameIgnoreCase(String name);
    List<PizzaEntity> findAllByAvailableTrueAndDescriptionContainingIgnoreCase(String ingrediente);
    List<PizzaEntity> findAllByAvailableTrueAndDescriptionNotContainingIgnoreCase(String ingrediente);
   List<PizzaEntity> findTop3ByAvailableTrueAndPriceLessThanEqualOrderByPriceAsc(double price);
    int countByVeganTrue();

    //Uso de @Modifying para ejecutar una query de tipo update o delete, usar transaccional en el metodo del servicio
    @Query(value = "UPDATE pizza "+
            "SET price = :#{#newPizzaPrice.newPrice} "+
            "WHERE id_pizza = :#{#newPizzaPrice.pizzaId}",nativeQuery = true
    )
    @Modifying
    //llamamos al DTO
    void updatePrice(@Param("newPizzaPrice")UpdatePizzaPriceDto newPizzaPrice);


}
