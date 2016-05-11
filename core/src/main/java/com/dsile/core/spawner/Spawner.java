package com.dsile.core.spawner;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.dsile.core.entities.Entity;
import com.dsile.core.entities.Herb;
import com.dsile.core.entities.Lizard;
import com.dsile.core.entities.Predator_Lizard;
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

        //Заполним мир ящерками
        boolean first = true;
        Brain brain = null;
        for(int y = 0; y < world.getWorldYsize(); y++)
        {
            for (int x = 0; x < world.getWorldXsize(); x++)
            {
                if (r.nextDouble() < 0.2)
                {
                    if(first) {
                        Lizard lizard = new Lizard(world, x, y);
                        lizard.learn();
                        brain = lizard.getBrain();
                        first = false;
                        world.add_to_entities(lizard);
                    }
                    else {
                        world.add_to_entities(new Lizard(world, x, y, brain));
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
                if (r.nextDouble() < 0.05)
                {
                    if(first) {
                        Predator_Lizard predator_lizard = new Predator_Lizard(world, x, y);
                        predator_lizard.learn();
                        brain = predator_lizard.getBrain();
                        first = false;
                        world.add_to_entities(predator_lizard);
                    }
                    else {
                        world.add_to_entities(new Predator_Lizard(world, x, y, brain));
                    }
                }
            }
        }
        //Заполним мир травой
        for(int y = 0; y < world.getWorldYsize(); y++)
        {
            for (int x = 0; x < world.getWorldXsize(); x++)
            {
                if (r.nextDouble() < 0.1)
                {
                    world.add_to_entities((new Herb(world, x, y)));
                }
            }
        }

        //world.add_to_entities(new Lizard(world,6,7, lizard.getBrain()));

        /*Predator_Lizard predator_lizard = new Predator_Lizard(world,6,7);
        predator_lizard.learn();
        world.add_to_entities(predator_lizard);*/

        //world.add_to_entities((new Herb(world, 6, 6)));

        /*Lizard lizard = new Lizard(world,6,6);
        lizard.learn();
        //lizard.setHP(100);

        world.add_to_entities(lizard);*/

        //lizard = new Lizard(world,6,7, lizard.getBrain());
        //lizard.setHP(100);

        //lizard.getBrain().saveNNToFile("lizard_nnt");

        //world.add_to_entities(lizard);

        //world.add_to_entities((new Herb(world, 7, 6)));

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
                if (r.nextDouble() < 0.001 && !world.getCell(x, y).isHerb())
                {
                    world.add_to_entities((new Herb(world, x, y)));
                }
            }
        }
    }
}

