package com.rocket.Domino.service;

import com.rocket.Domino.persistence.entity.PizzaEntity;
import com.rocket.Domino.persistence.repository.PizzaPagSortRepository;
import com.rocket.Domino.persistence.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PizzaService {
    private final PizzaRepository pizzaRepository;
    private final PizzaPagSortRepository pizzaPagSortRepository;

    @Autowired
    public PizzaService(PizzaRepository pizzaRepository, PizzaPagSortRepository pizzaPagSortRepository, JdbcTemplate jdbcTemplate) {
        this.pizzaRepository = pizzaRepository;
        this.pizzaPagSortRepository = pizzaPagSortRepository;
        this.jdbcTemplate = jdbcTemplate;
    }
    public Page<PizzaEntity> getAll(int page ,int element){
        Pageable pageRequest = PageRequest.of(page, element );
        return this.pizzaPagSortRepository.findAll(pageRequest);

    }

    public Page<PizzaEntity> getAvalible(int page , int element, String sortBy, String sortDirection){
        System.out.println(this.pizzaRepository.countByVeganTrue());
        //ordenamiento con direccion asc /desc
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection),sortBy);
        Pageable pageRequest = PageRequest.of(page, element, sort);
        //ordenamiento basico con Sort.by
        //Pageable pageRequest = PageRequest.of(page, element, Sort.by(sortBy));

        return this.pizzaPagSortRepository.findByAvailableTrue(pageRequest);
    }
    public PizzaEntity get(int idPizza){
        return this.pizzaRepository.findById(idPizza).orElse(null);
    }
    public PizzaEntity getName(String name){
        return this.pizzaRepository.findFirstByAvailableTrueAndNameIgnoreCase(name).orElseThrow(() -> new RuntimeException("La pizza no existe"));
    }
    public List<PizzaEntity> getDescriptionContein(String ingrediente){
        return this.pizzaRepository.findAllByAvailableTrueAndDescriptionContainingIgnoreCase(ingrediente);
    }
    public List<PizzaEntity> getDescriptionNotContein(String ingrediente){
        return this.pizzaRepository.findAllByAvailableTrueAndDescriptionNotContainingIgnoreCase(ingrediente);
    }
    public List<PizzaEntity> getCheapest(double price){
        return this.pizzaRepository.findTop3ByAvailableTrueAndPriceLessThanEqualOrderByPriceAsc(price);
    }

    public PizzaEntity save(PizzaEntity pizzaEntity){
        return this.pizzaRepository.save(pizzaEntity);
    }

    public void delete(int idPizza){
        this.pizzaRepository.deleteById(idPizza);
    }

    public boolean exists(int idPizza){
        return this.pizzaRepository.existsById(idPizza);
    }





   // Utilizando jdbctemplate para los query de busqueda

   private final JdbcTemplate jdbcTemplate;

    public List<PizzaEntity>  getAvailablePizzasOrderedByPrice() {
        String sql = "SELECT * FROM pizza WHERE available = 1 ORDER BY price";
        List<PizzaEntity> pizzas = jdbcTemplate.query(
                sql,
                new BeanPropertyRowMapper<>(PizzaEntity.class)
        );
        return pizzas;
    }
    public List<PizzaEntity>  getAvailablePizzaDescriptionContein(String ingrediente) {
        String sql = "SELECT * FROM pizza WHERE available = 1 AND upper(description) LIKE ?";
        List<PizzaEntity> pizzas = jdbcTemplate.query(
                sql,
                new Object[]{"%" + ingrediente.toUpperCase() + "%"},
                new BeanPropertyRowMapper<>(PizzaEntity.class)
        );
        return pizzas;
    }
    public List<PizzaEntity>  getAvailablePizzaDescriptionNotContein(String ingrediente) {
        String sql = "SELECT * FROM pizza WHERE available = 1 AND upper(description) NOT LIKE ?";
        List<PizzaEntity> pizzas = jdbcTemplate.query(
                sql,
                new Object[]{"%" + ingrediente.toUpperCase() + "%"},
                new BeanPropertyRowMapper<>(PizzaEntity.class)
        );
        return pizzas;
    }
    public List<PizzaEntity>  getCheapestJDBC(double price) {
        String sql = "SELECT * FROM pizza WHERE available = 1 AND price <= ? ORDER BY price ASC LIMIT 3";
        List<PizzaEntity> pizzas = jdbcTemplate.query(
                sql,
                new Object[]{price},
                new BeanPropertyRowMapper<>(PizzaEntity.class)
        );
        return pizzas;
    }
    public PizzaEntity  getNameJDBC(String name) {
        PizzaEntity pizza = null;
        try {
            String sql = "SELECT * FROM pizza WHERE upper(name) = upper(?)";
            pizza = jdbcTemplate.queryForObject(
                    sql,
                    new Object[]{name},
                    new BeanPropertyRowMapper<>(PizzaEntity.class)
            );
            return pizza;
        } catch (Exception e) {
            System.out.println("la pizza no existe");
            e =new RuntimeException("no existe pizza");
            e.printStackTrace();
            return pizza;
        }


    }

    public void insert(PizzaEntity pizzaEntity) {
        String sql = "INSERT INTO pizza (available, description, name, price, vegan, vegetarian) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(
                sql,
                pizzaEntity.getAvailable() ? 1 : 0, // Convierte el valor booleano a 1 o 0
                pizzaEntity.getDescription(),
                pizzaEntity.getName(),
                pizzaEntity.getPrice(),
                pizzaEntity.getVegan() ? 1 : 0, // Convierte el valor booleano a 1 o 0
                pizzaEntity.getVegetarian() ? 1 : 0 // Convierte el valor booleano a 1 o 0
        );
    }

        public void update(PizzaEntity pizzaEntity) {
            String sql = "UPDATE pizza SET available = ?, description = ?, name = ?, price = ?, vegan = ?, vegetarian = ? WHERE id_pizza = ?";
            jdbcTemplate.update(
                    sql,
                    pizzaEntity.getAvailable() ? 1 : 0, // Convierte el valor booleano a 1 o 0
                    pizzaEntity.getDescription(),
                    pizzaEntity.getName(),
                    pizzaEntity.getPrice(),
                    pizzaEntity.getVegan() ? 1 : 0, // Convierte el valor booleano a 1 o 0
                    pizzaEntity.getVegetarian() ? 1 : 0, // Convierte el valor booleano a 1 o 0
                    pizzaEntity.getIdPizza() // Utiliza el ID de la pizza para la condición WHERE
            );
        }


}
