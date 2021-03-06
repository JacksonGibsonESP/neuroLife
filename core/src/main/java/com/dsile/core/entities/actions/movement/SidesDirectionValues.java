package com.dsile.core.entities.actions.movement;

/**
 * Created by DeSile on 1/6/2016.
 */

import java.util.Random;

/**
 * Возможные направления
 */
public enum SidesDirectionValues {
    EAST(0),NORTH_EAST(45),NORTH(90),NORTH_WEST(135),WEST(180),SOUTH_WEST(225),SOUTH(270),SOUTH_EAST(315), NO_DIRECTION(-1);

    private int angle;
    private static Random r = new Random();

    SidesDirectionValues(int angle){
        this.angle = angle;
    }

    public int getAngle(){
        return angle;
    }

    public static SidesDirectionValues random(){
        return values()[(int) (Math.random() * values().length - 1)];
    }

    public static SidesDirectionValues random_Gauss(SidesDirectionValues dir){
        //отклоняется от текущего угла по гауссовскому закону
        int angle = dir.getAngle();
        double rand = r.nextGaussian() * 2; //среднеквадратичное отклонение - 2, матожидание - 0
        int out_angle;
        if (rand > 1.0) {
            out_angle = angle + 45;
            if (out_angle == 360)
                out_angle = 0;
        }
        else if (rand < -1.0)
        {
            out_angle = angle - 45;
            if (out_angle == -45)
                out_angle = 315;
        }
        else
        {
            out_angle = angle;
        }

        SidesDirectionValues rand_dir = SidesDirectionValues.fromAngle(out_angle);

        return rand_dir;
    }

    public static SidesDirectionValues fromAngle(int angle)
    {
        SidesDirectionValues dir = null;

        for (SidesDirectionValues d : SidesDirectionValues.values()){
            if (d.getAngle() == angle)
                dir = d;
        }

        return dir;
    }
}
