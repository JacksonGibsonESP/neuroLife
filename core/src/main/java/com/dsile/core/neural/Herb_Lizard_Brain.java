package com.dsile.core.neural;

import com.dsile.core.neural.training.Herb_Lizard_BackPropagation;
import com.dsile.core.neural.training.MyBackPropagation;
import org.neuroph.core.Neuron;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.transfer.Linear;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.util.NeuronProperties;
import org.neuroph.util.TransferFunctionType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Никита on 14.06.2016.
 */
public class Herb_Lizard_Brain extends Brain {

    public Herb_Lizard_Brain(){
        List<Integer> neuronsInLayers = new ArrayList<>();
        neuronsInLayers.add(28);
        neuronsInLayers.add(80);
        neuronsInLayers.add(40);
        neuronsInLayers.add(8);
        this.neuralNetwork = new MultiLayerPerceptron(neuronsInLayers, new NeuronProperties(TransferFunctionType.SIGMOID, true));

        rule = new Herb_Lizard_BackPropagation();
        neuralNetwork.setLearningRule(rule);

        Neuron[] OutputLayerNeurons = neuralNetwork.getOutputNeurons();
        for(int i = 0; i < OutputLayerNeurons.length; i++) {
            OutputLayerNeurons[i].setTransferFunction(new Linear());
        }

        trainingSet = new DataSet(28, 8);
    }

}
