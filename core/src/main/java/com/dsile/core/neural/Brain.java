package com.dsile.core.neural;

import com.dsile.core.neural.training.MyBackPropagation;
import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;

/**
 * Created by Никита on 15.12.2015.
 */
public abstract class Brain {
    protected NeuralNetwork neuralNetwork;
    protected DataSet trainingSet;
    protected MyBackPropagation rule;

    public void clearTrainingSet(){
        trainingSet.clear();
    }
    public void addRowToTrainingSet(double[] inputVector, double[] outputVector){
        trainingSet.addRow (new DataSetRow(inputVector, outputVector));
    }
    public void learn(){
        System.out.println("Started to learn");
        neuralNetwork.learn(trainingSet);
        System.out.println("Learning finished");
        rule.print_info();
        System.out.println("-------------------------------------------");
    }
    public void setInput(double[] inputVector){
        neuralNetwork.setInput(inputVector);
    }
    public void setInput(int[] inputVector){
        double[] doubleVec = new double[inputVector.length];
        for(int i = 0; i < doubleVec.length; i++){
            doubleVec[i] = inputVector[i];
        }
        neuralNetwork.setInput(doubleVec);
    }
    public void think(){
        neuralNetwork.calculate();
    }
    public double[] getOutput(){
        return neuralNetwork.getOutput();
    }
    public void saveNNToFile(String filePath){
        neuralNetwork.save(filePath);
    }
    public void loadFileToNNT(String filePath){
        this.neuralNetwork = this.neuralNetwork.createFromFile(filePath);
    }
    public NeuralNetwork getNNT(){return neuralNetwork;}
    public void setNNT(NeuralNetwork ann){
        neuralNetwork = ann;
    }
}
