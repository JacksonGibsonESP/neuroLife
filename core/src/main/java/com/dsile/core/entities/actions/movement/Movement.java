package com.dsile.core.entities.actions.movement;

import com.dsile.core.entities.Creature;
import com.dsile.core.world.Cell;

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

    public Cell getCellByDirection(double[] brainOutput)
    {
        Cell target_cell = null;
        DirectionValues dir = getDirection(brainOutput);
        Cell current_cell = creature.getCurrentCell();
        int x = current_cell.getX();
        int y = current_cell.getY();
        switch (dir)
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
        //System.out.println(target_cell.getX());
        //System.out.println(target_cell.getY());
        if (dir != DirectionValues.NO_DIRECTION)
            creature.setDirection(dir);
        return target_cell;
    }

    public DirectionValues getDirection(double[] brainOutput)
    {
        //Массив означающий текущий порядок направлений массива brainOutput
        int[] order = {0,1,2,3};
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

    public SidesDirectionValues getSidesDirection(double[] brainOutput){
        DirectionValues dir = getDirection(brainOutput);
        SidesDirectionValues sides = SidesDirectionValues.NO_DIRECTION;
        //Приведение типов
        switch (dir){
            case NO_DIRECTION:
                sides = SidesDirectionValues.NO_DIRECTION;
                break;
            case NORTH:
                sides = SidesDirectionValues.FORWARD;
                break;
            case NORTH_EAST:
                sides = SidesDirectionValues.FORWARD_RIGHT;
                break;
            case EAST:
                sides = SidesDirectionValues.RIGHT;
                break;
            case SOUTH_EAST:
                sides = SidesDirectionValues.BACKWARD_RIGHT;
                break;
            case SOUTH:
                sides = SidesDirectionValues.BACKWARD;
                break;
            case SOUTH_WEST:
                sides = SidesDirectionValues.BACKWARD_LEFT;
                break;
            case WEST:
                sides = SidesDirectionValues.LEFT;
                break;
            case NORTH_WEST:
                sides = SidesDirectionValues.FORWARD_LEFT;
                break;
        }
        return sides;
    }

    /**
     * Выполнение действия передвижения с учетом значений на выходе нейронной сети.
     */
    public void perform(double[] brainOutput) {
        SidesDirectionValues sides = getSidesDirection(brainOutput);
        if (sides == SidesDirectionValues.NO_DIRECTION) {
            if (creature.getLogger().isDebugEnabled()) {
                creature.getLogger().debug("Decided to go randomly");
            }
            DirectionValues dir = DirectionValues.random_Gauss(creature.getDirection()); //Попробую уменьшить беспорядочность рандома
            creature.setDirection(dir);
            if (creature.getLogger().isDebugEnabled()) {
                creature.getLogger().debug(dir.toString());
            }
        }
        else {
            creature.setDirection(sides);
            if (creature.getLogger().isDebugEnabled()) {
                creature.getLogger().debug(sides.toString());
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
                return DirectionValues.NORTH_EAST;
            }
            if((dir[0] == 1 && dir[1] == 2)||(dir[0] == 2 && dir[1] == 1)){
                return DirectionValues.NORTH_WEST;
            }
            if((dir[0] == 2 && dir[1] == 3)||(dir[0] == 3 && dir[1] == 2)){
                return DirectionValues.SOUTH_WEST;
            }
            if((dir[0] == 3 && dir[1] == 0)||(dir[0] == 0 && dir[1] == 3)){
                return DirectionValues.SOUTH_EAST;
            }
        }
        if(dir.length == 1){
            if(dir[0] == 0){
                return DirectionValues.EAST;
            }
            if(dir[0] == 1){
                return DirectionValues.NORTH;
            }
            if(dir[0] == 2){
                return DirectionValues.WEST;
            }
            if(dir[0] == 3){
                return DirectionValues.SOUTH;
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
