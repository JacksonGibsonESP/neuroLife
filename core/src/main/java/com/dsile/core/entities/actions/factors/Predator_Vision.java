package com.dsile.core.entities.actions.factors;

import com.dsile.core.entities.Creature;

/**
 * Created by Светлана on 07.05.2016.
 */
public class Predator_Vision extends Vision {
    public Predator_Vision(Creature creature){
        super(creature);
    }
    public int[] getEnvironment(){
        //Создаем массив наполненный нулевыми элементами
        int[] env = new int[10];
        for(int e : env) e = 0;
        int x = creature.x();
        int y = creature.y();

        //уровень жизни - hp:
        //    0 - <= 30 %
        //    1 - > 30%, <= 70 %
        //    2 - > 70 %

        if(creature.getHP() <= 30)
        {
            env[env.length - 1] = 0;
        }
        else if (creature.getHP() <= 70)
        {
            env[env.length - 1] = 1;
        }
        else
        {
            env[env.length - 1] = 2;
        }

        // Комбинации:
        // 0 - никого
        // 1 - трава
        // 2 - ящерица
        // 3 - ящерица-хищник
        // Значения перезаписывают друг друга в порядке выше

        //Отмечаем в соответствующих ячейках наличие падали
        if (creature.getWorld().getCell(x-1,y+1).isDeadBody(creature)){
            env[0] = 1;
        }
        if (creature.getWorld().getCell(x,y+1).isDeadBody(creature)){
            env[1] = 1;
        }
        if (creature.getWorld().getCell(x+1,y+1).isDeadBody(creature)){
            env[2] = 1;
        }
        if (creature.getWorld().getCell(x-1,y).isDeadBody(creature)){
            env[3] = 1;
        }
        if (creature.getWorld().getCell(x,y).isDeadBody(creature)){
            env[4] = 1;
        }
        if (creature.getWorld().getCell(x+1,y).isDeadBody(creature)){
            env[5] = 1;
        }
        if (creature.getWorld().getCell(x-1,y-1).isDeadBody(creature)){
            env[6] = 1;
        }
        if (creature.getWorld().getCell(x,y-1).isDeadBody(creature)){
            env[7] = 1;
        }
        if (creature.getWorld().getCell(x+1,y-1).isDeadBody(creature)){
            env[8] = 1;
        }

        //Проверяем наличие обычных ящериц
        if (creature.getWorld().getCell(x-1,y+1).isAliveLizard(creature)){
            env[0] = 2;
        }
        if (creature.getWorld().getCell(x,y+1).isAliveLizard(creature)){
            env[1] = 2;
        }
        if (creature.getWorld().getCell(x+1,y+1).isAliveLizard(creature)){
            env[2] = 2;
        }
        if (creature.getWorld().getCell(x-1,y).isAliveLizard(creature)){
            env[3] = 2;
        }
        if (creature.getWorld().getCell(x,y).isAliveLizard(creature)){
            env[4] = 2;
        }
        if (creature.getWorld().getCell(x+1,y).isAliveLizard(creature)){
            env[5] = 2;
        }
        if (creature.getWorld().getCell(x-1,y-1).isAliveLizard(creature)){
            env[6] = 2;
        }
        if (creature.getWorld().getCell(x,y-1).isAliveLizard(creature)){
            env[7] = 2;
        }
        if (creature.getWorld().getCell(x+1,y-1).isAliveLizard(creature)){
            env[8] = 2;
        }

        //Проверяем наличие ящериц-хищников
        if (creature.getWorld().getCell(x-1,y+1).isAlivePredator_Lizard(creature)){
            env[0] = 3;
        }
        if (creature.getWorld().getCell(x,y+1).isAlivePredator_Lizard(creature)){
            env[1] = 3;
        }
        if (creature.getWorld().getCell(x+1,y+1).isAlivePredator_Lizard(creature)){
            env[2] = 3;
        }
        if (creature.getWorld().getCell(x-1,y).isAlivePredator_Lizard(creature)){
            env[3] = 3;
        }
        if (creature.getWorld().getCell(x,y).isAlivePredator_Lizard(creature)){
            env[4] = 3;
        }
        if (creature.getWorld().getCell(x+1,y).isAlivePredator_Lizard(creature)){
            env[5] = 3;
        }
        if (creature.getWorld().getCell(x-1,y-1).isAlivePredator_Lizard(creature)){
            env[6] = 3;
        }
        if (creature.getWorld().getCell(x,y-1).isAlivePredator_Lizard(creature)){
            env[7] = 3;
        }
        if (creature.getWorld().getCell(x+1,y-1).isAlivePredator_Lizard(creature)){
            env[8] = 3;
        }

        return env;
    }
}
