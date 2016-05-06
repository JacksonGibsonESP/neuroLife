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

    public Lizard(World world, int x, int y, Brain brain) {
        super(world,x,y, brain);
        System.out.printf("Lizard was borned on (%d,%d)\n",x,y);
    }

    @Override
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
        *    Комбинации значений для разных сущностей на ячейках //пока отказываюсь из-за сложноси обучения
        *    0 - никого
        *    1 - трава
        *    2 - трава и обычная ящерица
        *    3 - ящерица
        *    4 - ящерица и ящерица-хищник
        *    5 - ящерица-хищник
        *    6 - ящерица-хищник и трава           // может быть никогда и не понадобится?
        *    7 - ящерица-хищник, ящерица и трава  // слишком уж гремучая комбинация
        *
        *    Но попрбуем сделать проще:
        *    0 - никого
        *    1 - трава
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
        *  6 - кушать
        *  7 - размножение
        **/

        //поведение при пустом поле, надо бы что-то поумнее придумать
        for(int i = 0; i <= 2; i++ ) {
            brain.addRowToTrainingSet(
                    new double[]{0, 0, 0, 0, 0, 0, 0, 0, 0, i},
                    new double[]{0, 0, 0, 0, 1, 0, 0, 0});
        }

        //поедание травы при малых и средних значениях hp            hp
        for(int i = 0; i <= 1; i++ ) {
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

        //Размножение происходит при большом HP             hp
        for(int i = 2; i <= 2; i++ ) {
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
                    new double[]{0, 0, 0, 0, 0, 0, 0, 1});
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

        //Убежать от ящерки-хищника
        /*for(int i = 0; i <= 2; i++ ) {
            brain.addRowToTrainingSet(
                    new double[]{0, 0, 0, 0, 0, 0, 0, 0, 3, i},
                    new double[]{0, 1, 1, 0, 1, 0, 0, 0});
            brain.addRowToTrainingSet(
                    new double[]{0, 0, 0, 0, 0, 0, 0, 3, 0, i},
                    new double[]{0, 1, 0, 0, 1, 0, 0, 0});
            brain.addRowToTrainingSet(
                    new double[]{0, 0, 0, 0, 0, 0, 3, 0, 0, i},
                    new double[]{1, 1, 0, 0, 1, 0, 0, 0});
            brain.addRowToTrainingSet(
                    new double[]{0, 0, 0, 0, 0, 3, 0, 0, 0, i},
                    new double[]{0, 0, 1, 0, 1, 0, 0, 0});
            brain.addRowToTrainingSet(
                    new double[]{0, 0, 0, 0, 3, 0, 0, 0, 0, i},
                    new double[]{0, 0, 0, 0, 0, 1, 0, 0}); //ударит, вдруг спасёт?
            brain.addRowToTrainingSet(
                    new double[]{0, 0, 0, 3, 0, 0, 0, 0, 0, i},
                    new double[]{1, 0, 0, 0, 1, 0, 0, 0});
            brain.addRowToTrainingSet(
                    new double[]{0, 0, 3, 0, 0, 0, 0, 0, 0, i},
                    new double[]{0, 0, 1, 1, 1, 0, 0, 0});
            brain.addRowToTrainingSet(
                    new double[]{0, 3, 0, 0, 0, 0, 0, 0, 0, i},
                    new double[]{0, 0, 0, 1, 1, 0, 0, 0});
            brain.addRowToTrainingSet(
                    new double[]{3, 0, 0, 0, 0, 0, 0, 0, 0, i},
                    new double[]{1, 0, 0, 1, 1, 0, 0, 0});
        }

        //Напасть на ящерку-хищника, если обычных ящерок много и много hp
        for(int i = 2; i <= 2; i++ ) {
            brain.addRowToTrainingSet(
                    new double[]{2, 2, 2, 2, 2, 2, 2, 2, 3, i},
                    new double[]{1, 0, 0, 1, 1, 0, 0, 0});
            brain.addRowToTrainingSet(
                    new double[]{2, 2, 2, 2, 2, 2, 2, 3, 2, i},
                    new double[]{0, 0, 0, 1, 1, 0, 0, 0});
            brain.addRowToTrainingSet(
                    new double[]{2, 2, 2, 2, 2, 2, 3, 2, 2, i},
                    new double[]{0, 0, 1, 1, 1, 0, 0, 0});
            brain.addRowToTrainingSet(
                    new double[]{2, 2, 2, 2, 2, 3, 2, 2, 2, i},
                    new double[]{1, 0, 0, 0, 1, 0, 0, 0});
            brain.addRowToTrainingSet(
                    new double[]{2, 2, 2, 2, 3, 2, 2, 2, 2, i},
                    new double[]{0, 0, 0, 0, 0, 1, 0, 0});
            brain.addRowToTrainingSet(
                    new double[]{2, 2, 2, 3, 2, 2, 2, 2, 2, i},
                    new double[]{1, 0, 0, 0, 1, 0, 0, 0});
            brain.addRowToTrainingSet(
                    new double[]{2, 2, 3, 2, 2, 2, 2, 2, 2, i},
                    new double[]{0, 0, 1, 1, 1, 0, 0, 0});
            brain.addRowToTrainingSet(
                    new double[]{2, 3, 2, 2, 2, 2, 2, 2, 2, i},
                    new double[]{0, 0, 0, 1, 1, 0, 0, 0});
            brain.addRowToTrainingSet(
                    new double[]{3, 2, 2, 2, 2, 2, 2, 2, 2, i},
                    new double[]{1, 0, 0, 1, 1, 0, 0, 0});
        }*/

        brain.learn();
    }

    @Override
    protected void attack() {
        System.out.println("Attacks");
        attack.perform();
    }

    @Override
    protected void move(double[] signal) {
        System.out.println("Moves");
        movement.perform(signal);
    }

    @Override
    protected void eat() {
        System.out.println("Eats");
        eating.perform();
    }

    @Override
    protected void decomposed() {
        System.out.println("Decomposed");
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
            /*if (thoughts[4] > 0.5) {
                move(Arrays.copyOfRange(thoughts, 0, 4));
            } else if (thoughts[5] > 0.5) {
                attack();
            } else if (thoughts[6] > 0.5) {
                eat();
            } else if (thoughts[7] > 0.5) {
                reproduce();
            }*/
            //Ищем победителя среди индексов 4 - 7:
            int decision = find_winner(Arrays.copyOfRange(thoughts, 4, 8));

            switch(decision){
                case 0:
                    move(Arrays.copyOfRange(thoughts, 0, 4));
                    break;
                case 1:
                    attack();
                    break;
                case 2:
                    eat();
                    break;
                case 3:
                    reproduce();
                    break;
            }

            System.out.printf("HP: %d\n", this.HP);
        }
        System.out.println("-------------------------------------------");
    }

    private int find_winner(double[] brainOutput)
    {
        //Метод возвращает индекс победителя
        //Массив означающий текущий порядок индексов массива brainOutput
        int[] order = new int[brainOutput.length];
        for (int i = 0; i < brainOutput.length; i++) {
            order[i] = i;
        }
        //Делаем сортировку массива brainOutput, заодно меняя места в массиве направлений
        //Таким образом мы получаем отсортированный массив brainOutput и не потеряли индексы
        /*System.out.println("Check before");
        System.out.println(Arrays.toString(brainOutput));
        System.out.println(Arrays.toString(order));*/
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (brainOutput[j] > brainOutput[j+1]) {
                    int tmp = order[j];
                    order[j] = order[j+1];
                    order[j+1] = tmp;
                    double tmp2 = brainOutput[j];
                    brainOutput[j] = brainOutput[j + 1];
                    brainOutput[j + 1] = tmp2;
                }
            }
        }
        return order[order.length - 1];
    }

    @Override
    public Brain getBrain() {
        return brain;
    }

    public int bitten() //укушен когда мёртв
    {
        this.HP -= 10;
        if(this.HP <= 0)
        {
            dead();
        }
        return 10;
    }

    public void attacked(Creature attacker){
        if (attacker instanceof Predator_Lizard) {
            this.HP -= 20;
        }
        else if (attacker instanceof Lizard) //используется только в наследнике
        {
            this.HP -= 5;
        }
        if (this.alive & this.HP <= 0) {
            dead();
        }
    }

    public void reproduce()
    {
        System.out.println("Trying to reproduce");
        reproduce.perform();
    }

    public void die()
    {
        dead();
    }
}
