package com.dsile.core.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.dsile.core.entities.actions.movement.DirectionValues;
import com.dsile.core.entities.actions.movement.SidesDirectionValues;
import com.dsile.core.world.Cell;
import com.dsile.core.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by DeSile on 2/21/2016.
 */
public abstract class Entity extends Actor {
    static protected final Logger logger = LoggerFactory.getLogger(Entity.class); //Может в отдельный статический класс вынести?
    protected static int SIZE;
    protected SidesDirectionValues direction;
    protected Cell currentCell;
    protected World world;
    protected Texture texture;

    protected boolean alive = true;
    protected int maxHP = 100;
    protected int HP = 50;

    //protected int id; //для сортировки объектов перед рисовкой на экране

    public Entity(World world, int x, int y){
        this.world = world;
        SIZE = this.world.getCellSize();
        this.currentCell = world.getCell(x, y).setEntity(this);
        this.setDirection(SidesDirectionValues.random());
        this.setAliveTexture();

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
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, currentCell.getDisplayX(), currentCell.getDisplayY(), this.getOriginX(), this.getOriginY(), this.getWidth(),
                this.getHeight(), this.getScaleX(), this.getScaleY(), this.getRotation(), 0, 0,
                texture.getWidth(), texture.getHeight(), false, false);
    }

    protected abstract void setAliveTexture();

    protected abstract void setDeadTexture();

    protected abstract void setNewbornTexture();

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

    public SidesDirectionValues getDirection(){
        return direction;
    }

    public void setDirection(SidesDirectionValues val){
        direction = val;
        setRotation(direction.getAngle());
    }

    public void setDirection(DirectionValues val){
        switch (direction){
            case NORTH:
                switch (val){
                    case NO_DIRECTION:
                        break;
                    case FORWARD:
                        break;
                    case FORWARD_RIGHT:
                        direction = SidesDirectionValues.NORTH_EAST;
                        break;
                    case RIGHT:
                        direction = SidesDirectionValues.EAST;
                        break;
                    case BACKWARD_RIGHT:
                        direction = SidesDirectionValues.SOUTH_EAST;
                        break;
                    case BACKWARD:
                        direction = SidesDirectionValues.SOUTH;
                        break;
                    case BACKWARD_LEFT:
                        direction = SidesDirectionValues.SOUTH_WEST;
                        break;
                    case LEFT:
                        direction = SidesDirectionValues.WEST;
                        break;
                    case FORWARD_LEFT:
                        direction = SidesDirectionValues.NORTH_WEST;
                        break;
                }
                break;
            case NORTH_EAST:
                switch (val){
                    case NO_DIRECTION:
                        break;
                    case FORWARD:
                        break;
                    case FORWARD_RIGHT:
                        direction = SidesDirectionValues.EAST;
                        break;
                    case RIGHT:
                        direction = SidesDirectionValues.SOUTH_EAST;
                        break;
                    case BACKWARD_RIGHT:
                        direction = SidesDirectionValues.SOUTH;
                        break;
                    case BACKWARD:
                        direction = SidesDirectionValues.SOUTH_WEST;
                        break;
                    case BACKWARD_LEFT:
                        direction = SidesDirectionValues.WEST;
                        break;
                    case LEFT:
                        direction = SidesDirectionValues.NORTH_WEST;
                        break;
                    case FORWARD_LEFT:
                        direction = SidesDirectionValues.NORTH;
                        break;
                }
                break;
            case EAST:
                switch (val){
                    case NO_DIRECTION:
                        break;
                    case FORWARD:
                        break;
                    case FORWARD_RIGHT:
                        direction = SidesDirectionValues.SOUTH_EAST;
                        break;
                    case RIGHT:
                        direction = SidesDirectionValues.SOUTH;
                        break;
                    case BACKWARD_RIGHT:
                        direction = SidesDirectionValues.SOUTH_WEST;
                        break;
                    case BACKWARD:
                        direction = SidesDirectionValues.WEST;
                        break;
                    case BACKWARD_LEFT:
                        direction = SidesDirectionValues.NORTH_WEST;
                        break;
                    case LEFT:
                        direction = SidesDirectionValues.NORTH;
                        break;
                    case FORWARD_LEFT:
                        direction = SidesDirectionValues.NORTH_EAST;
                        break;
                }
                break;
            case SOUTH_EAST:
                switch (val){
                    case NO_DIRECTION:
                        break;
                    case FORWARD:
                        break;
                    case FORWARD_RIGHT:
                        direction = SidesDirectionValues.SOUTH;
                        break;
                    case RIGHT:
                        direction = SidesDirectionValues.SOUTH_WEST;
                        break;
                    case BACKWARD_RIGHT:
                        direction = SidesDirectionValues.WEST;
                        break;
                    case BACKWARD:
                        direction = SidesDirectionValues.NORTH_WEST;
                        break;
                    case BACKWARD_LEFT:
                        direction = SidesDirectionValues.NORTH;
                        break;
                    case LEFT:
                        direction = SidesDirectionValues.NORTH_EAST;
                        break;
                    case FORWARD_LEFT:
                        direction = SidesDirectionValues.EAST;
                        break;
                }
                break;
            case SOUTH:
                switch (val){
                    case NO_DIRECTION:
                        break;
                    case FORWARD:
                        break;
                    case FORWARD_RIGHT:
                        direction = SidesDirectionValues.SOUTH_WEST;
                        break;
                    case RIGHT:
                        direction = SidesDirectionValues.WEST;
                        break;
                    case BACKWARD_RIGHT:
                        direction = SidesDirectionValues.NORTH_WEST;
                        break;
                    case BACKWARD:
                        direction = SidesDirectionValues.NORTH;
                        break;
                    case BACKWARD_LEFT:
                        direction = SidesDirectionValues.NORTH_EAST;
                        break;
                    case LEFT:
                        direction = SidesDirectionValues.EAST;
                        break;
                    case FORWARD_LEFT:
                        direction = SidesDirectionValues.SOUTH_EAST;
                        break;
                }
                break;
            case SOUTH_WEST:
                switch (val){
                    case NO_DIRECTION:
                        break;
                    case FORWARD:
                        break;
                    case FORWARD_RIGHT:
                        direction = SidesDirectionValues.WEST;
                        break;
                    case RIGHT:
                        direction = SidesDirectionValues.NORTH_WEST;
                        break;
                    case BACKWARD_RIGHT:
                        direction = SidesDirectionValues.NORTH;
                        break;
                    case BACKWARD:
                        direction = SidesDirectionValues.NORTH_EAST;
                        break;
                    case BACKWARD_LEFT:
                        direction = SidesDirectionValues.EAST;
                        break;
                    case LEFT:
                        direction = SidesDirectionValues.SOUTH_EAST;
                        break;
                    case FORWARD_LEFT:
                        direction = SidesDirectionValues.SOUTH;
                        break;
                }
                break;
            case WEST:
                switch (val){
                    case NO_DIRECTION:
                        break;
                    case FORWARD:
                        break;
                    case FORWARD_RIGHT:
                        direction = SidesDirectionValues.NORTH_WEST;
                        break;
                    case RIGHT:
                        direction = SidesDirectionValues.NORTH;
                        break;
                    case BACKWARD_RIGHT:
                        direction = SidesDirectionValues.NORTH_EAST;
                        break;
                    case BACKWARD:
                        direction = SidesDirectionValues.EAST;
                        break;
                    case BACKWARD_LEFT:
                        direction = SidesDirectionValues.SOUTH_EAST;
                        break;
                    case LEFT:
                        direction = SidesDirectionValues.SOUTH;
                        break;
                    case FORWARD_LEFT:
                        direction = SidesDirectionValues.SOUTH_WEST;
                        break;
                }
                break;
            case NORTH_WEST:
                switch (val){
                    case NO_DIRECTION:
                        break;
                    case FORWARD:
                        break;
                    case FORWARD_RIGHT:
                        direction = SidesDirectionValues.NORTH;
                        break;
                    case RIGHT:
                        direction = SidesDirectionValues.NORTH_EAST;
                        break;
                    case BACKWARD_RIGHT:
                        direction = SidesDirectionValues.EAST;
                        break;
                    case BACKWARD:
                        direction = SidesDirectionValues.SOUTH_EAST;
                        break;
                    case BACKWARD_LEFT:
                        direction = SidesDirectionValues.SOUTH;
                        break;
                    case LEFT:
                        direction = SidesDirectionValues.SOUTH_WEST;
                        break;
                    case FORWARD_LEFT:
                        direction = SidesDirectionValues.WEST;
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

    public Logger getLogger(){ return logger;}
}
