package com.dsile.core;

import com.badlogic.gdx.Game;
import com.dsile.core.screens.WorldScreen;
import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;

public class NeuroLife extends Game {

    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;

    @Override
    public void create() {
        //Logger root = (Logger)LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
        //root.setLevel(Level.DEBUG);
        setScreen(new WorldScreen());
    }

}
