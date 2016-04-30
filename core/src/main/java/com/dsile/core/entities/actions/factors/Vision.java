package com.dsile.core.entities.actions.factors;

import com.dsile.core.entities.Creature;
import com.dsile.core.neural.Brain;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Класс, отслеживающий сущностей в окружении.
 *
 * Created by DeSile on 1/6/2016.
 */
public class Vision {

    private Creature creature;

    public Vision(Creature creature){
        this.creature = creature;
    }

    /**
     * "Сканирование" окружающего пространства вокруг существа.
     * Если что-то обнаружено в ходе сканирования, данные об этом заносятся в результат.
     * @return массив результатов.
     */
    public int[] getEnvironment(){
        //Создаем массив наполненный нулевыми элементами
        int[] env = new int[28];
        for(int e : env) e = 0;
        int x = creature.x();
        int y = creature.y();

        env[env.length - 1] = creature.getHP();

        //Отмечаем в соответствующих ячейках наличие травы
        if (creature.getWorld().getCell(x-1,y+1).isHerb()){
            env[0] = 1;
        }
        if (creature.getWorld().getCell(x,y+1).isHerb()){
            env[1] = 1;
        }
        if (creature.getWorld().getCell(x+1,y+1).isHerb()){
            env[2] = 1;
        }
        if (creature.getWorld().getCell(x-1,y).isHerb()){
            env[3] = 1;
        }
        if (creature.getWorld().getCell(x,y).isHerb()){
            env[4] = 1;
        }
        if (creature.getWorld().getCell(x+1,y).isHerb()){
            env[5] = 1;
        }
        if (creature.getWorld().getCell(x-1,y-1).isHerb()){
            env[6] = 1;
        }
        if (creature.getWorld().getCell(x,y-1).isHerb()){
            env[7] = 1;
        }
        if (creature.getWorld().getCell(x+1,y-1).isHerb()){
            env[8] = 1;
        }

        //Проверяем наличие обычных ящериц, считаем, что клетка занята ящерицей
        if (creature.getWorld().getCell(x-1,y+1).isLizard()){
            env[9] = 2;
        }
        if (creature.getWorld().getCell(x,y+1).isLizard()){
            env[10] = 2;
        }
        if (creature.getWorld().getCell(x+1,y+1).isLizard()){
            env[11] = 2;
        }
        if (creature.getWorld().getCell(x-1,y).isLizard()){
            env[12] = 2;
        }
        if (creature.getWorld().getCell(x,y).isLizard()){
            env[13] = 2;
        }
        if (creature.getWorld().getCell(x+1,y).isLizard()){
            env[14] = 2;
        }
        if (creature.getWorld().getCell(x-1,y-1).isLizard()){
            env[15] = 2;
        }
        if (creature.getWorld().getCell(x,y-1).isLizard()){
            env[16] = 2;
        }
        if (creature.getWorld().getCell(x+1,y-1).isLizard()){
            env[17] = 2;
        }

        //Проверяем наличие ящериц-хищников, пускай тоже затерает инфу, так как это наи
        if (creature.getWorld().getCell(x-1,y+1).isPredator_Lizard()){
            env[18] = 3;
        }
        if (creature.getWorld().getCell(x,y+1).isPredator_Lizard()){
            env[19] = 3;
        }
        if (creature.getWorld().getCell(x+1,y+1).isPredator_Lizard()){
            env[20] = 3;
        }
        if (creature.getWorld().getCell(x-1,y).isPredator_Lizard()){
            env[21] = 3;
        }
        if (creature.getWorld().getCell(x,y).isPredator_Lizard()){
            env[22] = 3;
        }
        if (creature.getWorld().getCell(x+1,y).isPredator_Lizard()){
            env[23] = 3;
        }
        if (creature.getWorld().getCell(x-1,y-1).isPredator_Lizard()){
            env[24] = 3;
        }
        if (creature.getWorld().getCell(x,y-1).isPredator_Lizard()){
            env[25] = 3;
        }
        if (creature.getWorld().getCell(x+1,y-1).isPredator_Lizard()){
            env[26] = 3;
        }

        return env;
    }

    /**
     * Обработка окружения нейронной сетью (мозгом существа)
     * @return результат работы нейронной сети
     */
    public double[] accessSituation(){
        int[] environment = getEnvironment();
        System.out.println("Sees: " + Arrays.toString(environment));
        Brain brain = creature.getBrain();
        brain.setInput(environment);
        brain.think();
        double[] thinks = brain.getOutput();
        System.out.println("Decides: " + Arrays.toString(thinks));
        return thinks;
    }
}
