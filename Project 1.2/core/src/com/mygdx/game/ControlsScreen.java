package com.mygdx.game;

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
	Label.LabelStyle labelStyle;
	Label mode1;
	Label mode2;
	Label description;
	TextButton.TextButtonStyle style;
	TextButton backButton;
	Skin skin;

	public ControlsScreen(final Project2 game){
		this.game = game;
		camera = new OrthographicCamera();
		camera.setToOrtho(false,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

		labelStyle = new Label.LabelStyle();
		labelStyle.font = game.font;
		stage = new Stage(new ScreenViewport());

		descriptionText = "Try to get the ball into the hole. Watch out for slopes and obstacles! Slopes will decrease the speed of the ball and/or change its direction. When you hit an obstacle you have to start over. (OR #TODO: EDIT)"+"\n";
		description = new Label(descriptionText, labelStyle);
		description.setPosition(0, 3*Gdx.graphics.getHeight()/4);
		description.setSize(Gdx.graphics.getWidth(), description.getPrefHeight());
		description.setWrap(true);
		stage.addActor(description);
		mode1Descr = "Mode 1: Click on the ball and drag your mouse away from the ball in the opposite direction as you want the ball to go. The further you drag, the harder you hit the ball and the further the ball will move."+"\n";
		mode1 = new Label(mode1Descr, labelStyle);
		mode1.setPosition(0, description.getY()-description.getPrefHeight()-mode1.getHeight());
		mode1.setSize(Gdx.graphics.getWidth(), mode1.getPrefHeight());
		mode1.setWrap(true);
		stage.addActor(mode1);
		mode2Descr = "Mode 2: All data will be loaded from a file."+"\n";
		mode2 = new Label(mode2Descr, labelStyle);
		mode2.setPosition(0, mode1.getY()-mode1.getHeight()-mode2.getHeight());
		mode2.setSize(Gdx.graphics.getWidth(), mode2.getPrefHeight());
		mode2.setWrap(true);
		stage.addActor(mode2);

		Gdx.input.setInputProcessor(stage);
		style = new TextButton.TextButtonStyle();
		skin = new Skin();
		skin.add("default", game.font);
		//Create a texture
		String backLabel = "Back";
		Pixmap backgroundButton = new Pixmap((int)Gdx.graphics.getWidth()/2,(int)Gdx.graphics.getHeight()/10,Pixmap.Format.RGB888);//format is enum: how to store color values
		backgroundButton.setColor(Color.WHITE);
		backgroundButton.fill();
		//Create a button style
		skin.add("background",new Texture(backgroundButton));
		style.over = skin.newDrawable("background", Color.BROWN);
		style.down = skin.newDrawable("background", Color.BLACK);
		style.checked = skin.newDrawable("background", Color.BLACK);
		style.up = skin.newDrawable("background", Color.FOREST);
		style.font = skin.getFont("default");
		skin.add("default", style);
		backgroundButton.dispose();
		backButton = new TextButton(backLabel,skin);
		backButton.setPosition(Gdx.graphics.getWidth()/2 - backButton.getWidth()/2,2*Gdx.graphics.getHeight()/6);
		backButton.addListener(new ClickListener(){
			public void clicked(InputEvent event, float x, float y){
				game.setScreen(new MenuScreen(game));
				dispose();
			}
		});
		stage.addActor(backButton);
	}
	public void render(float delta) {
		Gdx.gl.glClearColor(0,0.7f,0,0);//65/255f, 77/255f,1/255f, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		game.batch.begin();
		stage.draw();
		game.batch.end();
	}
	@Override
	public void resize(int width, int height) {
	}
	@Override
	public void show(){
		//play music
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
	public void dispose(){
	}
}
