package com.dsile.core.generator;

import com.dsile.core.entities.actions.Actions;
import com.dsile.core.entities.actions.movement.DirectionValues;

import java.io.FileWriter;
import java.io.IOException;

/**
 * Класс генерирующий обучающее множесво для нейросети
 *
 * Created by Никита on 13.05.2016.
 */
public class Generator {
    private final String lizard_data_set_file_name          = "Lizard_data_set.txt";
    private final String predator_lizard_data_set_file_name = "Predator_Lizard_data_set.txt";
    private FileWriter writer;

    private void to_file(double [] in, double [] out)
    {
        try {
            for (int i = 0; i < in.length - 1; i++) {
                this.writer.write(String.valueOf(in[i]) + ' ');
            }
            writer.write(String.valueOf(in[in.length - 1]) + '\n');
            for (int i = 0; i < out.length - 1; i++) {
                this.writer.write(String.valueOf(out[i]) + ' ');
            }
            this.writer.write(String.valueOf(out[out.length - 1]) + '\n');
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }

    private void to_file(double [] herb_field, double [] lizard_field, double [] predator_lizard_field, double hp, DirectionValues dir, Actions decision)
    {
        double [] in  = new double[28];
        double [] out = new double [8];

        int index = 0;
        for (int i = 0; i < 27; i+=3)
        {
            in[i] = herb_field[index];
            index++;
        }

        index = 0;
        for (int i = 1; i < 27; i+=3)
        {
            in[i] = lizard_field[index];
            index++;
        }

        index = 0;
        for (int i = 2; i < 27; i+=3)
        {
            in[i] = predator_lizard_field[index];
            index++;
        }

        in[27] = hp;

        switch (dir)
        {
            case NO_DIRECTION:
                out[0] = 0;
                out[1] = 0;
                out[2] = 0;
                out[3] = 0;
                break;
            case EAST:
                out[0] = 1;
                out[1] = 0;
                out[2] = 0;
                out[3] = 0;
                break;
            case NORTH_EAST:
                out[0] = 1;
                out[1] = 1;
                out[2] = 0;
                out[3] = 0;
                break;
            case NORTH:
                out[0] = 0;
                out[1] = 1;
                out[2] = 0;
                out[3] = 0;
                break;
            case NORTH_WEST:
                out[0] = 0;
                out[1] = 1;
                out[2] = 1;
                out[3] = 0;
                break;
            case WEST:
                out[0] = 0;
                out[1] = 0;
                out[2] = 1;
                out[3] = 0;
                break;
            case SOUTH_WEST:
                out[0] = 0;
                out[1] = 0;
                out[2] = 1;
                out[3] = 1;
                break;
            case SOUTH:
                out[0] = 0;
                out[1] = 0;
                out[2] = 0;
                out[3] = 1;
                break;
            case SOUTH_EAST:
                out[0] = 1;
                out[1] = 0;
                out[2] = 0;
                out[3] = 1;
                break;
        }

        switch (decision){
            case MOVEMENT:
                out[4] = 1;
                out[5] = 0;
                out[6] = 0;
                out[7] = 0;
                break;
            case ATTACK:
                out[4] = 0;
                out[5] = 1;
                out[6] = 0;
                out[7] = 0;
                break;
            case EATING:
                out[4] = 0;
                out[5] = 0;
                out[6] = 1;
                out[7] = 0;
                break;
            case REPRODUCE:
                out[4] = 0;
                out[5] = 0;
                out[6] = 0;
                out[7] = 1;
                break;
        }

        to_file(in, out);
    }

    public void lizard_gen(){
        try {
            this.writer = new FileWriter(lizard_data_set_file_name);
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }

        //Запись в файл обучаемого множества для травоядных ящерок.
        double [] herb_field;
        double [] lizard_field;
        double [] predator_lizard_field;
        Actions decision;
        DirectionValues dir;

        //Ситуация замешательства, рандомное движение
        herb_field = new double[]
                {
                        0, 0, 0,
                        0, 0, 0,
                        0, 0, 0
                };

        lizard_field = new double[]
                {
                        0, 0, 0,
                        0, 0, 0,
                        0, 0, 0
                };

        predator_lizard_field = new double[]
                {
                        0, 0, 0,
                        0, 0, 0,
                        0, 0, 0
                };

        dir = DirectionValues.NO_DIRECTION;
        decision = Actions.MOVEMENT;
        to_file(herb_field, lizard_field, predator_lizard_field, 0.3, dir, decision);
        to_file(herb_field, lizard_field, predator_lizard_field, 0.5, dir, decision);
        to_file(herb_field, lizard_field, predator_lizard_field, 0.7, dir, decision);

        //Поедание пищи при малом и среднем значении hp
        lizard_field = new double[]
                {
                        0, 0, 0,
                        0, 0, 0,
                        0, 0, 0
                };

        predator_lizard_field = new double[]
                {
                        0, 0, 0,
                        0, 0, 0,
                        0, 0, 0
                };

        //1
        herb_field = new double[]
                {
                        1, 0, 0,
                        0, 0, 0,
                        0, 0, 0
                };

        dir = DirectionValues.NORTH_WEST;
        decision = Actions.MOVEMENT;
        to_file(herb_field, lizard_field, predator_lizard_field, 0.3, dir, decision);
        to_file(herb_field, lizard_field, predator_lizard_field, 0.5, dir, decision);

        //2
        herb_field = new double[]
                {
                        0, 1, 0,
                        0, 0, 0,
                        0, 0, 0
                };

        dir = DirectionValues.NORTH;
        decision = Actions.MOVEMENT;
        to_file(herb_field, lizard_field, predator_lizard_field, 0.3, dir, decision);
        to_file(herb_field, lizard_field, predator_lizard_field, 0.5, dir, decision);

        //3
        herb_field = new double[]
                {
                        0, 0, 1,
                        0, 0, 0,
                        0, 0, 0
                };

        dir = DirectionValues.NORTH_EAST;
        decision = Actions.MOVEMENT;
        to_file(herb_field, lizard_field, predator_lizard_field, 0.3, dir, decision);
        to_file(herb_field, lizard_field, predator_lizard_field, 0.5, dir, decision);

        //4
        herb_field = new double[]
                {
                        0, 0, 0,
                        1, 0, 0,
                        0, 0, 0
                };

        dir = DirectionValues.WEST;
        decision = Actions.MOVEMENT;
        to_file(herb_field, lizard_field, predator_lizard_field, 0.3, dir, decision);
        to_file(herb_field, lizard_field, predator_lizard_field, 0.5, dir, decision);

        //5
        herb_field = new double[]
                {
                        0, 0, 0,
                        0, 1, 0,
                        0, 0, 0
                };

        dir = DirectionValues.NO_DIRECTION;
        decision = Actions.EATING;
        to_file(herb_field, lizard_field, predator_lizard_field, 0.3, dir, decision);
        to_file(herb_field, lizard_field, predator_lizard_field, 0.5, dir, decision);

        //6
        herb_field = new double[]
                {
                        0, 0, 0,
                        0, 0, 1,
                        0, 0, 0
                };

        dir = DirectionValues.EAST;
        decision = Actions.MOVEMENT;
        to_file(herb_field, lizard_field, predator_lizard_field, 0.3, dir, decision);
        to_file(herb_field, lizard_field, predator_lizard_field, 0.5, dir, decision);

        //7
        herb_field = new double[]
                {
                        0, 0, 0,
                        0, 0, 0,
                        1, 0, 0
                };

        dir = DirectionValues.SOUTH_WEST;
        decision = Actions.MOVEMENT;
        to_file(herb_field, lizard_field, predator_lizard_field, 0.3, dir, decision);
        to_file(herb_field, lizard_field, predator_lizard_field, 0.5, dir, decision);

        //8
        herb_field = new double[]
                {
                        0, 0, 0,
                        0, 0, 0,
                        0, 1, 0
                };

        dir = DirectionValues.SOUTH;
        decision = Actions.MOVEMENT;
        to_file(herb_field, lizard_field, predator_lizard_field, 0.3, dir, decision);
        to_file(herb_field, lizard_field, predator_lizard_field, 0.5, dir, decision);

        //9
        herb_field = new double[]
                {
                        0, 0, 0,
                        0, 0, 0,
                        0, 0, 1
                };

        dir = DirectionValues.SOUTH_EAST;
        decision = Actions.MOVEMENT;
        to_file(herb_field, lizard_field, predator_lizard_field, 0.3, dir, decision);
        to_file(herb_field, lizard_field, predator_lizard_field, 0.5, dir, decision);


        //Размножение при большом hp с ящеркой с большим hp
        herb_field = new double[]
                {
                        0, 0, 0,
                        0, 0, 0,
                        0, 0, 0
                };
        predator_lizard_field = new double[]
                {
                        0, 0, 0,
                        0, 0, 0,
                        0, 0, 0
                };

        //1
        lizard_field = new double[]
                {
                        0.7, 0, 0,
                        0, 0, 0,
                        0, 0, 0
                };

        dir = DirectionValues.NORTH_WEST;
        decision = Actions.MOVEMENT;
        to_file(herb_field, lizard_field, predator_lizard_field, 0.7, dir, decision);

        //2
        lizard_field = new double[]
                {
                        0, 0.7, 0,
                        0, 0, 0,
                        0, 0, 0
                };
        dir = DirectionValues.NORTH;
        decision = Actions.MOVEMENT;
        to_file(herb_field, lizard_field, predator_lizard_field, 0.7, dir, decision);

        //3
        lizard_field = new double[]
                {
                        0, 0, 0.7,
                        0, 0, 0,
                        0, 0, 0
                };
        dir = DirectionValues.NORTH_EAST;
        decision = Actions.MOVEMENT;
        to_file(herb_field, lizard_field, predator_lizard_field, 0.7, dir, decision);

        //4
        lizard_field = new double[]
                {
                        0, 0, 0,
                        0.7, 0, 0,
                        0, 0, 0
                };
        dir = DirectionValues.WEST;
        decision = Actions.MOVEMENT;
        to_file(herb_field, lizard_field, predator_lizard_field, 0.7, dir, decision);

        //5
        lizard_field = new double[]
                {
                        0, 0, 0,
                        0, 0.7, 0,
                        0, 0, 0
                };
        dir = DirectionValues.NO_DIRECTION;
        decision = Actions.REPRODUCE;
        to_file(herb_field, lizard_field, predator_lizard_field, 0.7, dir, decision);

        //6
        lizard_field = new double[]
                {
                        0, 0, 0,
                        0, 0, 0.7,
                        0, 0, 0
                };
        dir = DirectionValues.EAST;
        decision = Actions.MOVEMENT;
        to_file(herb_field, lizard_field, predator_lizard_field, 0.7, dir, decision);

        //7
        lizard_field = new double[]
                {
                        0, 0, 0,
                        0, 0, 0,
                        0.7, 0, 0
                };
        dir = DirectionValues.SOUTH_WEST;
        decision = Actions.MOVEMENT;
        to_file(herb_field, lizard_field, predator_lizard_field, 0.7, dir, decision);

        //8
        lizard_field = new double[]
                {
                        0, 0, 0,
                        0, 0, 0,
                        0, 0.7, 0
                };
        dir = DirectionValues.SOUTH;
        decision = Actions.MOVEMENT;
        to_file(herb_field, lizard_field, predator_lizard_field, 0.7, dir, decision);

        //9
        lizard_field = new double[]
                {
                        0, 0, 0,
                        0, 0, 0,
                        0, 0, 0.7
                };
        dir = DirectionValues.SOUTH_EAST;
        decision = Actions.MOVEMENT;
        to_file(herb_field, lizard_field, predator_lizard_field, 0.7, dir, decision);

        //Убежать от ящерки-хищника
        herb_field = new double[]
                {
                        0, 0, 0,
                        0, 0, 0,
                        0, 0, 0
                };
        lizard_field = new double[]
                {
                        0, 0, 0,
                        0, 0, 0,
                        0, 0, 0
                };

        //1
        predator_lizard_field = new double[]
                {
                        0.7, 0, 0,
                        0, 0, 0,
                        0, 0, 0
                };
        dir = DirectionValues.SOUTH_EAST;
        decision = Actions.MOVEMENT;
        to_file(herb_field, lizard_field, predator_lizard_field, 0.3, dir, decision);
        to_file(herb_field, lizard_field, predator_lizard_field, 0.5, dir, decision);

        //2
        predator_lizard_field = new double[]
                {
                        0, 0.7, 0,
                        0, 0, 0,
                        0, 0, 0
                };
        dir = DirectionValues.SOUTH;
        decision = Actions.MOVEMENT;
        to_file(herb_field, lizard_field, predator_lizard_field, 0.3, dir, decision);
        to_file(herb_field, lizard_field, predator_lizard_field, 0.5, dir, decision);

        //3
        predator_lizard_field = new double[]
                {
                        0, 0, 0.7,
                        0, 0, 0,
                        0, 0, 0
                };
        dir = DirectionValues.SOUTH_WEST;
        decision = Actions.MOVEMENT;
        to_file(herb_field, lizard_field, predator_lizard_field, 0.3, dir, decision);
        to_file(herb_field, lizard_field, predator_lizard_field, 0.5, dir, decision);

        //4
        predator_lizard_field = new double[]
                {
                        0, 0, 0,
                        0.7, 0, 0,
                        0, 0, 0
                };
        dir = DirectionValues.EAST;
        decision = Actions.MOVEMENT;
        to_file(herb_field, lizard_field, predator_lizard_field, 0.3, dir, decision);
        to_file(herb_field, lizard_field, predator_lizard_field, 0.5, dir, decision);

        //5
        predator_lizard_field = new double[]
                {
                        0, 0, 0,
                        0, 0.7, 0,
                        0, 0, 0
                };
        dir = DirectionValues.NO_DIRECTION;
        decision = Actions.MOVEMENT;
        to_file(herb_field, lizard_field, predator_lizard_field, 0.3, dir, decision);
        to_file(herb_field, lizard_field, predator_lizard_field, 0.5, dir, decision);

        //6
        predator_lizard_field = new double[]
                {
                        0, 0, 0,
                        0, 0, 0.7,
                        0, 0, 0
                };
        dir = DirectionValues.WEST;
        decision = Actions.MOVEMENT;
        to_file(herb_field, lizard_field, predator_lizard_field, 0.3, dir, decision);
        to_file(herb_field, lizard_field, predator_lizard_field, 0.5, dir, decision);

        //7
        predator_lizard_field = new double[]
                {
                        0, 0, 0,
                        0, 0, 0,
                        0.7, 0, 0
                };
        dir = DirectionValues.NORTH_EAST;
        decision = Actions.MOVEMENT;
        to_file(herb_field, lizard_field, predator_lizard_field, 0.3, dir, decision);
        to_file(herb_field, lizard_field, predator_lizard_field, 0.5, dir, decision);

        //8
        predator_lizard_field = new double[]
                {
                        0, 0, 0,
                        0, 0, 0,
                        0, 0.7, 0
                };
        dir = DirectionValues.NORTH;
        decision = Actions.MOVEMENT;
        to_file(herb_field, lizard_field, predator_lizard_field, 0.3, dir, decision);
        to_file(herb_field, lizard_field, predator_lizard_field, 0.5, dir, decision);

        //9
        predator_lizard_field = new double[]
                {
                        0, 0, 0,
                        0, 0, 0,
                        0, 0, 0.7
                };
        dir = DirectionValues.NORTH_WEST;
        decision = Actions.MOVEMENT;
        to_file(herb_field, lizard_field, predator_lizard_field, 0.3, dir, decision);
        to_file(herb_field, lizard_field, predator_lizard_field, 0.5, dir, decision);

        //атака на хищника, если рядом есть здоровые травоядные
        herb_field = new double[]
                {
                        0, 0, 0,
                        0, 0, 0,
                        0, 0, 0
                };

        //1
        lizard_field = new double[]
                {
                        0, 0.7, 0,
                        0.7, 0, 0,
                        0, 0, 0
                };

        predator_lizard_field = new double[]
                {
                        0.7, 0, 0,
                        0, 0, 0,
                        0, 0, 0
                };
        dir = DirectionValues.NORTH_WEST;
        decision = Actions.ATTACK;
        to_file(herb_field, lizard_field, predator_lizard_field, 0.7, dir, decision);

        lizard_field = new double[]
                {
                        0, 0.5, 0,
                        0.5, 0, 0,
                        0, 0, 0
                };

        predator_lizard_field = new double[]
                {
                        0.5, 0, 0,
                        0, 0, 0,
                        0, 0, 0
                };

        to_file(herb_field, lizard_field, predator_lizard_field, 0.5, dir, decision);

        lizard_field = new double[]
                {
                        0, 0.3, 0,
                        0.3, 0, 0,
                        0, 0, 0
                };

        predator_lizard_field = new double[]
                {
                        0.3, 0, 0,
                        0, 0, 0,
                        0, 0, 0
                };

        to_file(herb_field, lizard_field, predator_lizard_field, 0.3, dir, decision);

        //2
        lizard_field = new double[]
                {
                        0.7, 0, 0.7,
                        0, 0, 0,
                        0, 0, 0
                };

        predator_lizard_field = new double[]
                {
                        0, 0.7, 0,
                        0, 0, 0,
                        0, 0, 0
                };
        dir = DirectionValues.NORTH;
        decision = Actions.ATTACK;
        to_file(herb_field, lizard_field, predator_lizard_field, 0.7, dir, decision);

        lizard_field = new double[]
                {
                        0.5, 0, 0.5,
                        0, 0, 0,
                        0, 0, 0
                };

        predator_lizard_field = new double[]
                {
                        0, 0.5, 0,
                        0, 0, 0,
                        0, 0, 0
                };
        to_file(herb_field, lizard_field, predator_lizard_field, 0.5, dir, decision);

        lizard_field = new double[]
                {
                        0.3, 0, 0.3,
                        0, 0, 0,
                        0, 0, 0
                };

        predator_lizard_field = new double[]
                {
                        0, 0.3, 0,
                        0, 0, 0,
                        0, 0, 0
                };
        to_file(herb_field, lizard_field, predator_lizard_field, 0.3, dir, decision);

        //3
        lizard_field = new double[]
                {
                        0, 0.7, 0,
                        0, 0, 0.7,
                        0, 0, 0
                };

        predator_lizard_field = new double[]
                {
                        0, 0, 0.7,
                        0, 0, 0,
                        0, 0, 0
                };
        dir = DirectionValues.NORTH_EAST;
        decision = Actions.ATTACK;
        to_file(herb_field, lizard_field, predator_lizard_field, 0.7, dir, decision);

        lizard_field = new double[]
                {
                        0, 0.5, 0,
                        0, 0, 0.5,
                        0, 0, 0
                };

        predator_lizard_field = new double[]
                {
                        0, 0, 0.5,
                        0, 0, 0,
                        0, 0, 0
                };
        to_file(herb_field, lizard_field, predator_lizard_field, 0.5, dir, decision);

        lizard_field = new double[]
                {
                        0, 0.3, 0,
                        0, 0, 0.3,
                        0, 0, 0
                };

        predator_lizard_field = new double[]
                {
                        0, 0, 0.3,
                        0, 0, 0,
                        0, 0, 0
                };
        to_file(herb_field, lizard_field, predator_lizard_field, 0.3, dir, decision);

        //4
        lizard_field = new double[]
                {
                        0.7, 0, 0,
                        0, 0, 0,
                        0.7, 0, 0
                };

        predator_lizard_field = new double[]
                {
                        0, 0, 0,
                        0.7, 0, 0,
                        0, 0, 0
                };
        dir = DirectionValues.WEST;
        decision = Actions.ATTACK;
        to_file(herb_field, lizard_field, predator_lizard_field, 0.7, dir, decision);

        lizard_field = new double[]
                {
                        0.5, 0, 0,
                        0, 0, 0,
                        0.5, 0, 0
                };

        predator_lizard_field = new double[]
                {
                        0, 0, 0,
                        0.5, 0, 0,
                        0, 0, 0
                };
        to_file(herb_field, lizard_field, predator_lizard_field, 0.5, dir, decision);

        lizard_field = new double[]
                {
                        0.3, 0, 0,
                        0, 0, 0,
                        0.3, 0, 0
                };

        predator_lizard_field = new double[]
                {
                        0, 0, 0,
                        0.3, 0, 0,
                        0, 0, 0
                };
        to_file(herb_field, lizard_field, predator_lizard_field, 0.3, dir, decision);

        //5
        lizard_field = new double[]
                {
                        0, 0.7, 0,
                        0.7, 0, 0.7,
                        0, 0.7, 0
                };

        predator_lizard_field = new double[]
                {
                        0, 0, 0,
                        0, 0.7, 0,
                        0, 0, 0
                };
        dir = DirectionValues.NO_DIRECTION;
        decision = Actions.ATTACK;
        to_file(herb_field, lizard_field, predator_lizard_field, 0.7, dir, decision);

        lizard_field = new double[]
                {
                        0, 0.5, 0,
                        0.5, 0, 0.5,
                        0, 0.5, 0
                };

        predator_lizard_field = new double[]
                {
                        0, 0, 0,
                        0, 0.5, 0,
                        0, 0, 0
                };
        to_file(herb_field, lizard_field, predator_lizard_field, 0.5, dir, decision);

        lizard_field = new double[]
                {
                        0, 0.3, 0,
                        0.3, 0, 0.3,
                        0, 0.3, 0
                };

        predator_lizard_field = new double[]
                {
                        0, 0, 0,
                        0, 0.3, 0,
                        0, 0, 0
                };
        to_file(herb_field, lizard_field, predator_lizard_field, 0.3, dir, decision);

        //6
        lizard_field = new double[]
                {
                        0, 0, 0.7,
                        0, 0, 0,
                        0, 0, 0.7
                };

        predator_lizard_field = new double[]
                {
                        0, 0, 0,
                        0, 0, 0.7,
                        0, 0, 0
                };
        dir = DirectionValues.EAST;
        decision = Actions.ATTACK;
        to_file(herb_field, lizard_field, predator_lizard_field, 0.7, dir, decision);

        lizard_field = new double[]
                {
                        0, 0, 0.5,
                        0, 0, 0,
                        0, 0, 0.5
                };

        predator_lizard_field = new double[]
                {
                        0, 0, 0,
                        0, 0, 0.5,
                        0, 0, 0
                };
        to_file(herb_field, lizard_field, predator_lizard_field, 0.5, dir, decision);

        lizard_field = new double[]
                {
                        0, 0, 0.3,
                        0, 0, 0,
                        0, 0, 0.3
                };

        predator_lizard_field = new double[]
                {
                        0, 0, 0,
                        0, 0, 0.3,
                        0, 0, 0
                };
        to_file(herb_field, lizard_field, predator_lizard_field, 0.3, dir, decision);

        //7
        lizard_field = new double[]
                {
                        0, 0, 0,
                        0.7, 0, 0,
                        0, 0.7, 0
                };

        predator_lizard_field = new double[]
                {
                        0, 0, 0,
                        0, 0, 0,
                        0.7, 0, 0
                };
        dir = DirectionValues.SOUTH_WEST;
        decision = Actions.ATTACK;
        to_file(herb_field, lizard_field, predator_lizard_field, 0.7, dir, decision);

        lizard_field = new double[]
                {
                        0, 0, 0,
                        0.5, 0, 0,
                        0, 0.5, 0
                };

        predator_lizard_field = new double[]
                {
                        0, 0, 0,
                        0, 0, 0,
                        0.5, 0, 0
                };
        to_file(herb_field, lizard_field, predator_lizard_field, 0.5, dir, decision);

        lizard_field = new double[]
                {
                        0, 0, 0,
                        0.3, 0, 0,
                        0, 0.3, 0
                };

        predator_lizard_field = new double[]
                {
                        0, 0, 0,
                        0, 0, 0,
                        0.3, 0, 0
                };
        to_file(herb_field, lizard_field, predator_lizard_field, 0.3, dir, decision);

        //8
        lizard_field = new double[]
                {
                        0, 0, 0,
                        0, 0, 0,
                        0.7, 0, 0.7
                };

        predator_lizard_field = new double[]
                {
                        0, 0, 0,
                        0, 0, 0,
                        0, 0.7, 0
                };
        dir = DirectionValues.SOUTH;
        decision = Actions.ATTACK;
        to_file(herb_field, lizard_field, predator_lizard_field, 0.7, dir, decision);

        lizard_field = new double[]
                {
                        0, 0, 0,
                        0, 0, 0,
                        0.5, 0, 0.5
                };

        predator_lizard_field = new double[]
                {
                        0, 0, 0,
                        0, 0, 0,
                        0, 0.5, 0
                };
        to_file(herb_field, lizard_field, predator_lizard_field, 0.5, dir, decision);

        lizard_field = new double[]
                {
                        0, 0, 0,
                        0, 0, 0,
                        0.3, 0, 0.3
                };

        predator_lizard_field = new double[]
                {
                        0, 0, 0,
                        0, 0, 0,
                        0, 0.3, 0
                };
        to_file(herb_field, lizard_field, predator_lizard_field, 0.3, dir, decision);

        //9
        lizard_field = new double[]
                {
                        0, 0, 0,
                        0, 0, 0.7,
                        0, 0.7, 0
                };

        predator_lizard_field = new double[]
                {
                        0, 0, 0,
                        0, 0, 0,
                        0, 0, 0.7
                };
        dir = DirectionValues.SOUTH_EAST;
        decision = Actions.ATTACK;
        to_file(herb_field, lizard_field, predator_lizard_field, 0.7, dir, decision);

        lizard_field = new double[]
                {
                        0, 0, 0,
                        0, 0, 0.5,
                        0, 0.5, 0
                };

        predator_lizard_field = new double[]
                {
                        0, 0, 0,
                        0, 0, 0,
                        0, 0, 0.5
                };
        to_file(herb_field, lizard_field, predator_lizard_field, 0.5, dir, decision);

        lizard_field = new double[]
                {
                        0, 0, 0,
                        0, 0, 0.3,
                        0, 0.3, 0
                };

        predator_lizard_field = new double[]
                {
                        0, 0, 0,
                        0, 0, 0,
                        0, 0, 0.3
                };
        to_file(herb_field, lizard_field, predator_lizard_field, 0.3, dir, decision);

        try {
            writer.close();
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }

    public void predator_lizard_gen(){
        try {
            this.writer = new FileWriter(predator_lizard_data_set_file_name);
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }

        //Запись в файл обучаемого множества для хищных ящерок.
        double [] corpse_field;
        double [] lizard_field;
        double [] predator_lizard_field;
        Actions decision;
        DirectionValues dir;

        //Ситуация замешательства, рандомное движение
        corpse_field = new double[]
                {
                        0, 0, 0,
                        0, 0, 0,
                        0, 0, 0
                };

        lizard_field = new double[]
                {
                        0, 0, 0,
                        0, 0, 0,
                        0, 0, 0
                };

        predator_lizard_field = new double[]
                {
                        0, 0, 0,
                        0, 0, 0,
                        0, 0, 0
                };

        dir = DirectionValues.NO_DIRECTION;
        decision = Actions.MOVEMENT;
        to_file(corpse_field, lizard_field, predator_lizard_field, 0.3, dir, decision);
        to_file(corpse_field, lizard_field, predator_lizard_field, 0.5, dir, decision);
        to_file(corpse_field, lizard_field, predator_lizard_field, 0.7, dir, decision);

        //Поедание падали при малом и среднем значении hp
        lizard_field = new double[]
                {
                        0, 0, 0,
                        0, 0, 0,
                        0, 0, 0
                };

        predator_lizard_field = new double[]
                {
                        0, 0, 0,
                        0, 0, 0,
                        0, 0, 0
                };

        //1
        corpse_field = new double[]
                {
                        1, 0, 0,
                        0, 0, 0,
                        0, 0, 0
                };

        dir = DirectionValues.NORTH_WEST;
        decision = Actions.MOVEMENT;
        to_file(corpse_field, lizard_field, predator_lizard_field, 0.3, dir, decision);
        to_file(corpse_field, lizard_field, predator_lizard_field, 0.5, dir, decision);
        to_file(corpse_field, lizard_field, predator_lizard_field, 0.7, dir, decision);

        //2
        corpse_field = new double[]
                {
                        0, 1, 0,
                        0, 0, 0,
                        0, 0, 0
                };

        dir = DirectionValues.NORTH;
        decision = Actions.MOVEMENT;
        to_file(corpse_field, lizard_field, predator_lizard_field, 0.3, dir, decision);
        to_file(corpse_field, lizard_field, predator_lizard_field, 0.5, dir, decision);
        to_file(corpse_field, lizard_field, predator_lizard_field, 0.7, dir, decision);

        //3
        corpse_field = new double[]
                {
                        0, 0, 1,
                        0, 0, 0,
                        0, 0, 0
                };

        dir = DirectionValues.NORTH_EAST;
        decision = Actions.MOVEMENT;
        to_file(corpse_field, lizard_field, predator_lizard_field, 0.3, dir, decision);
        to_file(corpse_field, lizard_field, predator_lizard_field, 0.5, dir, decision);
        to_file(corpse_field, lizard_field, predator_lizard_field, 0.7, dir, decision);

        //4
        corpse_field = new double[]
                {
                        0, 0, 0,
                        1, 0, 0,
                        0, 0, 0
                };

        dir = DirectionValues.WEST;
        decision = Actions.MOVEMENT;
        to_file(corpse_field, lizard_field, predator_lizard_field, 0.3, dir, decision);
        to_file(corpse_field, lizard_field, predator_lizard_field, 0.5, dir, decision);
        to_file(corpse_field, lizard_field, predator_lizard_field, 0.7, dir, decision);

        //5
        corpse_field = new double[]
                {
                        0, 0, 0,
                        0, 1, 0,
                        0, 0, 0
                };

        dir = DirectionValues.NO_DIRECTION;
        decision = Actions.EATING;
        to_file(corpse_field, lizard_field, predator_lizard_field, 0.3, dir, decision);
        to_file(corpse_field, lizard_field, predator_lizard_field, 0.5, dir, decision);
        to_file(corpse_field, lizard_field, predator_lizard_field, 0.7, dir, decision);

        //6
        corpse_field = new double[]
                {
                        0, 0, 0,
                        0, 0, 1,
                        0, 0, 0
                };

        dir = DirectionValues.EAST;
        decision = Actions.MOVEMENT;
        to_file(corpse_field, lizard_field, predator_lizard_field, 0.3, dir, decision);
        to_file(corpse_field, lizard_field, predator_lizard_field, 0.5, dir, decision);
        to_file(corpse_field, lizard_field, predator_lizard_field, 0.7, dir, decision);

        //7
        corpse_field = new double[]
                {
                        0, 0, 0,
                        0, 0, 0,
                        1, 0, 0
                };

        dir = DirectionValues.SOUTH_WEST;
        decision = Actions.MOVEMENT;
        to_file(corpse_field, lizard_field, predator_lizard_field, 0.3, dir, decision);
        to_file(corpse_field, lizard_field, predator_lizard_field, 0.5, dir, decision);
        to_file(corpse_field, lizard_field, predator_lizard_field, 0.7, dir, decision);

        //8
        corpse_field = new double[]
                {
                        0, 0, 0,
                        0, 0, 0,
                        0, 1, 0
                };

        dir = DirectionValues.SOUTH;
        decision = Actions.MOVEMENT;
        to_file(corpse_field, lizard_field, predator_lizard_field, 0.3, dir, decision);
        to_file(corpse_field, lizard_field, predator_lizard_field, 0.5, dir, decision);
        to_file(corpse_field, lizard_field, predator_lizard_field, 0.7, dir, decision);

        //9
        corpse_field = new double[]
                {
                        0, 0, 0,
                        0, 0, 0,
                        0, 0, 1
                };

        dir = DirectionValues.SOUTH_EAST;
        decision = Actions.MOVEMENT;
        to_file(corpse_field, lizard_field, predator_lizard_field, 0.3, dir, decision);
        to_file(corpse_field, lizard_field, predator_lizard_field, 0.5, dir, decision);
        to_file(corpse_field, lizard_field, predator_lizard_field, 0.7, dir, decision);

        //Размножение при большом hp с ящеркой с большим hp
        corpse_field = new double[]
                {
                        0, 0, 0,
                        0, 0, 0,
                        0, 0, 0
                };
        lizard_field = new double[]
                {
                        0, 0, 0,
                        0, 0, 0,
                        0, 0, 0
                };

        //1
        predator_lizard_field = new double[]
                {
                        0.7, 0, 0,
                        0, 0, 0,
                        0, 0, 0
                };

        dir = DirectionValues.NORTH_WEST;
        decision = Actions.MOVEMENT;
        to_file(corpse_field, lizard_field, predator_lizard_field, 0.7, dir, decision);

        //2
        predator_lizard_field = new double[]
                {
                        0, 0.7, 0,
                        0, 0, 0,
                        0, 0, 0
                };
        dir = DirectionValues.NORTH;
        decision = Actions.MOVEMENT;
        to_file(corpse_field, lizard_field, predator_lizard_field, 0.7, dir, decision);

        //3
        predator_lizard_field = new double[]
                {
                        0, 0, 0.7,
                        0, 0, 0,
                        0, 0, 0
                };
        dir = DirectionValues.NORTH_EAST;
        decision = Actions.MOVEMENT;
        to_file(corpse_field, lizard_field, predator_lizard_field, 0.7, dir, decision);

        //4
        predator_lizard_field = new double[]
                {
                        0, 0, 0,
                        0.7, 0, 0,
                        0, 0, 0
                };
        dir = DirectionValues.WEST;
        decision = Actions.MOVEMENT;
        to_file(corpse_field, lizard_field, predator_lizard_field, 0.7, dir, decision);

        //5
        predator_lizard_field = new double[]
                {
                        0, 0, 0,
                        0, 0.7, 0,
                        0, 0, 0
                };
        dir = DirectionValues.NO_DIRECTION;
        decision = Actions.REPRODUCE;
        to_file(corpse_field, lizard_field, predator_lizard_field, 0.7, dir, decision);

        //6
        predator_lizard_field = new double[]
                {
                        0, 0, 0,
                        0, 0, 0.7,
                        0, 0, 0
                };
        dir = DirectionValues.EAST;
        decision = Actions.MOVEMENT;
        to_file(corpse_field, lizard_field, predator_lizard_field, 0.7, dir, decision);

        //7
        predator_lizard_field = new double[]
                {
                        0, 0, 0,
                        0, 0, 0,
                        0.7, 0, 0
                };
        dir = DirectionValues.SOUTH_WEST;
        decision = Actions.MOVEMENT;
        to_file(corpse_field, lizard_field, predator_lizard_field, 0.7, dir, decision);

        //8
        predator_lizard_field = new double[]
                {
                        0, 0, 0,
                        0, 0, 0,
                        0, 0.7, 0
                };
        dir = DirectionValues.SOUTH;
        decision = Actions.MOVEMENT;
        to_file(corpse_field, lizard_field, predator_lizard_field, 0.7, dir, decision);

        //9
        predator_lizard_field = new double[]
                {
                        0, 0, 0,
                        0, 0, 0,
                        0, 0, 0.7
                };
        dir = DirectionValues.SOUTH_EAST;
        decision = Actions.MOVEMENT;
        to_file(corpse_field, lizard_field, predator_lizard_field, 0.7, dir, decision);

        //Сцена охоты
        corpse_field = new double[]
                {
                        0, 0, 0,
                        0, 0, 0,
                        0, 0, 0
                };
        predator_lizard_field = new double[]
                {
                        0, 0, 0,
                        0, 0, 0,
                        0, 0, 0
                };

        //1
        lizard_field = new double[]
                {
                        0.7, 0, 0,
                        0, 0, 0,
                        0, 0, 0
                };

        dir = DirectionValues.NORTH_WEST;
        decision = Actions.ATTACK;
        to_file(corpse_field, lizard_field, predator_lizard_field, 0.5, dir, decision);
        to_file(corpse_field, lizard_field, predator_lizard_field, 0.7, dir, decision);

        //2
        lizard_field = new double[]
                {
                        0, 0.7, 0,
                        0, 0, 0,
                        0, 0, 0
                };
        dir = DirectionValues.NORTH;
        decision = Actions.ATTACK;
        to_file(corpse_field, lizard_field, predator_lizard_field, 0.5, dir, decision);
        to_file(corpse_field, lizard_field, predator_lizard_field, 0.7, dir, decision);

        //3
        lizard_field = new double[]
                {
                        0, 0, 0.7,
                        0, 0, 0,
                        0, 0, 0
                };
        dir = DirectionValues.NORTH_EAST;
        decision = Actions.ATTACK;
        to_file(corpse_field, lizard_field, predator_lizard_field, 0.5, dir, decision);
        to_file(corpse_field, lizard_field, predator_lizard_field, 0.7, dir, decision);

        //4
        lizard_field = new double[]
                {
                        0, 0, 0,
                        0.7, 0, 0,
                        0, 0, 0
                };
        dir = DirectionValues.WEST;
        decision = Actions.ATTACK;
        to_file(corpse_field, lizard_field, predator_lizard_field, 0.5, dir, decision);
        to_file(corpse_field, lizard_field, predator_lizard_field, 0.7, dir, decision);

        //5
        lizard_field = new double[]
                {
                        0, 0, 0,
                        0, 0.7, 0,
                        0, 0, 0
                };
        dir = DirectionValues.NO_DIRECTION;
        decision = Actions.ATTACK;
        to_file(corpse_field, lizard_field, predator_lizard_field, 0.5, dir, decision);
        to_file(corpse_field, lizard_field, predator_lizard_field, 0.7, dir, decision);

        //6
        lizard_field = new double[]
                {
                        0, 0, 0,
                        0, 0, 0.7,
                        0, 0, 0
                };
        dir = DirectionValues.EAST;
        decision = Actions.ATTACK;
        to_file(corpse_field, lizard_field, predator_lizard_field, 0.5, dir, decision);
        to_file(corpse_field, lizard_field, predator_lizard_field, 0.7, dir, decision);

        //7
        lizard_field = new double[]
                {
                        0, 0, 0,
                        0, 0, 0,
                        0.7, 0, 0
                };
        dir = DirectionValues.SOUTH_WEST;
        decision = Actions.ATTACK;
        to_file(corpse_field, lizard_field, predator_lizard_field, 0.5, dir, decision);
        to_file(corpse_field, lizard_field, predator_lizard_field, 0.7, dir, decision);

        //8
        lizard_field = new double[]
                {
                        0, 0, 0,
                        0, 0, 0,
                        0, 0.7, 0
                };
        dir = DirectionValues.SOUTH;
        decision = Actions.ATTACK;
        to_file(corpse_field, lizard_field, predator_lizard_field, 0.5, dir, decision);
        to_file(corpse_field, lizard_field, predator_lizard_field, 0.7, dir, decision);

        //9
        lizard_field = new double[]
                {
                        0, 0, 0,
                        0, 0, 0,
                        0, 0, 0.7
                };
        dir = DirectionValues.SOUTH_EAST;
        decision = Actions.ATTACK;
        to_file(corpse_field, lizard_field, predator_lizard_field, 0.5, dir, decision);
        to_file(corpse_field, lizard_field, predator_lizard_field, 0.7, dir, decision);

        try {
            writer.close();
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }
}
