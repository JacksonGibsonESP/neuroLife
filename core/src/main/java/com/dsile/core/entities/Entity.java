package com.dsile.core.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.dsile.core.entities.actions.movement.DirectionValues;
import com.dsile.core.entities.actions.movement.SidesDirectionValues;
import com.dsile.core.world.Cell;
import com.dsile.core.world.World;

/**
 * Created by DeSile on 2/21/2016.
 */
public abstract class Entity extends Actor {
    protected static int SIZE = 32;
    protected DirectionValues direction;
    protected Cell currentCell;
    protected World world;
    //protected Texture texture;

    protected boolean alive = true;
    protected int maxHP = 100;
//    protected int maxEnergy = 1;
    protected int HP = 50;
//    protected int energy = maxEnergy;

    //protected int id; //для сортировки объектов перед рисовкой на экране

    //protected boolean reproducing = false;

    public Entity(World world, int x, int y){
        this.world = world;
        this.currentCell = world.getCell(x, y).setEntity(this);
        this.setDirection(DirectionValues.random());
        //this.setAliveTexture();

        setOrigin(SIZE / 2, SIZE / 2);
        setBounds(currentCell.getDisplayX(), currentCell.getDisplayY(), SIZE, SIZE);

        //System.out.println(getX() + "," + getY() + "," + getOriginX() + "," + getOriginY());
    }

    /**
     * Отрисовка существо с учетом текстуры, масштабов и угла поворота.
     * @param batch спрайтбатч-отрисовщик
     * @param parentAlpha ???
     */
    //@Override
    /*public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, currentCell.getDisplayX(), currentCell.getDisplayY(), this.getOriginX(), this.getOriginY(), this.getWidth(),
                this.getHeight(), this.getScaleX(), this.getScaleY(), this.getRotation(), 0, 0,
                texture.getWidth(), texture.getHeight(), false, false);
    }*/

    //protected abstract void setAliveTexture();

    //protected abstract void setDeadTexture();

    //protected abstract void setNewbornTexture();

    protected abstract void dead();

    public abstract int bitten();

    public World getWorld(){
        return world;
    }

    public int x(){
        return currentCell.getX();
    }

    public int y(){
        return currentCell.getY();
    }

    public Cell getCurrentCell() {
        return currentCell;
    }

    public void setCurrentCell(int x, int y){
        currentCell.removeEntity(this);
        currentCell = world.getCell(x,y);
        currentCell.setEntity(this);
    }

    public DirectionValues getDirection(){
        return direction;
    }

    public void setDirection(DirectionValues val){
        direction = val;
        setRotation(direction.getAngle());
    }

    public void setDirection(SidesDirectionValues val){
        switch (direction){
            case NORTH:
                switch (val){
                    case FORWARD:
                        break;
                    case FORWARD_RIGHT:
                        direction = DirectionValues.NORTH_EAST;
                        break;
                    case RIGHT:
                        direction = DirectionValues.EAST;
                        break;
                    case BACKWARD_RIGHT:
                        direction = DirectionValues.SOUTH_EAST;
                        break;
                    case BACKWARD:
                        direction = DirectionValues.SOUTH;
                        break;
                    case BACKWARD_LEFT:
                        direction = DirectionValues.SOUTH_WEST;
                        break;
                    case LEFT:
                        direction = DirectionValues.WEST;
                        break;
                    case FORWARD_LEFT:
                        direction = DirectionValues.NORTH_WEST;
                        break;
                }
                break;
            case NORTH_EAST:
                switch (val){
                    case FORWARD:
                        break;
                    case FORWARD_RIGHT:
                        direction = DirectionValues.EAST;
                        break;
                    case RIGHT:
                        direction = DirectionValues.SOUTH_EAST;
                        break;
                    case BACKWARD_RIGHT:
                        direction = DirectionValues.SOUTH;
                        break;
                    case BACKWARD:
                        direction = DirectionValues.SOUTH_WEST;
                        break;
                    case BACKWARD_LEFT:
                        direction = DirectionValues.WEST;
                        break;
                    case LEFT:
                        direction = DirectionValues.NORTH_WEST;
                        break;
                    case FORWARD_LEFT:
                        direction = DirectionValues.NORTH;
                        break;
                }
                break;
            case EAST:
                switch (val){
                    case FORWARD:
                        break;
                    case FORWARD_RIGHT:
                        direction = DirectionValues.SOUTH_EAST;
                        break;
                    case RIGHT:
                        direction = DirectionValues.SOUTH;
                        break;
                    case BACKWARD_RIGHT:
                        direction = DirectionValues.SOUTH_WEST;
                        break;
                    case BACKWARD:
                        direction = DirectionValues.WEST;
                        break;
                    case BACKWARD_LEFT:
                        direction = DirectionValues.NORTH_WEST;
                        break;
                    case LEFT:
                        direction = DirectionValues.NORTH;
                        break;
                    case FORWARD_LEFT:
                        direction = DirectionValues.NORTH_EAST;
                        break;
                }
                break;
            case SOUTH_EAST:
                switch (val){
                    case FORWARD:
                        break;
                    case FORWARD_RIGHT:
                        direction = DirectionValues.SOUTH;
                        break;
                    case RIGHT:
                        direction = DirectionValues.SOUTH_WEST;
                        break;
                    case BACKWARD_RIGHT:
                        direction = DirectionValues.WEST;
                        break;
                    case BACKWARD:
                        direction = DirectionValues.NORTH_WEST;
                        break;
                    case BACKWARD_LEFT:
                        direction = DirectionValues.NORTH;
                        break;
                    case LEFT:
                        direction = DirectionValues.NORTH_EAST;
                        break;
                    case FORWARD_LEFT:
                        direction = DirectionValues.EAST;
                        break;
                }
                break;
            case SOUTH:
                switch (val){
                    case FORWARD:
                        break;
                    case FORWARD_RIGHT:
                        direction = DirectionValues.SOUTH_WEST;
                        break;
                    case RIGHT:
                        direction = DirectionValues.WEST;
                        break;
                    case BACKWARD_RIGHT:
                        direction = DirectionValues.NORTH_WEST;
                        break;
                    case BACKWARD:
                        direction = DirectionValues.NORTH;
                        break;
                    case BACKWARD_LEFT:
                        direction = DirectionValues.NORTH_EAST;
                        break;
                    case LEFT:
                        direction = DirectionValues.EAST;
                        break;
                    case FORWARD_LEFT:
                        direction = DirectionValues.SOUTH_EAST;
                        break;
                }
                break;
            case SOUTH_WEST:
                switch (val){
                    case FORWARD:
                        break;
                    case FORWARD_RIGHT:
                        direction = DirectionValues.WEST;
                        break;
                    case RIGHT:
                        direction = DirectionValues.NORTH_WEST;
                        break;
                    case BACKWARD_RIGHT:
                        direction = DirectionValues.NORTH;
                        break;
                    case BACKWARD:
                        direction = DirectionValues.NORTH_EAST;
                        break;
                    case BACKWARD_LEFT:
                        direction = DirectionValues.EAST;
                        break;
                    case LEFT:
                        direction = DirectionValues.SOUTH_EAST;
                        break;
                    case FORWARD_LEFT:
                        direction = DirectionValues.SOUTH;
                        break;
                }
                break;
            case WEST:
                switch (val){
                    case FORWARD:
                        break;
                    case FORWARD_RIGHT:
                        direction = DirectionValues.NORTH_WEST;
                        break;
                    case RIGHT:
                        direction = DirectionValues.NORTH;
                        break;
                    case BACKWARD_RIGHT:
                        direction = DirectionValues.NORTH_EAST;
                        break;
                    case BACKWARD:
                        direction = DirectionValues.EAST;
                        break;
                    case BACKWARD_LEFT:
                        direction = DirectionValues.SOUTH_EAST;
                        break;
                    case LEFT:
                        direction = DirectionValues.SOUTH;
                        break;
                    case FORWARD_LEFT:
                        direction = DirectionValues.SOUTH_WEST;
                        break;
                }
                break;
            case NORTH_WEST:
                switch (val){
                    case FORWARD:
                        break;
                    case FORWARD_RIGHT:
                        direction = DirectionValues.NORTH;
                        break;
                    case RIGHT:
                        direction = DirectionValues.NORTH_EAST;
                        break;
                    case BACKWARD_RIGHT:
                        direction = DirectionValues.EAST;
                        break;
                    case BACKWARD:
                        direction = DirectionValues.SOUTH_EAST;
                        break;
                    case BACKWARD_LEFT:
                        direction = DirectionValues.SOUTH;
                        break;
                    case LEFT:
                        direction = DirectionValues.SOUTH_WEST;
                        break;
                    case FORWARD_LEFT:
                        direction = DirectionValues.WEST;
                        break;
                }
                break;
        }
        setRotation(direction.getAngle());
    }

    public int getHP()
    {
        return this.HP;
    }

    public double getnormalizedHP()
    {
        return (double) this.HP / (double) 100;
        /*if (this.HP >= 70)
            return 0.7;
        else if (this.HP >= 0.5)
            return 0.5;
        else
            return 0.3;*/
    }

    public void setHP(int new_hp) // only for testing
    {
        this.HP = new_hp;
    }

    public void incHP(int deltaHP)
    {
        if (this.HP + deltaHP > this.maxHP)
        {
            this.HP = this.maxHP;
        }
        else
        {
            this.HP += deltaHP;
        }
    }

    public boolean isAlive() {
        return alive;
    }

    //public int getId(){return id;}

    /*public boolean isReproducing()
    {
        return reproducing;
    }

    public void switchReproducing(){
        reproducing = !reproducing;
    }*/
}
