package com.dsile.core.entities.actions.reproduce;

import com.dsile.core.entities.Creature;
import com.dsile.core.entities.Entity;
import com.dsile.core.entities.Lizard;
import com.dsile.core.entities.Predator_Lizard;
import com.dsile.core.neural.Brain;
import com.dsile.core.world.World;
import org.neuroph.core.NeuralNetwork;

import java.util.Arrays;
import java.util.Random;
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
        if(creature instanceof Predator_Lizard)
        {
            Set<Entity> entities = creature.getCurrentCell().getEntityList(creature); //пытаемся размножиться
            for(Entity entity : entities){
                if (entity instanceof Predator_Lizard && entity.isAlive() && entity.getHP() > 70)
                {
                    newborn_brain = genetic_algorithm(((Predator_Lizard) entity).getBrain());
                    //birth
                    World world = creature.getWorld();
                    int x = creature.getCurrentCell().getX();
                    int y = creature.getCurrentCell().getY();
                    Predator_Lizard predator_lizard = new Predator_Lizard(world, x, y, newborn_brain);
                    world.add_to_entities(predator_lizard);
                    System.out.println("Birth complete");
                    //creature.die(); // временно, пока нет памяти у нейросети, а то их слишком много становится
                    //((Predator_Lizard) entity).die();
                    creature.incHP(-50);
                    entity.incHP(-50);
                    return;
                }
            }
        }
        else if(creature instanceof Lizard)
        {
            Set<Entity> entities = creature.getCurrentCell().getEntityList(creature); //пытаемся размножиться
            for(Entity entity : entities){
                if (entity.getClass() == creature.getClass() && entity.isAlive() && entity.getHP() > 70)
                {
                    //System.out.println(creature);
                    //System.out.println(entities);
                    newborn_brain = genetic_algorithm(((Lizard) entity).getBrain());
                    //birth
                    World world = creature.getWorld();
                    int x = creature.getCurrentCell().getX();
                    int y = creature.getCurrentCell().getY();
                    world.add_to_entities(new Lizard(world, x, y, newborn_brain));
                    System.out.println("Birth complete");
                    //creature.die(); // временно, пока нет памяти у нейросети, а то их слишком много становится
                    //((Lizard) entity).die();
                    creature.incHP(-50);
                    entity.incHP(-50);
                    return;
                }
            }
        }
    }

    private Brain genetic_algorithm(Brain brain)
    {
        NeuralNetwork parent_A = creature.getBrain().getNNT();
        NeuralNetwork parent_B = brain.getNNT();

        Double weights_A[] = parent_A.getWeights();
        Double weights_B[] = parent_B.getWeights();

        System.out.println("Weights A: " + Arrays.toString(weights_A));
        System.out.println("Weights B: " + Arrays.toString(weights_B));

        // Будем считать все веса одной сети одной хромосомой, будем скрещивать две хромосомы
        // Пускай будет равномерное скрещивание

        Random r = new Random();

        Double weights_child[] = new Double[weights_A.length];

        for (int i = 0; i < weights_child.length; i++)
        {
            weights_child[i] = (r.nextDouble() < 0.5) ? weights_A[i] : weights_B[i];
        }

        // Производим мутации генов
        for (int i = 0; i < weights_child.length; i++)
        {
            // Gaussian ("normally") distributed double value with mean 0.0 and standard deviation 1.0 from this random number generator's sequence.
            if (r.nextDouble() < 0.05) {
                weights_child[i] = r.nextGaussian() + weights_child[i];
            }
        }

        // Приведение типов

        double weights_child_casted[] = new double[weights_child.length];

        for (int i = 0; i < weights_child_casted.length; i++)
        {
            weights_child_casted [i] = weights_child[i].doubleValue();
        }

        System.out.println("Weights C: " + Arrays.toString(weights_child_casted));

        Brain child_brain = new Brain();

        child_brain.getNNT().setWeights(weights_child_casted);

        return child_brain;
    }

}
