package com.project.putting_game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class Ball {
    public Vector3 velocity;
    public Vector3 position;
    public Vector3 prevPosition;
    public Vector3 ballCenter;
    public Texture ballImage;
    public Rectangle shape;
    public Queue<Vector3> moveHistory;


    public Ball(Vector3 velocity, Vector3 position, String path, int width, int height) {
        this.velocity = velocity;
        moveHistory = new Queue<Vector3>();
        moveHistory.enqueue(velocity);

        this.position = position;
        this.ballImage = new Texture(Gdx.files.internal(path));
        this.prevPosition = null;
        this.shape = new Rectangle();
        shape.x = position.x;
        shape.y = position.y;
        shape.height = height;
        shape.width = width;

        this.ballCenter = new Vector3(position.x+shape.width/2, position.y+shape.height/2, 0);

    }

    public void setUserVelocity(Vector3 newVelocity) {
        this.velocity = newVelocity;
        moveHistory.enqueue(newVelocity);
    }

    public Vector3 getCenter() {
        ballCenter = new Vector3(position.x+shape.width/2, position.y+shape.height/2, 0);
        return ballCenter;
    }

}
