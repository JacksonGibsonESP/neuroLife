package com.dsile.core.neural.training;

/**
 * Created by Никита on 14.06.2016.
 */
public class Predator_Lizard_BackPropagation extends MyBackPropagation {
    public Predator_Lizard_BackPropagation()
    {
        super();
        this.setMaxError(0.05);
        this.setMaxIterations(10000);
        this.learningRate = 0.01;
    }
}
