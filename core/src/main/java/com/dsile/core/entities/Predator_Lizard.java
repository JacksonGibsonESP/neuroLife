package com.dsile.core.entities;

import com.badlogic.gdx.graphics.Texture;
import com.dsile.core.entities.actions.factors.Predator_Vision;
import com.dsile.core.neural.Brain;
import com.dsile.core.world.World;

/**
 * Created by Никита on 28.04.2016.
 */
public class Predator_Lizard extends Lizard {
    public Predator_Lizard(World world, int x, int y) {
        super(world,x,y);
        this.vision = new Predator_Vision(this);
        this.id = 2;
        System.out.printf("Predator lizard created on (%d,%d)\n",x,y);
    }

    public Predator_Lizard(World world, int x, int y, Brain brain) {
        super(world,x,y, brain);
        this.vision = new Predator_Vision(this);
        this.id = 2;
        System.out.printf("Predator lizard was borned on (%d,%d)\n",x,y);
    }

    //TODO: Переобучить на хищника
    public void learn() {
        /*
        *    Input Array: (на 4ой ячейке находимся мы)
        *    |---|---|---|
        *    | 0 | 1 | 2 |
        *    |---|---|---|
        *    | 3 | 4 | 5 |
        *    |---|---|---|
        *    | 6 | 7 | 8 |
        *    |---|---|---|
        *
        *    Возможные значения на ячейках:
        *    0 - никого
        *    1 - падаль
        *    2 - ящерица
        *    3 - ящерица-хищник
        *    Значения перезаписывают друг друга в порядке выше
        *
        *    9-й вход сети - уровень жизни - hp:
        *    0 - <= 30 %
        *    1 - > 30%, <= 70 %
        *    2 - > 70 %
        *
        *    Output Array: (первые четыре ячейки)
        *
        *         1
        *         ^
        *         |
        *   2 <-- * --> 0
        *         |
        *         v
        *         3
        *
        *  Остальные ячейки: действия (идти, атакатовать, есть)
        *
        *  4 - перемещение
        *  5 - атака
        *  6 - поедание
        *  7 - размножение
        **/

        //поведение при пустом поле, надо бы что-то поумнее придумать
        for(int i = 0; i <= 2; i++ ) {
            brain.addRowToTrainingSet(
                    new double[]{0, 0, 0, 0, 0, 0, 0, 0, 0, i},
                    new double[]{0, 0, 0, 0, 1, 0, 0, 0});
        }

        //поедание падали при всех значениях hp            hp
        //так как при охоте после убийства жертвы она становится падалью
        for(int i = 0; i <= 2; i++ ) {
            brain.addRowToTrainingSet(
                    new double[]{0, 0, 0, 0, 0, 0, 0, 0, 1, i},
                    new double[]{1, 0, 0, 1, 1, 0, 0, 0});
            brain.addRowToTrainingSet(
                    new double[]{0, 0, 0, 0, 0, 0, 0, 1, 0, i},
                    new double[]{0, 0, 0, 1, 1, 0, 0, 0});
            brain.addRowToTrainingSet(
                    new double[]{0, 0, 0, 0, 0, 0, 1, 0, 0, i},
                    new double[]{0, 0, 1, 1, 1, 0, 0, 0});
            brain.addRowToTrainingSet(
                    new double[]{0, 0, 0, 0, 0, 1, 0, 0, 0, i},
                    new double[]{1, 0, 0, 0, 1, 0, 0, 0});
            brain.addRowToTrainingSet(
                    new double[]{0, 0, 0, 0, 1, 0, 0, 0, 0, i},
                    new double[]{0, 0, 0, 0, 0, 0, 1, 0});
            brain.addRowToTrainingSet(
                    new double[]{0, 0, 0, 1, 0, 0, 0, 0, 0, i},
                    new double[]{0, 0, 1, 0, 1, 0, 0, 0});
            brain.addRowToTrainingSet(
                    new double[]{0, 0, 1, 0, 0, 0, 0, 0, 0, i},
                    new double[]{1, 1, 0, 0, 1, 0, 0, 0});
            brain.addRowToTrainingSet(
                    new double[]{0, 1, 0, 0, 0, 0, 0, 0, 0, i},
                    new double[]{0, 1, 0, 0, 1, 0, 0, 0});
            brain.addRowToTrainingSet(
                    new double[]{1, 0, 0, 0, 0, 0, 0, 0, 0, i},
                    new double[]{0, 1, 1, 0, 1, 0, 0, 0});
        }

        //охота при среднем и большом hp
        for(int i = 1; i <= 2; i++ ) {
            brain.addRowToTrainingSet(
                    new double[]{0, 0, 0, 0, 0, 0, 0, 0, 2, i},
                    new double[]{1, 0, 0, 1, 1, 0, 0, 0});
            brain.addRowToTrainingSet(
                    new double[]{0, 0, 0, 0, 0, 0, 0, 2, 0, i},
                    new double[]{0, 0, 0, 1, 1, 0, 0, 0});
            brain.addRowToTrainingSet(
                    new double[]{0, 0, 0, 0, 0, 0, 2, 0, 0, i},
                    new double[]{0, 0, 1, 1, 1, 0, 0, 0});
            brain.addRowToTrainingSet(
                    new double[]{0, 0, 0, 0, 0, 2, 0, 0, 0, i},
                    new double[]{1, 0, 0, 0, 1, 0, 0, 0});
            brain.addRowToTrainingSet(
                    new double[]{0, 0, 0, 0, 2, 0, 0, 0, 0, i},
                    new double[]{0, 0, 0, 0, 0, 1, 0, 0});
            brain.addRowToTrainingSet(
                    new double[]{0, 0, 0, 2, 0, 0, 0, 0, 0, i},
                    new double[]{0, 0, 1, 0, 1, 0, 0, 0});
            brain.addRowToTrainingSet(
                    new double[]{0, 0, 2, 0, 0, 0, 0, 0, 0, i},
                    new double[]{1, 1, 0, 0, 1, 0, 0, 0});
            brain.addRowToTrainingSet(
                    new double[]{0, 2, 0, 0, 0, 0, 0, 0, 0, i},
                    new double[]{0, 1, 0, 0, 1, 0, 0, 0});
            brain.addRowToTrainingSet(
                    new double[]{2, 0, 0, 0, 0, 0, 0, 0, 0, i},
                    new double[]{0, 1, 1, 0, 1, 0, 0, 0});
        }

        //Размножение происходит только при большом HP             hp
        for(int i = 2; i <= 2; i++ ) {
            brain.addRowToTrainingSet(
                    new double[]{0, 0, 0, 0, 0, 0, 0, 0, 3, i},
                    new double[]{1, 0, 0, 1, 1, 0, 0, 0});
            brain.addRowToTrainingSet(
                    new double[]{0, 0, 0, 0, 0, 0, 0, 3, 0, i},
                    new double[]{0, 0, 0, 1, 1, 0, 0, 0});
            brain.addRowToTrainingSet(
                    new double[]{0, 0, 0, 0, 0, 0, 3, 0, 0, i},
                    new double[]{0, 0, 1, 1, 1, 0, 0, 0});
            brain.addRowToTrainingSet(
                    new double[]{0, 0, 0, 0, 0, 3, 0, 0, 0, i},
                    new double[]{1, 0, 0, 0, 1, 0, 0, 0});
            brain.addRowToTrainingSet(
                    new double[]{0, 0, 0, 0, 3, 0, 0, 0, 0, i},
                    new double[]{0, 0, 0, 0, 0, 0, 0, 1});
            brain.addRowToTrainingSet(
                    new double[]{0, 0, 0, 3, 0, 0, 0, 0, 0, i},
                    new double[]{0, 0, 1, 0, 1, 0, 0, 0});
            brain.addRowToTrainingSet(
                    new double[]{0, 0, 3, 0, 0, 0, 0, 0, 0, i},
                    new double[]{1, 1, 0, 0, 1, 0, 0, 0});
            brain.addRowToTrainingSet(
                    new double[]{0, 3, 0, 0, 0, 0, 0, 0, 0, i},
                    new double[]{0, 1, 0, 0, 1, 0, 0, 0});
            brain.addRowToTrainingSet(
                    new double[]{3, 0, 0, 0, 0, 0, 0, 0, 0, i},
                    new double[]{0, 1, 1, 0, 1, 0, 0, 0});
        }
        brain.learn();
    }

    protected void setAliveTexture(){
        texture = new Texture("alive_predator_lizard.png");
    }

    protected void setDeadTexture(){
        texture = new Texture("dead_predator_lizard.png");
    }
}
