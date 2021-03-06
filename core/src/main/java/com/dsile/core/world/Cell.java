package com.dsile.core.world;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dsile.core.entities.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by DeSile on 07.12.2015.
 */
public abstract class Cell{

    protected int size;
    protected Texture texture;
    protected Set<Entity> entities;

    //Позиция ячейки в массиве мира.
    private int x;
    private int y;

    public Cell(int size, int x, int y){
        this.size = size;
        this.x = x;
        this.y = y;

        this.entities = new HashSet<>();
    }

    public Set<Entity> getEntityList(){
        return entities;
    }

    public Set<Entity> getEntityList(Entity without_self){
        Set<Entity> entitiesWithoutSelf = new HashSet<>(entities);
        entitiesWithoutSelf.remove(without_self);
        return  entitiesWithoutSelf;
    }

    public Herb getHerb(){
        for(Entity entity : entities){
            if(entity instanceof Herb){
                return (Herb)entity;
            }
        }
        return null;
    }

    public Herb_Lizard getAliveHerb_Lizard(){
        for(Entity entity : entities){
            if(entity instanceof Herb_Lizard && entity.isAlive()){
                return (Herb_Lizard)entity;
            }
        }
        return null;
    }

    public Herb_Lizard getAliveHerb_Lizard(Entity caller){
        for(Entity entity : entities){
            if(entity instanceof Herb_Lizard && entity.isAlive() && entity != caller){
                return (Herb_Lizard)entity;
            }
        }
        return null;
    }

    public Herb_Lizard getDeadHerb_Lizard(){
        for(Entity entity : entities){
            if(entity instanceof Herb_Lizard && !entity.isAlive()){
                return (Herb_Lizard)entity;
            }
        }
        return null;
    }

    public Herb_Lizard getDeadHerb_Lizard(Entity caller){
        for(Entity entity : entities){
            if(entity instanceof Herb_Lizard && !entity.isAlive() && entity != caller){
                return (Herb_Lizard)entity;
            }
        }
        return null;
    }

    public Predator_Lizard getAlivePredator_Lizard(){
        for(Entity entity : entities){
            if(entity instanceof Predator_Lizard && entity.isAlive()){
                return (Predator_Lizard)entity;
            }
        }
        return null;
    }

    public Predator_Lizard getAlivePredator_Lizard(Entity caller){
        for(Entity entity : entities){
            if(entity instanceof Predator_Lizard && entity.isAlive() && entity != caller){
                return (Predator_Lizard)entity;
            }
        }
        return null;
    }

    public Predator_Lizard getDeadPredator_Lizard(){
        for(Entity entity : entities){
            if(entity instanceof Predator_Lizard && !entity.isAlive()){
                return (Predator_Lizard)entity;
            }
        }
        return null;
    }

    public Predator_Lizard getDeadPredator_Lizard(Entity caller){
        for(Entity entity : entities){
            if(entity instanceof Predator_Lizard && !entity.isAlive() && entity != caller){
                return (Predator_Lizard)entity;
            }
        }
        return null;
    }

    public boolean isHerb(){
        if(getHerb() == null){
            return false;
        } else {
            return true;
        }
    }

    public boolean isAliveHerb_Lizard(Entity caller){
        for(Entity entity : entities){
            if(entity instanceof Herb_Lizard && entity.isAlive() && entity != caller){
                return true;
            }
        }
        return false;
    }

    public boolean isAliveHerb_Lizard(){
        for(Entity entity : entities){
            if(entity instanceof Herb_Lizard && entity.isAlive()){
                return true;
            }
        }
        return false;
    }

    public boolean isDeadHerb_Lizard(Entity caller){
        for(Entity entity : entities){
            if(entity instanceof Herb_Lizard && !entity.isAlive() && entity != caller){
                return true;
            }
        }
        return false;
    }

    public boolean isDeadHerb_Lizard(){
        for(Entity entity : entities){
            if(entity instanceof Herb_Lizard && !entity.isAlive()){
                return true;
            }
        }
        return false;
    }

    public boolean isAlivePredator_Lizard(Entity caller){
        for(Entity entity : entities){
            if(entity instanceof Predator_Lizard && entity.isAlive() && entity != caller){
                return true;
            }
        }
        return false;
    }

    public boolean isAlivePredator_Lizard(){
        for(Entity entity : entities){
            if(entity instanceof Predator_Lizard && entity.isAlive()){
                return true;
            }
        }
        return false;
    }

    public boolean isDeadPredator_Lizard(Entity caller){
        for(Entity entity : entities){
            if(entity instanceof Predator_Lizard && !entity.isAlive() && entity != caller){
                return true;
            }
        }
        return false;
    }

    public boolean isDeadPredator_Lizard(){
        for(Entity entity : entities){
            if(entity instanceof Predator_Lizard && !entity.isAlive()){
                return true;
            }
        }
        return false;
    }

    public boolean isDeadBody(Entity caller){
        for(Entity entity : entities){
            if(!entity.isAlive() && entity != caller){
                return true;
            }
        }
        return false;
    }

    public boolean isDeadBody(){
        for(Entity entity : entities){
            if(!entity.isAlive()){
                return true;
            }
        }
        return false;
    }

    public Cell setEntity(Entity e){
        entities.add(e);
        return this;
    }

    public void removeEntity(Entity e){
        entities.remove(e);
    }

    public void drawCell(SpriteBatch batch){
        batch.draw(texture, x*size, y*size, size, size);
    }

    public int getSize(){
        return size;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public int getDisplayX(){
        return size*x;
    }

    public int getDisplayY(){
        return size*y;
    }

}
