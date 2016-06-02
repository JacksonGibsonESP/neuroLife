package com.dsile.core.generator;

import com.dsile.core.entities.actions.Actions;
import com.dsile.core.entities.actions.movement.DirectionValues;
import com.dsile.core.entities.actions.movement.SidesDirectionValues;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

/**
 * Класс генерирующий обучающее множесво для нейросети
 *
 * Created by Никита on 13.05.2016.
 */
public class Generator {
    private final String lizard_data_set_file_name          = "Herb_Lizard_data_set.txt";
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

    private void to_file(double [] herb_field, double [] lizard_field, double [] predator_lizard_field, double hp, SidesDirectionValues dir, Actions decision)
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
            case RIGHT:
                out[0] = 1;
                out[1] = 0;
                out[2] = 0;
                out[3] = 0;
                break;
            case FORWARD_RIGHT:
                out[0] = 1;
                out[1] = 1;
                out[2] = 0;
                out[3] = 0;
                break;
            case FORWARD:
                out[0] = 0;
                out[1] = 1;
                out[2] = 0;
                out[3] = 0;
                break;
            case FORWARD_LEFT:
                out[0] = 0;
                out[1] = 1;
                out[2] = 1;
                out[3] = 0;
                break;
            case LEFT:
                out[0] = 0;
                out[1] = 0;
                out[2] = 1;
                out[3] = 0;
                break;
            case BACKWARD_LEFT:
                out[0] = 0;
                out[1] = 0;
                out[2] = 1;
                out[3] = 1;
                break;
            case BACKWARD:
                out[0] = 0;
                out[1] = 0;
                out[2] = 0;
                out[3] = 1;
                break;
            case BACKWARD_RIGHT:
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

    private void herb_lizard_confusion_pack(){
        double [] herb_field;
        double [] lizard_field;
        double [] predator_lizard_field;
        Actions decision;
        SidesDirectionValues dir;

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

        dir = SidesDirectionValues.NO_DIRECTION;
        decision = Actions.MOVEMENT;
        to_file(herb_field, lizard_field, predator_lizard_field, 0.3, dir, decision);
        to_file(herb_field, lizard_field, predator_lizard_field, 0.5, dir, decision);
        to_file(herb_field, lizard_field, predator_lizard_field, 0.7, dir, decision);

        //искать еду, а не размножаться
        herb_field = new double[]
                {
                        0, 0, 0,
                        0, 0, 0,
                        0, 0, 0
                };

        lizard_field = new double[]
                {
                        0.7, 0.7, 0.7,
                        0.7, 0.7, 0.7,
                        0.7, 0.7, 0.7
                };

        predator_lizard_field = new double[]
                {
                        0, 0, 0,
                        0, 0, 0,
                        0, 0, 0
                };

        dir = SidesDirectionValues.NO_DIRECTION;
        decision = Actions.MOVEMENT;
        to_file(herb_field, lizard_field, predator_lizard_field, 0.3, dir, decision);
        to_file(herb_field, lizard_field, predator_lizard_field, 0.5, dir, decision);
        to_file(herb_field, lizard_field, predator_lizard_field, 0.7, dir, decision);
    }

    private void herb_lizard_eating_random(double [] herb_field, double [] lizard_field, int i){
        if (i == 1){
            Random r = new Random();
            for (int k = 0; k < 9; k++) {
                lizard_field[k] = 0;
                if (r.nextDouble() < 0.8){
                    double tmp = r.nextDouble();
                    if (tmp >= 0.7) {
                        lizard_field[k] = 0.7;
                    } else if (tmp >= 0.5){
                        lizard_field[k] = 0.5;
                    } else{
                        lizard_field[k] = 0.3;
                    }
                }
            }
        }
    }

    private void herb_lizard_eating_pack(){
        double [] herb_field;
        double [] lizard_field;
        double [] predator_lizard_field;
        Actions decision;
        SidesDirectionValues dir;

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

        for (int i = 0; i < 2; i++) {
            //1
            herb_field = new double[]
                    {
                            1, 0, 0,
                            0, 0, 0,
                            0, 0, 0
                    };

            herb_lizard_eating_random(herb_field, lizard_field, i);
            dir = SidesDirectionValues.FORWARD_LEFT;
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

            herb_lizard_eating_random(herb_field, lizard_field, i);
            dir = SidesDirectionValues.FORWARD;
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

            herb_lizard_eating_random(herb_field, lizard_field, i);
            dir = SidesDirectionValues.FORWARD_RIGHT;
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

            herb_lizard_eating_random(herb_field, lizard_field, i);
            dir = SidesDirectionValues.LEFT;
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

            herb_lizard_eating_random(herb_field, lizard_field, i);
            dir = SidesDirectionValues.NO_DIRECTION;
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

            herb_lizard_eating_random(herb_field, lizard_field, i);
            dir = SidesDirectionValues.RIGHT;
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

            herb_lizard_eating_random(herb_field, lizard_field, i);
            dir = SidesDirectionValues.BACKWARD_LEFT;
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

            herb_lizard_eating_random(herb_field, lizard_field, i);
            dir = SidesDirectionValues.BACKWARD;
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

            herb_lizard_eating_random(herb_field, lizard_field, i);
            dir = SidesDirectionValues.BACKWARD_RIGHT;
            decision = Actions.MOVEMENT;
            to_file(herb_field, lizard_field, predator_lizard_field, 0.3, dir, decision);
            to_file(herb_field, lizard_field, predator_lizard_field, 0.5, dir, decision);

            //Комбинации:
            herb_field = new double[]
                    {
                            1, 1, 0,
                            1, 0, 0,
                            0, 0, 0
                    };

            herb_lizard_eating_random(herb_field, lizard_field, i);
            dir = SidesDirectionValues.FORWARD_LEFT;
            decision = Actions.MOVEMENT;
            to_file(herb_field, lizard_field, predator_lizard_field, 0.3, dir, decision);
            to_file(herb_field, lizard_field, predator_lizard_field, 0.5, dir, decision);

            herb_field = new double[]
                    {
                            1, 1, 1,
                            0, 0, 0,
                            0, 0, 0
                    };

            herb_lizard_eating_random(herb_field, lizard_field, i);
            dir = SidesDirectionValues.FORWARD;
            decision = Actions.MOVEMENT;
            to_file(herb_field, lizard_field, predator_lizard_field, 0.3, dir, decision);
            to_file(herb_field, lizard_field, predator_lizard_field, 0.5, dir, decision);

            herb_field = new double[]
                    {
                            0, 1, 1,
                            0, 0, 1,
                            0, 0, 0
                    };

            herb_lizard_eating_random(herb_field, lizard_field, i);
            dir = SidesDirectionValues.FORWARD_RIGHT;
            decision = Actions.MOVEMENT;
            to_file(herb_field, lizard_field, predator_lizard_field, 0.3, dir, decision);
            to_file(herb_field, lizard_field, predator_lizard_field, 0.5, dir, decision);

            herb_field = new double[]
                    {
                            0, 0, 1,
                            0, 0, 1,
                            0, 0, 1
                    };

            herb_lizard_eating_random(herb_field, lizard_field, i);
            dir = SidesDirectionValues.FORWARD_RIGHT;
            decision = Actions.MOVEMENT;
            to_file(herb_field, lizard_field, predator_lizard_field, 0.3, dir, decision);
            to_file(herb_field, lizard_field, predator_lizard_field, 0.5, dir, decision);

            herb_field = new double[]
                    {
                            0, 0, 0,
                            0, 0, 1,
                            0, 1, 1
                    };

            herb_lizard_eating_random(herb_field, lizard_field, i);
            dir = SidesDirectionValues.BACKWARD_RIGHT;
            decision = Actions.MOVEMENT;
            to_file(herb_field, lizard_field, predator_lizard_field, 0.3, dir, decision);
            to_file(herb_field, lizard_field, predator_lizard_field, 0.5, dir, decision);

            herb_field = new double[]
                    {
                            0, 0, 0,
                            0, 0, 0,
                            1, 1, 1
                    };

            herb_lizard_eating_random(herb_field, lizard_field, i);
            dir = SidesDirectionValues.BACKWARD;
            decision = Actions.MOVEMENT;
            to_file(herb_field, lizard_field, predator_lizard_field, 0.3, dir, decision);
            to_file(herb_field, lizard_field, predator_lizard_field, 0.5, dir, decision);

            herb_field = new double[]
                    {
                            0, 0, 0,
                            1, 0, 0,
                            1, 1, 0
                    };

            herb_lizard_eating_random(herb_field, lizard_field, i);
            dir = SidesDirectionValues.BACKWARD_LEFT;
            decision = Actions.MOVEMENT;
            to_file(herb_field, lizard_field, predator_lizard_field, 0.3, dir, decision);
            to_file(herb_field, lizard_field, predator_lizard_field, 0.5, dir, decision);

            herb_field = new double[]
                    {
                            1, 0, 0,
                            1, 0, 0,
                            1, 0, 0
                    };

            herb_lizard_eating_random(herb_field, lizard_field, i);
            dir = SidesDirectionValues.LEFT;
            decision = Actions.MOVEMENT;
            to_file(herb_field, lizard_field, predator_lizard_field, 0.3, dir, decision);
            to_file(herb_field, lizard_field, predator_lizard_field, 0.5, dir, decision);

            herb_field = new double[]
                    {
                            1, 1, 1,
                            0, 0, 0,
                            1, 1, 1
                    };

            herb_lizard_eating_random(herb_field, lizard_field, i);
            dir = SidesDirectionValues.FORWARD;
            decision = Actions.MOVEMENT;
            to_file(herb_field, lizard_field, predator_lizard_field, 0.3, dir, decision);
            to_file(herb_field, lizard_field, predator_lizard_field, 0.5, dir, decision);

            herb_field = new double[]
                    {
                            1, 1, 1,
                            1, 0, 1,
                            1, 1, 1
                    };

            herb_lizard_eating_random(herb_field, lizard_field, i);
            dir = SidesDirectionValues.FORWARD;
            decision = Actions.MOVEMENT;
            to_file(herb_field, lizard_field, predator_lizard_field, 0.3, dir, decision);
            to_file(herb_field, lizard_field, predator_lizard_field, 0.5, dir, decision);

            herb_field = new double[]
                    {
                            0, 1, 1,
                            1, 0, 1,
                            1, 1, 0
                    };

            herb_lizard_eating_random(herb_field, lizard_field, i);
            dir = SidesDirectionValues.FORWARD_RIGHT;
            decision = Actions.MOVEMENT;
            to_file(herb_field, lizard_field, predator_lizard_field, 0.3, dir, decision);
            to_file(herb_field, lizard_field, predator_lizard_field, 0.5, dir, decision);

            herb_field = new double[]
                    {
                            0, 0, 1,
                            0, 0, 0,
                            1, 0, 0
                    };

            herb_lizard_eating_random(herb_field, lizard_field, i);
            dir = SidesDirectionValues.FORWARD_RIGHT;
            decision = Actions.MOVEMENT;
            to_file(herb_field, lizard_field, predator_lizard_field, 0.3, dir, decision);
            to_file(herb_field, lizard_field, predator_lizard_field, 0.5, dir, decision);

            herb_field = new double[]
                    {
                            1, 0, 1,
                            1, 0, 1,
                            1, 0, 1
                    };

            herb_lizard_eating_random(herb_field, lizard_field, i);
            dir = SidesDirectionValues.NO_DIRECTION;
            decision = Actions.MOVEMENT;
            to_file(herb_field, lizard_field, predator_lizard_field, 0.3, dir, decision);
            to_file(herb_field, lizard_field, predator_lizard_field, 0.5, dir, decision);

            herb_field = new double[]
                    {
                            0, 0, 0,
                            1, 0, 1,
                            0, 0, 0
                    };

            herb_lizard_eating_random(herb_field, lizard_field, i);
            dir = SidesDirectionValues.NO_DIRECTION;
            decision = Actions.MOVEMENT;
            to_file(herb_field, lizard_field, predator_lizard_field, 0.3, dir, decision);
            to_file(herb_field, lizard_field, predator_lizard_field, 0.5, dir, decision);

            herb_field = new double[]
                    {
                            1, 1, 0,
                            1, 0, 1,
                            0, 1, 1
                    };

            herb_lizard_eating_random(herb_field, lizard_field, i);
            dir = SidesDirectionValues.FORWARD_LEFT;
            decision = Actions.MOVEMENT;
            to_file(herb_field, lizard_field, predator_lizard_field, 0.3, dir, decision);
            to_file(herb_field, lizard_field, predator_lizard_field, 0.5, dir, decision);

            herb_field = new double[]
                    {
                            1, 0, 0,
                            0, 0, 0,
                            0, 0, 1
                    };

            herb_lizard_eating_random(herb_field, lizard_field, i);
            dir = SidesDirectionValues.FORWARD_LEFT;
            decision = Actions.MOVEMENT;
            to_file(herb_field, lizard_field, predator_lizard_field, 0.3, dir, decision);
            to_file(herb_field, lizard_field, predator_lizard_field, 0.5, dir, decision);

            herb_field = new double[]
                    {
                            0, 0, 0,
                            0, 0, 0,
                            1, 0, 1
                    };

            herb_lizard_eating_random(herb_field, lizard_field, i);
            dir = SidesDirectionValues.NO_DIRECTION;
            decision = Actions.MOVEMENT;
            to_file(herb_field, lizard_field, predator_lizard_field, 0.3, dir, decision);
            to_file(herb_field, lizard_field, predator_lizard_field, 0.5, dir, decision);

            herb_field = new double[]
                    {
                            1, 0, 1,
                            0, 0, 0,
                            0, 0, 0
                    };

            herb_lizard_eating_random(herb_field, lizard_field, i);
            dir = SidesDirectionValues.NO_DIRECTION;
            decision = Actions.MOVEMENT;
            to_file(herb_field, lizard_field, predator_lizard_field, 0.3, dir, decision);
            to_file(herb_field, lizard_field, predator_lizard_field, 0.5, dir, decision);

            herb_field = new double[]
                    {
                            1, 1, 1,
                            1, 1, 1,
                            1, 1, 1
                    };

            herb_lizard_eating_random(herb_field, lizard_field, i);
            dir = SidesDirectionValues.NO_DIRECTION;
            decision = Actions.EATING;
            to_file(herb_field, lizard_field, predator_lizard_field, 0.3, dir, decision);
            to_file(herb_field, lizard_field, predator_lizard_field, 0.5, dir, decision);
        }
    }

    private void herb_lizard_reproduce_random(double [] herb_field, double[] lizard_field, int i) {
        if (i == 1){
            Random r = new Random();
            for (int j = 0; j < 9; j++){
                if (lizard_field[i] != 0 && r.nextDouble() < 0.8){
                    double tmp = r.nextDouble();
                    if (tmp > 0.5) {
                        lizard_field[j] = 0.5;
                    } else {
                        lizard_field[j] = 0.3;
                    }
                }
            }
            for (int j = 0; j < 9; j++){
                herb_field[j] = 0;
                if (r.nextDouble() < 0.8){
                    herb_field[j] = 1;
                }
            }
        }
    }

    private void herb_lizard_reproduction_pack(){
        double [] herb_field;
        double [] lizard_field;
        double [] predator_lizard_field;
        Actions decision;
        SidesDirectionValues dir;

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

        for (int i = 0; i < 2; i++) {
            //1
            lizard_field = new double[]
                    {
                            0.7, 0, 0,
                            0, 0, 0,
                            0, 0, 0
                    };

            herb_lizard_reproduce_random(herb_field, lizard_field, i);
            dir = SidesDirectionValues.FORWARD_LEFT;
            decision = Actions.MOVEMENT;
            to_file(herb_field, lizard_field, predator_lizard_field, 0.7, dir, decision);

            //2
            lizard_field = new double[]
                    {
                            0, 0.7, 0,
                            0, 0, 0,
                            0, 0, 0
                    };

            herb_lizard_reproduce_random(herb_field, lizard_field, i);
            dir = SidesDirectionValues.FORWARD;
            decision = Actions.MOVEMENT;
            to_file(herb_field, lizard_field, predator_lizard_field, 0.7, dir, decision);

            //3
            lizard_field = new double[]
                    {
                            0, 0, 0.7,
                            0, 0, 0,
                            0, 0, 0
                    };

            herb_lizard_reproduce_random(herb_field, lizard_field, i);
            dir = SidesDirectionValues.FORWARD_RIGHT;
            decision = Actions.MOVEMENT;
            to_file(herb_field, lizard_field, predator_lizard_field, 0.7, dir, decision);

            //4
            lizard_field = new double[]
                    {
                            0, 0, 0,
                            0.7, 0, 0,
                            0, 0, 0
                    };

            herb_lizard_reproduce_random(herb_field, lizard_field, i);
            dir = SidesDirectionValues.LEFT;
            decision = Actions.MOVEMENT;
            to_file(herb_field, lizard_field, predator_lizard_field, 0.7, dir, decision);

            //5
            lizard_field = new double[]
                    {
                            0, 0, 0,
                            0, 0.7, 0,
                            0, 0, 0
                    };

            herb_lizard_reproduce_random(herb_field, lizard_field, i);
            dir = SidesDirectionValues.NO_DIRECTION;
            decision = Actions.REPRODUCE;
            to_file(herb_field, lizard_field, predator_lizard_field, 0.7, dir, decision);

            //6
            lizard_field = new double[]
                    {
                            0, 0, 0,
                            0, 0, 0.7,
                            0, 0, 0
                    };

            herb_lizard_reproduce_random(herb_field, lizard_field, i);
            dir = SidesDirectionValues.RIGHT;
            decision = Actions.MOVEMENT;
            to_file(herb_field, lizard_field, predator_lizard_field, 0.7, dir, decision);

            //7
            lizard_field = new double[]
                    {
                            0, 0, 0,
                            0, 0, 0,
                            0.7, 0, 0
                    };

            herb_lizard_reproduce_random(herb_field, lizard_field, i);
            dir = SidesDirectionValues.BACKWARD_LEFT;
            decision = Actions.MOVEMENT;
            to_file(herb_field, lizard_field, predator_lizard_field, 0.7, dir, decision);

            //8
            lizard_field = new double[]
                    {
                            0, 0, 0,
                            0, 0, 0,
                            0, 0.7, 0
                    };

            herb_lizard_reproduce_random(herb_field, lizard_field, i);
            dir = SidesDirectionValues.BACKWARD;
            decision = Actions.MOVEMENT;
            to_file(herb_field, lizard_field, predator_lizard_field, 0.7, dir, decision);

            //9
            lizard_field = new double[]
                    {
                            0, 0, 0,
                            0, 0, 0,
                            0, 0, 0.7
                    };

            herb_lizard_reproduce_random(herb_field, lizard_field, i);
            dir = SidesDirectionValues.BACKWARD_RIGHT;
            decision = Actions.MOVEMENT;
            to_file(herb_field, lizard_field, predator_lizard_field, 0.7, dir, decision);

            //Комбинации
            lizard_field = new double[]
                    {
                            0.7, 0.7, 0,
                            0.7, 0, 0,
                            0, 0, 0
                    };

            herb_lizard_reproduce_random(herb_field, lizard_field, i);
            dir = SidesDirectionValues.FORWARD_LEFT;
            decision = Actions.MOVEMENT;
            to_file(herb_field, lizard_field, predator_lizard_field, 0.7, dir, decision);

            lizard_field = new double[]
                    {
                            0.7, 0.7, 0.7,
                            0, 0, 0,
                            0, 0, 0
                    };

            herb_lizard_reproduce_random(herb_field, lizard_field, i);
            dir = SidesDirectionValues.FORWARD;
            decision = Actions.MOVEMENT;
            to_file(herb_field, lizard_field, predator_lizard_field, 0.7, dir, decision);

            lizard_field = new double[]
                    {
                            0, 0.7, 0.7,
                            0, 0, 0.7,
                            0, 0, 0
                    };

            herb_lizard_reproduce_random(herb_field, lizard_field, i);
            dir = SidesDirectionValues.FORWARD_RIGHT;
            decision = Actions.MOVEMENT;
            to_file(herb_field, lizard_field, predator_lizard_field, 0.7, dir, decision);

            lizard_field = new double[]
                    {
                            0, 0, 0.7,
                            0, 0, 0.7,
                            0, 0, 0.7
                    };

            herb_lizard_reproduce_random(herb_field, lizard_field, i);
            dir = SidesDirectionValues.FORWARD_RIGHT;
            decision = Actions.MOVEMENT;
            to_file(herb_field, lizard_field, predator_lizard_field, 0.7, dir, decision);

            lizard_field = new double[]
                    {
                            0, 0, 0,
                            0, 0, 0.7,
                            0, 0.7, 0.7
                    };

            herb_lizard_reproduce_random(herb_field, lizard_field, i);
            dir = SidesDirectionValues.BACKWARD_RIGHT;
            decision = Actions.MOVEMENT;
            to_file(herb_field, lizard_field, predator_lizard_field, 0.7, dir, decision);

            lizard_field = new double[]
                    {
                            0, 0, 0,
                            0, 0, 0,
                            0.7, 0.7, 0.7
                    };

            herb_lizard_reproduce_random(herb_field, lizard_field, i);
            dir = SidesDirectionValues.BACKWARD;
            decision = Actions.MOVEMENT;
            to_file(herb_field, lizard_field, predator_lizard_field, 0.7, dir, decision);

            lizard_field = new double[]
                    {
                            0, 0, 0,
                            0.7, 0, 0,
                            0.7, 0.7, 0
                    };

            herb_lizard_reproduce_random(herb_field, lizard_field, i);
            dir = SidesDirectionValues.BACKWARD_LEFT;
            decision = Actions.MOVEMENT;
            to_file(herb_field, lizard_field, predator_lizard_field, 0.7, dir, decision);

            lizard_field = new double[]
                    {
                            0.7, 0, 0,
                            0.7, 0, 0,
                            0.7, 0, 0
                    };

            herb_lizard_reproduce_random(herb_field, lizard_field, i);
            dir = SidesDirectionValues.LEFT;
            decision = Actions.MOVEMENT;
            to_file(herb_field, lizard_field, predator_lizard_field, 0.7, dir, decision);

            lizard_field = new double[]
                    {
                            0.7, 0.7, 0.7,
                            0, 0, 0,
                            0.7, 0.7, 0.7
                    };

            herb_lizard_reproduce_random(herb_field, lizard_field, i);
            dir = SidesDirectionValues.FORWARD;
            decision = Actions.MOVEMENT;
            to_file(herb_field, lizard_field, predator_lizard_field, 0.7, dir, decision);

            lizard_field = new double[]
                    {
                            0.7, 0.7, 0.7,
                            0.7, 0, 0.7,
                            0.7, 0.7, 0.7
                    };

            herb_lizard_reproduce_random(herb_field, lizard_field, i);
            dir = SidesDirectionValues.FORWARD;
            decision = Actions.MOVEMENT;
            to_file(herb_field, lizard_field, predator_lizard_field, 0.7, dir, decision);

            lizard_field = new double[]
                    {
                            0, 0.7, 0.7,
                            0.7, 0, 0.7,
                            0.7, 0.7, 0
                    };

            herb_lizard_reproduce_random(herb_field, lizard_field, i);
            dir = SidesDirectionValues.FORWARD_RIGHT;
            decision = Actions.MOVEMENT;
            to_file(herb_field, lizard_field, predator_lizard_field, 0.7, dir, decision);

            lizard_field = new double[]
                    {
                            0, 0, 0.7,
                            0, 0, 0,
                            0.7, 0, 0
                    };

            herb_lizard_reproduce_random(herb_field, lizard_field, i);
            dir = SidesDirectionValues.FORWARD_RIGHT;
            decision = Actions.MOVEMENT;
            to_file(herb_field, lizard_field, predator_lizard_field, 0.7, dir, decision);

            lizard_field = new double[]
                    {
                            0.7, 0, 0.7,
                            0.7, 0, 0.7,
                            0.7, 0, 0.7
                    };

            herb_lizard_reproduce_random(herb_field, lizard_field, i);
            dir = SidesDirectionValues.NO_DIRECTION;
            decision = Actions.MOVEMENT;
            to_file(herb_field, lizard_field, predator_lizard_field, 0.7, dir, decision);

            lizard_field = new double[]
                    {
                            0, 0, 0,
                            0.7, 0, 0.7,
                            0, 0, 0
                    };

            herb_lizard_reproduce_random(herb_field, lizard_field, i);
            dir = SidesDirectionValues.NO_DIRECTION;
            decision = Actions.MOVEMENT;
            to_file(herb_field, lizard_field, predator_lizard_field, 0.7, dir, decision);

            lizard_field = new double[]
                    {
                            0.7, 0.7, 0,
                            0.7, 0, 0.7,
                            0, 0.7, 0.7
                    };

            herb_lizard_reproduce_random(herb_field, lizard_field, i);
            dir = SidesDirectionValues.FORWARD_LEFT;
            decision = Actions.MOVEMENT;
            to_file(herb_field, lizard_field, predator_lizard_field, 0.7, dir, decision);

            lizard_field = new double[]
                    {
                            0.7, 0, 0,
                            0, 0, 0,
                            0, 0, 0.7
                    };

            herb_lizard_reproduce_random(herb_field, lizard_field, i);
            dir = SidesDirectionValues.FORWARD_LEFT;
            decision = Actions.MOVEMENT;
            to_file(herb_field, lizard_field, predator_lizard_field, 0.7, dir, decision);

            lizard_field = new double[]
                    {
                            0, 0, 0,
                            0, 0, 0,
                            0.7, 0, 0.7
                    };

            herb_lizard_reproduce_random(herb_field, lizard_field, i);
            dir = SidesDirectionValues.NO_DIRECTION;
            decision = Actions.MOVEMENT;
            to_file(herb_field, lizard_field, predator_lizard_field, 0.7, dir, decision);

            lizard_field = new double[]
                    {
                            0.7, 0, 0.7,
                            0, 0, 0,
                            0, 0, 0
                    };

            herb_lizard_reproduce_random(herb_field, lizard_field, i);
            dir = SidesDirectionValues.NO_DIRECTION;
            decision = Actions.MOVEMENT;
            to_file(herb_field, lizard_field, predator_lizard_field, 0.7, dir, decision);

            lizard_field = new double[]
                    {
                            0.7, 0.7, 0.7,
                            0.7, 0.7, 0.7,
                            0.7, 0.7, 0.7
                    };

            herb_lizard_reproduce_random(herb_field, lizard_field, i);
            dir = SidesDirectionValues.NO_DIRECTION;
            decision = Actions.REPRODUCE;
            to_file(herb_field, lizard_field, predator_lizard_field, 0.7, dir, decision);
        }
    }

    private void herb_lizard_escape_predator_random(double [] herb_field, double[] lizard_field, int i){
        if (i == 1) {
            Random r = new Random();
            for (int j = 0; j < 9; j++) {
                lizard_field[j] = 0;
                if (r.nextDouble() < 0.8) {
                    double tmp = r.nextDouble();
                    if (tmp >= 0.5) {
                        lizard_field[j] = 0.5;
                    }
                    else{
                        lizard_field[j] = 0.3;
                    }
                }
            }
            for (int j = 0; j < 9; j++) {
                herb_field[j] = 0;
                if (r.nextDouble() < 0.8) {
                    herb_field[j] = 1;
                }
            }
        }
    }

    private void herb_lizard_escape_predator_pack(){
        double [] herb_field;
        double [] lizard_field;
        double [] predator_lizard_field;
        Actions decision;
        SidesDirectionValues dir;

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

        for (int i = 0; i < 2; i++) {
            //1
            predator_lizard_field = new double[]
                    {
                            0.7, 0, 0,
                            0, 0, 0,
                            0, 0, 0
                    };
            herb_lizard_escape_predator_random(herb_field, lizard_field, i);
            dir = SidesDirectionValues.BACKWARD_RIGHT;
            decision = Actions.MOVEMENT;
            to_file(herb_field, lizard_field, predator_lizard_field, 0.3, dir, decision);
            to_file(herb_field, lizard_field, predator_lizard_field, 0.5, dir, decision);
            to_file(herb_field, lizard_field, predator_lizard_field, 0.7, dir, decision);

            //2
            predator_lizard_field = new double[]
                    {
                            0, 0.7, 0,
                            0, 0, 0,
                            0, 0, 0
                    };
            herb_lizard_escape_predator_random(herb_field, lizard_field, i);
            dir = SidesDirectionValues.BACKWARD;
            decision = Actions.MOVEMENT;
            to_file(herb_field, lizard_field, predator_lizard_field, 0.3, dir, decision);
            to_file(herb_field, lizard_field, predator_lizard_field, 0.5, dir, decision);
            to_file(herb_field, lizard_field, predator_lizard_field, 0.7, dir, decision);

            //3
            predator_lizard_field = new double[]
                    {
                            0, 0, 0.7,
                            0, 0, 0,
                            0, 0, 0
                    };
            herb_lizard_escape_predator_random(herb_field, lizard_field, i);
            dir = SidesDirectionValues.BACKWARD_LEFT;
            decision = Actions.MOVEMENT;
            to_file(herb_field, lizard_field, predator_lizard_field, 0.3, dir, decision);
            to_file(herb_field, lizard_field, predator_lizard_field, 0.5, dir, decision);
            to_file(herb_field, lizard_field, predator_lizard_field, 0.7, dir, decision);

            //4
            predator_lizard_field = new double[]
                    {
                            0, 0, 0,
                            0.7, 0, 0,
                            0, 0, 0
                    };
            herb_lizard_escape_predator_random(herb_field, lizard_field, i);
            dir = SidesDirectionValues.RIGHT;
            decision = Actions.MOVEMENT;
            to_file(herb_field, lizard_field, predator_lizard_field, 0.3, dir, decision);
            to_file(herb_field, lizard_field, predator_lizard_field, 0.5, dir, decision);
            to_file(herb_field, lizard_field, predator_lizard_field, 0.7, dir, decision);

            //5
            predator_lizard_field = new double[]
                    {
                            0, 0, 0,
                            0, 0.7, 0,
                            0, 0, 0
                    };
            herb_lizard_escape_predator_random(herb_field, lizard_field, i);
            dir = SidesDirectionValues.NO_DIRECTION;
            decision = Actions.MOVEMENT;
            to_file(herb_field, lizard_field, predator_lizard_field, 0.3, dir, decision);
            to_file(herb_field, lizard_field, predator_lizard_field, 0.5, dir, decision);
            to_file(herb_field, lizard_field, predator_lizard_field, 0.7, dir, decision);

            //6
            predator_lizard_field = new double[]
                    {
                            0, 0, 0,
                            0, 0, 0.7,
                            0, 0, 0
                    };
            herb_lizard_escape_predator_random(herb_field, lizard_field, i);
            dir = SidesDirectionValues.LEFT;
            decision = Actions.MOVEMENT;
            to_file(herb_field, lizard_field, predator_lizard_field, 0.3, dir, decision);
            to_file(herb_field, lizard_field, predator_lizard_field, 0.5, dir, decision);
            to_file(herb_field, lizard_field, predator_lizard_field, 0.7, dir, decision);

            //7
            predator_lizard_field = new double[]
                    {
                            0, 0, 0,
                            0, 0, 0,
                            0.7, 0, 0
                    };
            herb_lizard_escape_predator_random(herb_field, lizard_field, i);
            dir = SidesDirectionValues.FORWARD_RIGHT;
            decision = Actions.MOVEMENT;
            to_file(herb_field, lizard_field, predator_lizard_field, 0.3, dir, decision);
            to_file(herb_field, lizard_field, predator_lizard_field, 0.5, dir, decision);
            to_file(herb_field, lizard_field, predator_lizard_field, 0.7, dir, decision);

            //8
            predator_lizard_field = new double[]
                    {
                            0, 0, 0,
                            0, 0, 0,
                            0, 0.7, 0
                    };
            herb_lizard_escape_predator_random(herb_field, lizard_field, i);
            dir = SidesDirectionValues.FORWARD;
            decision = Actions.MOVEMENT;
            to_file(herb_field, lizard_field, predator_lizard_field, 0.3, dir, decision);
            to_file(herb_field, lizard_field, predator_lizard_field, 0.5, dir, decision);
            to_file(herb_field, lizard_field, predator_lizard_field, 0.7, dir, decision);

            //9
            predator_lizard_field = new double[]
                    {
                            0, 0, 0,
                            0, 0, 0,
                            0, 0, 0.7
                    };
            herb_lizard_escape_predator_random(herb_field, lizard_field, i);
            dir = SidesDirectionValues.FORWARD_LEFT;
            decision = Actions.MOVEMENT;
            to_file(herb_field, lizard_field, predator_lizard_field, 0.3, dir, decision);
            to_file(herb_field, lizard_field, predator_lizard_field, 0.5, dir, decision);
            to_file(herb_field, lizard_field, predator_lizard_field, 0.7, dir, decision);
        }
    }

    private void herb_lizard_attack_predator_random(double [] herb_field, double[] lizard_field, int i){
        if (i == 1) {
            Random r = new Random();
            for (int j = 0; j < 9; j++) {
                herb_field[j] = 0;
                if (r.nextDouble() < 0.8) {
                    herb_field[j] = 1;
                }
            }
        }
    }

    private void herb_lizard_attack_predator_pack(){
        double [] herb_field;
        double [] lizard_field;
        double [] predator_lizard_field;
        Actions decision;
        SidesDirectionValues dir;

        //атака на хищника, если рядом есть здоровые травоядные
        herb_field = new double[]
                {
                        0, 0, 0,
                        0, 0, 0,
                        0, 0, 0
                };

        for (int i = 0; i < 2; i++) {
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

            dir = SidesDirectionValues.FORWARD_LEFT;
            decision = Actions.ATTACK;
            herb_lizard_attack_predator_random(herb_field, lizard_field, i);
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

            herb_lizard_attack_predator_random(herb_field, lizard_field, i);
            to_file(herb_field, lizard_field, predator_lizard_field, 0.7, dir, decision);
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

            herb_lizard_attack_predator_random(herb_field, lizard_field, i);
            to_file(herb_field, lizard_field, predator_lizard_field, 0.7, dir, decision);
            to_file(herb_field, lizard_field, predator_lizard_field, 0.5, dir, decision);
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
            dir = SidesDirectionValues.FORWARD;
            decision = Actions.ATTACK;
            herb_lizard_attack_predator_random(herb_field, lizard_field, i);
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
            herb_lizard_attack_predator_random(herb_field, lizard_field, i);
            to_file(herb_field, lizard_field, predator_lizard_field, 0.7, dir, decision);
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
            herb_lizard_attack_predator_random(herb_field, lizard_field, i);
            to_file(herb_field, lizard_field, predator_lizard_field, 0.7, dir, decision);
            to_file(herb_field, lizard_field, predator_lizard_field, 0.5, dir, decision);
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
            dir = SidesDirectionValues.FORWARD_RIGHT;
            decision = Actions.ATTACK;
            herb_lizard_attack_predator_random(herb_field, lizard_field, i);
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
            herb_lizard_attack_predator_random(herb_field, lizard_field, i);
            to_file(herb_field, lizard_field, predator_lizard_field, 0.7, dir, decision);
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
            herb_lizard_attack_predator_random(herb_field, lizard_field, i);
            to_file(herb_field, lizard_field, predator_lizard_field, 0.7, dir, decision);
            to_file(herb_field, lizard_field, predator_lizard_field, 0.5, dir, decision);
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
            dir = SidesDirectionValues.LEFT;
            decision = Actions.ATTACK;
            herb_lizard_attack_predator_random(herb_field, lizard_field, i);
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
            herb_lizard_attack_predator_random(herb_field, lizard_field, i);
            to_file(herb_field, lizard_field, predator_lizard_field, 0.7, dir, decision);
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
            herb_lizard_attack_predator_random(herb_field, lizard_field, i);
            to_file(herb_field, lizard_field, predator_lizard_field, 0.7, dir, decision);
            to_file(herb_field, lizard_field, predator_lizard_field, 0.5, dir, decision);
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
            dir = SidesDirectionValues.NO_DIRECTION;
            decision = Actions.ATTACK;
            herb_lizard_attack_predator_random(herb_field, lizard_field, i);
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
            herb_lizard_attack_predator_random(herb_field, lizard_field, i);
            to_file(herb_field, lizard_field, predator_lizard_field, 0.7, dir, decision);
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
            herb_lizard_attack_predator_random(herb_field, lizard_field, i);
            to_file(herb_field, lizard_field, predator_lizard_field, 0.7, dir, decision);
            to_file(herb_field, lizard_field, predator_lizard_field, 0.5, dir, decision);
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
            dir = SidesDirectionValues.RIGHT;
            decision = Actions.ATTACK;
            herb_lizard_attack_predator_random(herb_field, lizard_field, i);
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
            herb_lizard_attack_predator_random(herb_field, lizard_field, i);
            to_file(herb_field, lizard_field, predator_lizard_field, 0.7, dir, decision);
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
            herb_lizard_attack_predator_random(herb_field, lizard_field, i);
            to_file(herb_field, lizard_field, predator_lizard_field, 0.7, dir, decision);
            to_file(herb_field, lizard_field, predator_lizard_field, 0.5, dir, decision);
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
            dir = SidesDirectionValues.BACKWARD_LEFT;
            decision = Actions.ATTACK;
            herb_lizard_attack_predator_random(herb_field, lizard_field, i);
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
            herb_lizard_attack_predator_random(herb_field, lizard_field, i);
            to_file(herb_field, lizard_field, predator_lizard_field, 0.7, dir, decision);
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
            herb_lizard_attack_predator_random(herb_field, lizard_field, i);
            to_file(herb_field, lizard_field, predator_lizard_field, 0.7, dir, decision);
            to_file(herb_field, lizard_field, predator_lizard_field, 0.5, dir, decision);
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
            dir = SidesDirectionValues.BACKWARD;
            decision = Actions.ATTACK;
            herb_lizard_attack_predator_random(herb_field, lizard_field, i);
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
            herb_lizard_attack_predator_random(herb_field, lizard_field, i);
            to_file(herb_field, lizard_field, predator_lizard_field, 0.7, dir, decision);
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
            herb_lizard_attack_predator_random(herb_field, lizard_field, i);
            to_file(herb_field, lizard_field, predator_lizard_field, 0.7, dir, decision);
            to_file(herb_field, lizard_field, predator_lizard_field, 0.5, dir, decision);
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
            dir = SidesDirectionValues.BACKWARD_RIGHT;
            decision = Actions.ATTACK;
            herb_lizard_attack_predator_random(herb_field, lizard_field, i);
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
            herb_lizard_attack_predator_random(herb_field, lizard_field, i);
            to_file(herb_field, lizard_field, predator_lizard_field, 0.7, dir, decision);
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
            herb_lizard_attack_predator_random(herb_field, lizard_field, i);
            to_file(herb_field, lizard_field, predator_lizard_field, 0.7, dir, decision);
            to_file(herb_field, lizard_field, predator_lizard_field, 0.5, dir, decision);
            to_file(herb_field, lizard_field, predator_lizard_field, 0.3, dir, decision);
        }

    }

    public void herb_lizard_gen(){
        try {
            this.writer = new FileWriter(lizard_data_set_file_name);
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }

        //Запись в файл обучаемого множества для травоядных ящерок.
        herb_lizard_confusion_pack();
        herb_lizard_eating_pack();
        herb_lizard_reproduction_pack();
        herb_lizard_escape_predator_pack();
        herb_lizard_attack_predator_pack();

        try {
            writer.close();
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }

    private void predator_lizard_confusion_pack(){
        double [] corpse_field;
        double [] lizard_field;
        double [] predator_lizard_field;
        Actions decision;
        SidesDirectionValues dir;

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

        dir = SidesDirectionValues.NO_DIRECTION;
        decision = Actions.MOVEMENT;
        to_file(corpse_field, lizard_field, predator_lizard_field, 0.3, dir, decision);
        to_file(corpse_field, lizard_field, predator_lizard_field, 0.5, dir, decision);
        to_file(corpse_field, lizard_field, predator_lizard_field, 0.7, dir, decision);

        //искать еду, а не размножаться
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
                        0.7, 0.7, 0.7,
                        0.7, 0.7, 0.7,
                        0.7, 0.7, 0.7
                };

        dir = SidesDirectionValues.NO_DIRECTION;
        decision = Actions.MOVEMENT;
        to_file(corpse_field, lizard_field, predator_lizard_field, 0.3, dir, decision);
        to_file(corpse_field, lizard_field, predator_lizard_field, 0.5, dir, decision);
        to_file(corpse_field, lizard_field, predator_lizard_field, 0.7, dir, decision);
    }

    private void predator_lizard_eating_random(double[] lizard_field, double[] predator_field, int i){
        if (i == 1) {
            Random r = new Random();
            for (int k = 0; k < 9; k++) {
                lizard_field[k] = 0;
                if (r.nextDouble() < 0.8){
                    double tmp = r.nextDouble();
                    if (tmp >= 0.7) {
                        lizard_field[k] = 0.7;
                    } else if (tmp >= 0.5){
                        lizard_field[k] = 0.5;
                    } else {
                        lizard_field[k] = 0.3;
                    }
                }
            }
            for (int k = 0; k < 9; k++) {
                predator_field[k] = 0;
                if (r.nextDouble() < 0.8){
                    double tmp = r.nextDouble();
                    if (tmp >= 0.7) {
                        predator_field[k] = 0.7;
                    } else if (tmp >= 0.5){
                        predator_field[k] = 0.5;
                    } else {
                        predator_field[k] = 0.3;
                    }
                }
            }
        }
    }

    private void predator_lizard_eating_pack(){
        double [] corpse_field;
        double [] lizard_field;
        double [] predator_lizard_field;
        Actions decision;
        SidesDirectionValues dir;

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

        for (int i = 0; i < 2; i++) {
            //1
            corpse_field = new double[]
                    {
                            1, 0, 0,
                            0, 0, 0,
                            0, 0, 0
                    };

            predator_lizard_eating_random(lizard_field, predator_lizard_field, i);
            dir = SidesDirectionValues.FORWARD_LEFT;
            decision = Actions.MOVEMENT;
            to_file(corpse_field, lizard_field, predator_lizard_field, 0.3, dir, decision);
            to_file(corpse_field, lizard_field, predator_lizard_field, 0.5, dir, decision);

            //2
            corpse_field = new double[]
                    {
                            0, 1, 0,
                            0, 0, 0,
                            0, 0, 0
                    };

            predator_lizard_eating_random(lizard_field, predator_lizard_field, i);
            dir = SidesDirectionValues.FORWARD;
            decision = Actions.MOVEMENT;
            to_file(corpse_field, lizard_field, predator_lizard_field, 0.3, dir, decision);
            to_file(corpse_field, lizard_field, predator_lizard_field, 0.5, dir, decision);

            //3
            corpse_field = new double[]
                    {
                            0, 0, 1,
                            0, 0, 0,
                            0, 0, 0
                    };

            predator_lizard_eating_random(lizard_field, predator_lizard_field, i);
            dir = SidesDirectionValues.FORWARD_RIGHT;
            decision = Actions.MOVEMENT;
            to_file(corpse_field, lizard_field, predator_lizard_field, 0.3, dir, decision);
            to_file(corpse_field, lizard_field, predator_lizard_field, 0.5, dir, decision);

            //4
            corpse_field = new double[]
                    {
                            0, 0, 0,
                            1, 0, 0,
                            0, 0, 0
                    };

            predator_lizard_eating_random(lizard_field, predator_lizard_field, i);
            dir = SidesDirectionValues.LEFT;
            decision = Actions.MOVEMENT;
            to_file(corpse_field, lizard_field, predator_lizard_field, 0.3, dir, decision);
            to_file(corpse_field, lizard_field, predator_lizard_field, 0.5, dir, decision);

            //5
            corpse_field = new double[]
                    {
                            0, 0, 0,
                            0, 1, 0,
                            0, 0, 0
                    };

            predator_lizard_eating_random(lizard_field, predator_lizard_field, i);
            dir = SidesDirectionValues.NO_DIRECTION;
            decision = Actions.EATING;
            to_file(corpse_field, lizard_field, predator_lizard_field, 0.3, dir, decision);
            to_file(corpse_field, lizard_field, predator_lizard_field, 0.5, dir, decision);

            //6
            corpse_field = new double[]
                    {
                            0, 0, 0,
                            0, 0, 1,
                            0, 0, 0
                    };

            predator_lizard_eating_random(lizard_field, predator_lizard_field, i);
            dir = SidesDirectionValues.RIGHT;
            decision = Actions.MOVEMENT;
            to_file(corpse_field, lizard_field, predator_lizard_field, 0.3, dir, decision);
            to_file(corpse_field, lizard_field, predator_lizard_field, 0.5, dir, decision);

            //7
            corpse_field = new double[]
                    {
                            0, 0, 0,
                            0, 0, 0,
                            1, 0, 0
                    };

            predator_lizard_eating_random(lizard_field, predator_lizard_field, i);
            dir = SidesDirectionValues.BACKWARD_LEFT;
            decision = Actions.MOVEMENT;
            to_file(corpse_field, lizard_field, predator_lizard_field, 0.3, dir, decision);
            to_file(corpse_field, lizard_field, predator_lizard_field, 0.5, dir, decision);

            //8
            corpse_field = new double[]
                    {
                            0, 0, 0,
                            0, 0, 0,
                            0, 1, 0
                    };

            predator_lizard_eating_random(lizard_field, predator_lizard_field, i);
            dir = SidesDirectionValues.BACKWARD;
            decision = Actions.MOVEMENT;
            to_file(corpse_field, lizard_field, predator_lizard_field, 0.3, dir, decision);
            to_file(corpse_field, lizard_field, predator_lizard_field, 0.5, dir, decision);

            //9
            corpse_field = new double[]
                    {
                            0, 0, 0,
                            0, 0, 0,
                            0, 0, 1
                    };

            predator_lizard_eating_random(lizard_field, predator_lizard_field, i);
            dir = SidesDirectionValues.BACKWARD_RIGHT;
            decision = Actions.MOVEMENT;
            to_file(corpse_field, lizard_field, predator_lizard_field, 0.3, dir, decision);
            to_file(corpse_field, lizard_field, predator_lizard_field, 0.5, dir, decision);

            //Комбинации:
            corpse_field = new double[]
                    {
                            1, 1, 0,
                            1, 0, 0,
                            0, 0, 0
                    };

            predator_lizard_eating_random(lizard_field, predator_lizard_field, i);
            dir = SidesDirectionValues.FORWARD_LEFT;
            decision = Actions.MOVEMENT;
            to_file(corpse_field, lizard_field, predator_lizard_field, 0.3, dir, decision);
            to_file(corpse_field, lizard_field, predator_lizard_field, 0.5, dir, decision);

            corpse_field = new double[]
                    {
                            1, 1, 1,
                            0, 0, 0,
                            0, 0, 0
                    };

            predator_lizard_eating_random(lizard_field, predator_lizard_field, i);
            dir = SidesDirectionValues.FORWARD;
            decision = Actions.MOVEMENT;
            to_file(corpse_field, lizard_field, predator_lizard_field, 0.3, dir, decision);
            to_file(corpse_field, lizard_field, predator_lizard_field, 0.5, dir, decision);

            corpse_field = new double[]
                    {
                            0, 1, 1,
                            0, 0, 1,
                            0, 0, 0
                    };

            predator_lizard_eating_random(lizard_field, predator_lizard_field, i);
            dir = SidesDirectionValues.FORWARD_RIGHT;
            decision = Actions.MOVEMENT;
            to_file(corpse_field, lizard_field, predator_lizard_field, 0.3, dir, decision);
            to_file(corpse_field, lizard_field, predator_lizard_field, 0.5, dir, decision);

            corpse_field = new double[]
                    {
                            0, 0, 1,
                            0, 0, 1,
                            0, 0, 1
                    };

            predator_lizard_eating_random(lizard_field, predator_lizard_field, i);
            dir = SidesDirectionValues.FORWARD_RIGHT;
            decision = Actions.MOVEMENT;
            to_file(corpse_field, lizard_field, predator_lizard_field, 0.3, dir, decision);
            to_file(corpse_field, lizard_field, predator_lizard_field, 0.5, dir, decision);

            corpse_field = new double[]
                    {
                            0, 0, 0,
                            0, 0, 1,
                            0, 1, 1
                    };

            predator_lizard_eating_random(lizard_field, predator_lizard_field, i);
            dir = SidesDirectionValues.BACKWARD_RIGHT;
            decision = Actions.MOVEMENT;
            to_file(corpse_field, lizard_field, predator_lizard_field, 0.3, dir, decision);
            to_file(corpse_field, lizard_field, predator_lizard_field, 0.5, dir, decision);

            corpse_field = new double[]
                    {
                            0, 0, 0,
                            0, 0, 0,
                            1, 1, 1
                    };

            predator_lizard_eating_random(lizard_field, predator_lizard_field, i);
            dir = SidesDirectionValues.BACKWARD;
            decision = Actions.MOVEMENT;
            to_file(corpse_field, lizard_field, predator_lizard_field, 0.3, dir, decision);
            to_file(corpse_field, lizard_field, predator_lizard_field, 0.5, dir, decision);

            corpse_field = new double[]
                    {
                            0, 0, 0,
                            1, 0, 0,
                            1, 1, 0
                    };

            predator_lizard_eating_random(lizard_field, predator_lizard_field, i);
            dir = SidesDirectionValues.BACKWARD_LEFT;
            decision = Actions.MOVEMENT;
            to_file(corpse_field, lizard_field, predator_lizard_field, 0.3, dir, decision);
            to_file(corpse_field, lizard_field, predator_lizard_field, 0.5, dir, decision);

            corpse_field = new double[]
                    {
                            1, 0, 0,
                            1, 0, 0,
                            1, 0, 0
                    };

            predator_lizard_eating_random(lizard_field, predator_lizard_field, i);
            dir = SidesDirectionValues.LEFT;
            decision = Actions.MOVEMENT;
            to_file(corpse_field, lizard_field, predator_lizard_field, 0.3, dir, decision);
            to_file(corpse_field, lizard_field, predator_lizard_field, 0.5, dir, decision);

            corpse_field = new double[]
                    {
                            1, 1, 1,
                            0, 0, 0,
                            1, 1, 1
                    };

            predator_lizard_eating_random(lizard_field, predator_lizard_field, i);
            dir = SidesDirectionValues.FORWARD;
            decision = Actions.MOVEMENT;
            to_file(corpse_field, lizard_field, predator_lizard_field, 0.3, dir, decision);
            to_file(corpse_field, lizard_field, predator_lizard_field, 0.5, dir, decision);

            corpse_field = new double[]
                    {
                            1, 1, 1,
                            1, 0, 1,
                            1, 1, 1
                    };

            predator_lizard_eating_random(lizard_field, predator_lizard_field, i);
            dir = SidesDirectionValues.FORWARD;
            decision = Actions.MOVEMENT;
            to_file(corpse_field, lizard_field, predator_lizard_field, 0.3, dir, decision);
            to_file(corpse_field, lizard_field, predator_lizard_field, 0.5, dir, decision);

            corpse_field = new double[]
                    {
                            0, 1, 1,
                            1, 0, 1,
                            1, 1, 0
                    };

            predator_lizard_eating_random(lizard_field, predator_lizard_field, i);
            dir = SidesDirectionValues.FORWARD_RIGHT;
            decision = Actions.MOVEMENT;
            to_file(corpse_field, lizard_field, predator_lizard_field, 0.3, dir, decision);
            to_file(corpse_field, lizard_field, predator_lizard_field, 0.5, dir, decision);

            corpse_field = new double[]
                    {
                            0, 0, 1,
                            0, 0, 0,
                            1, 0, 0
                    };

            predator_lizard_eating_random(lizard_field, predator_lizard_field, i);
            dir = SidesDirectionValues.FORWARD_RIGHT;
            decision = Actions.MOVEMENT;
            to_file(corpse_field, lizard_field, predator_lizard_field, 0.3, dir, decision);
            to_file(corpse_field, lizard_field, predator_lizard_field, 0.5, dir, decision);

            corpse_field = new double[]
                    {
                            1, 0, 1,
                            1, 0, 1,
                            1, 0, 1
                    };

            predator_lizard_eating_random(lizard_field, predator_lizard_field, i);
            dir = SidesDirectionValues.NO_DIRECTION;
            decision = Actions.MOVEMENT;
            to_file(corpse_field, lizard_field, predator_lizard_field, 0.3, dir, decision);
            to_file(corpse_field, lizard_field, predator_lizard_field, 0.5, dir, decision);

            corpse_field = new double[]
                    {
                            0, 0, 0,
                            1, 0, 1,
                            0, 0, 0
                    };

            predator_lizard_eating_random(lizard_field, predator_lizard_field, i);
            dir = SidesDirectionValues.NO_DIRECTION;
            decision = Actions.MOVEMENT;
            to_file(corpse_field, lizard_field, predator_lizard_field, 0.3, dir, decision);
            to_file(corpse_field, lizard_field, predator_lizard_field, 0.5, dir, decision);

            corpse_field = new double[]
                    {
                            1, 1, 0,
                            1, 0, 1,
                            0, 1, 1
                    };

            predator_lizard_eating_random(lizard_field, predator_lizard_field, i);
            dir = SidesDirectionValues.FORWARD_LEFT;
            decision = Actions.MOVEMENT;
            to_file(corpse_field, lizard_field, predator_lizard_field, 0.3, dir, decision);
            to_file(corpse_field, lizard_field, predator_lizard_field, 0.5, dir, decision);

            corpse_field = new double[]
                    {
                            1, 0, 0,
                            0, 0, 0,
                            0, 0, 1
                    };

            predator_lizard_eating_random(lizard_field, predator_lizard_field, i);
            dir = SidesDirectionValues.FORWARD_LEFT;
            decision = Actions.MOVEMENT;
            to_file(corpse_field, lizard_field, predator_lizard_field, 0.3, dir, decision);
            to_file(corpse_field, lizard_field, predator_lizard_field, 0.5, dir, decision);

            corpse_field = new double[]
                    {
                            0, 0, 0,
                            0, 0, 0,
                            1, 0, 1
                    };

            predator_lizard_eating_random(lizard_field, predator_lizard_field, i);
            dir = SidesDirectionValues.NO_DIRECTION;
            decision = Actions.MOVEMENT;
            to_file(corpse_field, lizard_field, predator_lizard_field, 0.3, dir, decision);
            to_file(corpse_field, lizard_field, predator_lizard_field, 0.5, dir, decision);

            corpse_field = new double[]
                    {
                            1, 0, 1,
                            0, 0, 0,
                            0, 0, 0
                    };

            predator_lizard_eating_random(lizard_field, predator_lizard_field, i);
            dir = SidesDirectionValues.NO_DIRECTION;
            decision = Actions.MOVEMENT;
            to_file(corpse_field, lizard_field, predator_lizard_field, 0.3, dir, decision);
            to_file(corpse_field, lizard_field, predator_lizard_field, 0.5, dir, decision);

            corpse_field = new double[]
                    {
                            1, 1, 1,
                            1, 1, 1,
                            1, 1, 1
                    };

            predator_lizard_eating_random(lizard_field, predator_lizard_field, i);
            dir = SidesDirectionValues.NO_DIRECTION;
            decision = Actions.EATING;
            to_file(corpse_field, lizard_field, predator_lizard_field, 0.3, dir, decision);
            to_file(corpse_field, lizard_field, predator_lizard_field, 0.5, dir, decision);
        }
    }

    private void predator_lizard_hunting_random(double[] lizard_field, double[] predator_field, int i){
        Random r = new Random();
        for (int j = 0; j < 9; j++){
            if (lizard_field[j] == 1){
                double tmp = r.nextDouble();
                if (tmp >= 0.7) {
                    predator_field[j] = 0.7;
                } else if (tmp >= 0.5) {
                    predator_field[j] = 0.5;
                } else {
                    predator_field[j] = 0.3;
                }
            }
        }
        if (i == 1) {
            for (int j = 0; j < 9; j++) {
                predator_field[j] = 0;
            }
            for (int k = 0; k < 9; k++) {
                if (r.nextDouble() < 0.8) {
                    double tmp = r.nextDouble();
                    if (tmp >= 0.7) {
                        predator_field[k] = 0.7;
                    } else if (tmp >= 0.5) {
                        predator_field[k] = 0.5;
                    } else {
                        predator_field[k] = 0.3;
                    }
                }
            }
        }
    }

    private void predator_lizard_hunting_pack(){
        double [] corpse_field;
        double [] lizard_field;
        double [] predator_lizard_field;
        Actions decision;
        SidesDirectionValues dir;

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

        for (int i = 0; i < 2; i++) {
            //1
            lizard_field = new double[]
                    {
                            1, 0, 0,
                            0, 0, 0,
                            0, 0, 0
                    };

            dir = SidesDirectionValues.FORWARD_LEFT;
            decision = Actions.ATTACK;
            predator_lizard_hunting_random(lizard_field, predator_lizard_field, i);
            to_file(corpse_field, lizard_field, predator_lizard_field, 0.3, dir, decision);
            to_file(corpse_field, lizard_field, predator_lizard_field, 0.5, dir, decision);

            //2
            lizard_field = new double[]
                    {
                            0, 1, 0,
                            0, 0, 0,
                            0, 0, 0
                    };
            dir = SidesDirectionValues.FORWARD;
            decision = Actions.ATTACK;
            predator_lizard_hunting_random(lizard_field, predator_lizard_field, i);
            to_file(corpse_field, lizard_field, predator_lizard_field, 0.3, dir, decision);
            to_file(corpse_field, lizard_field, predator_lizard_field, 0.5, dir, decision);

            //3
            lizard_field = new double[]
                    {
                            0, 0, 1,
                            0, 0, 0,
                            0, 0, 0
                    };
            dir = SidesDirectionValues.FORWARD_RIGHT;
            decision = Actions.ATTACK;
            predator_lizard_hunting_random(lizard_field, predator_lizard_field, i);
            to_file(corpse_field, lizard_field, predator_lizard_field, 0.3, dir, decision);
            to_file(corpse_field, lizard_field, predator_lizard_field, 0.5, dir, decision);

            //4
            lizard_field = new double[]
                    {
                            0, 0, 0,
                            1, 0, 0,
                            0, 0, 0
                    };
            dir = SidesDirectionValues.LEFT;
            decision = Actions.ATTACK;
            predator_lizard_hunting_random(lizard_field, predator_lizard_field, i);
            to_file(corpse_field, lizard_field, predator_lizard_field, 0.3, dir, decision);
            to_file(corpse_field, lizard_field, predator_lizard_field, 0.5, dir, decision);

            //5
            lizard_field = new double[]
                    {
                            0, 0, 0,
                            0, 1, 0,
                            0, 0, 0
                    };
            dir = SidesDirectionValues.NO_DIRECTION;
            decision = Actions.ATTACK;
            predator_lizard_hunting_random(lizard_field, predator_lizard_field, i);
            to_file(corpse_field, lizard_field, predator_lizard_field, 0.3, dir, decision);
            to_file(corpse_field, lizard_field, predator_lizard_field, 0.5, dir, decision);

            //6
            lizard_field = new double[]
                    {
                            0, 0, 0,
                            0, 0, 1,
                            0, 0, 0
                    };
            dir = SidesDirectionValues.RIGHT;
            decision = Actions.ATTACK;
            predator_lizard_hunting_random(lizard_field, predator_lizard_field, i);
            to_file(corpse_field, lizard_field, predator_lizard_field, 0.3, dir, decision);
            to_file(corpse_field, lizard_field, predator_lizard_field, 0.5, dir, decision);

            //7
            lizard_field = new double[]
                    {
                            0, 0, 0,
                            0, 0, 0,
                            1, 0, 0
                    };
            dir = SidesDirectionValues.BACKWARD_LEFT;
            decision = Actions.ATTACK;
            predator_lizard_hunting_random(lizard_field, predator_lizard_field, i);
            to_file(corpse_field, lizard_field, predator_lizard_field, 0.3, dir, decision);
            to_file(corpse_field, lizard_field, predator_lizard_field, 0.5, dir, decision);

            //8
            lizard_field = new double[]
                    {
                            0, 0, 0,
                            0, 0, 0,
                            0, 1, 0
                    };
            dir = SidesDirectionValues.BACKWARD;
            decision = Actions.ATTACK;
            predator_lizard_hunting_random(lizard_field, predator_lizard_field, i);
            to_file(corpse_field, lizard_field, predator_lizard_field, 0.3, dir, decision);
            to_file(corpse_field, lizard_field, predator_lizard_field, 0.5, dir, decision);

            //9
            lizard_field = new double[]
                    {
                            0, 0, 0,
                            0, 0, 0,
                            0, 0, 1
                    };
            dir = SidesDirectionValues.BACKWARD_RIGHT;
            decision = Actions.ATTACK;
            predator_lizard_hunting_random(lizard_field, predator_lizard_field, i);
            to_file(corpse_field, lizard_field, predator_lizard_field, 0.3, dir, decision);
            to_file(corpse_field, lizard_field, predator_lizard_field, 0.5, dir, decision);

            //Комбинации:
            lizard_field = new double[]
                    {
                            1, 1, 0,
                            1, 0, 0,
                            0, 0, 0
                    };

            predator_lizard_hunting_random(lizard_field, predator_lizard_field, i);
            dir = SidesDirectionValues.FORWARD_LEFT;
            decision = Actions.ATTACK;
            to_file(corpse_field, lizard_field, predator_lizard_field, 0.3, dir, decision);
            to_file(corpse_field, lizard_field, predator_lizard_field, 0.5, dir, decision);

            lizard_field = new double[]
                    {
                            1, 1, 1,
                            0, 0, 0,
                            0, 0, 0
                    };

            predator_lizard_hunting_random(lizard_field, predator_lizard_field, i);
            dir = SidesDirectionValues.FORWARD;
            decision = Actions.ATTACK;
            to_file(corpse_field, lizard_field, predator_lizard_field, 0.3, dir, decision);
            to_file(corpse_field, lizard_field, predator_lizard_field, 0.5, dir, decision);

            lizard_field = new double[]
                    {
                            0, 1, 1,
                            0, 0, 1,
                            0, 0, 0
                    };

            predator_lizard_hunting_random(lizard_field, predator_lizard_field, i);
            dir = SidesDirectionValues.FORWARD_RIGHT;
            decision = Actions.ATTACK;
            to_file(corpse_field, lizard_field, predator_lizard_field, 0.3, dir, decision);
            to_file(corpse_field, lizard_field, predator_lizard_field, 0.5, dir, decision);

            lizard_field = new double[]
                    {
                            0, 0, 1,
                            0, 0, 1,
                            0, 0, 1
                    };

            predator_lizard_hunting_random(lizard_field, predator_lizard_field, i);
            dir = SidesDirectionValues.FORWARD_RIGHT;
            decision = Actions.ATTACK;
            to_file(corpse_field, lizard_field, predator_lizard_field, 0.3, dir, decision);
            to_file(corpse_field, lizard_field, predator_lizard_field, 0.5, dir, decision);

            lizard_field = new double[]
                    {
                            0, 0, 0,
                            0, 0, 1,
                            0, 1, 1
                    };

            predator_lizard_hunting_random(lizard_field, predator_lizard_field, i);
            dir = SidesDirectionValues.BACKWARD_RIGHT;
            decision = Actions.ATTACK;
            to_file(corpse_field, lizard_field, predator_lizard_field, 0.3, dir, decision);
            to_file(corpse_field, lizard_field, predator_lizard_field, 0.5, dir, decision);

            lizard_field = new double[]
                    {
                            0, 0, 0,
                            0, 0, 0,
                            1, 1, 1
                    };

            predator_lizard_hunting_random(lizard_field, predator_lizard_field, i);
            dir = SidesDirectionValues.BACKWARD;
            decision = Actions.ATTACK;
            to_file(corpse_field, lizard_field, predator_lizard_field, 0.3, dir, decision);
            to_file(corpse_field, lizard_field, predator_lizard_field, 0.5, dir, decision);

            lizard_field = new double[]
                    {
                            0, 0, 0,
                            1, 0, 0,
                            1, 1, 0
                    };

            predator_lizard_hunting_random(lizard_field, predator_lizard_field, i);
            dir = SidesDirectionValues.BACKWARD_LEFT;
            decision = Actions.ATTACK;
            to_file(corpse_field, lizard_field, predator_lizard_field, 0.3, dir, decision);
            to_file(corpse_field, lizard_field, predator_lizard_field, 0.5, dir, decision);

            lizard_field = new double[]
                    {
                            1, 0, 0,
                            1, 0, 0,
                            1, 0, 0
                    };

            predator_lizard_hunting_random(lizard_field, predator_lizard_field, i);
            dir = SidesDirectionValues.LEFT;
            decision = Actions.ATTACK;
            to_file(corpse_field, lizard_field, predator_lizard_field, 0.3, dir, decision);
            to_file(corpse_field, lizard_field, predator_lizard_field, 0.5, dir, decision);

            lizard_field = new double[]
                    {
                            1, 1, 1,
                            0, 0, 0,
                            1, 1, 1
                    };

            predator_lizard_hunting_random(lizard_field, predator_lizard_field, i);
            dir = SidesDirectionValues.FORWARD;
            decision = Actions.ATTACK;
            to_file(corpse_field, lizard_field, predator_lizard_field, 0.3, dir, decision);
            to_file(corpse_field, lizard_field, predator_lizard_field, 0.5, dir, decision);

            lizard_field = new double[]
                    {
                            1, 1, 1,
                            1, 0, 1,
                            1, 1, 1
                    };

            predator_lizard_hunting_random(lizard_field, predator_lizard_field, i);
            dir = SidesDirectionValues.FORWARD;
            decision = Actions.ATTACK;
            to_file(corpse_field, lizard_field, predator_lizard_field, 0.3, dir, decision);
            to_file(corpse_field, lizard_field, predator_lizard_field, 0.5, dir, decision);

            lizard_field = new double[]
                    {
                            0, 1, 1,
                            1, 0, 1,
                            1, 1, 0
                    };

            predator_lizard_hunting_random(lizard_field, predator_lizard_field, i);
            dir = SidesDirectionValues.FORWARD_RIGHT;
            decision = Actions.ATTACK;
            to_file(corpse_field, lizard_field, predator_lizard_field, 0.3, dir, decision);
            to_file(corpse_field, lizard_field, predator_lizard_field, 0.5, dir, decision);

            lizard_field = new double[]
                    {
                            0, 0, 1,
                            0, 0, 0,
                            1, 0, 0
                    };

            predator_lizard_hunting_random(lizard_field, predator_lizard_field, i);
            dir = SidesDirectionValues.FORWARD_RIGHT;
            decision = Actions.ATTACK;
            to_file(corpse_field, lizard_field, predator_lizard_field, 0.3, dir, decision);
            to_file(corpse_field, lizard_field, predator_lizard_field, 0.5, dir, decision);

            lizard_field = new double[]
                    {
                            1, 0, 1,
                            1, 0, 1,
                            1, 0, 1
                    };

            predator_lizard_hunting_random(lizard_field, predator_lizard_field, i);
            dir = SidesDirectionValues.NO_DIRECTION;
            decision = Actions.ATTACK;
            to_file(corpse_field, lizard_field, predator_lizard_field, 0.3, dir, decision);
            to_file(corpse_field, lizard_field, predator_lizard_field, 0.5, dir, decision);

            lizard_field = new double[]
                    {
                            0, 0, 0,
                            1, 0, 1,
                            0, 0, 0
                    };

            predator_lizard_hunting_random(lizard_field, predator_lizard_field, i);
            dir = SidesDirectionValues.NO_DIRECTION;
            decision = Actions.ATTACK;
            to_file(corpse_field, lizard_field, predator_lizard_field, 0.3, dir, decision);
            to_file(corpse_field, lizard_field, predator_lizard_field, 0.5, dir, decision);

            lizard_field = new double[]
                    {
                            1, 1, 0,
                            1, 0, 1,
                            0, 1, 1
                    };

            predator_lizard_hunting_random(lizard_field, predator_lizard_field, i);
            dir = SidesDirectionValues.FORWARD_LEFT;
            decision = Actions.ATTACK;
            to_file(corpse_field, lizard_field, predator_lizard_field, 0.3, dir, decision);
            to_file(corpse_field, lizard_field, predator_lizard_field, 0.5, dir, decision);

            lizard_field = new double[]
                    {
                            1, 0, 0,
                            0, 0, 0,
                            0, 0, 1
                    };

            predator_lizard_hunting_random(lizard_field, predator_lizard_field, i);
            dir = SidesDirectionValues.FORWARD_LEFT;
            decision = Actions.ATTACK;
            to_file(corpse_field, lizard_field, predator_lizard_field, 0.3, dir, decision);
            to_file(corpse_field, lizard_field, predator_lizard_field, 0.5, dir, decision);

            lizard_field = new double[]
                    {
                            0, 0, 0,
                            0, 0, 0,
                            1, 0, 1
                    };

            predator_lizard_hunting_random(lizard_field, predator_lizard_field, i);
            dir = SidesDirectionValues.NO_DIRECTION;
            decision = Actions.ATTACK;
            to_file(corpse_field, lizard_field, predator_lizard_field, 0.3, dir, decision);
            to_file(corpse_field, lizard_field, predator_lizard_field, 0.5, dir, decision);

            lizard_field = new double[]
                    {
                            1, 0, 1,
                            0, 0, 0,
                            0, 0, 0
                    };

            predator_lizard_hunting_random(lizard_field, predator_lizard_field, i);
            dir = SidesDirectionValues.NO_DIRECTION;
            decision = Actions.ATTACK;
            to_file(corpse_field, lizard_field, predator_lizard_field, 0.3, dir, decision);
            to_file(corpse_field, lizard_field, predator_lizard_field, 0.5, dir, decision);

            lizard_field = new double[]
                    {
                            1, 1, 1,
                            1, 1, 1,
                            1, 1, 1
                    };

            predator_lizard_hunting_random(lizard_field, predator_lizard_field, i);
            dir = SidesDirectionValues.NO_DIRECTION;
            decision = Actions.ATTACK;
            to_file(corpse_field, lizard_field, predator_lizard_field, 0.3, dir, decision);
            to_file(corpse_field, lizard_field, predator_lizard_field, 0.5, dir, decision);
        }
    }

    private void predator_lizard_reproduction_random(double[] corpse_field, double[] lizard_field, int i){
        Random r = new Random();
        if (i == 1) {
            for(int j = 0; j < 9; j++) {
                corpse_field[j] = 0;
                double tmp = r.nextDouble();
                if (tmp >= 0.5) {
                    corpse_field[j] = 1;
                }
            }
            for (int k = 0; k < 9; k++) {
                lizard_field[k] = 0;
                if (r.nextDouble() < 0.8) {
                    double tmp = r.nextDouble();
                    if (tmp >= 0.7) {
                        lizard_field[k] = 0.7;
                    } else if (tmp >= 0.5) {
                        lizard_field[k] = 0.5;
                    } else {
                        lizard_field[k] = 0.3;
                    }
                }
            }
        }
    }

    private void predator_lizard_reproduction_pack(){
        double [] corpse_field;
        double [] lizard_field;
        double [] predator_lizard_field;
        Actions decision;
        SidesDirectionValues dir;

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

        for (int i = 0; i < 2; i++) {
            //1
            predator_lizard_field = new double[]
                    {
                            0.7, 0, 0,
                            0, 0, 0,
                            0, 0, 0
                    };

            dir = SidesDirectionValues.FORWARD_LEFT;
            decision = Actions.MOVEMENT;
            predator_lizard_reproduction_random(corpse_field, lizard_field, i);
            to_file(corpse_field, lizard_field, predator_lizard_field, 0.7, dir, decision);

            //2
            predator_lizard_field = new double[]
                    {
                            0, 0.7, 0,
                            0, 0, 0,
                            0, 0, 0
                    };
            dir = SidesDirectionValues.FORWARD;
            decision = Actions.MOVEMENT;
            predator_lizard_reproduction_random(corpse_field, lizard_field, i);
            to_file(corpse_field, lizard_field, predator_lizard_field, 0.7, dir, decision);

            //3
            predator_lizard_field = new double[]
                    {
                            0, 0, 0.7,
                            0, 0, 0,
                            0, 0, 0
                    };
            dir = SidesDirectionValues.FORWARD_RIGHT;
            decision = Actions.MOVEMENT;
            predator_lizard_reproduction_random(corpse_field, lizard_field, i);
            to_file(corpse_field, lizard_field, predator_lizard_field, 0.7, dir, decision);

            //4
            predator_lizard_field = new double[]
                    {
                            0, 0, 0,
                            0.7, 0, 0,
                            0, 0, 0
                    };
            dir = SidesDirectionValues.LEFT;
            decision = Actions.MOVEMENT;
            predator_lizard_reproduction_random(corpse_field, lizard_field, i);
            to_file(corpse_field, lizard_field, predator_lizard_field, 0.7, dir, decision);

            //5
            predator_lizard_field = new double[]
                    {
                            0, 0, 0,
                            0, 0.7, 0,
                            0, 0, 0
                    };
            dir = SidesDirectionValues.NO_DIRECTION;
            decision = Actions.REPRODUCE;
            predator_lizard_reproduction_random(corpse_field, lizard_field, i);
            to_file(corpse_field, lizard_field, predator_lizard_field, 0.7, dir, decision);

            //6
            predator_lizard_field = new double[]
                    {
                            0, 0, 0,
                            0, 0, 0.7,
                            0, 0, 0
                    };
            dir = SidesDirectionValues.RIGHT;
            decision = Actions.MOVEMENT;
            predator_lizard_reproduction_random(corpse_field, lizard_field, i);
            to_file(corpse_field, lizard_field, predator_lizard_field, 0.7, dir, decision);

            //7
            predator_lizard_field = new double[]
                    {
                            0, 0, 0,
                            0, 0, 0,
                            0.7, 0, 0
                    };
            dir = SidesDirectionValues.BACKWARD_LEFT;
            decision = Actions.MOVEMENT;
            predator_lizard_reproduction_random(corpse_field, lizard_field, i);
            to_file(corpse_field, lizard_field, predator_lizard_field, 0.7, dir, decision);

            //8
            predator_lizard_field = new double[]
                    {
                            0, 0, 0,
                            0, 0, 0,
                            0, 0.7, 0
                    };
            dir = SidesDirectionValues.BACKWARD;
            decision = Actions.MOVEMENT;
            predator_lizard_reproduction_random(corpse_field, lizard_field, i);
            to_file(corpse_field, lizard_field, predator_lizard_field, 0.7, dir, decision);

            //9
            predator_lizard_field = new double[]
                    {
                            0, 0, 0,
                            0, 0, 0,
                            0, 0, 0.7
                    };
            dir = SidesDirectionValues.BACKWARD_RIGHT;
            decision = Actions.MOVEMENT;
            predator_lizard_reproduction_random(corpse_field, lizard_field, i);
            to_file(corpse_field, lizard_field, predator_lizard_field, 0.7, dir, decision);

            //Комбинации
            predator_lizard_field = new double[]
                    {
                            0.7, 0.7, 0,
                            0.7, 0, 0,
                            0, 0, 0
                    };

            predator_lizard_reproduction_random(corpse_field, lizard_field, i);
            dir = SidesDirectionValues.FORWARD_LEFT;
            decision = Actions.MOVEMENT;
            to_file(corpse_field, lizard_field, predator_lizard_field, 0.7, dir, decision);

            predator_lizard_field = new double[]
                    {
                            0.7, 0.7, 0.7,
                            0, 0, 0,
                            0, 0, 0
                    };

            predator_lizard_reproduction_random(corpse_field, lizard_field, i);
            dir = SidesDirectionValues.FORWARD;
            decision = Actions.MOVEMENT;
            to_file(corpse_field, lizard_field, predator_lizard_field, 0.7, dir, decision);

            predator_lizard_field = new double[]
                    {
                            0, 0.7, 0.7,
                            0, 0, 0.7,
                            0, 0, 0
                    };

            predator_lizard_reproduction_random(corpse_field, lizard_field, i);
            dir = SidesDirectionValues.FORWARD_RIGHT;
            decision = Actions.MOVEMENT;
            to_file(corpse_field, lizard_field, predator_lizard_field, 0.7, dir, decision);

            predator_lizard_field = new double[]
                    {
                            0, 0, 0.7,
                            0, 0, 0.7,
                            0, 0, 0.7
                    };

            predator_lizard_reproduction_random(corpse_field, lizard_field, i);
            dir = SidesDirectionValues.FORWARD_RIGHT;
            decision = Actions.MOVEMENT;
            to_file(corpse_field, lizard_field, predator_lizard_field, 0.7, dir, decision);

            predator_lizard_field = new double[]
                    {
                            0, 0, 0,
                            0, 0, 0.7,
                            0, 0.7, 0.7
                    };

            predator_lizard_reproduction_random(corpse_field, lizard_field, i);
            dir = SidesDirectionValues.BACKWARD_RIGHT;
            decision = Actions.MOVEMENT;
            to_file(corpse_field, lizard_field, predator_lizard_field, 0.7, dir, decision);

            predator_lizard_field = new double[]
                    {
                            0, 0, 0,
                            0, 0, 0,
                            0.7, 0.7, 0.7
                    };

            predator_lizard_reproduction_random(corpse_field, lizard_field, i);
            dir = SidesDirectionValues.BACKWARD;
            decision = Actions.MOVEMENT;
            to_file(corpse_field, lizard_field, predator_lizard_field, 0.7, dir, decision);

            predator_lizard_field = new double[]
                    {
                            0, 0, 0,
                            0.7, 0, 0,
                            0.7, 0.7, 0
                    };

            predator_lizard_reproduction_random(corpse_field, lizard_field, i);
            dir = SidesDirectionValues.BACKWARD_LEFT;
            decision = Actions.MOVEMENT;
            to_file(corpse_field, lizard_field, predator_lizard_field, 0.7, dir, decision);

            predator_lizard_field = new double[]
                    {
                            0.7, 0, 0,
                            0.7, 0, 0,
                            0.7, 0, 0
                    };

            predator_lizard_reproduction_random(corpse_field, lizard_field, i);
            dir = SidesDirectionValues.LEFT;
            decision = Actions.MOVEMENT;
            to_file(corpse_field, lizard_field, predator_lizard_field, 0.7, dir, decision);

            predator_lizard_field = new double[]
                    {
                            0.7, 0.7, 0.7,
                            0, 0, 0,
                            0.7, 0.7, 0.7
                    };

            predator_lizard_reproduction_random(corpse_field, lizard_field, i);
            dir = SidesDirectionValues.FORWARD;
            decision = Actions.MOVEMENT;
            to_file(corpse_field, lizard_field, predator_lizard_field, 0.7, dir, decision);

            predator_lizard_field = new double[]
                    {
                            0.7, 0.7, 0.7,
                            0.7, 0, 0.7,
                            0.7, 0.7, 0.7
                    };

            predator_lizard_reproduction_random(corpse_field, lizard_field, i);
            dir = SidesDirectionValues.FORWARD;
            decision = Actions.MOVEMENT;
            to_file(corpse_field, lizard_field, predator_lizard_field, 0.7, dir, decision);

            predator_lizard_field = new double[]
                    {
                            0, 0.7, 0.7,
                            0.7, 0, 0.7,
                            0.7, 0.7, 0
                    };

            predator_lizard_reproduction_random(corpse_field, lizard_field, i);
            dir = SidesDirectionValues.FORWARD_RIGHT;
            decision = Actions.MOVEMENT;
            to_file(corpse_field, lizard_field, predator_lizard_field, 0.7, dir, decision);

            predator_lizard_field = new double[]
                    {
                            0, 0, 0.7,
                            0, 0, 0,
                            0.7, 0, 0
                    };

            predator_lizard_reproduction_random(corpse_field, lizard_field, i);
            dir = SidesDirectionValues.FORWARD_RIGHT;
            decision = Actions.MOVEMENT;
            to_file(corpse_field, lizard_field, predator_lizard_field, 0.7, dir, decision);

            predator_lizard_field = new double[]
                    {
                            0.7, 0, 0.7,
                            0.7, 0, 0.7,
                            0.7, 0, 0.7
                    };

            predator_lizard_reproduction_random(corpse_field, lizard_field, i);
            dir = SidesDirectionValues.NO_DIRECTION;
            decision = Actions.MOVEMENT;
            to_file(corpse_field, lizard_field, predator_lizard_field, 0.7, dir, decision);

            predator_lizard_field = new double[]
                    {
                            0, 0, 0,
                            0.7, 0, 0.7,
                            0, 0, 0
                    };

            predator_lizard_reproduction_random(corpse_field, lizard_field, i);
            dir = SidesDirectionValues.NO_DIRECTION;
            decision = Actions.MOVEMENT;
            to_file(corpse_field, lizard_field, predator_lizard_field, 0.7, dir, decision);

            predator_lizard_field = new double[]
                    {
                            0.7, 0.7, 0,
                            0.7, 0, 0.7,
                            0, 0.7, 0.7
                    };

            predator_lizard_reproduction_random(corpse_field, lizard_field, i);
            dir = SidesDirectionValues.FORWARD_LEFT;
            decision = Actions.MOVEMENT;
            to_file(corpse_field, lizard_field, predator_lizard_field, 0.7, dir, decision);

            predator_lizard_field = new double[]
                    {
                            0.7, 0, 0,
                            0, 0, 0,
                            0, 0, 0.7
                    };

            predator_lizard_reproduction_random(corpse_field, lizard_field, i);
            dir = SidesDirectionValues.FORWARD_LEFT;
            decision = Actions.MOVEMENT;
            to_file(corpse_field, lizard_field, predator_lizard_field, 0.7, dir, decision);

            predator_lizard_field = new double[]
                    {
                            0, 0, 0,
                            0, 0, 0,
                            0.7, 0, 0.7
                    };

            predator_lizard_reproduction_random(corpse_field, lizard_field, i);
            dir = SidesDirectionValues.NO_DIRECTION;
            decision = Actions.MOVEMENT;
            to_file(corpse_field, lizard_field, predator_lizard_field, 0.7, dir, decision);

            predator_lizard_field = new double[]
                    {
                            0.7, 0, 0.7,
                            0, 0, 0,
                            0, 0, 0
                    };

            predator_lizard_reproduction_random(corpse_field, lizard_field, i);
            dir = SidesDirectionValues.NO_DIRECTION;
            decision = Actions.MOVEMENT;
            to_file(corpse_field, lizard_field, predator_lizard_field, 0.7, dir, decision);

            predator_lizard_field = new double[]
                    {
                            0.7, 0.7, 0.7,
                            0.7, 0.7, 0.7,
                            0.7, 0.7, 0.7
                    };

            predator_lizard_reproduction_random(corpse_field, lizard_field, i);
            dir = SidesDirectionValues.NO_DIRECTION;
            decision = Actions.REPRODUCE;
            to_file(corpse_field, lizard_field, predator_lizard_field, 0.7, dir, decision);
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
        predator_lizard_confusion_pack();
        predator_lizard_eating_pack();
        predator_lizard_hunting_pack();
        predator_lizard_reproduction_pack();

        try {
            writer.close();
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }
}
