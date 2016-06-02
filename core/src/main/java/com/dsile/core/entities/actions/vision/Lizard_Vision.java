package com.dsile.core.entities.actions.vision;

import com.dsile.core.entities.Creature;
import com.dsile.core.entities.actions.movement.DirectionValues;

/**
 * Created by Светлана on 07.05.2016.
 */
public class Lizard_Vision extends Vision {
    public Lizard_Vision(Creature creature){
        super(creature);
    }
    /**
     * "Сканирование" окружающего пространства вокруг существа.
     * Если что-то обнаружено в ходе сканирования, данные об этом заносятся в результат.
     * @return массив результатов.
     */

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
        if (creature.getWorld().getCell(x-1,y+1).isHerb()){
            env[0] = 1;
        }
        if (creature.getWorld().getCell(x,y+1).isHerb()){
            env[3] = 1;
        }
        if (creature.getWorld().getCell(x+1,y+1).isHerb()){
            env[6] = 1;
        }
        if (creature.getWorld().getCell(x-1,y).isHerb()){
            env[9] = 1;
        }
        if (creature.getWorld().getCell(x,y).isHerb()){
            env[12] = 1;
        }
        if (creature.getWorld().getCell(x+1,y).isHerb()){
            env[15] = 1;
        }
        if (creature.getWorld().getCell(x-1,y-1).isHerb()){
            env[18] = 1;
        }
        if (creature.getWorld().getCell(x,y-1).isHerb()){
            env[21] = 1;
        }
        if (creature.getWorld().getCell(x+1,y-1).isHerb()){
            env[24] = 1;
        }

        //Проверяем наличие обычных ящериц
        if (creature.getWorld().getCell(x-1,y+1).isAliveHerb_Lizard()){
            env[1] = creature.getWorld().getCell(x-1,y+1).getAliveHerb_Lizard().getnormalizedHP();
        }
        if (creature.getWorld().getCell(x,y+1).isAliveHerb_Lizard()){
            env[4] = creature.getWorld().getCell(x,y+1).getAliveHerb_Lizard().getnormalizedHP();
        }
        if (creature.getWorld().getCell(x+1,y+1).isAliveHerb_Lizard()){
            env[7] = creature.getWorld().getCell(x+1,y+1).getAliveHerb_Lizard().getnormalizedHP();;
        }
        if (creature.getWorld().getCell(x-1,y).isAliveHerb_Lizard()){
            env[10] = creature.getWorld().getCell(x-1,y).getAliveHerb_Lizard().getnormalizedHP();;
        }
        if (creature.getWorld().getCell(x,y).isAliveHerb_Lizard(creature)){
            env[13] = creature.getWorld().getCell(x,y).getAliveHerb_Lizard(creature).getnormalizedHP();;
        }
        if (creature.getWorld().getCell(x+1,y).isAliveHerb_Lizard()){
            env[16] = creature.getWorld().getCell(x+1,y).getAliveHerb_Lizard().getnormalizedHP();;
        }
        if (creature.getWorld().getCell(x-1,y-1).isAliveHerb_Lizard()){
            env[19] = creature.getWorld().getCell(x-1,y-1).getAliveHerb_Lizard().getnormalizedHP();;
        }
        if (creature.getWorld().getCell(x,y-1).isAliveHerb_Lizard()){
            env[22] = creature.getWorld().getCell(x,y-1).getAliveHerb_Lizard().getnormalizedHP();;
        }
        if (creature.getWorld().getCell(x+1,y-1).isAliveHerb_Lizard()){
            env[25] = creature.getWorld().getCell(x+1,y-1).getAliveHerb_Lizard().getnormalizedHP();;
        }

        //Проверяем наличие ящериц-хищников
        if (creature.getWorld().getCell(x-1,y+1).isAlivePredator_Lizard()){
            env[2] = creature.getWorld().getCell(x-1,y+1).getAlivePredator_Lizard().getnormalizedHP();
        }
        if (creature.getWorld().getCell(x,y+1).isAlivePredator_Lizard()){
            env[5] = creature.getWorld().getCell(x,y+1).getAlivePredator_Lizard().getnormalizedHP();
        }
        if (creature.getWorld().getCell(x+1,y+1).isAlivePredator_Lizard()){
            env[8] = creature.getWorld().getCell(x+1,y+1).getAlivePredator_Lizard().getnormalizedHP();
        }
        if (creature.getWorld().getCell(x-1,y).isAlivePredator_Lizard()){
            env[11] = creature.getWorld().getCell(x-1,y).getAlivePredator_Lizard().getnormalizedHP();
        }
        if (creature.getWorld().getCell(x,y).isAlivePredator_Lizard()){
            env[14] = creature.getWorld().getCell(x,y).getAlivePredator_Lizard().getnormalizedHP();
        }
        if (creature.getWorld().getCell(x+1,y).isAlivePredator_Lizard()){
            env[17] = creature.getWorld().getCell(x+1,y).getAlivePredator_Lizard().getnormalizedHP();
        }
        if (creature.getWorld().getCell(x-1,y-1).isAlivePredator_Lizard()){
            env[20] = creature.getWorld().getCell(x-1,y-1).getAlivePredator_Lizard().getnormalizedHP();
        }
        if (creature.getWorld().getCell(x,y-1).isAlivePredator_Lizard()){
            env[23] = creature.getWorld().getCell(x,y-1).getAlivePredator_Lizard().getnormalizedHP();
        }
        if (creature.getWorld().getCell(x+1,y-1).isAlivePredator_Lizard()){
            env[26] = creature.getWorld().getCell(x+1,y-1).getAlivePredator_Lizard().getnormalizedHP();
        }

        return env;
    }
}
