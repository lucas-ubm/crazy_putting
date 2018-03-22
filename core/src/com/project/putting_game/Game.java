package com.project.putting_game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class Game extends ApplicationAdapter {
	private SpriteBatch batch;
	private OrthographicCamera camera;
	private Texture ballImage;
	private Texture fieldTexture;
	private Ball ball;
	private Rectangle field;
	
	@Override
	public void create () {
	    //Creation of camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

		batch = new SpriteBatch();

		//load images of droplet and bucket
        fieldTexture = new Texture(Gdx.files.internal("green.png"));

        //Create bucket Rectangle
        ball = new Ball(null, new Vector3(80, 80, 0), "golfball.png", 32, 32);

        //Create field
        field = new Rectangle();
        field.x = 60;
        field.y = 60;
        field.width = 800 - 60*2;
        field.height = 480 - 60*2;



	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();

		batch.setProjectionMatrix(camera.combined);
		batch.begin();
        batch.draw(fieldTexture, field.x, field.y, field.width, field.height);
		batch.draw(ball.ballImage, ball.position.x, ball.position.y, ball.shape.width, ball.shape.height);

		batch.end();

        Vector3 origin = new Vector3();
        Vector3 ballPos = new Vector3();
        boolean condition = false;
		if(Gdx.input.isTouched()) {
		    
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            origin.set((int)touchPos.x - 64/2, (int)touchPos.y - 64/2, 0);

            condition = true;
            ballPos.set((int)ball.position.x, (int)ball.position.y, 0);

            Vector3 destination = new Vector3();
            Vector3 direction = new Vector3();

            direction.set((ballPos.x-origin.x), (ballPos.y-origin.y), 0);
            ball.velocity = direction;

            destination.set(ballPos.add(direction));
        }

//
        //Makes sure the bucket doesn't get out of the window
        if(ball.position.x < 60) ball.position.x = 60;
        if(ball.position.x > 800 - 124) ball.position.x = 800 - 124;
        if(ball.position.y < 60) ball.position.y = 60;
        if(ball.position.y > 480 - 124) ball.position.y = 480-124;


	}
	
	@Override
	public void dispose () {
        ballImage.dispose();
        fieldTexture.dispose();
		batch.dispose();
	}

}
