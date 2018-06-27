package com.project.putting_game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.io.*;

public class LoadInputScreen implements Screen {
	final Project2 game;
	private OrthographicCamera camera;
	private Stage stage;
	private Label error;
	private ImageButton checkBox1;
	private Texture checkImg;
	private Texture uncheckImg;

	/**Constructor of LoadInputScreen. Same as create() if extending ApplicationAdapter.
	 * Instantiating all variables defined above.
	 * @param game game created when 'run' was clicked (parent of all screens)
	 */
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
		playerLabel.setPosition(fileField.getX(),fileField.getY()-10-(playerField.getHeight())/2-playerLabel.getHeight()/2);
		stage.addActor(playerLabel);
		playerField.setPosition(playerLabel.getX()+playerLabel.getWidth(),fileField.getY()-playerField.getHeight()-10);
		stage.addActor(playerField);

		Label botLabel = new Label("Bot", game.skin);
		botLabel.setPosition(playerLabel.getX()+botLabel.getHeight()+10, playerField.getY()-botLabel.getHeight()-10);
		checkImg = new Texture(Gdx.files.internal("checked.png"));
		uncheckImg = new Texture(Gdx.files.internal("unchecked.png"));
		TextureRegionDrawable checkedState = new TextureRegionDrawable(new TextureRegion(checkImg));
		TextureRegionDrawable uncheckedState = new TextureRegionDrawable(new TextureRegion(uncheckImg));
		ImageButton.ImageButtonStyle imageButtonStyle = new ImageButton.ImageButtonStyle();
		imageButtonStyle.imageChecked = checkedState;
		imageButtonStyle.imageUp = uncheckedState;
		checkBox1 = new ImageButton(imageButtonStyle);
		checkBox1.setSize(botLabel.getHeight(), botLabel.getHeight());
		checkBox1.setPosition(botLabel.getX() - checkBox1.getWidth()-10, botLabel.getY());
		ButtonGroup radioButtons = new ButtonGroup();
		radioButtons.add(checkBox1);
		radioButtons.setMinCheckCount(0);
		radioButtons.setMaxCheckCount(1);
		checkBox1.setChecked(false);
		stage.addActor(botLabel);
		stage.addActor(checkBox1);

		error = new Label("", game.skin);

		TextButton cancel = new TextButton("Cancel", game.skin);
		cancel.setPosition(Gdx.graphics.getWidth()/2+10,Gdx.graphics.getHeight()/6);
		cancel.addListener(new ClickListener(){
			public void clicked(InputEvent event, float x, float y) {
				game.setScreen(new com.project.putting_game.MenuScreen(game));
				dispose();
			}
		});
		stage.addActor(cancel);
		TextButton ok = new TextButton("OK", game.skin);
		ok.setPosition(Gdx.graphics.getWidth()/2-ok.getWidth()-10,Gdx.graphics.getHeight()/6);
		ok.setSize(cancel.getPrefWidth(), cancel.getPrefHeight());
		if(!game.getGameMode()){//Mode 2
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
						game.setScreen(new com.project.putting_game.Game(game,"demo.txt",1,true));
						dispose();
					}// else display error and try again
					catch(FileNotFoundException exception){
						error.setText("Input was a non-existing file");
					}
				}
			});
		}else {//Mode 1
			ok.addListener(new ClickListener(){
				public void clicked(InputEvent event, float x, float y) {
					try{
						String file = fileField.getText()+".txt";
						FileReader reader = new FileReader(file);
						int players = Integer.parseInt(playerField.getText());
						//succeeds then
						error.setText("");
						boolean bot;
						if(checkBox1.isChecked())
							bot=true;
						else
							bot=false;
						game.setScreen(new com.project.putting_game.Game(game,file,players,bot));
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

	/** Called many times a second. Draws all textures and elements of the stage, such as buttons and labels, on the screen.
	 * @param delta time elapsed since rendering the last frame
	 */
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

	/** Resize the Screen
	 * @param width new width of screen
	 * @param height new height of screen
	 */
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

	/**Will be called when a button is clicked and we move to another screen, as specified in the listeners.
	 * Deletes elements of the WinScreen
	 */
	@Override
	public void dispose() {
		checkImg.dispose();
		uncheckImg.dispose();
		stage.dispose();
	}
}
