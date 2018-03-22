package com.project.putting_game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Project2 extends Game {
	public SpriteBatch batch;
	public BitmapFont font;
	
	@Override
	/** Gets called when 'run' is clicked.
	 */
	public void create () {
		batch = new SpriteBatch();
		font = new BitmapFont();
		font.getData().setScale(1.2f);
		this.setScreen(new MenuScreen(this));
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
		//GameScreen.dispose();
	}
}
