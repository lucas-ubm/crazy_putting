package com.project.putting_game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Game implements Screen {
	private OrthographicCamera camera;
	private Texture fieldTexture;
	private Ball ball;
	private Field field;
	public int i =0;
	private Rectangle fieldShape;
	private Hole hole;
	final Project2 game;
	final private String file;
	private boolean condition = true;
	private ArrayList<String> fieldFormula;
	private String course;
	private boolean gameMode1;

	public Game (Project2 game, String file) {
	    //Creation of camera
        this.game = game;
        this.gameMode1 = game.getGameMode();
        this.file = file;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

		Settings fieldVariables = textToSettings();
		this.fieldFormula = FunctionAnalyser.ShuntingYard(fieldVariables.courseFunction);
		//Create bucket Rectangle
		ball = new Ball(fieldVariables.startPosition, "golfball.png", 32);
		hole = new Hole(fieldVariables.goalPosition, "hole.png", fieldVariables.goalRadius);
		this.course = fieldVariables.courseFunction;
		//Create field
        fieldShape = new Rectangle();
        fieldShape.x = fieldVariables.borderLength;
        fieldShape.y = fieldVariables.borderLength;
        fieldShape.width = Gdx.graphics.getWidth() - fieldVariables.borderLength*2;
        fieldShape.height = Gdx.graphics.getHeight() - fieldVariables.borderLength*2;
		field = new Field(course);
		Pixmap pixmap = new Pixmap((int) Gdx.graphics.getWidth(), (int) Gdx.graphics.getHeight(), Pixmap.Format.RGBA8888);
		for (int y = 0; y < Gdx.graphics.getHeight(); y++) {
			for (int x = 0; x < Gdx.graphics.getWidth(); x++) {
				if(field.getMatrix()[y][x].height >=0) {
					float  value = -1*map(field.getMatrix()[y][x].height, field.getMax());
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

	public void render (float delta) {
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		game.batch.setProjectionMatrix(camera.combined);
		game.batch.begin();

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
            Engine.calculate(ball, field, fieldFormula);
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

    public static float map (double x, double max) {

	    return (float) (0 - (x)/(max));

    }

    public void setGameMode(boolean gameMode1)
    {
        this.gameMode1 = gameMode1;
    }
	public Settings textToSettings() {
		try{
			FileReader reader = new FileReader(file);
			BufferedReader in = new BufferedReader(reader);
			String line = in.readLine();
			ArrayList<String> fileString = new ArrayList<String>();
			int i = 0;
			while (line!=null){
				StringTokenizer st = new StringTokenizer(line);

				while (st.hasMoreTokens() && i!=0)
					fileString.add(st.nextToken());
				if(i == 0) fileString.add(line);
				line = in.readLine();
				i++;
			}
			in.close();
			reader.close();
			System.out.println(fileString);
			Settings result = new Settings(fileString.get(0),new Vector3(Integer.parseInt(fileString.get(1)),
					Integer.parseInt(fileString.get(2)),0), new Vector3(Integer.parseInt(fileString.get(3)),
					Integer.parseInt(fileString.get(4)),0), Integer.parseInt(fileString.get(5)),
                    Integer.parseInt(fileString.get(6)));
			return result;
		}
		catch(IOException i){
			System.out.println("File mistake, probably");
		}
		return null;
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
