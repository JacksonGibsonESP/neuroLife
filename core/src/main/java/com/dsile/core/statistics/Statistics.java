package com.dsile.core.statistics;

import com.dsile.core.world.World;
//import org.knowm.xchart.XYChart;
//import org.knowm.xchart.QuickChart;
//import org.knowm.xchart.SwingWrapper;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

/**
 * Created by Никита on 04.06.2016.
 */
public class Statistics{
    private World world;
    //private SwingWrapper swing_wrapper;
    //private XYChart chart;
    private final String statistics_file_name          = "statistics.txt";
    private FileWriter writer;
    private int counter = 0;
    private int delta;

    //delta - количесвто итераций, после которых производится замер
    public Statistics(World world, int delta){
        this.world = world;
        //this.delta = delta;
        this.delta = 1;
        this.counter = this.delta; //запись начальной ситуации
        /*this.chart = new XYChart(500, 500);
        this.swing_wrapper = new SwingWrapper(chart);
        this.swing_wrapper.displayChart();*/

        try {
            this.writer = new FileWriter(statistics_file_name);
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }

        try {
            this.writer.write("Delta: " + this.delta + '\n'
                    + "Herb lizards count:\t"
                    + "Dead bodies count:\t"
                    + "Herbs count:\t"
                    + "Generations count:\t"
                    + "Generations stat:\n");
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }

        try {
            this.writer.close();
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }

    public void act() {
        /*double[] xData = new double[] { 0.0, 1.0, 2.0 };
        double[] yData = new double[] { 2.0, 1.0, 0.0 };

        // Create Chart
        this.chart = QuickChart.getChart("Sample Chart", "X", "Y", "y(x)", xData, yData);

        // Show it
        this.swing_wrapper.displayChart();*/
        // Запишем статистику в файл, т.к. не нашёл библиотеки, которая позволила бы перестраивать графики в реалтайме

        if (this.counter == this.delta) {
            this.counter = 0;

            try {
                this.writer = new FileWriter(statistics_file_name, true);
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }

            try {
                int [] generations_stat = world.get_generations_stat();
                this.writer.write(world.get_herb_lizard_count() + "\t"
                        + world.get_dead_bodies_count() + "\t"
                        + world.get_herbs_count() + "\t"
                        + generations_stat.length / 2 + "\t");
                for (int i = 0; i < generations_stat.length / 2; i++){
                    this.writer.write( "[" + (i + 1) + ": " + generations_stat[i * 2] + ", " + generations_stat[i * 2 + 1] + "] ");
                }
                this.writer.write("\n");
            }
            catch(IOException ex){
                System.out.println(ex.getMessage());
            }

            try {
                writer.close();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
        this.counter++;
    }
}
