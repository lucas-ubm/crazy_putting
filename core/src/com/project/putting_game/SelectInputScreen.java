package com.project.putting_game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.graphics.Color;

import java.io.*;

public class SelectInputScreen implements Screen {
	final Project2 game;
	OrthographicCamera camera;
	private Stage popStage;
	private TextField textField;
	private TextButton ok;
	private Label error;
	private TextButton cancel;

	public SelectInputScreen(final Project2 game){
		this.game = game;
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);

		popStage = new Stage(new ScreenViewport());
		Gdx.input.setInputProcessor(popStage);
		textField = new TextField("Enter textfile name here",game.skin);
		textField.setSize(Gdx.graphics.getWidth()*3/5,textField.getPrefHeight());
		textField.setPosition(Gdx.graphics.getWidth()/2-textField.getWidth()/2,4*Gdx.graphics.getHeight()/6);
		popStage.addActor(textField);
		error = new Label("", game.skin);
		error.setPosition(textField.getX(),textField.getY()-textField.getHeight());
		popStage.addActor(error);
		cancel = new TextButton("Cancel", game.skin);
		cancel.setPosition(Gdx.graphics.getWidth()/2+10,2*Gdx.graphics.getHeight()/6);
		cancel.addListener(new ClickListener(){
			public void clicked(InputEvent event, float x, float y) {
				game.setScreen(new com.project.putting_game.MenuScreen(game));
				dispose();
			}
		});
		popStage.addActor(cancel);
		ok = new TextButton("OK", game.skin);
		ok.setPosition(Gdx.graphics.getWidth()/2-ok.getWidth()-10,2*Gdx.graphics.getHeight()/6);
		ok.setSize(cancel.getPrefWidth(), cancel.getPrefHeight());
		ok.addListener(new ClickListener(){
			public void clicked(InputEvent event, float x, float y) {
				try{
					String file = textField.getText()+".txt";
					FileReader reader = new FileReader(file);
					Moves Course1 = new Moves(file);
					Vector3[] data = Course1.getData();
					//succeeds then
					error.setText("");
					game.inputfile = file;
					game.setScreen(new com.project.putting_game.Game(game));
					dispose();
				}// else display error and try again
				catch(FileNotFoundException exception){
					error.setText("Input was a non-existing file");
				}
			}
		});
		popStage.addActor(ok);
	}

	@Override
	public void show() {
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0.7f, 0, 0); //set color of screen/background
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		game.batch.begin();
		popStage.act();
		popStage.getBatch().setProjectionMatrix(camera.combined);
		popStage.draw();//draw stage (so the elements of the stage)
		game.batch.end();
	}

	@Override
	public void resize(int width, int height) {
		popStage.getViewport().update(width, height);
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
		popStage.dispose();
	}

}