package com.dsile.core.statistics;

import com.dsile.core.world.World;
import com.sun.xml.internal.bind.v2.runtime.RuntimeUtil;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

/**
 * Created by Никита on 04.06.2016.
 */
public class Statistics{
    private World world;
    private SwingWrapper swing_wrapper;
    private XYChart chart;
    private final String statistics_file_name          = "statistics.txt";
    private FileWriter writer;
    private int counter = 0;
    private int delta;

    //delta - количесвто итераций, после которых производится замер
    public Statistics(World world, int delta){
        this.world = world;
        //this.delta = delta;
        this.delta = 10;
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
            this.writer.write("delta: " + this.delta + '\n' + "Herb lizards count:\t" + "Deaths:\t" + "Herbs:\n"
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
                this.writer.write(world.get_herb_lizard_count() + "\t"
                        + world.get_dead_bodies_count() + "\t"
                        + world.get_herbs_count() + "\n"
                        + Arrays.toString(world.get_generations_stat()) + "\n");
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
