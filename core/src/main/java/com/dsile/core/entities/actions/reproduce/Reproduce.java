package com.dsile.core.entities.actions.reproduce;

import com.dsile.core.entities.Creature;
import com.dsile.core.entities.Entity;
import com.dsile.core.entities.Lizard;
import com.dsile.core.entities.Predator_Lizard;
import com.dsile.core.neural.Brain;
import com.dsile.core.world.World;

import java.util.Set;

/**
 * Created by Никита on 03.05.2016.
 *
 * Класс, отвечающий за размножение, содержит в себе реализацию генетического алгоритма.
 * В случае, если размножение успешно, то добавляет новое существо в текущую ячейку.
 */
public class Reproduce {
    //Существо, которое пытается размножиться
    private Creature creature;

    public Reproduce(Creature creature){
        this.creature = creature;
    }

    public void perform() { //нужен паттерн проектирования
        Brain newborn_brain;
        if(creature instanceof Lizard) // с любой в клетке и с любым hp?
        {
            Set<Entity> entities = creature.getCurrentCell().getEntityList(creature); //пытаемся размножиться
            for(Entity entity : entities){
                if (entity instanceof Lizard && entity.getHP() > 70) // для всех?
                {
                    newborn_brain = genetic_algorithm(((Lizard) entity).getBrain());
                    //birth
                    World world = creature.getWorld();
                    int x = creature.getCurrentCell().getX();
                    int y = creature.getCurrentCell().getY();
                    world.add_to_entities(new Lizard(world, x, y, newborn_brain));
                    System.out.println("Birth complete");
                    creature.die(); // временно, пока нет памяти у нейросети, а то их слишком много становится
                    ((Lizard) entity).die();
                }
            }
        }
        if(creature instanceof Predator_Lizard)
        {
            Set<Entity> entities = creature.getCurrentCell().getEntityList(creature); //пытаемся размножиться
            for(Entity entity : entities){
                if (entity instanceof Predator_Lizard && entity.getHP() > 70) // для всех?
                {
                    newborn_brain = genetic_algorithm(((Predator_Lizard) entity).getBrain());
                    //birth
                    World world = creature.getWorld();
                    int x = creature.getCurrentCell().getX();
                    int y = creature.getCurrentCell().getY();
                    Predator_Lizard predator_lizard = new Predator_Lizard(world, x, y, newborn_brain);
                    world.add_to_entities(predator_lizard);
                    System.out.println("Birth complete");
                    creature.die(); // временно, пока нет памяти у нейросети, а то их слишком много становится
                    ((Predator_Lizard) entity).die();
                }
            }
        }
    }

    private Brain genetic_algorithm(Brain brain)
    {
        //this.creature.getBrain() + brain
        return brain;
    }
}
