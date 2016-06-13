package com.dsile.core.entities;

import com.badlogic.gdx.graphics.Texture;
import com.dsile.core.entities.actions.vision.Predator_Vision;
import com.dsile.core.neural.Brain;
import com.dsile.core.neural.Predator_Lizard_Brain;
import com.dsile.core.world.World;

/**
 * Created by Никита on 28.04.2016.
 */
public class Predator_Lizard extends Lizard {

    static private Texture aliveTexture     = new Texture("alive_predator_lizard.png");
    static private Texture deadTexture      = new Texture("dead_predator_lizard.png");
    static private Texture newbornTexture   = new Texture("newborn_predator_lizard.png");

    public Predator_Lizard(World world, int x, int y) {
        super(world,x,y);
        this.brain = new Predator_Lizard_Brain();
        this.data_set_filename = "Predator_Lizard_data_set.txt";
        this.vision = new Predator_Vision(this);
        //this.id = 2;
        if (this.logger.isDebugEnabled()) {
            this.logger.debug("Predator lizard created on {}, {}.", x, y);
        }
    }

    public Predator_Lizard(World world, int x, int y, Brain brain, boolean newborn) {
        super(world, x, y, brain, newborn);
        this.data_set_filename = "Predator_Lizard_data_set.txt";
        this.vision = new Predator_Vision(this);
        //this.id = 2;
        if (newborn){
            if (this.logger.isDebugEnabled()) {
                this.logger.debug("Predator Lizard was born on {}, {}.", x, y);
            }
        }
        else{
            if (this.logger.isDebugEnabled()) {
                this.logger.debug("Predator Lizard created on {}, {}.", x, y);
            }
        }
    }

    protected void setAliveTexture(){
        texture = aliveTexture;
    }

    protected void setDeadTexture(){
        texture = deadTexture;
    }

    protected void setNewbornTexture(){
        texture = newbornTexture;
    }
}
