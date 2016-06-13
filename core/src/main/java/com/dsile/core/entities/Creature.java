package com.dsile.core.entities;

import com.dsile.core.entities.actions.attack.Attack;
import com.dsile.core.entities.actions.vision.Vision;
import com.dsile.core.entities.actions.movement.Movement;
import com.dsile.core.entities.actions.reproduce.Reproduce;
import com.dsile.core.neural.Brain;
import com.dsile.core.world.World;

import com.dsile.core.entities.actions.eating.Eating;

import java.math.BigInteger;

/**
 * Created by DeSile on 2/18/2016.
 */
public abstract class Creature extends Entity{
    protected Brain brain;
    protected Vision vision;
    protected Movement movement;
    protected Eating eating;
    protected Attack attack;
    protected Reproduce reproduce;
    protected String data_set_filename;
    protected int generation = 1;
    //protected BigInteger life_duration = BigInteger.ZERO;
    protected int life_duration = 0;

    public Creature(World world, int x, int y, Brain brain)
    {
        super(world,x,y);
        this.brain = brain;
        this.movement = new Movement(this);
        this.eating = new Eating(this);
        this.attack = new Attack(this);
        this.reproduce = new Reproduce(this);
    }

    public Creature(World world, int x, int y){
        super(world,x,y);
        this.brain = new Brain();
        this.movement = new Movement(this);
        this.eating = new Eating(this);
        this.attack = new Attack(this);
        this.reproduce = new Reproduce(this);
    }

    public abstract void learn();

    public abstract void attacked(Creature attacker);

    protected abstract void attack(double[] signal);

    protected abstract void move(double[] signal);

    protected abstract void eat();

    protected abstract void decomposed();

    public abstract void reproduce();

    public abstract void die();

    public abstract Brain getBrain();

    public int getGeneration() {return generation;}

    //protected void incGeneration() {generation++;}

    public void setGeneration(int new_generation_number) {this.generation = new_generation_number;}

    /*protected void incLife_duration(){
        life_duration = life_duration.add(BigInteger.ONE);
    }

    public BigInteger getLife_duration(){
        return life_duration;
    }*/

    protected void incLife_duration(){
        life_duration++;
    }

    public int getLife_duration(){
        return life_duration;
    }

    public Movement getMovement(){
        return movement;
    }
}
