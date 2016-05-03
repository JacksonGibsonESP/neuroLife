package com.dsile.core.world;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dsile.core.entities.Entity;
import com.dsile.core.entities.Herb;
import com.dsile.core.entities.Lizard;
import com.dsile.core.entities.Creature;
import com.dsile.core.screens.WorldScreen;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * Created by DeSile on 07.12.2015.
 */
public class World {

    Random randomize = new Random();

    private int cellsX;
    private int cellsY;
    private Cell[][] cells;

    private Set<Entity> entities = new HashSet<>();

    private WorldScreen world_screen;

    public World(int cellsX, int cellsY, int cellSize, WorldScreen screen){
        System.out.println("Creating world");

        this.world_screen = screen;

        this.cellsX = cellsX;
        this.cellsY = cellsY;

        //TODO: Cell fabric?
        cells = new Cell[cellsX][cellsY];
        for(int i = 0; i < cellsX; i++){
            for(int j = 0; j < cellsY; j++){
                cells[i][j] = new GroundCell(cellSize,i,j);
            }
        }

        System.out.println("Creating entities");

        //entities.add(new Lizard(this,2,3));

        entities.add(new Herb(this,7,7));
        entities.add(new Herb(this,7,8));

        Lizard lizard = new Lizard(this,6,6);
        lizard.learn();

        entities.add(lizard);
        entities.add(new Lizard(this,6,9, lizard.getBrain()));

        System.out.println("Creating complete");
        System.out.println("-------------------------------------------");
    }

    public void drawMap(SpriteBatch batch){
        for(Cell[] cc : cells){
            for(Cell c : cc){
                c.drawCell(batch);
            }
        }
    }

    //DANGEROUS METHOD
    public GroundCell getRandomGroundCell(){
        int randomX = randomize.nextInt(cellsX);
        int randomY = randomize.nextInt(cellsY);
        while(!(cells[randomX][randomY] instanceof GroundCell)){
            randomX = randomize.nextInt(cellsX);
            randomY = randomize.nextInt(cellsY);
        }
        return (GroundCell) cells[randomX][randomY];
    }

    //TODO: ADD EXCEPTIONS
    public Cell getCell(int x, int y){
        if(x >= cellsX){
            x = 0;
        }
        if(x < 0){
            x = cellsX-1;
        }
        if(y >= cellsY){
            y = 0;
        }
        if(y < 0){
            y = cellsY-1;
        }
        return cells[x][y];
    }

    public int getWorldXsize(){
        return cellsX;
    }

    public int getWorldYsize(){
        return cellsY;
    }

    public Set<Entity> getEntities(){
        return entities;
    }

    public void add_to_entities(Entity entity)
    {
        entities.add(entity);
        world_screen.addActor(entity);
    }
}
