package com.project.putting_game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import static com.badlogic.gdx.math.MathUtils.sin;

public class GameScreen implements Screen {
	final com.project.putting_game.Project2 game;
	Texture ballImg;
	Texture holeImg;
	Rectangle ball;
	Rectangle hole;
	OrthographicCamera camera;
	Texture course;
	TextButton backButton;
	Stage stage;
	TextButton.TextButtonStyle style;
	Skin skin;

	public GameScreen (final com.project.putting_game.Project2 game) {
		this.game = game;
		ballImg = new Texture(Gdx.files.internal("golfball.png"));
		ball = new Rectangle();
		ball.width = 64;
		ball.height = 64;
		ball.x = Gdx.graphics.getWidth()/2 - ball.width/2;
		ball.y = Gdx.graphics.getHeight()/6 - ball.height/2;
		holeImg = new Texture(Gdx.files.internal("end.png"));
		hole = new Rectangle();
		hole.width = 96;
		hole.height = 160;
		hole.x = Gdx.graphics.getWidth()/2 - hole.width/2;
		hole.y = Gdx.graphics.getHeight() - hole.height;;
		camera = new OrthographicCamera();
		camera.setToOrtho(false,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		Pixmap pixmap = new Pixmap((int) Gdx.graphics.getWidth(), (int) Gdx.graphics.getHeight(), Pixmap.Format.RGBA8888);
		for (int x = 0; x < Gdx.graphics.getWidth(); x++) {
			for (int y = 0; y < Gdx.graphics.getHeight(); y++) {
				pixmap.setColor(new Color(0, sin(x+y),0,1));// set color White with Alpha=0.5
				pixmap.drawPixel(x, y);
			}
		}
		course = new Texture(pixmap);
		//It's the textures responsibility now... get rid of the pixmap
		pixmap.dispose();
		stage = new Stage(new ScreenViewport());
		Gdx.input.setInputProcessor(stage);
		style = new TextButton.TextButtonStyle();
		skin = new Skin();
		skin.add("default", game.font);
		//Create a texture
		String backLabel = "Back";
		Pixmap backgroundButton = new Pixmap(backLabel.length()*4,10,Pixmap.Format.RGB888);//format is enum: how to store color values#TODO size
		backgroundButton.setColor(Color.WHITE);
		backgroundButton.fill();
		skin.add("background",new Texture(backgroundButton));
		style.over = skin.newDrawable("background", Color.BROWN);
		style.down = skin.newDrawable("background", Color.BLACK);
		style.checked = skin.newDrawable("background", Color.BLACK);
		style.font = skin.getFont("default");
		skin.add("default", style);
		backgroundButton.dispose();
		backButton = new TextButton(backLabel,skin);
		backButton.setPosition(20,Gdx.graphics.getHeight()-40-backButton.getHeight());
		backButton.addListener(new ClickListener(){
			public void clicked(InputEvent event, float x, float y){
				game.setScreen(new MenuScreen(game));
				dispose();
			}
		});
		stage.addActor(backButton);
	}

	@Override
	public void render(float delta) {
		//Gdx.gl.glClearColor(1, 1,1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		stage.act();
		game.batch.setProjectionMatrix(camera.combined);
		game.batch.begin();
		game.batch.draw(course, 0,0);//,Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		game.batch.draw(ballImg, ball.x, ball.y, ball.width, ball.height);
		game.batch.draw(holeImg, hole.x, hole.y, hole.width, hole.height);
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
	public void dispose() {
		ballImg.dispose();
		holeImg.dispose();
		course.dispose();
	}
}
