package com.project.putting_game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class ControlsScreen implements Screen {
	final Project2 game;
	OrthographicCamera camera;
	String mode1Descr;
	String mode2Descr;
	String descriptionText;
	private Stage stage;
	Label mode1;
	Label mode2;
	Label description;
	TextButton backButton;
	/**Constructor of ControlsScreen. Same as create() if extending ApplicationAdapter. 
	 * Instantiating all variables defined above and its components (such as position and size).
	 * @param game - game created when 'run' was clicked (parent of all screens)
	 */
	public ControlsScreen(final Project2 game){
		this.game = game;
		camera = new OrthographicCamera();
		camera.setToOrtho(false,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		//create stage
		stage = new Stage(new ScreenViewport());
		//create labels for displaying descriptions of controls/modes and general description
		descriptionText = "Try to get the ball into the hole. Watch out for slopes and obstacles! Slopes will decrease the speed of the ball and/or change its direction. When you hit an obstacle you have to start over. (OR #TODO: EDIT)"+"\n";
		description = new Label(descriptionText, game.skin);
		description.setPosition(0, 3*Gdx.graphics.getHeight()/4);
		description.setSize(Gdx.graphics.getWidth(), description.getPrefHeight());
		description.setWrap(true);//make text fit on screen by automatic newlines
		stage.addActor(description);//add label to stage
		mode1Descr = "Mode 1: Click behind the ball on the opposite side as you want the ball to go. The further you click from the ball, the harder you hit the ball and the further the ball will move."+"\n";
		mode1 = new Label(mode1Descr,game.skin);
		mode1.setPosition(0, description.getY()-description.getPrefHeight()-mode1.getHeight());
		mode1.setSize(Gdx.graphics.getWidth(), mode1.getPrefHeight());
		mode1.setWrap(true);//make text fit on screen by automatic newlines
		stage.addActor(mode1);//add label to stage
		mode2Descr = "Mode 2: All shots (speed and direction) will be loaded from a file."+"\n";
		mode2 = new Label(mode2Descr, game.skin);
		mode2.setPosition(0, mode1.getY()-mode1.getHeight()-mode2.getHeight());
		mode2.setSize(Gdx.graphics.getWidth(), mode2.getPrefHeight());
		mode2.setWrap(true);//make text fit on screen by automatic newlines
		stage.addActor(mode2);//add label to stage

		Gdx.input.setInputProcessor(stage);//let game take input from the stage
		//Create 'Back' button with listener
		backButton = new TextButton("Back",game.skin);
		backButton.setPosition(Gdx.graphics.getWidth()/2-backButton.getWidth()/2, mode2.getY()-backButton.getHeight());
		backButton.addListener(new ClickListener(){
			public void clicked(InputEvent event, float x, float y){
				game.setScreen(new MenuScreen(game));
				dispose();
			}
		});
		stage.addActor(backButton);
	}
	/** Called many times a second. Draws all elements of the stage and background color on the screen.
	 * @param delta -  time elapsed since rendering the last frame
	 */
	public void render(float delta) {
		Gdx.gl.glClearColor(0,0.7f,0,0);//set color of screen/background
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		game.batch.begin();
		stage.draw(); //draw stage (so the elements of the stage/add stage to screen)
		game.batch.end();
	}
	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height);
	}
	@Override
	public void show(){
	}
	@Override
	public void hide(){
	}
	@Override
	public void pause() {
	}
	@Override
	public void resume() {
	}
	@Override
	/**Will be called when a button is clicked and we move to another screen, as specified in the listener. 
	 */
	public void dispose(){
	}
}
