package com.rocket.Domino.web.controller;

import com.rocket.Domino.persistence.entity.PizzaEntity;
import com.rocket.Domino.service.PizzaService;
import org.apache.coyote.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pizzas")
public class PizzaController {
    private final PizzaService pizzaService;

    @Autowired
    public PizzaController(PizzaService pizzaService) {
        this.pizzaService = pizzaService;
    }
@GetMapping
public ResponseEntity<List<PizzaEntity>> getAll() {
        return ResponseEntity.ok(this.pizzaService.getAll());
        }
@GetMapping("/{idPizza}")
public ResponseEntity<PizzaEntity> get(@PathVariable int idPizza) {

        return ResponseEntity.ok(this.pizzaService.get(idPizza));
        }
@GetMapping("/name/{name}")
public ResponseEntity<PizzaEntity> getName(@PathVariable String name) {

        return ResponseEntity.ok(this.pizzaService.getNameJDBC(name));
        }
@GetMapping("/with/{ingrediente}")
public ResponseEntity<List<PizzaEntity>> getIngrediente(@PathVariable String ingrediente) {
        return ResponseEntity.ok(this.pizzaService.getAvailablePizzaDescriptionContein(ingrediente));
        }
@GetMapping("/without/{ingrediente}")
public ResponseEntity<List<PizzaEntity>> getNotIngrediente(@PathVariable String ingrediente) {
        return ResponseEntity.ok(this.pizzaService.getAvailablePizzaDescriptionNotContein(ingrediente));
        }

@GetMapping("/available")
public ResponseEntity<List<PizzaEntity>> getAvailable() {
        return ResponseEntity.ok(this.pizzaService.getAvailablePizzasOrderedByPrice() );
        }

//@PostMapping() al utilizar otro tipo de datos se tiene que castear para ser almacenada en bd postgres
public ResponseEntity<PizzaEntity> add(@RequestBody  PizzaEntity pizza) {
        if (pizza.getIdPizza()==null || !this.pizzaService.exists(pizza.getIdPizza())){
        return ResponseEntity.ok(this.pizzaService.save(pizza));
        }
        return ResponseEntity.badRequest().build();
        }
public ResponseEntity<PizzaEntity> update(@RequestBody  PizzaEntity pizza) {
        if (pizza.getIdPizza()!=null && this.pizzaService.exists(pizza.getIdPizza())){
        return ResponseEntity.ok(this.pizzaService.save(pizza));
        }
        return ResponseEntity.badRequest().build();
        }

@PostMapping()
public ResponseEntity<PizzaEntity> insertar(@RequestBody  PizzaEntity pizza) {
        if (pizza.getIdPizza()==null || !this.pizzaService.exists(pizza.getIdPizza())){
        pizzaService.insert(pizza);
        return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
        }

@PutMapping()
public ResponseEntity<PizzaEntity> actualizar(@RequestBody  PizzaEntity pizza) {
        if (pizza.getIdPizza()!=null && this.pizzaService.exists(pizza.getIdPizza())){
        pizzaService.update(pizza);
        return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
        }

@DeleteMapping("/{idPizza}")
public ResponseEntity<Void> delete(@PathVariable int idPizza){
        if(this.pizzaService.exists(idPizza)){
        this.pizzaService.delete(idPizza);
        return ResponseEntity.ok().build();
        }
        return  ResponseEntity.badRequest().build();
        }

        }