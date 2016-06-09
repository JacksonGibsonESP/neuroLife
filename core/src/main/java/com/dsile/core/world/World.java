package com.dsile.core.world;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dsile.core.entities.*;
import com.dsile.core.screens.WorldScreen;
import com.dsile.core.spawner.Spawner;
import com.dsile.core.statistics.Statistics;

import java.math.BigInteger;
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
    private Spawner spawner;
    private Statistics statistics;

    private Set<Entity> entities = new HashSet<>();

    private WorldScreen world_screen;

    public World(int cellsX, int cellsY, int cellSize, WorldScreen screen){
        System.out.println("Creating world");

        this.spawner = new Spawner(this);
        this.statistics = new Statistics(this, 10);
        this.world_screen = screen;

        this.cellsX = cellsX;
        this.cellsY = cellsY;

        cells = new Cell[cellsX][cellsY];
        for(int i = 0; i < cellsX; i++){
            for(int j = 0; j < cellsY; j++){
                cells[i][j] = new GroundCell(cellSize,i,j);
            }
        }

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

    public int get_herb_lizard_count(){
        int count = 0;
        for(Entity e: entities){
            if (e instanceof Herb_Lizard && e.isAlive()){
                count++;
            }
        }
        return count;
    }

    public int get_predator_lizard_count(){
        int count = 0;
        for(Entity e: entities){
            if (e instanceof Predator_Lizard && e.isAlive()){
                count++;
            }
        }
        return count;
    }

    public int get_dead_bodies_count(){
        int count = 0;
        for(Entity e: entities){
            if (e instanceof Herb_Lizard && !e.isAlive()){
                count++;
            }
        }
        return count;
    }

    public int get_herbs_count(){
        int count = 0;
        for(Entity e: entities){
            if (e instanceof Herb){
                count++;
            }
        }
        return count;
    }

    public int[] get_generations_stat(){
        int max_generations = 0;
        for (Entity e: entities){
            if (e instanceof Herb_Lizard && e.isAlive()){
                if (((Herb_Lizard) e).getGeneration() > max_generations){
                    max_generations = ((Herb_Lizard) e).getGeneration();
                }
            }
        }
        int[] stat = new int[max_generations * 2];
        for (int e : stat){
            e = 0;
        }
        //сначала пишется количество ящерок в поколении, а затем продолжительность жизни самой старой из поколения
        for (Entity e: entities){
            if (e instanceof Herb_Lizard && e.isAlive()){
                stat[(((Herb_Lizard) e).getGeneration() - 1) * 2]++;
                if(((Herb_Lizard) e).getLife_duration() > stat[(((Herb_Lizard) e).getGeneration() - 1) * 2 + 1]){
                    stat[(((Herb_Lizard) e).getGeneration() - 1) * 2 + 1] = ((Herb_Lizard) e).getLife_duration();
                }
            }
        }
        return stat;
    }

    public Spawner getSpawner(){return spawner;}

    public Statistics getStatistics(){return statistics;}

    public void removeEntity(Entity e){
        entities.remove(e);
    }
}
