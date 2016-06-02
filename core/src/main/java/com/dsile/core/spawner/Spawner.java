package com.dsile.core.spawner;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.dsile.core.entities.*;
import com.dsile.core.entities.actions.movement.DirectionValues;
import com.dsile.core.generator.Generator;
import com.dsile.core.neural.Brain;
import com.dsile.core.world.World;

import java.util.Random;
import java.util.Set;

/**
 * Created by Никита on 06.05.2016.
 *
 * Класс Spawner отвечает за спаун объектов на поле
 *
 */
public class Spawner extends Actor {
    private World world;
    private Random r;

    public Spawner(World world) {
        this.world = world;
        this.r = new Random();
    }

    public void init() {
        //Стартовое положение вещей на карте мира
        System.out.println("Creating entities");

        //Сгенерируем обучающее множество для нейросети
        Generator generator = new Generator();
        generator.herb_lizard_gen();
        generator.predator_lizard_gen();

        //Заполним мир ящерками
        boolean first = true;
        Brain brain = null;
        for(int y = 0; y < world.getWorldYsize(); y++)
        {
            for (int x = 0; x < world.getWorldXsize(); x++)
            {
                if (r.nextDouble() < 0.01)
                {
                    if(first) {
                        Lizard lizard = new Herb_Lizard(world, x, y);
                        //lizard.learn();
                        brain = lizard.getBrain();
                        //brain.saveNNToFile("herb_lizard_brain");
                        brain.loadFileToNNT("herb_lizard_brain");
                        first = false;
                        world.add_to_entities(lizard);
                    }
                    else {
                        world.add_to_entities(new Herb_Lizard(world, x, y, brain, false));
                    }
                }
            }
        }

        //Заполним мир ящерками-хищниками-падальщиками
        first = true;
        brain = null;
        for(int y = 0; y < world.getWorldYsize(); y++)
        {
            for (int x = 0; x < world.getWorldXsize(); x++)
            {
                if (r.nextDouble() < 0.0025)
                {
                    if(first) {
                        Predator_Lizard predator_lizard = new Predator_Lizard(world, x, y);
                        //predator_lizard.learn();
                        brain = predator_lizard.getBrain();
                        //brain.saveNNToFile("predator_lizard_brain");
                        brain.loadFileToNNT("predator_lizard_brain");
                        first = false;
                        world.add_to_entities(predator_lizard);
                    }
                    else {
                        world.add_to_entities(new Predator_Lizard(world, x, y, brain, false));
                    }
                }
            }
        }

        //Заполним мир травой
        for(int y = 0; y < world.getWorldYsize(); y++)
        {
            for (int x = 0; x < world.getWorldXsize(); x++)
            {
                if (r.nextDouble() < 0.5)
                {
                    world.add_to_entities((new Herb(world, x, y)));
                }
            }
        }

        //Predator_Lizard predator_lizard = new Predator_Lizard(world,6,7);
        //predator_lizard.learn();
        //predator_lizard.getBrain().saveNNToFile("predator_lizard_brain");
        //predator_lizard.getBrain().loadFileToNNT("predator_lizard_brain");
        //world.add_to_entities(predator_lizard);

        //world.add_to_entities((new Herb(world, 6, 6)));

        /*Lizard lizard = new Herb_Lizard(world,6,6);
        lizard.learn();
        lizard.getBrain().saveNNToFile("herb_lizard_brain");
        //lizard.getBrain().loadFileToNNT("herb_lizard_brain");
        //lizard.setHP(100);
        lizard.setDirection(DirectionValues.NORTH);
        world.add_to_entities(lizard);

        lizard = new Herb_Lizard(world,6,7, lizard.getBrain(), false);
        lizard.setHP(100);

        world.add_to_entities(lizard);

        world.add_to_entities((new Herb(world, 5, 7)));

        world.add_to_entities((new Herb(world, 5, 6)));*/

        //Lizard lizard2 = new Lizard(this,6,7);
        //lizard2.learn();
        //entities.add(lizard2);

        /*for(Entity e : entities)
        {
            e.setHP(100);
        }*/

        System.out.println("Creating complete");
        System.out.println("-------------------------------------------");
    }

    @Override
    public void act(float delta) {
        //Подбрасываем травы
        for(int y = 0; y < world.getWorldYsize(); y++)
        {
            for (int x = 0; x < world.getWorldXsize(); x++)
            {
                if (r.nextDouble() < 0.005 && !world.getCell(x, y).isHerb())
                {
                    world.add_to_entities((new Herb(world, x, y)));
                }
            }
        }
    }
}

