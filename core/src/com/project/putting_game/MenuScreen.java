package com.project.putting_game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;


public class MenuScreen implements Screen {
	final com.project.putting_game.Project2 game;
	OrthographicCamera camera;
	Skin skin;
	private Stage stage;
	private TextButton startButton;
	private TextButton controlsButton;
	TextButton.TextButtonStyle textButtonStyle;
	private Texture golfImg;
	private Rectangle golf;
	private ButtonGroup radioButtons;
	private Texture checkImg;
	private Texture uncheckImg;
	Label.LabelStyle labelStyle;
	private Label mode1Label;
	private Label mode2Label;
	ImageButton checkBox1;
	ImageButton checkBox2;
	TextureRegionDrawable checkedState;
	TextureRegionDrawable uncheckedState;

	public MenuScreen(final com.project.putting_game.Project2 game){ //create()
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
		textButtonStyle.over = skin.newDrawable("background", Color.BROWN);
		textButtonStyle.font = skin.getFont("default");
		skin.add("default", textButtonStyle);
		backgroundButton.dispose();
		stage = new Stage(new ScreenViewport());
		Gdx.input.setInputProcessor(stage);
		startButton = new TextButton("Play!", skin);
		startButton.setPosition(Gdx.graphics.getWidth()/2 - startButton.getWidth()/2,2*Gdx.graphics.getHeight()/6);
		startButton.addListener(new ClickListener(){
			public void clicked(InputEvent event,float x, float y){
				game.setScreen(new com.project.putting_game.Game(game));
				dispose();
			}
		});
		stage.addActor(startButton);
		controlsButton = new TextButton("Controls", skin);
		controlsButton.setPosition(Gdx.graphics.getWidth()/2 - controlsButton.getWidth()/2,Gdx.graphics.getHeight()/6);
		controlsButton.addListener(new ClickListener(){
			public void clicked(InputEvent event,float x, float y){
				game.setScreen(new com.project.putting_game.ControlsScreen(game));
				dispose();
			}
		});
		stage.addActor(controlsButton);

		labelStyle = new Label.LabelStyle();
		labelStyle.font = game.font;
		mode1Label = new Label("Mode 1",labelStyle);
		mode1Label.setPosition(Gdx.graphics.getWidth()/2-mode1Label.getWidth()/2,3*Gdx.graphics.getHeight()/6 );
		mode2Label = new Label("Mode 2",labelStyle);
		mode2Label.setPosition(Gdx.graphics.getWidth()/2-mode2Label.getWidth()/2,mode1Label.getY()-mode2Label.getHeight());
		checkImg = new Texture(Gdx.files.internal("checked.png"));
		uncheckImg = new Texture(Gdx.files.internal("unchecked.png"));
		checkedState = new TextureRegionDrawable(new TextureRegion(checkImg));
		uncheckedState = new TextureRegionDrawable(new TextureRegion(uncheckImg));
		ImageButton.ImageButtonStyle imageButtonStyle = new ImageButton.ImageButtonStyle();
		imageButtonStyle.imageChecked = checkedState;
		imageButtonStyle.imageUp = uncheckedState;
		checkBox1 = new ImageButton(imageButtonStyle);
		checkBox2 = new ImageButton(imageButtonStyle);
		checkBox1.setSize(mode1Label.getHeight(),mode1Label.getHeight());
		checkBox2.setSize(mode2Label.getHeight(),mode2Label.getHeight());
		checkBox1.setPosition(mode1Label.getX()-checkBox1.getWidth(), mode1Label.getY());
		checkBox2.setPosition(mode2Label.getX()-checkBox2.getWidth(), mode2Label.getY());
		radioButtons= new ButtonGroup();
		radioButtons.add(checkBox1);
		radioButtons.add(checkBox2);
		radioButtons.setMaxCheckCount(1);
		checkBox1.setChecked(true);
		stage.addActor(mode1Label);
		stage.addActor(mode2Label);
		stage.addActor(checkBox1);
		stage.addActor(checkBox2);
	}
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0.7f, 0, 0);//65/255f, 77/255f,1/255f, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		stage.act();

		stage.getBatch().setProjectionMatrix(camera.combined);
		stage.getBatch().begin();
		stage.getBatch().draw(golfImg, golf.x, golf.y, golf.width, golf.height);
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
		checkImg.dispose();
		uncheckImg.dispose();
		stage.dispose();
	}
}