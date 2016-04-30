package com.dsile.core.entities;

import com.dsile.core.entities.actions.attack.Attack;
import com.dsile.core.entities.actions.factors.Vision;
import com.dsile.core.entities.actions.movement.Movement;
import com.dsile.core.neural.Brain;
import com.dsile.core.world.World;

import com.dsile.core.entities.actions.eating.Eating;

/**
 * Created by DeSile on 2/18/2016.
 */
public abstract class Creature extends Entity implements HasBrain {
    protected Brain brain;
    protected Vision vision;
    protected Movement movement;
    protected Eating eating;
    protected Attack attack;

    //protected int hungrines = 0;

    public Creature(World world, int x, int y){
        super(world,x,y);
        this.brain = new Brain();
        this.vision = new Vision(this);
        this.movement = new Movement(this);
        this.eating = new Eating(this);
        this.attack = new Attack(this);
    }

    public abstract void learn();

    public abstract void attacked(Creature attacker);

    protected abstract void attack();

    protected abstract void move(double[] signal);

    protected abstract void eat();

    protected abstract void decomposed();

}
