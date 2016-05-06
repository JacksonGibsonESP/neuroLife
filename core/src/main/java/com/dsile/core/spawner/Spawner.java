package com.dsile.core.spawner;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.dsile.core.entities.Entity;
import com.dsile.core.entities.Herb;
import com.dsile.core.entities.Lizard;
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

        //Заполним мир травой
        for(int y = 0; y < world.getWorldYsize(); y++)
        {
            for (int x = 0; x < world.getWorldXsize(); x++)
            {
                if (r.nextDouble() < 0.3)
                {
                    world.add_to_entities((new Herb(world, x, y)));
                }
            }
        }

        //Заполним мир ящерками
        boolean first = true;
        Brain brain = null;
        for(int y = 0; y < world.getWorldYsize(); y++)
        {
            for (int x = 0; x < world.getWorldXsize(); x++)
            {
                if (r.nextDouble() < 0.3)
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

        //Lizard lizard = new Lizard(world,6,6);
        //lizard.learn();

        //lizard.getBrain().saveNNToFile("lizard_nnt");

        //entities.add(lizard);
        //entities.add(new Lizard(world,6,25, lizard.getBrain()));

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

