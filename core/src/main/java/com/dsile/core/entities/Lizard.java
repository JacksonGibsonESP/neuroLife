package com.dsile.core.entities;

import com.badlogic.gdx.graphics.Texture;
import com.dsile.core.neural.Brain;
import com.dsile.core.world.World;

import java.util.Arrays;

/**
 * Тестовое существо для отладки функций приложения.
 * Впоследствии будет развита иерархия существ и деления на падальщиков, хищинков и травоядных.
 *
 * Created by DeSile on 08.12.2015.
 */
//TODO: Создать абстрактный класс, чтобы избежать создания анонимного класса в WorldScreen.
public class Lizard extends Creature {

    /*protected int maxHp = 50;
    protected int maxEnergy = 20;
    protected int hp = maxHp;
    protected int energy = maxEnergy;*/

    /**
     * Создание существа (актера) в клеточном мире.
     */
    public Lizard(World world, int x, int y) {
        super(world,x,y);
        System.out.printf("Lizard created on (%d,%d)\n",x,y);
    }

    @Override
    public void learn() {

        //TODO: Генерировать массивы в отдельном классе

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
        *    9 - уровень жизни (HP)
        *
        *    Значение - событие на ячейке (0 - ничего, 1 - растение, 2 - ящерка, 3 - ящерка-хищник)
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
        *
        *  Остальные ячейки: действия (идти, атакатовать, есть)
        *
        *  4 - перемещение
        *  5 - атака
        *  6 - кушать
        *  7 - размножение
        **/
        /*
        //поведение при пустом поле, надо бы что-то поумнее придумать
        brain.addRowToTrainingSet(new double[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 10}, new double[]{0, 0, 0, 0, 1, 0, 0, 0});
        brain.addRowToTrainingSet(new double[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 20}, new double[]{0, 0, 0, 0, 1, 0, 0, 0});
        brain.addRowToTrainingSet(new double[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 30}, new double[]{0, 0, 0, 0, 1, 0, 0, 0});
        brain.addRowToTrainingSet(new double[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 40}, new double[]{0, 0, 0, 0, 1, 0, 0, 0});
        brain.addRowToTrainingSet(new double[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 50}, new double[]{0, 0, 0, 0, 1, 0, 0, 0});
        brain.addRowToTrainingSet(new double[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 60}, new double[]{0, 0, 0, 0, 1, 0, 0, 0});
        brain.addRowToTrainingSet(new double[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 70}, new double[]{0, 0, 0, 0, 1, 0, 0, 0});
        brain.addRowToTrainingSet(new double[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 80}, new double[]{0, 0, 0, 0, 1, 0, 0, 0});
        brain.addRowToTrainingSet(new double[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 90}, new double[]{0, 0, 0, 0, 1, 0, 0, 0});
        brain.addRowToTrainingSet(new double[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 100}, new double[]{0, 0, 0, 0, 1, 0, 0, 0});

        //поедание травы при разных значениях hp                          hp
        brain.addRowToTrainingSet(new double[]{0, 0, 0, 0, 0, 0, 0, 0, 1, 10}, new double[]{1, 0, 0, 1, 1, 0, 0, 0});
        brain.addRowToTrainingSet(new double[]{0, 0, 0, 0, 0, 0, 0, 1, 0, 10}, new double[]{0, 0, 0, 1, 1, 0, 0, 0});
        brain.addRowToTrainingSet(new double[]{0, 0, 0, 0, 0, 0, 1, 0, 0, 10}, new double[]{0, 0, 1, 1, 1, 0, 0, 0});
        brain.addRowToTrainingSet(new double[]{0, 0, 0, 0, 0, 1, 0, 0, 0, 10}, new double[]{1, 0, 0, 0, 1, 0, 0, 0});
        brain.addRowToTrainingSet(new double[]{0, 0, 0, 0, 1, 0, 0, 0, 0, 10}, new double[]{0, 0, 0, 0, 0, 0, 1, 0});
        brain.addRowToTrainingSet(new double[]{0, 0, 0, 1, 0, 0, 0, 0, 0, 10}, new double[]{0, 0, 1, 0, 1, 0, 0, 0});
        brain.addRowToTrainingSet(new double[]{0, 0, 1, 0, 0, 0, 0, 0, 0, 10}, new double[]{1, 1, 0, 0, 1, 0, 0, 0});
        brain.addRowToTrainingSet(new double[]{0, 1, 0, 0, 0, 0, 0, 0, 0, 10}, new double[]{0, 1, 0, 0, 1, 0, 0, 0});
        brain.addRowToTrainingSet(new double[]{1, 0, 0, 0, 0, 0, 0, 0, 0, 10}, new double[]{0, 1, 1, 0, 1, 0, 0, 0});

        brain.addRowToTrainingSet(new double[]{0, 0, 0, 0, 0, 0, 0, 0, 1, 30}, new double[]{1, 0, 0, 1, 1, 0, 0, 0});
        brain.addRowToTrainingSet(new double[]{0, 0, 0, 0, 0, 0, 0, 1, 0, 30}, new double[]{0, 0, 0, 1, 1, 0, 0, 0});
        brain.addRowToTrainingSet(new double[]{0, 0, 0, 0, 0, 0, 1, 0, 0, 30}, new double[]{0, 0, 1, 1, 1, 0, 0, 0});
        brain.addRowToTrainingSet(new double[]{0, 0, 0, 0, 0, 1, 0, 0, 0, 30}, new double[]{1, 0, 0, 0, 1, 0, 0, 0});
        brain.addRowToTrainingSet(new double[]{0, 0, 0, 0, 1, 0, 0, 0, 0, 30}, new double[]{0, 0, 0, 0, 0, 0, 1, 0});
        brain.addRowToTrainingSet(new double[]{0, 0, 0, 1, 0, 0, 0, 0, 0, 30}, new double[]{0, 0, 1, 0, 1, 0, 0, 0});
        brain.addRowToTrainingSet(new double[]{0, 0, 1, 0, 0, 0, 0, 0, 0, 30}, new double[]{1, 1, 0, 0, 1, 0, 0, 0});
        brain.addRowToTrainingSet(new double[]{0, 1, 0, 0, 0, 0, 0, 0, 0, 30}, new double[]{0, 1, 0, 0, 1, 0, 0, 0});
        brain.addRowToTrainingSet(new double[]{1, 0, 0, 0, 0, 0, 0, 0, 0, 30}, new double[]{0, 1, 1, 0, 1, 0, 0, 0});

        brain.addRowToTrainingSet(new double[]{0, 0, 0, 0, 0, 0, 0, 0, 1, 50}, new double[]{1, 0, 0, 1, 1, 0, 0, 0});
        brain.addRowToTrainingSet(new double[]{0, 0, 0, 0, 0, 0, 0, 1, 0, 50}, new double[]{0, 0, 0, 1, 1, 0, 0, 0});
        brain.addRowToTrainingSet(new double[]{0, 0, 0, 0, 0, 0, 1, 0, 0, 50}, new double[]{0, 0, 1, 1, 1, 0, 0, 0});
        brain.addRowToTrainingSet(new double[]{0, 0, 0, 0, 0, 1, 0, 0, 0, 50}, new double[]{1, 0, 0, 0, 1, 0, 0, 0});
        brain.addRowToTrainingSet(new double[]{0, 0, 0, 0, 1, 0, 0, 0, 0, 50}, new double[]{0, 0, 0, 0, 0, 0, 1, 0});
        brain.addRowToTrainingSet(new double[]{0, 0, 0, 1, 0, 0, 0, 0, 0, 50}, new double[]{0, 0, 1, 0, 1, 0, 0, 0});
        brain.addRowToTrainingSet(new double[]{0, 0, 1, 0, 0, 0, 0, 0, 0, 50}, new double[]{1, 1, 0, 0, 1, 0, 0, 0});
        brain.addRowToTrainingSet(new double[]{0, 1, 0, 0, 0, 0, 0, 0, 0, 50}, new double[]{0, 1, 0, 0, 1, 0, 0, 0});
        brain.addRowToTrainingSet(new double[]{1, 0, 0, 0, 0, 0, 0, 0, 0, 50}, new double[]{0, 1, 1, 0, 1, 0, 0, 0});

        brain.addRowToTrainingSet(new double[]{0, 0, 0, 0, 0, 0, 0, 0, 1, 70}, new double[]{1, 0, 0, 1, 1, 0, 0, 0});
        brain.addRowToTrainingSet(new double[]{0, 0, 0, 0, 0, 0, 0, 1, 0, 70}, new double[]{0, 0, 0, 1, 1, 0, 0, 0});
        brain.addRowToTrainingSet(new double[]{0, 0, 0, 0, 0, 0, 1, 0, 0, 70}, new double[]{0, 0, 1, 1, 1, 0, 0, 0});
        brain.addRowToTrainingSet(new double[]{0, 0, 0, 0, 0, 1, 0, 0, 0, 70}, new double[]{1, 0, 0, 0, 1, 0, 0, 0});
        brain.addRowToTrainingSet(new double[]{0, 0, 0, 0, 1, 0, 0, 0, 0, 70}, new double[]{0, 0, 0, 0, 0, 0, 1, 0});
        brain.addRowToTrainingSet(new double[]{0, 0, 0, 1, 0, 0, 0, 0, 0, 70}, new double[]{0, 0, 1, 0, 1, 0, 0, 0});
        brain.addRowToTrainingSet(new double[]{0, 0, 1, 0, 0, 0, 0, 0, 0, 70}, new double[]{1, 1, 0, 0, 1, 0, 0, 0});
        brain.addRowToTrainingSet(new double[]{0, 1, 0, 0, 0, 0, 0, 0, 0, 70}, new double[]{0, 1, 0, 0, 1, 0, 0, 0});
        brain.addRowToTrainingSet(new double[]{1, 0, 0, 0, 0, 0, 0, 0, 0, 70}, new double[]{0, 1, 1, 0, 1, 0, 0, 0});

        //Убежать от ящерки-хищника
        brain.addRowToTrainingSet(new double[]{0, 0, 0, 0, 0, 0, 0, 0, 3, 10}, new double[]{0, 1, 1, 0, 1, 0, 0, 0});
        brain.addRowToTrainingSet(new double[]{0, 0, 0, 0, 0, 0, 0, 3, 0, 10}, new double[]{0, 1, 0, 0, 1, 0, 0, 0});
        brain.addRowToTrainingSet(new double[]{0, 0, 0, 0, 0, 0, 3, 0, 0, 10}, new double[]{1, 1, 0, 0, 1, 0, 0, 0});
        brain.addRowToTrainingSet(new double[]{0, 0, 0, 0, 0, 3, 0, 0, 0, 10}, new double[]{0, 0, 1, 0, 1, 0, 0, 0});
        brain.addRowToTrainingSet(new double[]{0, 0, 0, 0, 3, 0, 0, 0, 0, 10}, new double[]{0, 0, 0, 0, 0, 1, 0, 0});
        brain.addRowToTrainingSet(new double[]{0, 0, 0, 3, 0, 0, 0, 0, 0, 10}, new double[]{1, 0, 0, 0, 1, 0, 0, 0});
        brain.addRowToTrainingSet(new double[]{0, 0, 3, 0, 0, 0, 0, 0, 0, 10}, new double[]{0, 0, 1, 1, 1, 0, 0, 0});
        brain.addRowToTrainingSet(new double[]{0, 3, 0, 0, 0, 0, 0, 0, 0, 10}, new double[]{0, 0, 0, 1, 1, 0, 0, 0});
        brain.addRowToTrainingSet(new double[]{3, 0, 0, 0, 0, 0, 0, 0, 0, 10}, new double[]{1, 0, 0, 1, 1, 0, 0, 0});

        brain.addRowToTrainingSet(new double[]{0, 0, 0, 0, 0, 0, 0, 0, 3, 30}, new double[]{0, 1, 1, 0, 1, 0, 0, 0});
        brain.addRowToTrainingSet(new double[]{0, 0, 0, 0, 0, 0, 0, 3, 0, 30}, new double[]{0, 1, 0, 0, 1, 0, 0, 0});
        brain.addRowToTrainingSet(new double[]{0, 0, 0, 0, 0, 0, 3, 0, 0, 30}, new double[]{1, 1, 0, 0, 1, 0, 0, 0});
        brain.addRowToTrainingSet(new double[]{0, 0, 0, 0, 0, 3, 0, 0, 0, 30}, new double[]{0, 0, 1, 0, 1, 0, 0, 0});
        brain.addRowToTrainingSet(new double[]{0, 0, 0, 0, 3, 0, 0, 0, 0, 30}, new double[]{0, 0, 0, 0, 0, 1, 0, 0});
        brain.addRowToTrainingSet(new double[]{0, 0, 0, 3, 0, 0, 0, 0, 0, 30}, new double[]{1, 0, 0, 0, 1, 0, 0, 0});
        brain.addRowToTrainingSet(new double[]{0, 0, 3, 0, 0, 0, 0, 0, 0, 30}, new double[]{0, 0, 1, 1, 1, 0, 0, 0});
        brain.addRowToTrainingSet(new double[]{0, 3, 0, 0, 0, 0, 0, 0, 0, 30}, new double[]{0, 0, 0, 1, 1, 0, 0, 0});
        brain.addRowToTrainingSet(new double[]{3, 0, 0, 0, 0, 0, 0, 0, 0, 30}, new double[]{1, 0, 0, 1, 1, 0, 0, 0});

        brain.addRowToTrainingSet(new double[]{0, 0, 0, 0, 0, 0, 0, 0, 3, 40}, new double[]{0, 1, 1, 0, 1, 0, 0, 0});
        brain.addRowToTrainingSet(new double[]{0, 0, 0, 0, 0, 0, 0, 3, 0, 40}, new double[]{0, 1, 0, 0, 1, 0, 0, 0});
        brain.addRowToTrainingSet(new double[]{0, 0, 0, 0, 0, 0, 3, 0, 0, 40}, new double[]{1, 1, 0, 0, 1, 0, 0, 0});
        brain.addRowToTrainingSet(new double[]{0, 0, 0, 0, 0, 3, 0, 0, 0, 40}, new double[]{0, 0, 1, 0, 1, 0, 0, 0});
        brain.addRowToTrainingSet(new double[]{0, 0, 0, 0, 3, 0, 0, 0, 0, 40}, new double[]{0, 0, 0, 0, 0, 1, 0, 0});
        brain.addRowToTrainingSet(new double[]{0, 0, 0, 3, 0, 0, 0, 0, 0, 40}, new double[]{1, 0, 0, 0, 1, 0, 0, 0});
        brain.addRowToTrainingSet(new double[]{0, 0, 3, 0, 0, 0, 0, 0, 0, 40}, new double[]{0, 0, 1, 1, 1, 0, 0, 0});
        brain.addRowToTrainingSet(new double[]{0, 3, 0, 0, 0, 0, 0, 0, 0, 40}, new double[]{0, 0, 0, 1, 1, 0, 0, 0});
        brain.addRowToTrainingSet(new double[]{3, 0, 0, 0, 0, 0, 0, 0, 0, 40}, new double[]{1, 0, 0, 1, 1, 0, 0, 0});

        brain.addRowToTrainingSet(new double[]{0, 0, 0, 0, 0, 0, 0, 0, 3, 50}, new double[]{0, 1, 1, 0, 1, 0, 0, 0});
        brain.addRowToTrainingSet(new double[]{0, 0, 0, 0, 0, 0, 0, 3, 0, 50}, new double[]{0, 1, 0, 0, 1, 0, 0, 0});
        brain.addRowToTrainingSet(new double[]{0, 0, 0, 0, 0, 0, 3, 0, 0, 50}, new double[]{1, 1, 0, 0, 1, 0, 0, 0});
        brain.addRowToTrainingSet(new double[]{0, 0, 0, 0, 0, 3, 0, 0, 0, 50}, new double[]{0, 0, 1, 0, 1, 0, 0, 0});
        brain.addRowToTrainingSet(new double[]{0, 0, 0, 0, 3, 0, 0, 0, 0, 50}, new double[]{0, 0, 0, 0, 0, 1, 0, 0});
        brain.addRowToTrainingSet(new double[]{0, 0, 0, 3, 0, 0, 0, 0, 0, 50}, new double[]{1, 0, 0, 0, 1, 0, 0, 0});
        brain.addRowToTrainingSet(new double[]{0, 0, 3, 0, 0, 0, 0, 0, 0, 50}, new double[]{0, 0, 1, 1, 1, 0, 0, 0});
        brain.addRowToTrainingSet(new double[]{0, 3, 0, 0, 0, 0, 0, 0, 0, 50}, new double[]{0, 0, 0, 1, 1, 0, 0, 0});
        brain.addRowToTrainingSet(new double[]{3, 0, 0, 0, 0, 0, 0, 0, 0, 50}, new double[]{1, 0, 0, 1, 1, 0, 0, 0});
*/
        brain.addRowToTrainingSet(new double[]{0, 0, 0, 0, 0, 0, 0, 0, 0}, new double[]{0, 0, 0, 0, 1, 0, 0});


        brain.addRowToTrainingSet(new double[]{0, 0, 0, 0, 0, 0, 0, 0, 1}, new double[]{1, 0, 0, 1, 1, 0, 0});
        brain.addRowToTrainingSet(new double[]{0, 0, 0, 0, 0, 0, 0, 1, 0}, new double[]{0, 0, 0, 1, 1, 0, 0});
        brain.addRowToTrainingSet(new double[]{0, 0, 0, 0, 0, 0, 1, 0, 0}, new double[]{0, 0, 1, 1, 1, 0, 0});
        brain.addRowToTrainingSet(new double[]{0, 0, 0, 0, 0, 1, 0, 0, 0}, new double[]{1, 0, 0, 0, 1, 0, 0});
        brain.addRowToTrainingSet(new double[]{0, 0, 0, 0, 1, 0, 0, 0, 0}, new double[]{0, 0, 0, 0, 0, 0, 1});
        brain.addRowToTrainingSet(new double[]{0, 0, 0, 1, 0, 0, 0, 0, 0}, new double[]{0, 0, 1, 0, 1, 0, 0});
        brain.addRowToTrainingSet(new double[]{0, 0, 1, 0, 0, 0, 0, 0, 0}, new double[]{1, 1, 0, 0, 1, 0, 0});
        brain.addRowToTrainingSet(new double[]{0, 1, 0, 0, 0, 0, 0, 0, 0}, new double[]{0, 1, 0, 0, 1, 0, 0});
        brain.addRowToTrainingSet(new double[]{1, 0, 0, 0, 0, 0, 0, 0, 0}, new double[]{0, 1, 1, 0, 1, 0, 0});

        brain.learn();
    }

    @Override
    protected void attack() {

    }

    @Override
    protected void move(double[] signal) {
        movement.perform(signal);
    }

    @Override
    protected void eat() {
        System.out.println("Eats");
        eating.perform();
    }

    @Override
    protected void decomposed() {
        currentCell.removeEntity(this);
        remove();
    }

    @Override
    protected void setAliveTexture(){
        texture = new Texture("alive_lizard.png");
    }

    protected void setDeadTexture(){
        texture = new Texture("dead_lizard.png");
    }

    @Override
    protected void dead() {
        if(alive) {
            alive = false;
            this.HP = this.maxHP; //Пусть будет энергией от поедания разлагающегося трупа
            //energy = maxEnergy;
            setDeadTexture();
        }
        //процесс разложения
        this.HP -= 10; //разложение тела
        if(this.HP <= 0){
            decomposed();
            System.out.println("Dead Lizard Body Decomposed");
        }
        System.out.printf("Dead Lizard Body Energy: %d\n", this.HP);
    }

    /**
     * Метод-тик выполняющийся непрерывно в ходе работы программы.
     * Содержит в себе всю логику, которую существо способно выполнить за тик.
     * @param delta задержка по времени между выполнением тика
     */
    @Override
    public void act(float delta) {
        if(this.alive & this.HP <= 0){
            dead();
        }
        else if(!this.alive)
        {
            dead();
        }
        else {
            this.HP -= 5; //плата за жизнь
            double[] thoughts = vision.accessSituation();
            if (thoughts[4] > 0.5) {
                move(Arrays.copyOfRange(thoughts, 0, 4));
            } else if (thoughts[5] > 0.5) {

            } else if (thoughts[6] > 0.5) {
                eat();
            }
            System.out.printf("HP: %d\n", this.HP);
        }
        System.out.println("-------------------------------------------");
    }

    @Override
    public Brain getBrain() {
        return brain;
    }

    public int bitten()
    {
        return 0;
    }

    public void attacked(Creature attacker){
        if (attacker instanceof Predator_Lizard) {
            this.HP -= 20;
        }
        else if (attacker instanceof Lizard)
        {
            this.HP -= 5;
        }
        if (this.alive & this.HP <= 0) {
            dead();
        }
    }
}
