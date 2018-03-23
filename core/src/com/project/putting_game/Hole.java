package com.project.putting_game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Vector3;

public class Hole
{
    public Vector3 position;
    public Ellipse holeShape;
    //height, width, x, y (floats)
    public Texture holeImage;
    public Vector3 holeCenter;
    public double radius;

    public Hole(Vector3 position, String path, int height, int width)
    {
        this.position = position;
        this.holeImage = new Texture(Gdx.files.internal(path));
        this.holeShape = new Ellipse();
        holeShape.x = position.x;
        holeShape.y = position.y;
        holeShape.height = height;
        holeShape.width = width;
        this.holeCenter = new Vector3(position.x+holeShape.width/2, position.y+holeShape.height/2,0);
        this.radius = holeShape.height/2;
    }

    public Vector3 getHoleCenter(){
        holeCenter = new Vector3(position.x+holeShape.width/2, position.y+holeShape.height/2,0);
        return holeCenter;
    }



}
