package com.dsile.core.entities;

import com.badlogic.gdx.graphics.Texture;
import com.dsile.core.entities.actions.vision.Lizard_Vision;
import com.dsile.core.neural.Brain;
import com.dsile.core.world.World;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.Locale;
import java.util.Scanner;

/**
 * Тестовое существо для отладки функций приложения.
 * Впоследствии будет развита иерархия существ и деления на падальщиков, хищинков и травоядных.
 *
 * Created by DeSile on 08.12.2015.
 */
public abstract class Lizard extends Creature {
    /**
     * Создание существа (актера) в клеточном мире.
     */
    public Lizard(World world, int x, int y) {
        super(world,x,y);
        //this.id = 1;
    }

    public Lizard(World world, int x, int y, Brain brain, boolean newborn) {
        super(world,x,y, brain);
        //this.id = 1;
        if (newborn){
            setNewbornTexture();
        }
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
        *    Значения в каждой ячейке описываются тремя входами идущими подряд.
        *    Значения это HP за исключением травы и падали (просто 1)
        *
        *    9-й вход сети - уровень жизни существа - hp, значения:
        *    0.0 ... 1.0
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

        //Будем считывать из файла
        try {
            FileReader file = new FileReader(data_set_filename);
            Scanner scan = new Scanner(file);
            scan.useLocale(Locale.US);
            while(scan.hasNextDouble())
            {
                double [] in    = new double[28];
                double [] out   = new double [8];
                for (int i = 0; i < 28; i++)
                {
                    in[i] = scan.nextDouble();
                }
                for (int i = 0; i < 8; i++)
                {
                    out[i] = scan.nextDouble();
                }
                //System.out.println(Arrays.toString(in));
                //System.out.println(Arrays.toString(out));
                brain.addRowToTrainingSet(in, out);
            }
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }
        brain.learn();
    }

    @Override
    protected void attack(double[] signal) {
        if (this.logger.isDebugEnabled()) {
            this.logger.debug("Attacks");
        }
        attack.perform(signal);
    }

    @Override
    protected void move(double[] signal) {
        if (this.logger.isDebugEnabled()) {
            this.logger.debug("Moves");
        }
        movement.perform(signal);
    }

    @Override
    protected void eat() {
        if (this.logger.isDebugEnabled()) {
            this.logger.debug("Eats");
        }
        eating.perform();
    }

    @Override
    protected void decomposed() {
        if (this.logger.isDebugEnabled()) {
            this.logger.debug("Decomposed");
        }
        currentCell.removeEntity(this);
        world.removeEntity(this);
        remove();
    }

    @Override
    protected void dead() {
        if(alive) {
            alive = false;
            this.HP = this.maxHP; //Пусть будет энергией от поедания разлагающегося трупа
            //energy = maxEnergy;
            this.brain = null;
            setDeadTexture();
        }
        //процесс разложения
        this.HP -= 10; //разложение тела
        if(this.HP <= 0){
            decomposed();
        }
        if (this.logger.isDebugEnabled()) {
            this.logger.debug("Dead Lizard Body Energy: {}", this.HP);
        }
    }

    /**
     * Метод-тик выполняющийся непрерывно в ходе работы программы.
     * Содержит в себе всю логику, которую существо способно выполнить за тик.
     * @param delta задержка по времени между выполнением тика
     */
    @Override
    public void act(float delta) {
        this.incLife_duration();
        if(this.alive & this.HP <= 0){
            dead();
        }
        else if(!this.alive)
        {
            dead();
        }
        else {
            this.HP -= 5; //плата за жизнь
            if (this.logger.isDebugEnabled()) {
                this.logger.debug("HP: {}", this.HP);
            }
            double[] thoughts = vision.accessSituation();
            //Ищем победителя среди индексов 4 - 7:
            int decision = find_winner(Arrays.copyOfRange(thoughts, 4, 8));

            switch(decision){
                case 0:
                    move(Arrays.copyOfRange(thoughts, 0, 4));
                    break;
                case 1:
                    attack(Arrays.copyOfRange(thoughts, 0, 4)); //Атака в соседнюю клетку или в ту же
                    break;
                case 2:
                    eat();
                    break;
                case 3:
                    reproduce();
                    break;
            }
        }
        if (this.logger.isDebugEnabled()) {
            this.logger.debug("-------------------------------------------");
        }
    }

    private int find_winner(double[] brainOutput)
    {
        /*if (this.getnormalizedHP() < 0.7) {
            brainOutput[3] = 0;
        }*/
        //System.out.println(Arrays.toString(brainOutput));
        //Метод возвращает индекс победителя
        //Массив означающий текущий порядок индексов массива brainOutput
        int[] order = new int[brainOutput.length];
        for (int i = 0; i < brainOutput.length; i++) {
            order[i] = i;
        }

        //Делаем сортировку массива brainOutput, заодно меняя места в массиве направлений
        //Таким образом мы получаем отсортированный массив brainOutput и не потеряли индексы
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
            this.HP -= attacker.getnormalizedHP() * 200;
        }
        else if (attacker instanceof Lizard) //используется только в наследнике
        {
            this.HP -= 10;
        }
        if (this.alive & this.HP <= 0) {
            dead();
        }
    }

    public void reproduce()
    {
        if (this.logger.isDebugEnabled()) {
            this.logger.debug("Trying to reproduce");
        }
        reproduce.perform();
    }

    public void die()
    {
        dead();
    }
}
