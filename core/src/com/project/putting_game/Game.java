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
	private Rectangle ball;
	private Rectangle field;
	
	@Override
	public void create () {
	    //Creation of camera
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

		batch = new SpriteBatch();

		//load images of droplet and bucket
        ballImage = new Texture(Gdx.files.internal("golfball.png"));
        fieldTexture = new Texture(Gdx.files.internal("green.png"));

        //Create bucket Rectangle
        ball = new Rectangle();
        ball.x = 90 + 64;
        ball.y = 64;
        ball.width = 64;
        ball.height = 64;

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
		batch.draw(ballImage, ball.x, ball.y, 64, 64);

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
            ballPos.set((int)ball.x, (int)ball.y, 0);

            Vector3 destination = new Vector3();
            Vector3 direction = new Vector3();

            direction.set((ballPos.x-origin.x), (ballPos.y-origin.y), 0);
            destination.set(ballPos.add(direction));

            int velocity = direction.len();
            direction.nor();

            while(Math.abs(ball.x - destination.x) > 1 || Math.abs(ball.y - destination.y) > 1) {
                System.out.println("Direction is " + direction + " ball was in" + ballPos + " clicked in " +origin+ " ball is in " + ball.x + " " + ball.y +" destination is " +destination);

                if(ball.x < destination.x) {
                    ball.x += Math.abs(direction.x) * Gdx.graphics.getDeltaTime();
                }
                if(ball.x > destination.x) {
                    ball.x -= Math.abs(direction.x) * Gdx.graphics.getDeltaTime();
                }

                if(ball.y < destination.y) {
                    ball.y += Math.abs(direction.y) * Gdx.graphics.getDeltaTime();
                }
                if(ball.y > destination.y){
                    ball.y -= Math.abs(direction.y) * Gdx.graphics.getDeltaTime();
                }
            }
        }

//		//Makes clicking or touching to move bucket possible
//		if(Gdx.input.isTouched()) {
//            Vector3 touchPos = new Vector3();
//            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
//            camera.unproject(touchPos);
//            ball.x = touchPos.x-64/2;
//            ball.y = touchPos.y-20;
//        }
//
//        //Makes using clicks to move bucket possible
//        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) ball.x -= 400 * Gdx.graphics.getDeltaTime();
//        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) ball.x += 400 * Gdx.graphics.getDeltaTime();
//        if(Gdx.input.isKeyPressed(Input.Keys.UP)) ball.y += 400 * Gdx.graphics.getDeltaTime();
//        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) ball.y -= 400 * Gdx.graphics.getDeltaTime();
//
        //Makes sure the bucket doesn't get out of the window
        if(ball.x < 60) ball.x = 60;
        if(ball.x > 800 - 124) ball.x = 800 - 124;
        if(ball.y < 60) ball.y = 60;
        if(ball.y > 480 - 124) ball.y = 480-124;


	}
	
	@Override
	public void dispose () {
        ballImage.dispose();
        fieldTexture.dispose();
		batch.dispose();
	}

}
