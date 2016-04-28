package com.dsile.core.entities.actions.eating;

import com.dsile.core.entities.Creature;

/**
 * Created by Никита on 28.04.2016.
 */
public class Eating {
    //Существо, которое ест
    private Creature creature;

    public Eating(Creature creature){
        this.creature = creature;
    }

    public void perform() {
        if(creature.getCurrentCell().isHerb())
        {
            creature.incHP(creature.getCurrentCell().getHerb().bitten()); //кусаем и получаем некоторый прирост hp
        }

    }
}
