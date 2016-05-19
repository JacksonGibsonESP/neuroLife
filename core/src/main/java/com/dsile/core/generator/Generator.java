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

    private void lizard_confusion_pack(){
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
    }

    private void lizard_eating_pack(){
        double [] herb_field;
        double [] lizard_field;
        double [] predator_lizard_field;
        Actions decision;
        SidesDirectionValues dir;

        Random r = new Random();

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

        for (int i = 0; i < 5; i++) {
            //1
            herb_field = new double[]
                    {
                            1, 0, 0,
                            0, 0, 0,
                            0, 0, 0
                    };

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
            dir = SidesDirectionValues.NO_DIRECTION;
            decision = Actions.EATING;
            to_file(herb_field, lizard_field, predator_lizard_field, 0.3, dir, decision);
            to_file(herb_field, lizard_field, predator_lizard_field, 0.5, dir, decision);

            //нагенерим соседей
            for (int k = 0; k < 9; k++) {
                if (r.nextDouble() < 0.8){
                    double tmp = r.nextDouble();
                    if (tmp >= 0.5) {
                        lizard_field[k] = 0.7;
                    } else if (tmp > 0.25){
                        lizard_field[k] = 0.5;
                    } else{
                        lizard_field[k] = 0.3;
                    }
                }
            }
            //и хищников
            //...
        }
    }

    private void lizard_reproduction_pack(){
        double [] herb_field;
        double [] lizard_field;
        double [] predator_lizard_field;
        Actions decision;
        SidesDirectionValues dir;

        Random r = new Random();

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

        for (int i = 0; i < 5; i++) {
            //1
            lizard_field = new double[]
                    {
                            0.7, 0, 0,
                            0, 0, 0,
                            0, 0, 0
                    };
            reproduce_random_noize(lizard_field, i, r);
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
            reproduce_random_noize(lizard_field, i, r);
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
            reproduce_random_noize(lizard_field, i, r);
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
            reproduce_random_noize(lizard_field, i, r);
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
            reproduce_random_noize(lizard_field, i, r);
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
            reproduce_random_noize(lizard_field, i, r);
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
            reproduce_random_noize(lizard_field, i, r);
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
            reproduce_random_noize(lizard_field, i, r);
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
            reproduce_random_noize(lizard_field, i, r);
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
            reproduce_random_noize(lizard_field, i, r);
            dir = SidesDirectionValues.FORWARD_LEFT;
            decision = Actions.MOVEMENT;
            to_file(herb_field, lizard_field, predator_lizard_field, 0.3, dir, decision);
            to_file(herb_field, lizard_field, predator_lizard_field, 0.5, dir, decision);

            lizard_field = new double[]
                    {
                            0.7, 0.7, 0.7,
                            0, 0, 0,
                            0, 0, 0
                    };
            reproduce_random_noize(lizard_field, i, r);
            dir = SidesDirectionValues.FORWARD;
            decision = Actions.MOVEMENT;
            to_file(herb_field, lizard_field, predator_lizard_field, 0.3, dir, decision);
            to_file(herb_field, lizard_field, predator_lizard_field, 0.5, dir, decision);

            lizard_field = new double[]
                    {
                            0, 0.7, 0.7,
                            0, 0, 0.7,
                            0, 0, 0
                    };
            reproduce_random_noize(lizard_field, i, r);
            dir = SidesDirectionValues.FORWARD_RIGHT;
            decision = Actions.MOVEMENT;
            to_file(herb_field, lizard_field, predator_lizard_field, 0.3, dir, decision);
            to_file(herb_field, lizard_field, predator_lizard_field, 0.5, dir, decision);

            lizard_field = new double[]
                    {
                            0, 0, 0.7,
                            0, 0, 0.7,
                            0, 0, 0.7
                    };
            reproduce_random_noize(lizard_field, i, r);
            dir = SidesDirectionValues.FORWARD_RIGHT;
            decision = Actions.MOVEMENT;
            to_file(herb_field, lizard_field, predator_lizard_field, 0.3, dir, decision);
            to_file(herb_field, lizard_field, predator_lizard_field, 0.5, dir, decision);

            lizard_field = new double[]
                    {
                            0, 0, 0,
                            0, 0, 0.7,
                            0, 0.7, 0.7
                    };
            reproduce_random_noize(lizard_field, i, r);
            dir = SidesDirectionValues.BACKWARD_RIGHT;
            decision = Actions.MOVEMENT;
            to_file(herb_field, lizard_field, predator_lizard_field, 0.3, dir, decision);
            to_file(herb_field, lizard_field, predator_lizard_field, 0.5, dir, decision);

            lizard_field = new double[]
                    {
                            0, 0, 0,
                            0, 0, 0,
                            0.7, 0.7, 0.7
                    };
            reproduce_random_noize(lizard_field, i, r);
            dir = SidesDirectionValues.BACKWARD;
            decision = Actions.MOVEMENT;
            to_file(herb_field, lizard_field, predator_lizard_field, 0.3, dir, decision);
            to_file(herb_field, lizard_field, predator_lizard_field, 0.5, dir, decision);

            lizard_field = new double[]
                    {
                            0, 0, 0,
                            0.7, 0, 0,
                            0.7, 0.7, 0
                    };
            reproduce_random_noize(lizard_field, i, r);
            dir = SidesDirectionValues.BACKWARD_LEFT;
            decision = Actions.MOVEMENT;
            to_file(herb_field, lizard_field, predator_lizard_field, 0.3, dir, decision);
            to_file(herb_field, lizard_field, predator_lizard_field, 0.5, dir, decision);

            lizard_field = new double[]
                    {
                            0.7, 0, 0,
                            0.7, 0, 0,
                            0.7, 0, 0
                    };
            reproduce_random_noize(lizard_field, i, r);
            dir = SidesDirectionValues.LEFT;
            decision = Actions.MOVEMENT;
            to_file(herb_field, lizard_field, predator_lizard_field, 0.3, dir, decision);
            to_file(herb_field, lizard_field, predator_lizard_field, 0.5, dir, decision);

            lizard_field = new double[]
                    {
                            0.7, 0.7, 0.7,
                            0, 0, 0,
                            0.7, 0.7, 0.7
                    };
            reproduce_random_noize(lizard_field, i, r);
            dir = SidesDirectionValues.FORWARD;
            decision = Actions.MOVEMENT;
            to_file(herb_field, lizard_field, predator_lizard_field, 0.3, dir, decision);
            to_file(herb_field, lizard_field, predator_lizard_field, 0.5, dir, decision);

            lizard_field = new double[]
                    {
                            0.7, 0.7, 0.7,
                            0.7, 0, 0.7,
                            0.7, 0.7, 0.7
                    };
            reproduce_random_noize(lizard_field, i, r);
            dir = SidesDirectionValues.FORWARD;
            decision = Actions.MOVEMENT;
            to_file(herb_field, lizard_field, predator_lizard_field, 0.3, dir, decision);
            to_file(herb_field, lizard_field, predator_lizard_field, 0.5, dir, decision);

            lizard_field = new double[]
                    {
                            0, 0.7, 0.7,
                            0.7, 0, 0.7,
                            0.7, 0.7, 0
                    };
            reproduce_random_noize(lizard_field, i, r);
            dir = SidesDirectionValues.FORWARD_RIGHT;
            decision = Actions.MOVEMENT;
            to_file(herb_field, lizard_field, predator_lizard_field, 0.3, dir, decision);
            to_file(herb_field, lizard_field, predator_lizard_field, 0.5, dir, decision);

            lizard_field = new double[]
                    {
                            0, 0, 0.7,
                            0, 0, 0,
                            0.7, 0, 0
                    };
            reproduce_random_noize(lizard_field, i, r);
            dir = SidesDirectionValues.FORWARD_RIGHT;
            decision = Actions.MOVEMENT;
            to_file(herb_field, lizard_field, predator_lizard_field, 0.3, dir, decision);
            to_file(herb_field, lizard_field, predator_lizard_field, 0.5, dir, decision);

            lizard_field = new double[]
                    {
                            0.7, 0, 0.7,
                            0.7, 0, 0.7,
                            0.7, 0, 0.7
                    };
            reproduce_random_noize(lizard_field, i, r);
            dir = SidesDirectionValues.NO_DIRECTION;
            decision = Actions.MOVEMENT;
            to_file(herb_field, lizard_field, predator_lizard_field, 0.3, dir, decision);
            to_file(herb_field, lizard_field, predator_lizard_field, 0.5, dir, decision);

            lizard_field = new double[]
                    {
                            0, 0, 0,
                            0.7, 0, 0.7,
                            0, 0, 0
                    };
            reproduce_random_noize(lizard_field, i, r);
            dir = SidesDirectionValues.NO_DIRECTION;
            decision = Actions.MOVEMENT;
            to_file(herb_field, lizard_field, predator_lizard_field, 0.3, dir, decision);
            to_file(herb_field, lizard_field, predator_lizard_field, 0.5, dir, decision);

            lizard_field = new double[]
                    {
                            0.7, 0.7, 0,
                            0.7, 0, 0.7,
                            0, 0.7, 0.7
                    };
            reproduce_random_noize(lizard_field, i, r);
            dir = SidesDirectionValues.FORWARD_LEFT;
            decision = Actions.MOVEMENT;
            to_file(herb_field, lizard_field, predator_lizard_field, 0.3, dir, decision);
            to_file(herb_field, lizard_field, predator_lizard_field, 0.5, dir, decision);

            lizard_field = new double[]
                    {
                            0.7, 0, 0,
                            0, 0, 0,
                            0, 0, 0.7
                    };
            reproduce_random_noize(lizard_field, i, r);
            dir = SidesDirectionValues.FORWARD_LEFT;
            decision = Actions.MOVEMENT;
            to_file(herb_field, lizard_field, predator_lizard_field, 0.3, dir, decision);
            to_file(herb_field, lizard_field, predator_lizard_field, 0.5, dir, decision);

            lizard_field = new double[]
                    {
                            0, 0, 0,
                            0, 0, 0,
                            0.7, 0, 0.7
                    };
            reproduce_random_noize(lizard_field, i, r);
            dir = SidesDirectionValues.NO_DIRECTION;
            decision = Actions.MOVEMENT;
            to_file(herb_field, lizard_field, predator_lizard_field, 0.3, dir, decision);
            to_file(herb_field, lizard_field, predator_lizard_field, 0.5, dir, decision);

            lizard_field = new double[]
                    {
                            0.7, 0, 0.7,
                            0, 0, 0,
                            0, 0, 0
                    };
            reproduce_random_noize(lizard_field, i, r);
            dir = SidesDirectionValues.NO_DIRECTION;
            decision = Actions.MOVEMENT;
            to_file(herb_field, lizard_field, predator_lizard_field, 0.3, dir, decision);
            to_file(herb_field, lizard_field, predator_lizard_field, 0.5, dir, decision);

            lizard_field = new double[]
                    {
                            0.7, 0.7, 0.7,
                            0.7, 0.7, 0.7,
                            0.7, 0.7, 0.7
                    };
            reproduce_random_noize(lizard_field, i, r);
            dir = SidesDirectionValues.NO_DIRECTION;
            decision = Actions.REPRODUCE;
            to_file(herb_field, lizard_field, predator_lizard_field, 0.3, dir, decision);
            to_file(herb_field, lizard_field, predator_lizard_field, 0.5, dir, decision);

            for (int k = 0; k < 9; k++) {
                if (r.nextDouble() < 0.5){
                    herb_field[k] = 1;
                }
            }

        }
    }

    private void reproduce_random_noize(double[] lizard_field, int i, Random r) {
        if (i != 0){
            for (int j = 0; j < 9; j++){
                if (lizard_field[i] != 0){
                    double tmp = r.nextDouble();
                    if (tmp > 0.5) {
                        lizard_field[j] = 0.5;
                    } else {
                        lizard_field[j] = 0.3;
                    }
                }
            }
        }
    }

    public void lizard_gen(){
        try {
            this.writer = new FileWriter(lizard_data_set_file_name);
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }

        //Запись в файл обучаемого множества для травоядных ящерок.
        lizard_confusion_pack();
        lizard_eating_pack();
        //lizard_reproduction_pack();


/*


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
        dir = SidesDirectionValues.SOUTH_EAST;
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
        dir = SidesDirectionValues.SOUTH;
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
        dir = SidesDirectionValues.SOUTH_WEST;
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
        dir = SidesDirectionValues.EAST;
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
        dir = SidesDirectionValues.NO_DIRECTION;
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
        dir = SidesDirectionValues.WEST;
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
        dir = SidesDirectionValues.NORTH_EAST;
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
        dir = SidesDirectionValues.NORTH;
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
        dir = SidesDirectionValues.NORTH_WEST;
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
        dir = SidesDirectionValues.NORTH_WEST;
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
        dir = SidesDirectionValues.NORTH;
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
        dir = SidesDirectionValues.NORTH_EAST;
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
        dir = SidesDirectionValues.WEST;
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
        dir = SidesDirectionValues.NO_DIRECTION;
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
        dir = SidesDirectionValues.EAST;
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
        dir = SidesDirectionValues.SOUTH_WEST;
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
        dir = SidesDirectionValues.SOUTH;
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
        dir = SidesDirectionValues.SOUTH_EAST;
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
/*
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

        dir = SidesDirectionValues.NORTH_WEST;
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

        dir = SidesDirectionValues.NORTH;
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

        dir = SidesDirectionValues.NORTH_EAST;
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

        dir = SidesDirectionValues.WEST;
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

        dir = SidesDirectionValues.NO_DIRECTION;
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

        dir = SidesDirectionValues.EAST;
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

        dir = SidesDirectionValues.SOUTH_WEST;
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

        dir = SidesDirectionValues.SOUTH;
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

        dir = SidesDirectionValues.SOUTH_EAST;
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

        dir = SidesDirectionValues.NORTH_WEST;
        decision = Actions.MOVEMENT;
        to_file(corpse_field, lizard_field, predator_lizard_field, 0.7, dir, decision);

        //2
        predator_lizard_field = new double[]
                {
                        0, 0.7, 0,
                        0, 0, 0,
                        0, 0, 0
                };
        dir = SidesDirectionValues.NORTH;
        decision = Actions.MOVEMENT;
        to_file(corpse_field, lizard_field, predator_lizard_field, 0.7, dir, decision);

        //3
        predator_lizard_field = new double[]
                {
                        0, 0, 0.7,
                        0, 0, 0,
                        0, 0, 0
                };
        dir = SidesDirectionValues.NORTH_EAST;
        decision = Actions.MOVEMENT;
        to_file(corpse_field, lizard_field, predator_lizard_field, 0.7, dir, decision);

        //4
        predator_lizard_field = new double[]
                {
                        0, 0, 0,
                        0.7, 0, 0,
                        0, 0, 0
                };
        dir = SidesDirectionValues.WEST;
        decision = Actions.MOVEMENT;
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
        to_file(corpse_field, lizard_field, predator_lizard_field, 0.7, dir, decision);

        //6
        predator_lizard_field = new double[]
                {
                        0, 0, 0,
                        0, 0, 0.7,
                        0, 0, 0
                };
        dir = SidesDirectionValues.EAST;
        decision = Actions.MOVEMENT;
        to_file(corpse_field, lizard_field, predator_lizard_field, 0.7, dir, decision);

        //7
        predator_lizard_field = new double[]
                {
                        0, 0, 0,
                        0, 0, 0,
                        0.7, 0, 0
                };
        dir = SidesDirectionValues.SOUTH_WEST;
        decision = Actions.MOVEMENT;
        to_file(corpse_field, lizard_field, predator_lizard_field, 0.7, dir, decision);

        //8
        predator_lizard_field = new double[]
                {
                        0, 0, 0,
                        0, 0, 0,
                        0, 0.7, 0
                };
        dir = SidesDirectionValues.SOUTH;
        decision = Actions.MOVEMENT;
        to_file(corpse_field, lizard_field, predator_lizard_field, 0.7, dir, decision);

        //9
        predator_lizard_field = new double[]
                {
                        0, 0, 0,
                        0, 0, 0,
                        0, 0, 0.7
                };
        dir = SidesDirectionValues.SOUTH_EAST;
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

        dir = SidesDirectionValues.NORTH_WEST;
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
        dir = SidesDirectionValues.NORTH;
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
        dir = SidesDirectionValues.NORTH_EAST;
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
        dir = SidesDirectionValues.WEST;
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
        dir = SidesDirectionValues.NO_DIRECTION;
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
        dir = SidesDirectionValues.EAST;
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
        dir = SidesDirectionValues.SOUTH_WEST;
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
        dir = SidesDirectionValues.SOUTH;
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
        dir = SidesDirectionValues.SOUTH_EAST;
        decision = Actions.ATTACK;
        to_file(corpse_field, lizard_field, predator_lizard_field, 0.5, dir, decision);
        to_file(corpse_field, lizard_field, predator_lizard_field, 0.7, dir, decision);
*/
        try {
            writer.close();
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }
}
