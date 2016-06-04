package com.dsile.core.entities;

import com.badlogic.gdx.graphics.Texture;
import com.dsile.core.entities.actions.vision.Lizard_Vision;
import com.dsile.core.neural.Brain;
import com.dsile.core.world.World;

/**
 * Created by Никита on 19.05.2016.
 */
public class Herb_Lizard extends Lizard {
    public Herb_Lizard(World world, int x, int y) {
        super(world,x,y);
        this.data_set_filename = "Herb_Lizard_data_set.txt";
        this.vision = new Lizard_Vision(this);
        //this.id = 1;
        System.out.printf("Herb Lizard created on (%d,%d)\n",x,y);
    }

    public Herb_Lizard(World world, int x, int y, Brain brain, boolean newborn) {
        super(world,x,y, brain, newborn);
        this.data_set_filename = "Herb_Lizard_data_set.txt";
        this.vision = new Lizard_Vision(this);
        //this.id = 1;
        if (newborn){
            System.out.printf("Herb Lizard was born on (%d,%d)\n", x, y);
        }
        else{
            System.out.printf("Herb Lizard created on (%d,%d)\n", x ,y);
        }
    }

    @Override
    protected void setAliveTexture(){
        texture = new Texture("alive_herb_lizard.png");
    }

    protected void setDeadTexture(){
        texture = new Texture("dead_herb_lizard.png");
    }

    protected void setNewbornTexture(){
        texture = new Texture("newborn_herb_lizard.png");
    }
}
