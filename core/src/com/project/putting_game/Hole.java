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

    public Hole(Vector3 position, String path, int height, int width)
    {
        this.position = position;
        this.holeImage = new Texture(Gdx.files.internal(path));
        this.holeShape = new Ellipse();
        holeShape.x = position.x;
        holeShape.y = position.y;
        holeShape.height = height;
        holeShape.width = width;

    }

}
