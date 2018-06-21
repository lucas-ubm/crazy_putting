package com.project.putting_game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

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
	private ArrayList<Ball> balls;
	private Ball ball;
	private Hole hole;
	private Field field;
	public int i = 0;
	private Rectangle fieldShape;
	private ArrayList<Hole> holes;
	final Project2 game;
	final private String file;
	private boolean condition = true;
	private String course;
	private boolean gameMode1;
	public ArrayList<String> fieldFormula;
	private int players;
	private boolean design;
	private Stage stage;
	private Pixmap pixmap;

	public Game (Project2 game, String file) {
	    //Creation of camera
        this.players = 3;
        this.game = game;
        this.gameMode1 = game.getGameMode();
        this.file = file;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
		design = true;
		Settings fieldVariables = textToSettings();

        if(!fieldVariables.courseFunction.equalsIgnoreCase("spline"))
            this.fieldFormula = FunctionAnalyser.ShuntingYard(fieldVariables.courseFunction);

        this.balls = new ArrayList<Ball>();
        this.holes = new ArrayList<Hole>();

        for(int i = 0; i < this.players; i++){
            balls.add(new Ball(fieldVariables.startPosition.scl((float)(1+0.3*i)).cpy(), "golfball.png", 32));
            holes.add(new Hole(fieldVariables.goalPosition.scl((float)(1-0.1*i)).cpy(), "hole.png", fieldVariables.goalRadius));
        }
        this.ball = balls.get(0);
        this.hole = holes.get(0);


		this.course = fieldVariables.courseFunction;
		//Create field

        fieldShape = new Rectangle();
				fieldShape.x = 0;//game.borderLength;
				fieldShape.y = 0;//game.borderLength;
				fieldShape.width = Gdx.graphics.getWidth();// - game.borderLength*2;
				fieldShape.height = Gdx.graphics.getHeight();// - game.borderLength*2;
		field = new Field(course.replaceAll(" ",""));
		pixmap = new Pixmap((int) Gdx.graphics.getWidth(), (int) Gdx.graphics.getHeight(), Pixmap.Format.RGBA8888);
		for (int y = 0; y < Gdx.graphics.getHeight(); y++) {
			for (int x = 0; x < Gdx.graphics.getWidth(); x++) {
				if(field.getMatrix()[y][x].height >=0) {
					float  value = map(field.getMatrix()[y][x].height, field.getMax(),field.getMin());
					pixmap.setColor(new Color(0,  value, 0, 1f));// set color White with Alpha=0.5
				}
				else{
					pixmap.setColor(new Color(0,0,0.4f,1f));
				}
				pixmap.drawPixel(x, y);
			}
		}

		stage = new Stage(new ScreenViewport());
		Label text = new Label("Drag the mouse over the screen to create rivers, when you are done press ENTER",game.skin);
		text.setPosition(0,Gdx.graphics.getHeight()-text.getPrefHeight());
		stage.addActor(text);
		Gdx.input.setInputProcessor(new InputAdapter(){
			@Override
			public boolean touchDown (int x, int y, int pointer, int button) {
				//System.out.println("Touched:"+" "+x+" "+(Gdx.graphics.getHeight()-y));
				if (design) {
					for (int i = y - 5; i <= y + 5; i++) //for all points within radius of 5
						for (int j = x - 5; j <= x + 5; j++){
							pixmap.setColor(Color.BLUE);
							pixmap.drawPixel(j, i);
							field.getMatrix()[i][j].height=-1;//set equal to water so ball reacts same way
						}
					fieldTexture = new Texture(pixmap);
				}
				return true; // return true to indicate the event was handled
			}
			@Override
			public boolean touchDragged (int x, int y, int pointer) {
				if (design) {
					//System.out.println(x+" "+y+" "+(field.getMatrix().length-1-y));
					for (int i = y - 5; i <= y + 5; i++) //for all points within radius of 5
						for (int j = x - 5; j <= x + 5; j++){
							pixmap.setColor(Color.BLUE);
							pixmap.drawPixel(j, i);
							field.getMatrix()[i][j].height=-1;//set equal to water so ball reacts same way
						}
					fieldTexture = new Texture(pixmap);
				}
				return true; // return true to indicate the event was handled
			}
			@Override
			public boolean keyDown(int keycode){
				if(keycode==Input.Keys.ENTER) {
					if(design)
						stage.dispose();
					design = false;
				}
				if(keycode==Input.Keys.ESCAPE) {
					//game.setScreen(new Game(game,file));
				}
				return true;
			}
		});

		fieldTexture = new Texture(pixmap);
	}

	public void render (float delta) {
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		game.batch.setProjectionMatrix(camera.combined);
		game.batch.begin();
        game.batch.draw(fieldTexture, fieldShape.x, fieldShape.y, fieldShape.width, fieldShape.height);


        for(Ball b: balls) {
            game.batch.draw(b.ballImage, b.position.x, b.position.y, b.shape.width, b.shape.height);
        }
        for(Hole h: holes){
            game.batch.draw(h.holeImage, h.position.x, h.position.y, h.holeShape.width, h.holeShape.height);
        }
		game.batch.end();
		if(stage!=null){
			stage.act();
			stage.getBatch().setProjectionMatrix(camera.combined);
			stage.draw();//draw stage (so the elements of the stage)
		}

        play();


	}

	public boolean checkRadius(Ball ball, Hole hole)
    {
        boolean result = false;
        if(Math.pow((ball.position.x+ball.shape.width/2-(hole.position.x+hole.holeShape.width/2)), 2) + Math.pow((ball.position.y+ball.shape.height/2-(hole.position.y+hole.holeShape.height/2)), 2) <= Math.pow(hole.holeShape.height/2-ball.shape.width/2,2)){
            result = true;
        }
        return result;
    }

    public boolean play(){
	    int balln = ball.id;
        Vector3 origin = new Vector3();
        Vector3 ballPos = new Vector3();
        if(Gdx.input.justTouched() && condition && gameMode1) {
            ball = balls.get(nextBall(ball));
            hole = holes.get(nextBall(ball));
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            origin.set((int)touchPos.x - ball.shape.width/2, (int)touchPos.y - ball.shape.height/2, 0);

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
            ball = balls.get(0);
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
                game.setScreen(new WinScreen(game));
                //System.exit(0);
            }
        }

        if(ball.velocity.len() >= 0.02) {
            Engine.calculate(ball, field, fieldFormula);
        }

        condition = ball.velocity.len() == 0;


        if(ball.velocity.len() <= 0.02 && checkRadius(ball, hole))
        {
            outputGame(ball);
            game.setScreen(new com.project.putting_game.WinScreen(game));
        }
        return condition;
    }

    public int nextBall(Ball ball){
        int id = 0;
	    if(ball.velocity.len() == 0 && Gdx.input.isTouched()){
	        if(ball.id < balls.size() - 1){
	            id = ball.id + 1;
            }
            return id;
        }
        else{
	        return ball.id;
        }

    }

	@Override
	public void dispose () {
        fieldTexture.dispose();
	}

    @Override
    public void resize(int width, int height) {stage.getViewport().update(width, height);
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
        return (float) ((x)/(max-min));

    }

    public void setGameMode(boolean gameMode1)
    {
        this.gameMode1 = gameMode1;
    }
	public Settings textToSettings() {
		try{
			FileReader reader = new FileReader(Gdx.files.local(file).file());
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
					Integer.parseInt(fileString.get(4)),0), Integer.parseInt(fileString.get(5)));
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
            System.out.println("You messed up");
        }
    }
}
