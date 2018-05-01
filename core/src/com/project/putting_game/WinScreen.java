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

public class WinScreen implements Screen {
    final com.project.putting_game.Project2 game;
    OrthographicCamera camera;
    String playButton;
    private Stage stage;
    private TextButton playAgain;
    private Texture golfImg;
    private Rectangle golf;

    /**Constructor of WinScreen. Same as create() if extending ApplicationAdapter.
	 * Instantiating all variables defined above and its components (such as position and size).
	 * @param game - game created when 'run' was clicked (parent of all screens)
	 */
    public WinScreen(final Project2 game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800,480);
        //create a nice picture for the background of the WinScreen
        golfImg = new Texture(Gdx.files.internal("Golf.jpg"));
        golf = new Rectangle(); //create a Rectangle which can contain the picture
        golf.width = Gdx.graphics.getWidth(); //set it to cover the entire width of the screen
        golf.height = Gdx.graphics.getWidth()*721/1280; //but not the entire height, just what is needed to keep the original size
        golf.x = 0;
        golf.y = Gdx.graphics.getHeight() - golf.height;

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        playAgain = new TextButton("Play again!", game.skin);
        playAgain.setPosition(Gdx.graphics.getWidth()/2 - playAgain.getWidth()/2,2*Gdx.graphics.getHeight()/6);
        //When button 'Play again!' is clicked, set the screen to the Game (and close WinScreen)
        playAgain.addListener(new ClickListener(){
            public void clicked(InputEvent event,float x, float y){
                game.setScreen(new com.project.putting_game.Game(game));
                dispose();
            }
        });
        stage.addActor(playAgain);
    }

    /** Called many times a second. Draws all textures and elements of the stage, such as buttons and labels, on the screen.
	 * @param delta -  time elapsed since rendering the last frame
	 */
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0.7f, 0, 0); //set color of screen/background
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        stage.act();
        stage.getBatch().setProjectionMatrix(camera.combined);
        stage.getBatch().begin();
        stage.getBatch().draw(golfImg, golf.x, golf.y, golf.width, golf.height);//draw background picture
        stage.getBatch().end();
        stage.draw();//draw stage (so the elements of the stage)
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
    /**Will be called when a button is clicked and we move to another screen, as specified in the listeners.
	 * Deletes elements of the WinScreen
	 */
    public void dispose(){
        stage.dispose();
    }
}
