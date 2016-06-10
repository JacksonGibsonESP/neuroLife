package com.dsile.core.world;

import com.badlogic.gdx.graphics.Texture;

/**
 * Created by DeSile on 07.12.2015.
 */
public class GroundCell extends Cell {

    private static Texture sand = new Texture("sand.png");

    public GroundCell(int size, int x, int y) {
        super(size,x,y);
        //texture = new Texture("sand.png");
        texture = sand;
    }

}
