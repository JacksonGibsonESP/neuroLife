package com.dsile.core.neural.training;

import org.neuroph.nnet.learning.BackPropagation;

/**
 * Created by Никита on 27.04.2016.
 */
public abstract class MyBackPropagation extends BackPropagation{

    public void print_info()
    {
        System.out.printf("Last Epoch Error: %f\n", this.previousEpochError);
        System.out.printf("Epochs passed: %d\n", this.currentIteration);
    }
}
