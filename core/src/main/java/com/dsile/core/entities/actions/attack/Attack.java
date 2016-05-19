package com.dsile.core.entities.actions.attack;

import com.dsile.core.entities.Creature;
import com.dsile.core.entities.Lizard;
import com.dsile.core.entities.Predator_Lizard;
import com.dsile.core.world.Cell;

/**
 * Created by Никита on 28.04.2016.
 */
public class Attack {
    //Существо, которое атакует
    private Creature creature;

    public Attack(Creature creature){
        this.creature = creature;
    }

    /*public void perform() {
        if(creature instanceof Predator_Lizard && creature.getCurrentCell().isAliveLizard(creature))
        {
            creature.getCurrentCell().getAliveLizard().attacked(creature); //атакуем чтобы убить
        }
        else if(creature instanceof Lizard && creature.getCurrentCell().isAlivePredator_Lizard(creature))
        {
            creature.getCurrentCell().getAlivePredator_Lizard().attacked(creature); //атакуем чтобы убить
        }
    }*/

    public void perform(Cell cell) {
        //Получим ячейку по направлению
        if(creature instanceof Predator_Lizard)
        {
            Lizard lizard = cell.getAliveHerb_Lizard();
            //System.out.println(lizard);
            //System.out.println(cell);
            if (lizard != null) {
                cell.getAliveHerb_Lizard().attacked(creature); //атакуем чтобы убить
            }
        }
        else if(creature instanceof Lizard)
        {
            Predator_Lizard predator_lizard = cell.getAlivePredator_Lizard();
            //System.out.println(predator_lizard);
            //System.out.println(cell);
            if (predator_lizard != null) {
                cell.getAlivePredator_Lizard().attacked(creature); //атакуем чтобы убить
            }
        }
    }
}