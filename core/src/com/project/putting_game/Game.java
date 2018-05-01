package com.project.putting_game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;

public class Game implements Screen {
	private OrthographicCamera camera;
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
        camera.setToOrtho(false, 800, 480);

        //Create bucket Rectangle
        ball = new Ball(new Vector3(80, 80, 0), "golfball.png", 32);
        hole = new Hole(new Vector3(300,300,0), "hole.png", 50);
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
        course = "slope";

        Field field = new Field(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), new Vector3(0, 0, 0), 3, course);
        Pixmap pixmap = new Pixmap((int) Gdx.graphics.getWidth(), (int) Gdx.graphics.getHeight(), Pixmap.Format.RGBA8888);
		for (int y = 0; y < Gdx.graphics.getHeight(); y++) {
        for (int x = 0; x < Gdx.graphics.getWidth(); x++) {
                if(field.matrix[x][y].height >=0) {
                    float value = 0.8f; //flat
                    if(course.equals("sinx+siny")) {
                        value = -1 * map(field.matrix[x][y].height, 2, -2);
                    }
                    else if (course.equals("slope")){
                        value = -1*map(field.matrix[x][y].height, Gdx.graphics.getWidth()+Gdx.graphics.getHeight(),0);
                    }
                    else if(course.equals("parabola")) {
                        value = -1*map(field.matrix[x][y].height, Gdx.graphics.getWidth()*Gdx.graphics.getHeight(),0);
                    }
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
            Moves Course1 = new Moves(game.inputfile);
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
                ball.position = ball.prevPosition;
            }
            if(ball.position.x > 800 - 92) {
                ball.position = ball.prevPosition;

            }
            if(ball.position.y < 60) {
                ball.position = ball.prevPosition;
            }
            if(ball.position.y > 480 - 92) {
                ball.position = ball.prevPosition;
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
        fieldTexture.dispose();
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
	    return (float) (0 - (max-x)/(max-min));

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
