package com.dsile.core.entities;

import com.badlogic.gdx.graphics.Texture;
import com.dsile.core.entities.actions.vision.Predator_Vision;
import com.dsile.core.neural.Brain;
import com.dsile.core.world.World;

/**
 * Created by Никита on 28.04.2016.
 */
public class Predator_Lizard extends Lizard {
    public Predator_Lizard(World world, int x, int y) {
        super(world,x,y);
        this.data_set_filename = "Predator_Lizard_data_set.txt";
        this.vision = new Predator_Vision(this);
        //this.id = 2;
        //System.out.printf("Predator lizard created on (%d,%d)\n",x,y);
    }

    public Predator_Lizard(World world, int x, int y, Brain brain, boolean newborn) {
        super(world, x, y, brain, newborn);
        this.data_set_filename = "Predator_Lizard_data_set.txt";
        this.vision = new Predator_Vision(this);
        //this.id = 2;
        if (newborn){
            //System.out.printf("Predator Lizard was born on (%d,%d)\n", x, y);
        }
        else{
            //System.out.printf("Predator Lizard created on (%d,%d)\n", x, y);
        }
    }

    /*protected void setAliveTexture(){
        texture = new Texture("alive_predator_lizard.png");
    }*/

    /*protected void setDeadTexture(){
        texture = new Texture("dead_predator_lizard.png");
    }*/

    /*protected void setNewbornTexture(){
        texture = new Texture("newborn_predator_lizard.png");
    }*/
}
