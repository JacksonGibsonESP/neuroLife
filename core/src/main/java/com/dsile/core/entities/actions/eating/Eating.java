package com.dsile.core.entities.actions.eating;

import com.dsile.core.entities.Creature;
import com.dsile.core.entities.Lizard;
import com.dsile.core.entities.Predator_Lizard;

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
        if(creature instanceof Lizard && creature.getCurrentCell().isHerb())
        {
            creature.incHP(creature.getCurrentCell().getHerb().bitten()); //кусаем и получаем некоторый прирост hp
        }
        else if(creature instanceof Predator_Lizard) {
            if (creature.getCurrentCell().isDeadHerb_Lizard()) {
                creature.incHP(creature.getCurrentCell().getDeadHerb_Lizard().bitten()); //кусаем и получаем некоторый прирост hp
            }
            else if (creature.getCurrentCell().isDeadPredator_Lizard(creature)){
                creature.incHP(creature.getCurrentCell().getDeadPredator_Lizard().bitten());
            }
        }
    }
}
