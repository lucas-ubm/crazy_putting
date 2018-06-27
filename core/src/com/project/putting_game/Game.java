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
    private int maxDistance;
    private Rectangle fieldShape;
    private ArrayList<Hole> holes;
    private final Project2 game;
    final private String file;
    private boolean condition = true;
    private String course;
    private boolean gameMode1;
    public ArrayList<String> fieldFormula;
    private int players;
    private boolean design;
    private Stage stage;
    private Pixmap pixmap;
    private Stage mpStage;
    private Label.LabelStyle ballStyle;
    private static boolean startriver = false;
    private static int s;
    private Label scoreLabel;
//    private Play botPlay;
    private int score;
    private boolean bot;

    public Game (Project2 game, String file,int players) {
        //Creation of camera
        this.bot = true;
        this.players = players;
        this.maxDistance =150;
        this.game = game;
        this.gameMode1 = game.getGameMode();
        this.file = file;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        design = true;
        Settings fieldVariables = textToSettings();

        if(!fieldVariables.courseFunction.equalsIgnoreCase("spline"))
            this.fieldFormula = FunctionAnalyser.ShuntingYard(fieldVariables.courseFunction);
        else{
            this.fieldFormula = new ArrayList<String>();
            this.fieldFormula.add("spline");
        }

        this.balls = new ArrayList<Ball>();
        this.holes = new ArrayList<Hole>();

        for(int i = 0; i < this.players; i++){
            balls.add(new Ball(players,fieldVariables.startPosition.cpy(), "golfball.png", 24));
            holes.add(new Hole(players,fieldVariables.goalPosition.scl((float)(1-0.1*i)).cpy(), "hole.png", fieldVariables.goalRadius));
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
        ballStyle = new Label.LabelStyle();
        ballStyle.font = game.skin.getFont("default");
        Label turn = new Label("turn",ballStyle);
        turn.setPosition(10,Gdx.graphics.getHeight()-turn.getPrefHeight()-10);
        mpStage = new Stage(new ScreenViewport());
        mpStage.addActor(turn);

        scoreLabel = new Label("shots taken: 0",game.skin);
        scoreLabel.setPosition(Gdx.graphics.getWidth()-scoreLabel.getWidth()-10,Gdx.graphics.getHeight()-scoreLabel.getPrefHeight()-10);
        mpStage.addActor(scoreLabel);

        stage = new Stage(new ScreenViewport());
        Label text = new Label("Drag the mouse to create rivers, click for boulders. When you are done press ENTER",game.skin);
        text.setPosition(100,Gdx.graphics.getHeight()-text.getPrefHeight() - 20);
        stage.addActor(text);

        Gdx.input.setInputProcessor(new InputAdapter(){

            @Override
            public boolean touchDown (int x, int y, int pointer, int button) {
                startriver = true;
                return true; // return true to indicate the event was handled
            }

            @Override
            public boolean touchUp(int x, int y, int pointer, int button) {
                if (design && startriver) {
                    for (int i = y - 15; i <= y + 15; i++)
                        for (int j = x - 15; j <= x + 15; j++){
                            if (x >= 16 && x <= 800-16 && y >= 16 && y <= 480-16) {
                                pixmap.setColor(Color.LIGHT_GRAY);
                                pixmap.drawPixel(j, i);
                                field.getMatrix()[i][j].height = -1;
                            }
                            else
                                s =0;
                        }
                    fieldTexture = new Texture(pixmap);
                }
                startriver = false;
                return true;
            }


            @Override
            public boolean touchDragged (int x, int y, int pointer) throws ArrayIndexOutOfBoundsException {
                if (design && !startriver ) {
                    for (int i = y - 6; i <= y + 6; i++) //for all points within radius of 5
                        for (int j = x - 6; j <= x + 6; j++){
                            if (x >= 16 && x <= 800-16 && y >= 16 && y <= 480-16) {
                                pixmap.setColor(Color.BLUE);
                                pixmap.drawCircle(j, i, 9 );
                                field.getMatrix()[i][j].height = -1;
                            }
                            else
                                s =0;
                        }
                    fieldTexture = new Texture(pixmap);
                }
                startriver = false;
                return true; // return true to indicate the event was handled
            }


            @Override
            public boolean keyDown(int keycode) throws ArrayIndexOutOfBoundsException {

                if(keycode==Input.Keys.ENTER) {
                    if(design)
                        stage.dispose();
                    design = false;
                }
                if(keycode==Input.Keys.ESCAPE) {
                    design =false;
                    stage.dispose();
                }
                if(keycode ==Input.Keys.BACKSPACE) {
                    //First row vertical, etc.
                    if(design) {

                        pixmap.setColor(Color.BLUE);

                        pixmap.drawLine(80, 0, 80, 80);
                            for(int j =0; j<80; j++) {
                                field.getMatrix()[j][80].height = -1;
                            }
                        pixmap.drawLine(160, 0, 160, 80);
                            for(int j =0; j<80; j++) {
                                field.getMatrix()[j][160].height = -1;
                            }
                        pixmap.drawLine(640, 0, 640, 80);
                            for(int j =0; j<80; j++) {
                                field.getMatrix()[j][640].height = -1;
                            }


                        pixmap.drawLine(160, 80, 160, 400);
                            for(int j =80; j<400; j++) {
                                field.getMatrix()[j][160].height = -1;
                            }

                        pixmap.drawLine(400, 80, 400, 160);
                            for(int j =80; j<160; j++) {
                                field.getMatrix()[j][400].height = -1;
                            }
                        pixmap.drawLine(720, 80, 720, 160);
                            for(int j =80; j<160; j++) {
                                field.getMatrix()[j][720].height = -1;
                            }



                        pixmap.drawLine(320, 160, 320, 400);
                            for(int j =160; j<400; j++) {
                                field.getMatrix()[j][320].height = -1;
                            }
                        pixmap.drawLine(400,160, 400, 240);
                            for(int j =160; j<240; j++) {
                                field.getMatrix()[j][400].height = -1;
                            }
                        pixmap.drawLine(480,160, 480, 240);
                            for(int j =160; j<240; j++) {
                                field.getMatrix()[j][480].height = -1;
                            }
                        pixmap.drawLine(560,160,560, 240);
                            for(int j =160; j<240; j++) {
                                field.getMatrix()[j][560].height = -1;
                            }


                        pixmap.drawLine(80, 240, 80, 320);
                            for(int j =240; j<320; j++) {
                                field.getMatrix()[j][80].height = -1;
                            }
                        pixmap.drawLine(640,240,640,320);
                            for(int j =240; j<320; j++) {
                                field.getMatrix()[j][640].height = -1;
                            }

                        pixmap.drawLine(240,320, 240, 400);
                            for(int j =320; j<400; j++) {
                                field.getMatrix()[j][240].height = -1;
                            }
                        pixmap.drawLine(480,240, 480,480);
                            for(int j =240; j<480; j++) {
                                field.getMatrix()[j][480].height = -1;
                            }
                        pixmap.drawLine(720,240, 720, 400);
                            for(int j =240; j<400; j++) {
                                field.getMatrix()[j][720].height = -1;
                            }

                        pixmap.drawLine(400,400, 400, 480);
                            for(int j =400; j<480; j++) {
                                field.getMatrix()[j][400].height = -1;
                            }

                        //First row first horizontal, etc
                        pixmap.drawLine(160,80,240, 80);
                            for(int j =160; j<240; j++) {
                                field.getMatrix()[80][j].height = -1;
                            }
                        pixmap.drawLine(320,80,640, 80);
                            for(int j =320; j<640; j++) {
                                field.getMatrix()[80][j].height = -1;
                            }



                        pixmap.drawLine(80,160, 160, 160);
                            for(int j =80; j<160; j++) {
                                field.getMatrix()[160][j].height = -1;
                            }
                        pixmap.drawLine(240,160, 320,160);
                            for(int j =240; j<320; j++) {
                                field.getMatrix()[160][j].height = -1;
                            }
                        pixmap.drawLine(560,160, 640,160);
                            for(int j =560; j<640; j++) {
                                field.getMatrix()[160][j].height = -1;
                            }
                        pixmap.drawLine(720,160, 800,160);
                            for(int j =720; j<800; j++) {
                                field.getMatrix()[160][j].height = -1;
                            }



                        pixmap.drawLine(160,240, 240,240);
                            for(int j =160; j<240; j++) {
                                field.getMatrix()[240][j].height = -1;
                            }
                        pixmap.drawLine(320,240, 560,240);
                            for(int j =320; j<560; j++) {
                                field.getMatrix()[240][j].height = -1;
                            }
                        pixmap.drawLine(640,240,720,240);
                            for(int j =640; j<720; j++) {
                                field.getMatrix()[240][j].height = -1;
                            }



                        pixmap.drawLine(0,320, 80,320);
                            for(int j =0; j<80; j++) {
                                field.getMatrix()[320][j].height = -1;
                            }
                        pixmap.drawLine(400,320, 560,320);
                            for(int j =400; j<560; j++) {
                                field.getMatrix()[320][j].height = -1;
                            }



                        pixmap.drawLine(80,400, 240,400);
                            for(int j =80; j<240; j++) {
                                field.getMatrix()[400][j].height = -1;
                            }
                        pixmap.drawLine(560,400,720,400);
                            for(int j =560; j<720; j++) {
                                field.getMatrix()[400][j].height = -1;
                            }

                        fieldTexture = new Texture(pixmap);

                    }

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
            b.ballImage.setPosition(b.position.x-b.shape.height/2, b.position.y-b.shape.height/2);
            if(!b.arrived) {
                b.ballImage.draw(game.batch);
            }
        }
        for(Hole h: holes){
            h.holeImage.draw(game.batch);
        }
        game.batch.end();
        if(design){
            stage.act();
            stage.getBatch().setProjectionMatrix(camera.combined);
            stage.draw();//draw stage (so the elements of the stage)
        }
        else{
            float value = ((float) nextBallColor(ball)+1)/((float)players);
            ballStyle.fontColor=new Color(value, (float)0.2, 1-value, 1f);
            scoreLabel.setText("shots taken: "+score);
            mpStage.act();
            mpStage.getBatch().setProjectionMatrix(camera.combined);
            mpStage.draw();//draw stage (so the elements of the stage)
        }
        play();
    }

    public boolean checkRadius(Ball ball, Hole hole) {
        return hole.holeShape.contains(ball.position.x, ball.position.y);
    }

//    public boolean checkRadius(Ball ball, Hole hole) {
//        boolean result = false;
//        if(Math.pow((ball.position.x+ball.shape.width/2-(hole.position.x+hole.holeShape.width/2)), 2) + Math.pow((ball.position.y+ball.shape.height/2-(hole.position.y+hole.holeShape.height/2)), 2) <= Math.pow(hole.holeShape.height/2-ball.shape.width/2,2)){
//            result = true;
//        }
//        return result;
//    }

    public void play(){
        Vector3 origin = new Vector3();
        Vector3 ballPos = new Vector3();
        ball = balls.get(nextBall(ball, condition));
        hole = holes.get(nextBall(ball, condition));
        if(!bot&&Gdx.input.justTouched() && condition && gameMode1  && !design && !ball.arrived) {
            score();
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);
            origin.set((int)touchPos.x - ball.shape.width/2, (int)touchPos.y - ball.shape.height/2, 0);

            ballPos.set((int)ball.position.x, (int)ball.position.y, 0);
            Vector3 direction = new Vector3();

            direction.set((ballPos.x-origin.x), (ballPos.y-origin.y), 0);
            ball.setUserVelocity(direction.scl(6f));
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
                game.setScreen(new WinScreen(game, score));
                //System.exit(0);
            }

        }

        if(bot && Gdx.input.justTouched() && condition && gameMode1  && !design && !ball.arrived) {
            int p = 0;
            int m = 0;
            Vector3 direction =  new Vector3(0,0,0);
//            while(botPlay==null) {
//                System.out.println("here for the "+p+m+" time.");
//                GeneticBot bot = new GeneticBot(field, ball, hole, 150+50*p, 3+m);
//                this.botPlay = bot.startProcess();
//
//                if(m < p || p>=3){
//                    m++;
//                }
//                else{
//                    p++;
//                }
//
//                if(botPlay != null){
//                    System.out.println("Bot score is "+botPlay.getScore());
//                    botPlay.print();
//                }
//
//            }
            if(direction.len() == 0){

//                GeneticBot bot = new GeneticBot(field, ball, hole, 50, 3);
//                BinaryBot bot = new BinaryBot(field, ball, hole, field.getFormula());
                RandomBot bot = new RandomBot(field, ball, hole, field.getFormula());
                long start = System.currentTimeMillis();
//                this.botPlay = bot.startProcess();
                direction = bot.startProcess();
                long end = System.currentTimeMillis();
                System.out.println(end-start);
                ball.moveHistory = new Queue<Vector3>();
                System.out.println(direction);

//                game.setScreen(new WinScreen(game, score));

            }
            if (direction.len() != 0){
//                ball.setUserVelocity(botPlay.moves.get(i).getDirection());
                ball.setUserVelocity(direction);
                ball.prevPosition = ball.position.cpy();
                i++;
            }
            else{
                System.out.println("No more shots for the bot");
            }


        }

        Engine.calculate(ball, field, fieldFormula);
        if(ball.velocity.len() == 0 &&ball==balls.get(0)&&!checkDistance()){
            ball.position = ball.prevPosition;
            ball.velocity.scl(0);
        }

        condition = ball.velocity.len() == 0;

        if(checkFinished()) {
            outputGame(ball);
            game.setScreen(new com.project.putting_game.WinScreen(game, score));
        }


        if(ball.velocity.len() == 0 && checkRadius(ball, hole)) {
            System.out.println("This ball is in his hole");
            ball.arrived = true;
        }
    }

    public int nextBall(Ball ball,boolean condition){
        int id = 0;
        if(ball.velocity.len() == 0 && Gdx.input.isTouched()&&condition){
            if(ball.getId() < balls.size() - 1){
                id = ball.getId() + 1;
            }
            return id;
        }
        else{
            return ball.getId();
        }
    }

    public boolean checkDistance(){
        for (Ball b : balls) {
            if (!distanceBalls(b)) {
                return false;
            }
        }
        return true;
    }

    public int nextBallColor(Ball ball){
        int id = 0;
        if(ball.velocity.len() == 0){
            if(ball.getId() < balls.size() - 1){
                id = ball.getId() + 1;
            }
            return id;
        }
        else{
            return ball.getId();
        }
    }


    public boolean distanceBalls(Ball ball) {
        Vector3 origin = ball.position.cpy();
        for(Ball b: balls){

            if(Math.abs(origin.dst(b.position)) > maxDistance){
                System.out.println(Math.abs(origin.dst(b.position)));
                return false;
            }
        }
        return true;
    }

    public void score(){
        int maxScore = 0;
        for(Ball b: balls){
            if(b.moveHistory.getSize() > maxScore) {
                maxScore = b.moveHistory.getSize();
            }
        }
        score = maxScore;
    }

    public boolean checkFinished(){
        for(Ball b: balls){
            if(!b.arrived) {
                return false;
            }
        }
        return true;
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