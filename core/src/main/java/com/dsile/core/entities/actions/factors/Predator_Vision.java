package com.dsile.core.entities.actions.factors;

import com.dsile.core.entities.Creature;

/**
 * Created by Светлана on 07.05.2016.
 */
public class Predator_Vision extends Vision {
    public Predator_Vision(Creature creature){
        super(creature);
    }
    public double[] getEnvironment(){
        //Создаем массив наполненный нулевыми элементами
        double[] env = new double[28];
        for(double e : env) e = 0;
        int x = creature.x();
        int y = creature.y();

        //уровень жизни - hp:
        //0.0 ... 1.0
        env[env.length - 1] = creature.getnormalizedHP();

        //Отмечаем в соответствующих ячейках наличие травы
        if (creature.getWorld().getCell(x-1,y+1).isDeadBody(creature)){
            env[0] = 1;
        }
        if (creature.getWorld().getCell(x,y+1).isDeadBody(creature)){
            env[3] = 1;
        }
        if (creature.getWorld().getCell(x+1,y+1).isDeadBody(creature)){
            env[6] = 1;
        }
        if (creature.getWorld().getCell(x-1,y).isDeadBody(creature)){
            env[9] = 1;
        }
        if (creature.getWorld().getCell(x,y).isDeadBody(creature)){
            env[12] = 1;
        }
        if (creature.getWorld().getCell(x+1,y).isDeadBody(creature)){
            env[15] = 1;
        }
        if (creature.getWorld().getCell(x-1,y-1).isDeadBody(creature)){
            env[18] = 1;
        }
        if (creature.getWorld().getCell(x,y-1).isDeadBody(creature)){
            env[21] = 1;
        }
        if (creature.getWorld().getCell(x+1,y-1).isDeadBody(creature)){
            env[24] = 1;
        }

        //Проверяем наличие обычных ящериц
        if (creature.getWorld().getCell(x-1,y+1).isAliveLizard(creature)){
            env[1] = creature.getWorld().getCell(x-1,y+1).getAliveLizard().getnormalizedHP();
        }
        if (creature.getWorld().getCell(x,y+1).isAliveLizard(creature)){
            env[4] = creature.getWorld().getCell(x,y+1).getAliveLizard().getnormalizedHP();
        }
        if (creature.getWorld().getCell(x+1,y+1).isAliveLizard(creature)){
            env[7] = creature.getWorld().getCell(x+1,y+1).getAliveLizard().getnormalizedHP();;
        }
        if (creature.getWorld().getCell(x-1,y).isAliveLizard(creature)){
            env[10] = creature.getWorld().getCell(x-1,y).getAliveLizard().getnormalizedHP();;
        }
        if (creature.getWorld().getCell(x,y).isAliveLizard(creature)){
            env[13] = creature.getWorld().getCell(x,y).getAliveLizard().getnormalizedHP();;
        }
        if (creature.getWorld().getCell(x+1,y).isAliveLizard(creature)){
            env[16] = creature.getWorld().getCell(x+1,y).getAliveLizard().getnormalizedHP();;
        }
        if (creature.getWorld().getCell(x-1,y-1).isAliveLizard(creature)){
            env[19] = creature.getWorld().getCell(x-1,y-1).getAliveLizard().getnormalizedHP();;
        }
        if (creature.getWorld().getCell(x,y-1).isAliveLizard(creature)){
            env[22] = creature.getWorld().getCell(x,y-1).getAliveLizard().getnormalizedHP();;
        }
        if (creature.getWorld().getCell(x+1,y-1).isAliveLizard(creature)){
            env[25] = creature.getWorld().getCell(x+1,y-1).getAliveLizard().getnormalizedHP();;
        }

        //Проверяем наличие ящериц-хищников
        if (creature.getWorld().getCell(x-1,y+1).isAlivePredator_Lizard(creature)){
            env[2] = creature.getWorld().getCell(x-1,y+1).getAlivePredator_Lizard().getnormalizedHP();
        }
        if (creature.getWorld().getCell(x,y+1).isAlivePredator_Lizard(creature)){
            env[5] = creature.getWorld().getCell(x,y+1).getAlivePredator_Lizard().getnormalizedHP();
        }
        if (creature.getWorld().getCell(x+1,y+1).isAlivePredator_Lizard(creature)){
            env[8] = creature.getWorld().getCell(x+1,y+1).getAlivePredator_Lizard().getnormalizedHP();
        }
        if (creature.getWorld().getCell(x-1,y).isAlivePredator_Lizard(creature)){
            env[11] = creature.getWorld().getCell(x-1,y).getAlivePredator_Lizard().getnormalizedHP();
        }
        if (creature.getWorld().getCell(x,y).isAlivePredator_Lizard(creature)){
            env[14] = creature.getWorld().getCell(x,y).getAlivePredator_Lizard().getnormalizedHP();
        }
        if (creature.getWorld().getCell(x+1,y).isAlivePredator_Lizard(creature)){
            env[17] = creature.getWorld().getCell(x+1,y).getAlivePredator_Lizard().getnormalizedHP();
        }
        if (creature.getWorld().getCell(x-1,y-1).isAlivePredator_Lizard(creature)){
            env[20] = creature.getWorld().getCell(x-1,y-1).getAlivePredator_Lizard().getnormalizedHP();
        }
        if (creature.getWorld().getCell(x,y-1).isAlivePredator_Lizard(creature)){
            env[23] = creature.getWorld().getCell(x,y-1).getAlivePredator_Lizard().getnormalizedHP();
        }
        if (creature.getWorld().getCell(x+1,y-1).isAlivePredator_Lizard(creature)){
            env[26] = creature.getWorld().getCell(x+1,y-1).getAlivePredator_Lizard().getnormalizedHP();
        }

        return env;
    }
}
