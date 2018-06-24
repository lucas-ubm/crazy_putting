<<<<<<< HEAD
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
=======
package com.project.putting_game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Vector3;

public class Hole
{
    public Vector3 position;
    public Ellipse holeShape;
    //height, width, x, y (floats)
    public Sprite holeImage;
    public static int c=0;
    public int id;

    public Hole(int players,Vector3 position, String path, int side) {
        id = c;
        c++;
        this.position = position;
        Texture texture = new Texture(Gdx.files.internal(path));
        this.holeImage = new Sprite(texture);
        float  value = ((float) id+1)/((float)players);
        holeImage.setColor(new Color(value, (float)0.2, 1-value, 1f));
        this.holeShape = new Ellipse();
        holeShape.x = position.x;
        holeShape.y = position.y;
        holeShape.height = side;
        holeShape.width = side;
        holeImage.setSize(holeShape.width, holeShape.height);
	    holeImage.setPosition(position.x-holeShape.height/4, position.y-holeShape.height/2);
    }

}
>>>>>>> master
