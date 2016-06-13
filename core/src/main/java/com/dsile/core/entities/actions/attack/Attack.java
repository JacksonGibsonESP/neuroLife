package com.dsile.core.entities.actions.attack;

import com.dsile.core.entities.Creature;
import com.dsile.core.entities.Lizard;
import com.dsile.core.entities.Predator_Lizard;
import com.dsile.core.entities.actions.movement.DirectionValues;
import com.dsile.core.entities.actions.movement.SidesDirectionValues;
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

    public void perform(double [] brainOutput) {

        //Получим ячейку по направлению
        SidesDirectionValues sdir = creature.getMovement().getSidesDirection(brainOutput);
        Cell cell = creature.getMovement().getCellBySidesDirection(sdir);
        //Выставляем направление ящерки при атаке
        creature.setDirection(sdir);

        if (creature.getLogger().isDebugEnabled()){
            DirectionValues dir = creature.getMovement().getDirection(brainOutput);
            creature.getLogger().debug(dir.toString());
        }

        if(creature instanceof Predator_Lizard)
        {
            Lizard lizard = cell.getAliveHerb_Lizard();
            if (lizard != null) {
                cell.getAliveHerb_Lizard().attacked(creature); //атакуем чтобы убить
            }
        }
        else if(creature instanceof Lizard)
        {
            Predator_Lizard predator_lizard = cell.getAlivePredator_Lizard();
            if (predator_lizard != null) {
                cell.getAlivePredator_Lizard().attacked(creature); //атакуем чтобы убить
            }
        }
    }
}