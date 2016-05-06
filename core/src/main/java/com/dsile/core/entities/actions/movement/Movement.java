package com.dsile.core.entities.actions.movement;

import com.dsile.core.entities.Creature;

import java.util.Arrays;

/**
 * Класс, отвечающий за перемещение.
 *
 * Created by DeSile on 1/6/2016.
 */
//TODO: Unit-тесты
public class Movement {

    /**
     * Верхняя и нижния границы для определения направлений по значениям из нейронной сети
     */
    private static double CONFIDENCE_VALUE_OF_BRAIN_OUTPUT_DIRECTION = 0.5;

    /**
     * Перемещаемое существо.
     */
    private Creature creature;

    /**
     * Устанавливает текущее существо, устанавливает вектор скорости и начальное количество шагов в клетке.
     * @param creature перемещаемое существо.
     */
    public Movement(Creature creature){
        this.creature = creature;
    }


    /**
     * Выполнение шага в пространтсве.
     * Проверяет не пора ли сменить текущую клетку, а следовательно и угол поворота.
     * Устанавливает для существа новые координаты в непрерывном пространстве
     */
    public void perform(){

    }

    /**
     * Выполнение действия передвижения с учетом значений на выходе нейронной сети.
     */
    public void perform(double[] brainOutput){
        //Берем модули от элементов массива
        for(int i = 0; i < 4; i++){
            brainOutput[i] = Math.abs(brainOutput[i]);
        }
        //Массив означающий текущий порядок направлений массива brainOutput
        int[] order = {0,1,2,3};
        //Делаем сортировку массива brainOutput, заодно меняя места в массиве направлений
        //Таким образом мы получаем отсортированный массив brainOutput и не потеряли направления
        /*System.out.println("Check before");
        System.out.println(Arrays.toString(brainOutput));
        System.out.println(Arrays.toString(order));*/
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
        /*System.out.println("Check after");
        System.out.println(Arrays.toString(brainOutput));
        System.out.println(Arrays.toString(order));*/
        //Как известно, нейронная сеть может вернуть нам в качестве результата сразу два возможных направления.
        boolean fstDir = false; //Наличие первого направления
        boolean sndDir = false; //Наличие второго направления
        DirectionValues dir; //Результирующее направление

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
            else
            {
                System.out.println("Decided to go randomly");
                //dir = DirectionValues.random();
                dir = DirectionValues.random_Gauss(creature.getDirection()); //Попробую уменьшить беспорядочность рандома
            }
        }
        creature.setDirection(dir);
        System.out.println(dir);
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
        return DirectionValues.EAST; //в случае нештатной ситуации идти вправо
    }

    private void moveByDirection(){
        int speed = 1; //по хорошему должно передаваться от сущности (могут способности вроде рывка, например, на три клетки)
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
