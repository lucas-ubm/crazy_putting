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

public class LoadInputScreen implements Screen {
	final Project2 game;
	private OrthographicCamera camera;
	private Stage stage;
	private Label error;

	public LoadInputScreen(final Project2 game){
		this.game = game;
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);

		stage = new Stage(new ScreenViewport());
		Gdx.input.setInputProcessor(stage);
		final TextField fileField = new TextField("Enter textfile name",game.skin);
		fileField.setSize(Gdx.graphics.getWidth()*3/5,fileField.getPrefHeight());
		fileField.setPosition(Gdx.graphics.getWidth()/2-fileField.getWidth()/2,4*Gdx.graphics.getHeight()/6);
		stage.addActor(fileField);
		final TextField playerField = new TextField("",game.skin);
		playerField.setSize(50,playerField.getPrefHeight());
		Label playerLabel = new Label("Number of players: ",game.skin);
		playerLabel.setPosition(fileField.getX(),fileField.getY()-10-(playerField.getHeight())/2-playerLabel.getPrefHeight()/2);
		stage.addActor(playerLabel);
		playerField.setPosition(playerLabel.getX()+playerLabel.getWidth(),fileField.getY()-playerField.getHeight()-10);
		stage.addActor(playerField);
		error = new Label("", game.skin);
		TextButton cancel = new TextButton("Cancel", game.skin);
		cancel.setPosition(Gdx.graphics.getWidth()/2+10,2*Gdx.graphics.getHeight()/6);
		cancel.addListener(new ClickListener(){
			public void clicked(InputEvent event, float x, float y) {
				game.setScreen(new com.project.putting_game.MenuScreen(game));
				dispose();
			}
		});
		stage.addActor(cancel);
		TextButton ok = new TextButton("OK", game.skin);
		ok.setPosition(Gdx.graphics.getWidth()/2-ok.getWidth()-10,2*Gdx.graphics.getHeight()/6);
		ok.setSize(cancel.getPrefWidth(), cancel.getPrefHeight());
		if(!game.getGameMode()){
			ok.addListener(new ClickListener(){
				public void clicked(InputEvent event, float x, float y) {
					try{
						String file = fileField.getText()+".txt";
						FileReader reader = new FileReader(file);
						Moves Course1 = new Moves(file);
						Vector3[] data = Course1.getData();
						//succeeds then
						error.setText("");
						game.inputfile = file;
						//give default course
						game.setScreen(new com.project.putting_game.Game(game,"demo.txt",1));
						dispose();
					}// else display error and try again
					catch(FileNotFoundException exception){
						error.setText("Input was a non-existing file");
					}
				}
			});
		}else {
			ok.addListener(new ClickListener(){
				public void clicked(InputEvent event, float x, float y) {
					try{//TODO check inputs
						String file = fileField.getText()+".txt";
						FileReader reader = new FileReader(file);
						int players = Integer.parseInt(playerField.getText());
						//succeeds then
						error.setText("");
						game.setScreen(new com.project.putting_game.Game(game,file,players));
						dispose();
					}// else display error and try again
					catch(FileNotFoundException exception){
						error.setText("Input was a non-existing file");
					}
					catch(NumberFormatException e) {
						error.setText("Please check the number of players");
					}
				}
			});
		}
		error.setPosition(fileField.getX(),ok.getY()+ok.getHeight()+error.getPrefHeight());
		stage.addActor(error);
		stage.addActor(ok);
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
		stage.act();
		stage.getBatch().setProjectionMatrix(camera.combined);
		stage.draw();//draw stage (so the elements of the stage)
		game.batch.end();
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height);
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
		stage.dispose();
	}

}
