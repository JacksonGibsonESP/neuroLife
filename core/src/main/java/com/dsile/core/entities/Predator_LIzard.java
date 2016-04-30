package com.dsile.core.entities;

import com.badlogic.gdx.graphics.Texture;
import com.dsile.core.world.World;

/**
 * Created by Никита on 28.04.2016.
 */
public class Predator_Lizard extends Lizard {
    Predator_Lizard(World world, int x, int y) {
        super(world,x,y);
    }

    protected void setAliveTexture(){
        texture = new Texture("alive_predator_lizard.png");
    }

    protected void setDeadTexture(){
        texture = new Texture("dead_predator_lizard.png");
    }
}
