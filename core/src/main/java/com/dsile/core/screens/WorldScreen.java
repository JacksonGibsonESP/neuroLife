package com.dsile.core.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.dsile.core.NeuroLife;
import com.dsile.core.entities.*;
import com.dsile.core.world.World;

/**
 * Created by DeSile on 07.12.2015.
 */
public class WorldScreen implements Screen {

    private SpriteBatch batch;
    private World world;
    //private Stage stage;
    private Stage herb_stage;
    private Stage herb_lizard_stage;
    private Stage predator_lizard_stage;
    private MyInputProcessor keysProcessor;
    private OrthographicCamera cam;
    private boolean continuously = false;

    @Override
    public void show() {

        batch = new SpriteBatch();
        //Размер по из клеток горизонтали, по вертикали, размер клетки в пикселях.
        world = new World(30, 20, 32, this);
        //stage = new Stage();
        herb_stage = new Stage();
        herb_lizard_stage = new Stage();
        predator_lizard_stage = new Stage();
        // Constructs a new OrthographicCamera, using the given viewport width and height
        // Height is multiplied by aspect ratio.
        cam = new OrthographicCamera(30, 30 * (NeuroLife.HEIGHT / NeuroLife.WIDTH));

        cam.position.set(cam.viewportWidth / 2f, cam.viewportHeight / 2f, 0);
        cam.update();

        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        keysProcessor = new MyInputProcessor();
        //inputMultiplexer.addProcessor(stage);
        inputMultiplexer.addProcessor(herb_stage);
        inputMultiplexer.addProcessor(herb_lizard_stage);
        inputMultiplexer.addProcessor(predator_lizard_stage);
        inputMultiplexer.addProcessor(keysProcessor);
        Gdx.input.setInputProcessor(inputMultiplexer);

        //stage.getViewport().setCamera(cam);
        herb_stage.getViewport().setCamera(cam);
        herb_lizard_stage.getViewport().setCamera(cam);
        predator_lizard_stage.getViewport().setCamera(cam);

        world.getSpawner().init();

        //world.getEntities().stream().forEach(stage::addActor);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        cam.update();
        batch.setProjectionMatrix(cam.combined);

        batch.begin();
        world.drawMap(batch);
        batch.end();

        //Сортировка актёров в стедже перед рисовкой
        //stage.getActors().sort(new ActorComparator());

        //stage.draw();
        herb_stage.draw();
        herb_lizard_stage.draw();
        predator_lizard_stage.draw();

        if (keysProcessor.isSpaceClicked()) {
            //stage.act(delta);
            herb_lizard_stage.act(delta);
            predator_lizard_stage.act(delta);
            world.getSpawner().act();
            world.getStatistics().act();
        }

        if (keysProcessor.isEnterPressed()) {
            //stage.act(delta);
            herb_lizard_stage.act(delta);
            predator_lizard_stage.act(delta);
            world.getSpawner().act();
            world.getStatistics().act();
        }

        if (keysProcessor.isRClicked()) {
            continuously = !continuously;
            if(continuously){
                System.out.println("Playing");
            }
            else{
                System.out.println("Stopped");
            }
        }

        if (continuously) {
            //stage.act(delta);
            herb_lizard_stage.act(delta);
            predator_lizard_stage.act(delta);
            world.getSpawner().act();
            world.getStatistics().act();
        }

        keysProcessor.moveCameraByKeys(cam);
    }

    @Override
    public void resize(int width, int height) {
        cam.viewportWidth = width;
        cam.viewportHeight = height;
        cam.position.set(width/2f, height/2f, 0); //by default camera position on (0,0,0)
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    public void addActor(Actor actor)
    {
        //stage.addActor(actor);
        if (actor instanceof Herb)
        {
            herb_stage.addActor(actor);
        }
        else if (actor instanceof Herb_Lizard)
        {
            herb_lizard_stage.addActor(actor);
        }
        else
        {
            predator_lizard_stage.addActor(actor);
        }
    }
}
