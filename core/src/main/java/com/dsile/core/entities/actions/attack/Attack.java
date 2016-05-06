package com.dsile.core.entities.actions.attack;

import com.dsile.core.entities.Creature;
import com.dsile.core.entities.Lizard;
import com.dsile.core.entities.Predator_Lizard;

/**
 * Created by Никита on 28.04.2016.
 */
public class Attack {
    //Существо, которое атакует
    private Creature creature;

    public Attack(Creature creature){
        this.creature = creature;
    }

    public void perform() {
        if(creature instanceof Predator_Lizard && creature.getCurrentCell().isAliveLizard(creature))
        {
            creature.getCurrentCell().getAliveLizard().attacked(creature); //атакуем чтобы убить
        }
        else if(creature instanceof Lizard && creature.getCurrentCell().isAlivePredator_Lizard(creature))
        {
            creature.getCurrentCell().getAlivePredator_Lizard().attacked(creature); //атакуем чтобы убить
        }
    }
}