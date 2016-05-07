package com.dsile.core.entities;

import com.badlogic.gdx.graphics.Texture;
import com.dsile.core.world.World;

/**
 * Created by DeSile on 2/21/2016.
 */
public class Herb extends Entity {

    public Herb(World world, int x, int y) {
        super(world, x, y);
        this.HP = this.maxHP;
        this.id = 0;
        System.out.printf("Herb created on (%d,%d)\n",x,y);
    }

    @Override
    protected void setAliveTexture() {
        texture = new Texture("herb.png");
    }

    protected void setDeadTexture() {
        texture = new Texture("herb.png");
    }

    @Override
    protected void dead() {
        remove();
        currentCell.removeEntity(this);
    }

    public int bitten()
    {
        if(this.alive)
        {
            this.HP -= 10;
            if(this.HP <= 0)
            {
                this.dead();
            }
            return 10;
        }
        return 0;
    }
}
