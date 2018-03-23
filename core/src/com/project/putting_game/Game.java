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
	private Rectangle fieldShape;
	private Hole hole;
	private Project2 game;
	boolean condition = true;
	private String course;


	public Game (Project2 game) {
	    //Creation of camera
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);



        //Create bucket Rectangle
        ball = new Ball(new Vector3(0,0,0), new Vector3(80, 80, 0), "golfball.png", 32, 32);
        hole = new Hole(new Vector3(300,300,0), "hole.png", 50, 50);
        //Create field
        fieldShape = new Rectangle();
        fieldShape.x = 60;
        fieldShape.y = 60;
        fieldShape.width = 800 - 120;
        fieldShape.height = 480 - 120;

	}

	public void render (float delta) {
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();

		game.batch.setProjectionMatrix(camera.combined);
		game.batch.begin();
        course = "parabola";
        Field field = new Field(800, 480, new Vector3(0, 0, 0), 3, course);
        Pixmap pixmap = new Pixmap((int) Gdx.graphics.getWidth(), (int) Gdx.graphics.getHeight(), Pixmap.Format.RGBA8888);

		if(course.equals("sinx+siny")) {
            for (int x = 0; x < Gdx.graphics.getWidth(); x++) {
                for (int y = 0; y < Gdx.graphics.getHeight(); y++) {
                    if(field.matrix[y][x].height >=0) {
                        float value = -1*map(Math.sin(x/(400/5.1))+Math.sin(y/(240/5.1)), 2,-2);
                        pixmap.setColor(new Color(0,  value, 0, 1f));// set color White with Alpha=0.5
                    }
                    else{
                        pixmap.setColor(new Color(0,0,0.4f,1f));
                    }
                    pixmap.drawPixel(x, y);
                }
            }
            fieldTexture = new Texture(pixmap);
            pixmap.dispose();
        }
        else if(course.equals("flat")) {
		    for (int x = 0; x < Gdx.graphics.getWidth(); x++) {
                for (int y = 0; y < Gdx.graphics.getHeight(); y++) {
                    pixmap.setColor(new Color(0,  0.8f, 0, 1f));// set color White with Alpha=0.5
                    pixmap.drawPixel(x, y);
                }
            }
            fieldTexture = new Texture(pixmap);
            pixmap.dispose();
        }
        else if(course.equals("slope")) {
            for (int x = 0; x < Gdx.graphics.getWidth(); x++) {
                for (int y = 0; y < Gdx.graphics.getHeight(); y++) {
                    if(field.matrix[y][x].height >=0) {
                        float value = -1*map(x+y, 1280,0);
                        pixmap.setColor(new Color(0,  value, 0, 1f));// set color White with Alpha=0.5
                    }
                    else{
                        pixmap.setColor(new Color(0,0,0.4f,1f));
                    }
                    pixmap.drawPixel(x, y);
                }
            }
            fieldTexture = new Texture(pixmap);
            pixmap.dispose();
        }
        else if(course.equals("parabola")) {
            for (int x = 0; x < Gdx.graphics.getWidth(); x++) {
                for (int y = 0; y < Gdx.graphics.getHeight(); y++) {
                    if(field.matrix[y][x].height >=0) {
                        float value = -1*map(0.1*x+0.03*Math.pow(x, 2)+0.2*y, 19376,0);
                        pixmap.setColor(new Color(0,  value, 0, 1f));// set color White with Alpha=0.5
                    }
                    else{
                        pixmap.setColor(new Color(0,0,0.4f,1f));
                    }
                    pixmap.drawPixel(x, y);
                }
            }
            fieldTexture = new Texture(pixmap);
            pixmap.dispose();
        }

        game.batch.draw(fieldTexture, fieldShape.x, fieldShape.y, fieldShape.width, fieldShape.height);
		game.batch.draw(ball.ballImage, ball.position.x, ball.position.y, ball.shape.width, ball.shape.height);
		game.batch.draw(hole.holeImage, hole.position.x, hole.position.y, hole.holeShape.width, hole.holeShape.height );

		game.batch.end();
        Vector3 origin = new Vector3();
        Vector3 ballPos = new Vector3();


		if(Gdx.input.justTouched() && condition) {
		    boolean condition = true;

            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            origin.set((int)touchPos.x - 64/2, (int)touchPos.y - 64/2, 0);

            ballPos.set((int)ball.position.x, (int)ball.position.y, 0);
            Vector3 direction = new Vector3();

            direction.set((ballPos.x-origin.x), (ballPos.y-origin.y), 0);
            ball.velocity = direction.scl(3f);
            ball.prevPosition = ballPos;

        }



        if(ball.velocity.len() >= 0.02) {
		    condition = false;
            Engine.calculate(ball, field, course);
            if(ball.position.x < 60){
                ball.position.x = 60;
            }
            if(ball.position.x > 800 - 92) {
                ball.position.x = 800 - 92;

            }
            if(ball.position.y < 60) {
                ball.position.y = 60;
            }
            if(ball.position.y > 480 - 92) {
                ball.position.y = 480- 92;
            }

        }
        if(ball.velocity.len() < 200){
		    ball.velocity.x = 0;
		    ball.velocity.y = 0;
		    condition = true;
        }
        if(ball.velocity.len() <= 0.2 && checkRadius())
        {
            game.setScreen(new com.project.putting_game.WinScreen(game));
        }

	}

	public boolean checkRadius()
    {
        boolean result = false;
        if(Math.pow(ball.getCenter().x-hole.getHoleCenter().x, 2) + Math.pow(ball.getCenter().y-hole.getHoleCenter().y, 2) <= Math.pow(hole.radius,2)){
            result = true;
        }
        return result;

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
	    return (float) (0.4 - (max-x)/(max-min));

    }


}
