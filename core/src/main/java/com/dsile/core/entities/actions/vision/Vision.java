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

    /**
     * Обработка окружения нейронной сетью (мозгом существа)
     * @return результат работы нейронной сети
     */
    public double[] accessSituation(){
        System.out.println(creature);
        double[] environment = getEnvironment();
        System.out.println("Sees: " + Arrays.toString(environment));
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
