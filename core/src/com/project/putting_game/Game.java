package com.project.putting_game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class Game implements Screen {
	private SpriteBatch batch;
	private OrthographicCamera camera;
	private Texture ballImage;
	private Texture fieldTexture;
	private Ball ball;
	private Hole hole;
	private Rectangle field;
	private Project2 game;

	public Game (Project2 game) {
	    //Creation of camera
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);



        //Create bucket Rectangle
        ball = new Ball(new Vector3(0,0,0), new Vector3(80, 80, 0), "golfball.png", 32, 32);
        hole = new Hole(new Vector3(750,430,0), "hole.png", 50, 50);
        //Create field
        field = new Rectangle();
        field.x = 0;
        field.y = 0;
        field.width = 800;
        field.height = 480;

	}

	public void render (float delta) {
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();

		game.batch.setProjectionMatrix(camera.combined);
		game.batch.begin();
        Pixmap pixmap = new Pixmap((int) Gdx.graphics.getWidth(), (int) Gdx.graphics.getHeight(), Pixmap.Format.RGBA8888);
        for (int x = 0; x < Gdx.graphics.getWidth(); x++) {
            for (int y = 0; y < Gdx.graphics.getHeight(); y++) {

                pixmap.setColor(new Color(0f, 0.5f , 0f, 1f));// set color White with Alpha=0.5
                pixmap.drawPixel(x, y);
            }
        }
        fieldTexture = new Texture(pixmap);
        pixmap.dispose();
        game.batch.draw(fieldTexture, field.x, field.y, field.width, field.height);
		game.batch.draw(ball.ballImage, ball.position.x, ball.position.y, ball.shape.width, ball.shape.height);
		game.batch.draw(hole.holeImage, hole.position.x, hole.position.y, hole.holeShape.height, hole.holeShape.width );

		game.batch.end();

        Vector3 origin = new Vector3();
        Vector3 ballPos = new Vector3();

        Field field = new Field(800, 480, new Vector3(0, 0, 0), 3);

		if(Gdx.input.isTouched() ) {
		    
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            origin.set((int)touchPos.x - 64/2, (int)touchPos.y - 64/2, 0);


            ballPos.set((int)ball.position.x, (int)ball.position.y, 0);
            Vector3 direction = new Vector3();

            direction.set((ballPos.x-origin.x), (ballPos.y-origin.y), 0);
            ball.velocity = direction.scl(0.2f);


        }
        boolean condition = true;
        while(ball.velocity.len() >= 0.02 && condition) {
            //Makes sure the bucket doesn't get out of the window
            Engine.calculate(ball, field);
            if(ball.position.x < 0){
                ball.position.x = 60;
//                ball.velocity.x = 0;
//                ball.velocity.y = 0;
                condition = false;
            }

            if(ball.position.x > 800 - 32) {
                ball.position.x = 800 - 32;
//                ball.velocity.x = 0;
//                ball.velocity.y = 0;
                condition = false;
            }
            if(ball.position.y < 0) {
                ball.position.y = 0;
//                ball.velocity.x = 0;
//                ball.velocity.y = 0;
                condition = false;
            }
            if(ball.position.y > 480 - 32) {
                ball.position.y = 480- 32;
//                ball.velocity.x = 0;
//                ball.velocity.y = 0;
                condition = false;
            }


        }
	}
	
	@Override
	public void dispose () {
        ballImage.dispose();
        fieldTexture.dispose();
		batch.dispose();
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

    public static float map (double x, double max, double min) {
        return (float) (0.1 - (max-x)/(max-min));

    }


}
