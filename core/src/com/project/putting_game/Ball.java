package com.project.putting_game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class Ball {
    public Vector3 velocity;
    public Vector3 position;
    public Vector3 prevPosition;
    public Texture ballImage;
    public Rectangle shape;
    public Queue<Vector3> moveHistory;


    public Ball(Vector3 velocity, Vector3 position, String path, int width, int height) {
        this.velocity = velocity;
        moveHistory = new Queue<Vector3>();
        moveHistory.enqueue(velocity);
        moveHistory = new Queue<Vector3>();

        this.position = position;
        this.ballImage = new Texture(Gdx.files.internal(path));
        this.prevPosition = null;
        this.shape = new Rectangle();
        shape.x = position.x;
        shape.y = position.y;
        shape.height = height;
        shape.width = width;

    }


    public void setUserVelocity(Vector3 newVelocity) {
        this.velocity = newVelocity;
        moveHistory.enqueue(newVelocity);
        moveHistory.enqueue(newVelocity.cpy());
    }

    }
