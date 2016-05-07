package com.dsile.core.entities.actions.factors;

import com.dsile.core.entities.Creature;

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
        /*
        // Комбинации:
        // 0 - никого
        // 1 - трава
        // 2 - трава и обычная ящерица
        // 3 - ящерица
        // 4 - ящерица и ящерица-хищник
        // 5 - ящерица-хищник
        // 6 - ящерица-хищник и трава           // может быть никогда и не понадобится?
        // 7 - ящерица-хищник, ящерица и трава  // слишком уж гремучая комбинация

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

        //Проверяем наличие обычных ящериц
        if (creature.getWorld().getCell(x-1,y+1).isLizard(creature)){
            env[0] = (env[0] == 0) ? 3 : 2;
        }
        if (creature.getWorld().getCell(x,y+1).isLizard(creature)){
            env[1] = (env[1] == 0) ? 3 : 2;
        }
        if (creature.getWorld().getCell(x+1,y+1).isLizard(creature)){
            env[2] = (env[2] == 0) ? 3 : 2;
        }
        if (creature.getWorld().getCell(x-1,y).isLizard(creature)){
            env[3] = (env[3] == 0) ? 3 : 2;
        }
        if (creature.getWorld().getCell(x,y).isLizard(creature)){
            env[4] = (env[4] == 0) ? 3 : 2;
        }
        if (creature.getWorld().getCell(x+1,y).isLizard(creature)){
            env[5] = (env[5] == 0) ? 3 : 2;
        }
        if (creature.getWorld().getCell(x-1,y-1).isLizard(creature)){
            env[6] = (env[6] == 0) ? 3 : 2;
        }
        if (creature.getWorld().getCell(x,y-1).isLizard(creature)){
            env[7] = (env[7] == 0) ? 3 : 2;
        }
        if (creature.getWorld().getCell(x+1,y-1).isLizard(creature)){
            env[8] = (env[8] == 0) ? 3 : 2;
        }

        //Проверяем наличие ящериц-хищников
        if (creature.getWorld().getCell(x-1,y+1).isPredator_Lizard(creature)){
            env[0] = (env[0] == 2 || env[0] == 3) ? 4 : 5;
        }
        if (creature.getWorld().getCell(x,y+1).isPredator_Lizard(creature)){
            env[1] = (env[1] == 2 || env[1] == 3) ? 4 : 5;
        }
        if (creature.getWorld().getCell(x+1,y+1).isPredator_Lizard(creature)){
            env[2] = (env[2] == 2 || env[2] == 3) ? 4 : 5;
        }
        if (creature.getWorld().getCell(x-1,y).isPredator_Lizard(creature)){
            env[3] = (env[3] == 2 || env[3] == 3) ? 4 : 5;
        }
        if (creature.getWorld().getCell(x,y).isPredator_Lizard(creature)){
            env[4] = (env[4] == 2 || env[4] == 3) ? 4 : 5;
        }
        if (creature.getWorld().getCell(x+1,y).isPredator_Lizard(creature)){
            env[5] = (env[5] == 2 || env[5] == 3) ? 4 : 5;
        }
        if (creature.getWorld().getCell(x-1,y-1).isPredator_Lizard(creature)){
            env[6] = (env[6] == 2 || env[6] == 3) ? 4 : 5;
        }
        if (creature.getWorld().getCell(x,y-1).isPredator_Lizard(creature)){
            env[7] = (env[7] == 2 || env[7] == 3) ? 4 : 5;
        }
        if (creature.getWorld().getCell(x+1,y-1).isPredator_Lizard(creature)){
            env[8] = (env[8] == 2 || env[8] == 3) ? 4 : 5;
        }
        */

        // Комбинации:
        // 0 - никого
        // 1 - трава
        // 2 - ящерица
        // 3 - ящерица-хищник
        // Значения перезаписывают друг друга в порядке выше

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
