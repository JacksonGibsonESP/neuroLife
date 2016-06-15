package com.dsile.core.entities;

import com.badlogic.gdx.graphics.Texture;
import com.dsile.core.entities.actions.vision.Lizard_Vision;
import com.dsile.core.neural.Brain;
import com.dsile.core.neural.Herb_Lizard_Brain;
import com.dsile.core.world.World;

/**
 * Created by Никита on 19.05.2016.
 */
public class Herb_Lizard extends Lizard {

    static private Texture aliveTexture     = new Texture("alive_herb_lizard_b&w.png");
    static private Texture deadTexture      = new Texture("dead_herb_lizard_b&w.png");
    static private Texture newbornTexture   = new Texture("newborn_herb_lizard_b&w.png");

    public Herb_Lizard(World world, int x, int y) {
        super(world,x,y);
        this.brain = new Herb_Lizard_Brain();
        this.data_set_filename = "Herb_Lizard_data_set.txt";
        this.vision = new Lizard_Vision(this);
        //this.id = 1;
        if (this.logger.isDebugEnabled()) {
            this.logger.debug("Herb Lizard created on {}, {}.", x, y);
        }
    }

    public Herb_Lizard(World world, int x, int y, Brain brain, boolean newborn) {
        super(world,x,y, brain, newborn);
        this.data_set_filename = "Herb_Lizard_data_set.txt";
        this.vision = new Lizard_Vision(this);
        //this.id = 1;
        if (newborn){
            if (this.logger.isDebugEnabled()) {
                this.logger.debug("Herb Lizard was born on {}, {}.", x, y);
            }
        }
        else{
            if (this.logger.isDebugEnabled()) {
                this.logger.debug("Herb Lizard created on {}, {}.", x, y);
            }
        }
    }

    //@Override
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
