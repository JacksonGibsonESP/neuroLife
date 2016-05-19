package com.dsile.core.entities.actions.vision;

import com.dsile.core.entities.Creature;
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

    abstract public double[] getEnvironmentByDirection();

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
