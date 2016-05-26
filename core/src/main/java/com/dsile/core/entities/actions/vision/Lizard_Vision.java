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
    public double[] getEnvironmentByDirection(){
        //Возвращае поле зрения по направлению взгляда
        double[] env = getEnvironment();
        DirectionValues dir = creature.getDirection();

        double[] env_dir = new double[28];
        env_dir[env_dir.length - 1] = env[env.length - 1];

        //Буду копировать по три ячейки, чтобы не запутаться
        double [][] vision_in = new double [9][3];
        for (int i = 0; i < 9; i++)
            for (int j = 0; j < 3; j++)
                vision_in[i][j] = env[i * 3 + j];
        double [][] vision_out =  new double [9][3];

        switch(dir){
            case NO_DIRECTION:
                //Такого произойти не должно
                vision_out = vision_in;
                break;
            case NORTH:
                //Не нужно ничего делать
                vision_out = vision_in;
                break;
            case NORTH_EAST:
                /*
                    1 2 5
                    0 4 8
                    3 6 7
                 */
                vision_out[0] = vision_in[1];
                vision_out[1] = vision_in[2];
                vision_out[2] = vision_in[5];
                vision_out[3] = vision_in[0];
                vision_out[4] = vision_in[4];
                vision_out[5] = vision_in[8];
                vision_out[6] = vision_in[3];
                vision_out[7] = vision_in[6];
                vision_out[8] = vision_in[7];
                break;
            case EAST:
                /*
                    2 5 8
                    1 4 7
                    0 3 6
                 */
                vision_out[0] = vision_in[2];
                vision_out[1] = vision_in[5];
                vision_out[2] = vision_in[8];
                vision_out[3] = vision_in[1];
                vision_out[4] = vision_in[4];
                vision_out[5] = vision_in[7];
                vision_out[6] = vision_in[0];
                vision_out[7] = vision_in[3];
                vision_out[8] = vision_in[6];
                break;
            case SOUTH_EAST:
                /*
                    5 8 7
                    2 4 6
                    1 0 3
                 */
                vision_out[0] = vision_in[5];
                vision_out[1] = vision_in[8];
                vision_out[2] = vision_in[7];
                vision_out[3] = vision_in[2];
                vision_out[4] = vision_in[4];
                vision_out[5] = vision_in[6];
                vision_out[6] = vision_in[1];
                vision_out[7] = vision_in[0];
                vision_out[8] = vision_in[3];
                break;
            case SOUTH:
                /*
                    8 7 6
                    5 4 3
                    2 1 0
                 */
                vision_out[0] = vision_in[8];
                vision_out[1] = vision_in[7];
                vision_out[2] = vision_in[6];
                vision_out[3] = vision_in[5];
                vision_out[4] = vision_in[4];
                vision_out[5] = vision_in[3];
                vision_out[6] = vision_in[2];
                vision_out[7] = vision_in[1];
                vision_out[8] = vision_in[0];
                break;
            case SOUTH_WEST:
                /*
                    7 6 3
                    8 4 0
                    5 2 1
                 */
                vision_out[0] = vision_in[7];
                vision_out[1] = vision_in[6];
                vision_out[2] = vision_in[3];
                vision_out[3] = vision_in[8];
                vision_out[4] = vision_in[4];
                vision_out[5] = vision_in[0];
                vision_out[6] = vision_in[5];
                vision_out[7] = vision_in[2];
                vision_out[8] = vision_in[1];
                break;
            case WEST:
                /*
                    6 3 0
                    7 4 1
                    8 5 2
                 */
                vision_out[0] = vision_in[6];
                vision_out[1] = vision_in[3];
                vision_out[2] = vision_in[0];
                vision_out[3] = vision_in[7];
                vision_out[4] = vision_in[4];
                vision_out[5] = vision_in[1];
                vision_out[6] = vision_in[8];
                vision_out[7] = vision_in[5];
                vision_out[8] = vision_in[2];
                break;
            case NORTH_WEST:
                /*
                    3 0 1
                    6 4 2
                    7 8 5
                 */
                vision_out[0] = vision_in[3];
                vision_out[1] = vision_in[0];
                vision_out[2] = vision_in[1];
                vision_out[3] = vision_in[6];
                vision_out[4] = vision_in[4];
                vision_out[5] = vision_in[2];
                vision_out[6] = vision_in[7];
                vision_out[7] = vision_in[8];
                vision_out[8] = vision_in[5];
                break;
        }

        for (int i = 0; i < 9; i++)
            for (int j = 0; j < 3; j++)
                env_dir[i * 3 + j] = vision_out[i][j];

        return env_dir;
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
