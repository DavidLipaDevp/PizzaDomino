package com.rocket.Domino.persistence.audit;

import com.rocket.Domino.persistence.entity.PizzaEntity;
import jakarta.persistence.PostLoad;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostUpdate;
import jakarta.persistence.PreRemove;
import org.springframework.util.SerializationUtils;

public class AuditPizzaListener {
    private PizzaEntity currentValue;

    //se ejecuta despues al realizar un select
    //SerializationUtils se debe serializar a la entidad
    @PostLoad
    public void onPostLoad(PizzaEntity entity){
        System.out.println("POST LOAD");
        //evita la sobrecarga de memoria
        this.currentValue= SerializationUtils.clone(entity);

    }

    //se ejecuta despues al crear o actualizar
    @PostPersist
    @PostUpdate
    public void onPostPersist(PizzaEntity entity){
        System.out.println("POST PERSIST OR UPDATE");
        System.out.println("OLD VALUE: "+this.currentValue.toString());
        System.out.println("NEW VALUE: " +entity.toString());
    }
    //se ejecuta antes de eliminar un registo
    @PreRemove
    public void onPreRemote(PizzaEntity entity){
        System.out.println(entity.toString());
    }
}

