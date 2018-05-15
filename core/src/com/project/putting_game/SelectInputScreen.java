package com.project.putting_game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class SelectInputScreen implements Screen {
	final Project2 game;
	OrthographicCamera camera;
	private Stage stage;
	final private int padding=10;
	private TextButton load;
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
		load = new TextButton("Load course from file", game.skin);
		load.setPosition(Gdx.graphics.getWidth()/2-load.getWidth()/2,7*Gdx.graphics.getHeight()/8);
		load.addListener(new ClickListener(){
			public void clicked(InputEvent event, float x, float y) {
				game.setScreen(new com.project.putting_game.LoadInputScreen(game));
			}
		});
		stage.addActor(load);

		Label courseLabel = new Label("course function:",game.skin);
		courseLabel.setPosition(padding,load.getY()-courseLabel.getHeight()-padding);
		stage.addActor(courseLabel);
		courseField = new TextField(" ",game.skin);
		courseField.setSize(courseField.getPrefWidth(),courseLabel.getHeight());
		courseField.setPosition(courseLabel.getX()+courseLabel.getWidth()+padding,courseLabel.getY());
		stage.addActor(courseField);

		labeltext = "Enter positions as integers where 0<x<"+Gdx.graphics.getWidth()+" and 0<y<"+Gdx.graphics.getHeight();
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
		radiusField = new TextField(" ",game.skin);
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
				//TODO
				try {
					if(Integer.parseInt(startFieldX.getText())<=Gdx.graphics.getWidth() && Integer.parseInt(startFieldY.getText())<=Gdx.graphics.getHeight()&& Integer.parseInt(goalFieldX.getText())<=Gdx.graphics.getWidth()&&Integer.parseInt(goalFieldY.getText())<=Gdx.graphics.getHeight()) {
						//TODO create/edit file with inputs
						//could be made separate course designer and save course, only start with loading
						//PrintWriter writer = new PrintWriter("design.txt", ÜTF-8");
						game.setScreen(new com.project.putting_game.Game(game, "course.txt"));
					}
					else
						error.setText("Please enter a x and y in the range mentioned above");
				} catch(NumberFormatException e) {
					error.setText("Please check your inputs, this input cannot be accepted");
				} catch(NullPointerException e) {
					error.setText("Please check your inputs, this input cannot be accepted");
				}
			}
		});
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
		labeltext = "Enter positions as integers where 0<x<"+Gdx.graphics.getWidth()+"and 0<y<"+Gdx.graphics.getHeight();
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