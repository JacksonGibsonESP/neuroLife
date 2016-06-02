package com.dsile.core.entities.actions.vision;

import com.dsile.core.entities.Creature;
import com.dsile.core.entities.actions.movement.DirectionValues;
import com.dsile.core.neural.Brain;

import java.util.Arrays;

/**
 * Класс, отслеживающий сущностей в окружении.
 *
 * Created by DeSile on 1/6/2016.
 */
abstract public class Vision {

    protected Creature creature;

    public Vision(Creature creature){
        this.creature = creature;
    }

    abstract public double[] getEnvironment();

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

    /**
     * Обработка окружения нейронной сетью (мозгом существа)
     * @return результат работы нейронной сети
     */
    public double[] accessSituation(){
        System.out.println(creature);
        double[] environment = getEnvironmentByDirection();

        System.out.println("Sees:\nFood:         Lizards:      Predators:");
        System.out.print(environment[0] + " " + environment[3] + " " + environment[6] + " | " + environment[1] + " " + environment[4] + " " + environment[7] + " | " + environment[2] + " " + environment[5] + " " + environment[8] + "\n");
        System.out.print(environment[9] + " " + environment[12] + " " + environment[15] + " | " + environment[10] + " " + environment[13] + " " + environment[16] + " | " + environment[11] + " " + environment[14] + " " + environment[17] + "\n");
        System.out.print(environment[18] + " " + environment[21] + " " + environment[24] + " | " + environment[19] + " " + environment[22] + " " + environment[25] + " | " + environment[20] + " " + environment[23] + " " + environment[26] + "\n");

        Brain brain = creature.getBrain();
        brain.setInput(environment);
        brain.think();
        double[] thinks = brain.getOutput();
        System.out.println("Decides:");
        System.out.printf("Directions: [%f, %f, %f, %f]\n",  thinks[0], thinks[1], thinks[2], thinks[3]);
        System.out.printf("Movement:     %10f\n", thinks[4]);
        System.out.printf("Attack:       %10f\n", thinks[5]);
        System.out.printf("Eating:       %10f\n", thinks[6]);
        System.out.printf("Reproduction: %10f\n", thinks[7]);
        return thinks;
    }
}
