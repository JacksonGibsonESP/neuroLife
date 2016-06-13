package com.dsile.core.entities.actions.reproduce;

import com.dsile.core.entities.*;
import com.dsile.core.neural.Brain;
import com.dsile.core.neural.Herb_Lizard_Brain;
import com.dsile.core.neural.Predator_Lizard_Brain;
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

        if(creature instanceof Predator_Lizard && creature.getHP() >= 70)
        {
            Set<Entity> entities = creature.getCurrentCell().getEntityList(creature); //пытаемся размножиться
            for(Entity entity : entities){
                if (entity instanceof Predator_Lizard && entity.isAlive() && entity.getHP() >= 70)
                {
                    Predator_Lizard_Brain newborn_brain = new Predator_Lizard_Brain();
                    newborn_brain.getNNT().setWeights(genetic_algorithm(((Predator_Lizard) entity).getBrain()));
                    //birth
                    World world = creature.getWorld();
                    int x = creature.getCurrentCell().getX();
                    int y = creature.getCurrentCell().getY();
                    Predator_Lizard predator_lizard = new Predator_Lizard(world, x, y, newborn_brain, true);
                    predator_lizard.setGeneration(Math.max(creature.getGeneration(), ((Creature)entity).getGeneration()) + 1);
                    world.add_to_entities(predator_lizard);
                    if (creature.getLogger().isDebugEnabled()) {
                        creature.getLogger().debug("Birth complete");
                    }
                    //creature.die(); // временно, пока нет памяти у нейросети, а то их слишком много становится
                    //((Predator_Lizard) entity).die();
                    //creature.incHP(-50);
                    //entity.incHP(-50);
                    creature.setHP(50);
                    entity.setHP(50);
                    return;
                }
            }
        }
        else if(creature instanceof Herb_Lizard && creature.getHP() >= 70)
        {
            Set<Entity> entities = creature.getCurrentCell().getEntityList(creature); //пытаемся размножиться
            for(Entity entity : entities){
                if (entity.getClass() == creature.getClass() && entity.isAlive() && entity.getHP() >= 70)
                {
                    Herb_Lizard_Brain newborn_brain = new Herb_Lizard_Brain();
                    newborn_brain.getNNT().setWeights(genetic_algorithm(((Herb_Lizard) entity).getBrain()));
                    //birth
                    World world = creature.getWorld();
                    int x = creature.getCurrentCell().getX();
                    int y = creature.getCurrentCell().getY();
                    Herb_Lizard herb_lizard = new Herb_Lizard(world, x, y, newborn_brain, true);
                    herb_lizard.setGeneration(Math.max(creature.getGeneration(), ((Creature)entity).getGeneration()) + 1);
                    world.add_to_entities(herb_lizard);
                    if (creature.getLogger().isDebugEnabled()) {
                        creature.getLogger().debug("Birth complete");
                    }
                    //creature.die(); // временно, пока нет памяти у нейросети, а то их слишком много становится
                    //((Herb_Lizard) entity).die();
                    //creature.incHP(-50);
                    //entity.incHP(-50);
                    creature.setHP(50);
                    entity.setHP(50);
                    return;
                }
            }
        }
    }

    private double[] genetic_algorithm(Brain brain)
    {
        NeuralNetwork parent_A = creature.getBrain().getNNT();
        NeuralNetwork parent_B = brain.getNNT();

        Double weights_A[] = parent_A.getWeights();
        Double weights_B[] = parent_B.getWeights();

        if (creature.getLogger().isDebugEnabled()) {
            creature.getLogger().debug("Weights A: " + Arrays.toString(weights_A));
            creature.getLogger().debug("Weights B: " + Arrays.toString(weights_B));
        }

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
                weights_child[i] = r.nextGaussian() / 2 + weights_child[i];
            }
        }

        // Приведение типов

        double weights_child_casted[] = new double[weights_child.length];

        for (int i = 0; i < weights_child_casted.length; i++)
        {
            weights_child_casted[i] = weights_child[i].doubleValue();
        }

        if (creature.getLogger().isDebugEnabled()) {
            creature.getLogger().debug("Weights C: " + Arrays.toString(weights_child_casted));
        }

        return weights_child_casted;
    }

}
