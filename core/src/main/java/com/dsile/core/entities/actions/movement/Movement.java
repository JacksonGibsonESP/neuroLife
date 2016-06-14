package com.dsile.core.entities.actions.movement;

import com.dsile.core.entities.Creature;
import com.dsile.core.world.Cell;

import java.util.Arrays;

/**
 * Класс, отвечающий за перемещение.
 *
 * Created by DeSile on 1/6/2016.
 */
public class Movement {

    /**
     * Верхняя и нижния границы для определения направлений по значениям из нейронной сети
     */
    private static double CONFIDENCE_VALUE_OF_BRAIN_OUTPUT_DIRECTION = 0.7;

    /**
     * Перемещаемое существо.
     */
    private Creature creature;

    /**
     * Устанавливает текущее существо.
     * @param creature перемещаемое существо.
     */
    public Movement(Creature creature){
        this.creature = creature;
    }

    public Cell getCellBySidesDirection(SidesDirectionValues sdir)
    {
        Cell target_cell = null;
        Cell current_cell = creature.getCurrentCell();
        int x = current_cell.getX();
        int y = current_cell.getY();
        switch (sdir)
        {
            case EAST:
                target_cell = creature.getWorld().getCell(x + 1, y);
                break;
            case NORTH_EAST:
                target_cell = creature.getWorld().getCell(x + 1, y + 1);
                break;
            case NORTH:
                target_cell = creature.getWorld().getCell(x, y + 1);
                break;
            case NORTH_WEST:
                target_cell = creature.getWorld().getCell(x - 1, y + 1);
                break;
            case WEST:
                target_cell = creature.getWorld().getCell(x - 1, y);
                break;
            case SOUTH_WEST:
                target_cell = creature.getWorld().getCell(x - 1, y - 1);
                break;
            case SOUTH:
                target_cell = creature.getWorld().getCell(x, y - 1);
                break;
            case SOUTH_EAST:
                target_cell = creature.getWorld().getCell(x + 1, y - 1);
                break;
            case NO_DIRECTION:
                target_cell = creature.getCurrentCell(); //берём текущую
                break;
            }
        return target_cell;
    }

    public SidesDirectionValues getSidesDirection(double[] brainOutput) {
        DirectionValues         dir     = getDirection(brainOutput);
        SidesDirectionValues    sdir    = creature.getDirection();
        switch (sdir){
            case NORTH:
                switch (dir){
                    case NO_DIRECTION:
                        sdir = SidesDirectionValues.NO_DIRECTION;
                        break;
                    case FORWARD:
                        break;
                    case FORWARD_RIGHT:
                        sdir = SidesDirectionValues.NORTH_EAST;
                        break;
                    case RIGHT:
                        sdir = SidesDirectionValues.EAST;
                        break;
                    case BACKWARD_RIGHT:
                        sdir = SidesDirectionValues.SOUTH_EAST;
                        break;
                    case BACKWARD:
                        sdir = SidesDirectionValues.SOUTH;
                        break;
                    case BACKWARD_LEFT:
                        sdir = SidesDirectionValues.SOUTH_WEST;
                        break;
                    case LEFT:
                        sdir = SidesDirectionValues.WEST;
                        break;
                    case FORWARD_LEFT:
                        sdir = SidesDirectionValues.NORTH_WEST;
                        break;
                }
                break;
            case NORTH_EAST:
                switch (dir){
                    case NO_DIRECTION:
                        sdir = SidesDirectionValues.NO_DIRECTION;
                        break;
                    case FORWARD:
                        break;
                    case FORWARD_RIGHT:
                        sdir = SidesDirectionValues.EAST;
                        break;
                    case RIGHT:
                        sdir = SidesDirectionValues.SOUTH_EAST;
                        break;
                    case BACKWARD_RIGHT:
                        sdir = SidesDirectionValues.SOUTH;
                        break;
                    case BACKWARD:
                        sdir = SidesDirectionValues.SOUTH_WEST;
                        break;
                    case BACKWARD_LEFT:
                        sdir = SidesDirectionValues.WEST;
                        break;
                    case LEFT:
                        sdir = SidesDirectionValues.NORTH_WEST;
                        break;
                    case FORWARD_LEFT:
                        sdir = SidesDirectionValues.NORTH;
                        break;
                }
                break;
            case EAST:
                switch (dir){
                    case NO_DIRECTION:
                        sdir = SidesDirectionValues.NO_DIRECTION;
                        break;
                    case FORWARD:
                        break;
                    case FORWARD_RIGHT:
                        sdir = SidesDirectionValues.SOUTH_EAST;
                        break;
                    case RIGHT:
                        sdir = SidesDirectionValues.SOUTH;
                        break;
                    case BACKWARD_RIGHT:
                        sdir = SidesDirectionValues.SOUTH_WEST;
                        break;
                    case BACKWARD:
                        sdir = SidesDirectionValues.WEST;
                        break;
                    case BACKWARD_LEFT:
                        sdir = SidesDirectionValues.NORTH_WEST;
                        break;
                    case LEFT:
                        sdir = SidesDirectionValues.NORTH;
                        break;
                    case FORWARD_LEFT:
                        sdir = SidesDirectionValues.NORTH_EAST;
                        break;
                }
                break;
            case SOUTH_EAST:
                switch (dir){
                    case NO_DIRECTION:
                        sdir = SidesDirectionValues.NO_DIRECTION;
                        break;
                    case FORWARD:
                        break;
                    case FORWARD_RIGHT:
                        sdir = SidesDirectionValues.SOUTH;
                        break;
                    case RIGHT:
                        sdir = SidesDirectionValues.SOUTH_WEST;
                        break;
                    case BACKWARD_RIGHT:
                        sdir = SidesDirectionValues.WEST;
                        break;
                    case BACKWARD:
                        sdir = SidesDirectionValues.NORTH_WEST;
                        break;
                    case BACKWARD_LEFT:
                        sdir = SidesDirectionValues.NORTH;
                        break;
                    case LEFT:
                        sdir = SidesDirectionValues.NORTH_EAST;
                        break;
                    case FORWARD_LEFT:
                        sdir = SidesDirectionValues.EAST;
                        break;
                }
                break;
            case SOUTH:
                switch (dir){
                    case NO_DIRECTION:
                        sdir = SidesDirectionValues.NO_DIRECTION;
                        break;
                    case FORWARD:
                        break;
                    case FORWARD_RIGHT:
                        sdir = SidesDirectionValues.SOUTH_WEST;
                        break;
                    case RIGHT:
                        sdir = SidesDirectionValues.WEST;
                        break;
                    case BACKWARD_RIGHT:
                        sdir = SidesDirectionValues.NORTH_WEST;
                        break;
                    case BACKWARD:
                        sdir = SidesDirectionValues.NORTH;
                        break;
                    case BACKWARD_LEFT:
                        sdir = SidesDirectionValues.NORTH_EAST;
                        break;
                    case LEFT:
                        sdir = SidesDirectionValues.EAST;
                        break;
                    case FORWARD_LEFT:
                        sdir = SidesDirectionValues.SOUTH_EAST;
                        break;
                }
                break;
            case SOUTH_WEST:
                switch (dir){
                    case NO_DIRECTION:
                        sdir = SidesDirectionValues.NO_DIRECTION;
                        break;
                    case FORWARD:
                        break;
                    case FORWARD_RIGHT:
                        sdir = SidesDirectionValues.WEST;
                        break;
                    case RIGHT:
                        sdir = SidesDirectionValues.NORTH_WEST;
                        break;
                    case BACKWARD_RIGHT:
                        sdir = SidesDirectionValues.NORTH;
                        break;
                    case BACKWARD:
                        sdir = SidesDirectionValues.NORTH_EAST;
                        break;
                    case BACKWARD_LEFT:
                        sdir = SidesDirectionValues.EAST;
                        break;
                    case LEFT:
                        sdir = SidesDirectionValues.SOUTH_EAST;
                        break;
                    case FORWARD_LEFT:
                        sdir = SidesDirectionValues.SOUTH;
                        break;
                }
                break;
            case WEST:
                switch (dir){
                    case NO_DIRECTION:
                        sdir = SidesDirectionValues.NO_DIRECTION;
                        break;
                    case FORWARD:
                        break;
                    case FORWARD_RIGHT:
                        sdir = SidesDirectionValues.NORTH_WEST;
                        break;
                    case RIGHT:
                        sdir = SidesDirectionValues.NORTH;
                        break;
                    case BACKWARD_RIGHT:
                        sdir = SidesDirectionValues.NORTH_EAST;
                        break;
                    case BACKWARD:
                        sdir = SidesDirectionValues.EAST;
                        break;
                    case BACKWARD_LEFT:
                        sdir = SidesDirectionValues.SOUTH_EAST;
                        break;
                    case LEFT:
                        sdir = SidesDirectionValues.SOUTH;
                        break;
                    case FORWARD_LEFT:
                        sdir = SidesDirectionValues.SOUTH_WEST;
                        break;
                }
                break;
            case NORTH_WEST:
                switch (dir){
                    case NO_DIRECTION:
                        sdir = SidesDirectionValues.NO_DIRECTION;
                        break;
                    case FORWARD:
                        break;
                    case FORWARD_RIGHT:
                        sdir = SidesDirectionValues.NORTH;
                        break;
                    case RIGHT:
                        sdir = SidesDirectionValues.NORTH_EAST;
                        break;
                    case BACKWARD_RIGHT:
                        sdir = SidesDirectionValues.EAST;
                        break;
                    case BACKWARD:
                        sdir = SidesDirectionValues.SOUTH_EAST;
                        break;
                    case BACKWARD_LEFT:
                        sdir = SidesDirectionValues.SOUTH;
                        break;
                    case LEFT:
                        sdir = SidesDirectionValues.SOUTH_WEST;
                        break;
                    case FORWARD_LEFT:
                        sdir = SidesDirectionValues.WEST;
                        break;
                }
                break;
        }

        return sdir;
    }

    public DirectionValues getDirection(double[] signal)
    {
        //Массив означающий текущий порядок направлений массива brainOutput
        int[] order = {0,1,2,3};
        //Скопируем массив
        double[] brainOutput = Arrays.copyOfRange(signal, 0, 4);
        //Делаем сортировку массива brainOutput, заодно меняя места в массиве направлений
        //Таким образом мы получаем отсортированный массив brainOutput и не потеряли направления
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (brainOutput[j] > brainOutput[j+1]) {
                    int tmp = order[j];
                    order[j] = order[j+1];
                    order[j+1] = tmp;
                    double tmp2 = brainOutput[j];
                    brainOutput[j] = brainOutput[j + 1];
                    brainOutput[j + 1] = tmp2;
                }
            }
        }

        //Как известно, нейронная сеть может вернуть нам в качестве результата сразу два возможных направления.
        boolean fstDir = false; //Наличие первого направления
        boolean sndDir = false; //Наличие второго направления
        DirectionValues dir = DirectionValues.NO_DIRECTION; //Результирующее направление

        //2 и 3 - номера индексов самых больших значений массива brainOutput
        //Проверяем можно ли считать первое по величине значение направлением.
        if(brainOutput[3] > CONFIDENCE_VALUE_OF_BRAIN_OUTPUT_DIRECTION){
            fstDir = true;
        }

        //Проверяем можно ли считать второе по величине значение направлением.
        if(brainOutput[2] > CONFIDENCE_VALUE_OF_BRAIN_OUTPUT_DIRECTION){
            sndDir = true;
        }

        if((fstDir & sndDir) == true){
            dir = choseDirection(order[2],order[3]);

        } else {
            if(fstDir){
                dir = choseDirection(order[3]);
            }
        }

        return dir;
    }

    /**
     * Выполнение действия передвижения с учетом значений на выходе нейронной сети.
     */
    public void perform(double[] brainOutput) {
        DirectionValues dir = getDirection(brainOutput);
        if (dir == DirectionValues.NO_DIRECTION) {
            if (creature.getLogger().isDebugEnabled()) {
                creature.getLogger().debug("Decided to go randomly");
            }
            SidesDirectionValues sdir = SidesDirectionValues.random_Gauss(creature.getDirection()); //Попробую уменьшить беспорядочность рандома
            creature.setDirection(sdir);
            if (creature.getLogger().isDebugEnabled()) {
                creature.getLogger().debug(dir.toString());
            }
        }
        else {
            creature.setDirection(dir);
            if (creature.getLogger().isDebugEnabled()) {
                creature.getLogger().debug(dir.toString());
            }
        }
        moveByDirection();
    }

    /**
     * Конвертация численных значений в конкретное направление.
     * @param dir массив из одного или двух элементов со значениями от 0 до 3, которые интерпретируются в направления.
     * @return Конкретное направление.
    */
    private DirectionValues choseDirection(int... dir){ //либо одно, либо два числа
        if(dir.length == 2){
            if((dir[0] == 0 && dir[1] == 1)||(dir[0] == 1 && dir[1] == 0)){
                return DirectionValues.FORWARD_RIGHT;
            }
            if((dir[0] == 1 && dir[1] == 2)||(dir[0] == 2 && dir[1] == 1)){
                return DirectionValues.FORWARD_LEFT;
            }
            if((dir[0] == 2 && dir[1] == 3)||(dir[0] == 3 && dir[1] == 2)){
                return DirectionValues.BACKWARD_LEFT;
            }
            if((dir[0] == 3 && dir[1] == 0)||(dir[0] == 0 && dir[1] == 3)){
                return DirectionValues.BACKWARD_RIGHT;
            }
        }
        if(dir.length == 1){
            if(dir[0] == 0){
                return DirectionValues.RIGHT;
            }
            if(dir[0] == 1){
                return DirectionValues.FORWARD;
            }
            if(dir[0] == 2){
                return DirectionValues.LEFT;
            }
            if(dir[0] == 3){
                return DirectionValues.BACKWARD;
            }
        }
        return DirectionValues.NO_DIRECTION; //в случае нештатной ситуации
    }

    private void moveByDirection(){
        int speed = 1; //по хорошему должно передаваться от сущности (могут быть способности вроде рывка, например, на три клетки)
        switch (creature.getDirection()){
            case EAST:
                move(speed,0);
                break;
            case NORTH_EAST:
                move(speed,speed);
                break;
            case NORTH:
                move(0,speed);
                break;
            case NORTH_WEST:
                move(-speed,speed);
                break;
            case WEST:
                move(-speed,0);
                break;
            case SOUTH_WEST:
                move(-speed,-speed);
                break;
            case SOUTH:
                move(0,-speed);
                break;
            case SOUTH_EAST:
                move(speed,-speed);
                break;
        }
    }

    private void move(int difX, int difY){
        creature.setCurrentCell(creature.x() + difX, creature.y() + difY);
    }
}
