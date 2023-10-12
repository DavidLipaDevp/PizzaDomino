package com.rocket.Domino.service;

import com.rocket.Domino.persistence.entity.PizzaEntity;
import com.rocket.Domino.persistence.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PizzaService {
    private final PizzaRepository pizzaRepository;

    @Autowired
    public PizzaService(PizzaRepository pizzaRepository, JdbcTemplate jdbcTemplate) {
        this.pizzaRepository = pizzaRepository;
        this.jdbcTemplate = jdbcTemplate;
    }
    public List<PizzaEntity> getAll(){
        return this.pizzaRepository.findAll();

    }

    public List<PizzaEntity> getAvalible(){
        System.out.println(this.pizzaRepository.countByVeganTrue());
        return this.pizzaRepository.findAllByAvailableTrueOrderByPrice();
    }
    public PizzaEntity get(int idPizza){
        return this.pizzaRepository.findById(idPizza).orElse(null);
    }
    public PizzaEntity getName(String name){
        return this.pizzaRepository.findAllByAvailableTrueAndNameIgnoreCase(name);
    }
    public List<PizzaEntity> getDescriptionContein(String ingrediente){
        return this.pizzaRepository.findAllByAvailableTrueAndDescriptionContainingIgnoreCase(ingrediente);
    }
    public List<PizzaEntity> getDescriptionNotContein(String ingrediente){
        return this.pizzaRepository.findAllByAvailableTrueAndDescriptionNotContainingIgnoreCase(ingrediente);
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
    public PizzaEntity  getNameJDBC(String name) {
        String sql = "SELECT * FROM pizza WHERE upper(name) = upper(?)";
        PizzaEntity pizza = jdbcTemplate.queryForObject(
                sql,
                new Object[]{name},
                new BeanPropertyRowMapper<>(PizzaEntity.class)
        );
        return pizza;
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
