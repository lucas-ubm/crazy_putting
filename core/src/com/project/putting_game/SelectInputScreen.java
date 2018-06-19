package com.project.putting_game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
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
import java.util.ArrayList;
import java.util.StringTokenizer;

public class SelectInputScreen implements Screen {
	final Project2 game;
	OrthographicCamera camera;
	private Stage stage;
	final private int padding=10;
	private TextField fileField;
	private TextField courseField;
	private Label label;
	private String labeltext;
	private TextField startFieldX;
	private TextField startFieldY;
	private TextField goalFieldX;
	private TextField goalFieldY;
	private TextField radiusField;
	private TextButton ok;
	private Label error;
	private TextButton cancel;

	public SelectInputScreen(final Project2 game){
		this.game = game;
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);

		stage = new Stage(new ScreenViewport());
		Gdx.input.setInputProcessor(stage);

		Label fileLabel = new Label("filename:",game.skin);
		fileLabel.setPosition(padding, 7*Gdx.graphics.getHeight()/8);
		stage.addActor(fileLabel);
		fileField = new TextField("",game.skin);
		fileField.setSize(fileField.getPrefWidth(),fileLabel.getHeight());
		fileField.setPosition(fileLabel.getX()+fileLabel.getWidth()+padding,fileLabel.getY());
		stage.addActor(fileField);

		Label courseLabel = new Label("course function:",game.skin);
		courseLabel.setPosition(fileLabel.getX(), fileLabel.getY()-courseLabel.getHeight()-padding);
		stage.addActor(courseLabel);
		courseField = new TextField("",game.skin);
		courseField.setSize(courseField.getPrefWidth(),courseLabel.getHeight());
		courseField.setPosition(courseLabel.getX()+courseLabel.getWidth()+padding,courseLabel.getY());
		stage.addActor(courseField);

		labeltext = "Enter positions as integers where "+game.borderLength+"<x<"+(Gdx.graphics.getWidth()-game.borderLength)+" and "+game.borderLength+"<y<"+(Gdx.graphics.getHeight()-game.borderLength);
		label = new Label(labeltext,game.skin);
		label.setPosition(courseLabel.getX(),courseLabel.getY()-label.getHeight()-padding);
		stage.addActor(label);

		Label startLabel = new Label("start position:",game.skin);
		startLabel.setPosition(label.getX(),label.getY()-startLabel.getHeight()-padding);
		stage.addActor(startLabel);
		startFieldX = new TextField("x",game.skin);
		startFieldX.setSize(startFieldX.getPrefWidth(),startLabel.getHeight());
		startFieldX.setPosition(startLabel.getX()+startLabel.getWidth()+padding,startLabel.getY());
		stage.addActor(startFieldX);
		startFieldY = new TextField("y",game.skin);
		startFieldY.setSize(startFieldY.getPrefWidth(),startLabel.getHeight());
		startFieldY.setPosition(startFieldX.getX()+startFieldX.getWidth()+padding,startLabel.getY());
		stage.addActor(startFieldY);

		Label goalLabel = new Label("goal position:",game.skin);
		goalLabel.setPosition(label.getX(),startLabel.getY()-goalLabel.getHeight()-padding);
		stage.addActor(goalLabel);
		goalFieldX = new TextField("x",game.skin);
		goalFieldX.setSize(goalFieldX.getPrefWidth(),goalLabel.getHeight());
		goalFieldX.setPosition(goalLabel.getX()+goalLabel.getWidth()+padding,goalLabel.getY());
		stage.addActor(goalFieldX);
		goalFieldY = new TextField("y",game.skin);
		goalFieldY.setSize(goalFieldY.getPrefWidth(),goalLabel.getHeight());
		goalFieldY.setPosition(goalFieldX.getX()+goalFieldX.getWidth()+padding,goalLabel.getY());
		stage.addActor(goalFieldY);

		Label radiusLabel = new Label("goal radius:",game.skin);
		radiusLabel.setPosition(label.getX(),goalLabel.getY()-radiusLabel.getHeight()-padding);
		stage.addActor(radiusLabel);
		radiusField = new TextField("",game.skin);
		radiusField.setSize(radiusField.getPrefWidth(),radiusLabel.getHeight());
		radiusField.setPosition(radiusLabel.getX()+radiusLabel.getWidth()+padding,radiusLabel.getY());
		stage.addActor(radiusField);

		cancel = new TextButton("Cancel", game.skin);
		cancel.setPosition(Gdx.graphics.getWidth()/2+10,2*Gdx.graphics.getHeight()/6);
		cancel.addListener(new ClickListener(){
			public void clicked(InputEvent event, float x, float y) {
				game.setScreen(new com.project.putting_game.MenuScreen(game));
				dispose();
			}
		});
		stage.addActor(cancel);
		ok = new TextButton("OK", game.skin);
		ok.setPosition(Gdx.graphics.getWidth()/2-ok.getWidth()-10,2*Gdx.graphics.getHeight()/6);
		ok.setSize(cancel.getPrefWidth(), cancel.getPrefHeight());
		ok.addListener(new ClickListener(){
			public void clicked(InputEvent event, float x, float y) {
				try {
					if(fileField.getText().equals("")||courseField.getText().equals("")||startFieldX.getText().equals("")||startFieldY.getText().equals("")||goalFieldX.getText().equals("")||goalFieldY.getText().equals("")||radiusField.getText().equals(""))
						throw new NullPointerException();
					courseField.setText(courseField.getText().replaceAll(" ",""));
					if(Integer.parseInt(startFieldX.getText())<=Gdx.graphics.getWidth()&&game.borderLength<=Integer.parseInt(startFieldX.getText())&&Integer.parseInt(startFieldY.getText())<=Gdx.graphics.getHeight()&&game.borderLength<=Integer.parseInt(startFieldY.getText())&&Integer.parseInt(goalFieldX.getText())<=Gdx.graphics.getWidth()&&game.borderLength<=Integer.parseInt(goalFieldX.getText())&&Integer.parseInt(goalFieldY.getText())<=Gdx.graphics.getHeight()&&game.borderLength<=Integer.parseInt(goalFieldY.getText())&&Integer.parseInt(radiusField.getText())>0) {
						String file = fileField.getText()+".txt";
						FileHandle writer = Gdx.files.local(file);
						//save file with inputs
						writer.writeString(courseField.getText(), false);
						writer.writeString("\n"+startFieldX.getText(), true);
						writer.writeString(" "+startFieldY.getText(), true);
						writer.writeString("\n"+goalFieldX.getText(), true);
						writer.writeString(" "+goalFieldY.getText(), true);
						writer.writeString("\n"+radiusField.getText(), true);
						System.out.println("got here");
						game.setScreen(new com.project.putting_game.MenuScreen(game));
						dispose();
					}
					else
						error.setText("Please enter a x and y in the range mentioned above and a radius>0");
				} catch(NumberFormatException e) {
					error.setText("Please check your inputs, this input cannot be accepted");
					System.out.println(e);
				} catch(NullPointerException e) {
					error.setText("Please check your inputs, this input cannot be accepted");
					System.out.println(e);
				}
			}});
		stage.addActor(ok);
		error = new Label("", game.skin);
		error.setPosition(ok.getX(),ok.getY()-error.getHeight()-padding);
		stage.addActor(error);
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
		labeltext = "Enter positions as integers where "+game.borderLength+"<x<"+(Gdx.graphics.getWidth()-game.borderLength)+" and "+game.borderLength+"<y<"+(Gdx.graphics.getHeight()-game.borderLength);
		label.setText(labeltext);
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