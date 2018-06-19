package com.project.putting_game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Vector3;

public class Hole
{
    public Vector3 position;
    public Ellipse holeShape;
    public Texture holeImage;

    /** Constructor of Hole
     * @param position goal position
     * @param side radius of the hole
     */
    public Hole(Vector3 position, int side)
    {
        this.position = position;
        this.holeImage = new Texture("hole.png");
        this.holeShape = new Ellipse();
        holeShape.x = position.x;
        holeShape.y = position.y;
        holeShape.height = side;
        holeShape.width = side;
    }

    public Vector3 getPosition() {
        return position;
    }
}
