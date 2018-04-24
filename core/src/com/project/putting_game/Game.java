package com.project.putting_game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Set;

public class Game implements Screen {
	private SpriteBatch batch;
	private OrthographicCamera camera;
	private Texture ballImage;
	private Texture fieldTexture;
	private Ball ball;
	public int i =0;
	private Rectangle fieldShape;
	private Hole hole;
	private Project2 game;
	private boolean condition = true;
	private String course;
	private boolean gameMode1;


	public Game (Project2 game) {
	    //Creation of camera
        this.game = game;
        this.gameMode1 = game.gameMode1;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Settings.windowWidth, Settings.windowHeight);



        //Create bucket Rectangle
        ball = new Ball(Settings.ballPosition, "golfball.png", Settings.ballSide);
        hole = new Hole(Settings.holePosition, "hole.png", Settings.holeSide);
        //Create field
        fieldShape = new Rectangle();
        fieldShape.x = Settings.borderLength;
        fieldShape.y = Settings.borderLength;
        fieldShape.width = Settings.windowWidth - 2*Settings.borderLength;
        fieldShape.height = Settings.windowHeight - 2*Settings.borderLength;

	}

	public void render (float delta) {
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();

		game.batch.setProjectionMatrix(camera.combined);
		game.batch.begin();
        course = "sinx+siny";
        Field field = new Field((int)fieldShape.width, (int)fieldShape.height, 3, course);

        Pixmap pixmap = new Pixmap((int)fieldShape.width, (int)fieldShape.height, Pixmap.Format.RGBA8888);

		if(course.equals("sinx+siny")) {
            for (int x = 0; x < (int)fieldShape.width; x++) {
                for (int y = 0; y < (int)fieldShape.height; y++) {
                    if(field.matrix[y][x].height < 0) {
                        pixmap.setColor(new Color(0,0,0.4f,1f));
                    }
                    else{
                        float value = -1*map(field.matrix[y][x].height, 2,-2);
                        pixmap.setColor(new Color(value,  0, 0, 1f));// set color White with Alpha=0.5
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
                        float value = -1*map(x*y, 384000,0);
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


		if(Gdx.input.justTouched() && condition && gameMode1) {
		    boolean condition = true;

            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            origin.set((int)touchPos.x - 64/2, (int)touchPos.y - 64/2, 0);

            ballPos.set((int)ball.position.x, (int)ball.position.y, 0);
            Vector3 direction = new Vector3();

            direction.set((ballPos.x-origin.x), (ballPos.y-origin.y), 0);
            ball.setUserVelocity(direction.scl(3f));
            ball.prevPosition = ballPos;

        }

        if(!gameMode1 && condition)
        {

            Moves Course1 = new Moves("Input.txt");
            Vector3[] data = Course1.getData();
            if(data[i] != null && i < data.length)
            {
                ball.setUserVelocity(data[i]);
                ballPos.set((int)ball.position.x, (int)ball.position.y, 0);
                ball.prevPosition = ballPos;
                i++;
            }
            else{
                System.out.println("No velocities left");
                outputGame(ball);
                System.exit(0);
            }
        }

        if(ball.velocity.len() >= 0.02) {
		    condition = false;
            //Makes sure the bucket doesn't get out of the window
            Engine.calculate(ball, field);
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
            outputGame(ball);
            game.setScreen(new com.project.putting_game.WinScreen(game));

        }

	}

	public boolean checkRadius()
    {
        boolean result = false;
        if(Math.pow((ball.position.x+ball.shape.width/2-(hole.position.x+hole.holeShape.width/2)), 2) + Math.pow((ball.position.y+ball.shape.height/2-(hole.position.y+hole.holeShape.height/2)), 2) <= Math.pow(hole.holeShape.height/2-ball.shape.width/2,2)){
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

    public static float map (double height, double max, double min) {
	    return (float) (0 - (max-height)/(max-min));

    }

    public void setGameMode(boolean gameMode1)
    {
        this.gameMode1 = gameMode1;
    }

    public static void outputGame(Ball ball) {
        String move;
        ArrayList<String> lines = new ArrayList<String>();
        while(!ball.moveHistory.isEmpty()) {
            move = ball.moveHistory.dequeue().toString();
            System.out.println(move);
            lines.add(move);

        }
        try {
            lines.add("");
            Path file = Paths.get("Games.txt");
            Files.write(file, lines, Charset.forName("UTF-8"), StandardOpenOption.APPEND);
        }
        catch(IOException e) {
            System.out.println("You fucked up");
        }

    }


}
