package com.dsile.core.neural;

import org.neuroph.nnet.learning.BackPropagation;

/**
 * Created by Никита on 27.04.2016.
 */
public class MyBackPropagation extends BackPropagation{
    MyBackPropagation()
    {
        super();
        this.setMaxError(0.001);
        this.setMaxIterations(1000);
    }

    public void print_info()
    {
        System.out.printf("Last Epoch Error: %f\n", this.previousEpochError);
        System.out.printf("Epochs passed: %d\n", this.currentIteration);
    }
}
