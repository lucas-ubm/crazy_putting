package com.project.putting_game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.*;

public class Project2 extends Game {
	public SpriteBatch batch;
	public BitmapFont font;
	private boolean gameMode1;
	public String inputfile = "Input.txt";
	public Skin uiskin;
	public Skin skin;
	public TextButton.TextButtonStyle textButtonStyle;
	public Label.LabelStyle labelStyle;
	public TextField.TextFieldStyle textFieldStyle;
	final public int borderLength = 60;

	@Override
	/** Gets called when 'run' is clicked.
	 */
	public void create () {
		batch = new SpriteBatch();
		font = new BitmapFont();
		font.getData().setScale(1.2f);
		uiskin = new Skin(new TextureAtlas(Gdx.files.internal("uiskin.atlas")));
		//Create a custom skin for the buttons and labels
		skin = new Skin();
		skin.add("default", font);
		//Create a texture
		Pixmap backgroundButton = new Pixmap((int) Gdx.graphics.getWidth()/2,(int)Gdx.graphics.getHeight()/10,Pixmap.Format.RGB888);//format is enum: how to store color values
		backgroundButton.setColor(Color.WHITE);
		backgroundButton.fill();
		skin.add("white", new Texture(backgroundButton));
		skin.add("background",new Texture(backgroundButton));
		//Create a button style
		textButtonStyle = new TextButton.TextButtonStyle();
		textButtonStyle.up = skin.newDrawable("background", Color.FOREST); //standard color of the button (no special action)
		textButtonStyle.down = skin.newDrawable("background", Color.BLACK); //let the button turn black if you press your mouse when standing on the button
		textButtonStyle.checked = skin.newDrawable("background", Color.BLACK);
		textButtonStyle.over = skin.newDrawable("background", Color.BROWN); //let the button turn brown when the mouse is standing on the button
		textButtonStyle.font = skin.getFont("default");
		skin.add("default", textButtonStyle);
		backgroundButton.dispose();//don't need it anymore, only taking memory
		//create label style
		labelStyle = new Label.LabelStyle();
		labelStyle.font = skin.getFont("default");
		skin.add("default", labelStyle);
		this.setScreen(new MenuScreen(this));
		//create textfield style
		textFieldStyle = new TextField.TextFieldStyle();
		textFieldStyle.font = font;
		textFieldStyle.fontColor = Color.BLUE;
		textFieldStyle.background = skin.newDrawable("white", Color.LIGHT_GRAY);
		textFieldStyle.cursor = uiskin.getDrawable("cursor");
		textFieldStyle.selection = uiskin.getDrawable("selection");
		skin.add("default", textFieldStyle);
	}

	@Override
	/** Called many times a second. Keeps screen up to date. 
	 */
	public void render () {
		super.render();
	}
	
	@Override
	/** Called when user clicks on close window. Disposes everything created in this class.
	 */
	public void dispose () {
		batch.dispose();
		font.dispose();
		//Game.dispose();
	}

	public void setGameMode(boolean gameMode) {
		this.gameMode1 = gameMode;
	}
	public boolean getGameMode() {
		return gameMode1;
	}
}
