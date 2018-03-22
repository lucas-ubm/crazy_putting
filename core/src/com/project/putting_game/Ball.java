package com.project.putting_game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class Ball {
    public Vector3 velocity;
    public Vector3 position;
    public Vector3 prevPosition;
    private Texture ballImage;
    private Rectangle ball;


    public Ball(Vector3 velocity, Vector3 position, Texture ballImage, int width, int height) {
        this.velocity = velocity;
        this.position = position;
        this.ballImage = ballImage;
        this.prevPosition = null;
        this.ball = new Rectangle();
        ball.x = position.x;
        ball.y = position.y;
        ball.height = height;
        ball.width = width;


    }

}
