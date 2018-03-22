package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.scenes.scene2d.InputEvent;

import java.awt.*;


public class MenuScreen implements Screen {
	final Project2 game;
	OrthographicCamera camera;
	Skin skin;
	private Stage stage;
	private TextButton startButton;
	private TextButton controlsButton;
	TextButton.TextButtonStyle textButtonStyle;
	private Texture golfImg;
	private Rectangle golf;
	TextButton.TextButtonStyle radioButtonStyle;
	private TextButton mode1;
	private TextButton mode2;

	public MenuScreen(final Project2 game){ //create()
		this.game = game;
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 480,800);

		golfImg = new Texture(Gdx.files.internal("Golf.jpg"));
		golf = new Rectangle();
		golf.width = Gdx.graphics.getWidth();
		golf.height = Gdx.graphics.getWidth()*721/1280;
		golf.x = 0;
		golf.y = Gdx.graphics.getHeight() - golf.height;

		skin = new Skin();
		skin.add("default", game.font);
		//Create a texture
		Pixmap backgroundButton = new Pixmap((int)Gdx.graphics.getWidth()/2,(int)Gdx.graphics.getHeight()/10,Pixmap.Format.RGB888);//format is enum: how to store color values
		backgroundButton.setColor(Color.WHITE);
		backgroundButton.fill();
		skin.add("background",new Texture(backgroundButton));
		//Create a button style
		textButtonStyle = new TextButton.TextButtonStyle();
		textButtonStyle.up = skin.newDrawable("background", Color.FOREST);
		textButtonStyle.down = skin.newDrawable("background", Color.BLACK);
		textButtonStyle.checked = skin.newDrawable("background", Color.BLACK);
		textButtonStyle.over = skin.newDrawable("background", Color.BROWN); //#TODO special case not working
		textButtonStyle.font = skin.getFont("default");
		skin.add("default", textButtonStyle);
		backgroundButton.dispose();
		stage = new Stage(new ScreenViewport());
		Gdx.input.setInputProcessor(stage);
		startButton = new TextButton("Create Course", skin);
		startButton.setPosition(Gdx.graphics.getWidth()/2 - startButton.getWidth()/2,2*Gdx.graphics.getHeight()/8);
		startButton.addListener(new ClickListener(){
			public void clicked(InputEvent event,float x, float y){
				game.setScreen(new GameScreen(game)); //#TODO take radioButtons input
				dispose();
			}
		});
		stage.addActor(startButton);
		controlsButton = new TextButton("Controls", skin);
		controlsButton.setPosition(Gdx.graphics.getWidth()/2 - controlsButton.getWidth()/2,Gdx.graphics.getHeight()/8);
		controlsButton.addListener(new ClickListener(){
			public void clicked(InputEvent event,float x, float y){
				game.setScreen(new ControlsScreen(game));
				dispose();
			}
		});
		stage.addActor(controlsButton);
		radioButtonStyle = new TextButton.TextButtonStyle();
		radioButtonStyle.font = game.font;
		mode1 = new TextButton("Mode 1", radioButtonStyle);
		mode2 = new TextButton("Mode 2", radioButtonStyle);
		mode1.setPosition(Gdx.graphics.getWidth()/2-mode1.getWidth()/2,3*Gdx.graphics.getHeight()/8 );
		mode2.setPosition(Gdx.graphics.getWidth()/2-mode2.getWidth()/2,mode1.getY()-mode2.getHeight());
		ButtonGroup radioButtons= new ButtonGroup(); //#TODO not working (checking+visible)
		radioButtons.add(mode1);
		radioButtons.add(mode2);
		radioButtons.setMaxCheckCount(1);
		radioButtons.setChecked("mode2");
		stage.addActor(mode1);
		stage.addActor(mode2);

	}
	public void render(float delta){
		Gdx.gl.glClearColor(0,0.7f,0,0);//65/255f, 77/255f,1/255f, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		stage.act();
		stage.getBatch().setProjectionMatrix(camera.combined);
		stage.getBatch().begin();
		stage.getBatch().draw(golfImg, golf.x, golf.y, golf.width, golf.height);
		//mode1Label.draw(stage.getBatch(), );//mode1Label, Gdx.graphics.getWidth()/2-mode1Label.getWidth(), golf.y, golf.width, golf.height);
		//stage.getBatch().draw(mode2Label, golf.x, golf.y, golf.width, golf.height);
		stage.getBatch().end();
		stage.draw();
	}
	@Override
	public void resize(int width, int height) {
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
	public void dispose(){
		golfImg.dispose();
	}
}