package com.dsile.core.entities;

import com.badlogic.gdx.graphics.Texture;
import com.dsile.core.neural.Brain;
import com.dsile.core.world.World;

/**
 * Created by Никита on 28.04.2016.
 */
public class Predator_Lizard extends Lizard {
    public Predator_Lizard(World world, int x, int y) {
        super(world,x,y);
        System.out.printf("Predator lizard created on (%d,%d)\n",x,y);
    }

    public Predator_Lizard(World world, int x, int y, Brain brain) {
        super(world,x,y, brain);
        System.out.printf("Predator lizard was borned on (%d,%d)\n",x,y);
    }

    //TODO: Переобучить на хищника
/*    public void learn() {

    }
*/
    protected void setAliveTexture(){
        texture = new Texture("alive_predator_lizard.png");
    }

    protected void setDeadTexture(){
        texture = new Texture("dead_predator_lizard.png");
    }
}
