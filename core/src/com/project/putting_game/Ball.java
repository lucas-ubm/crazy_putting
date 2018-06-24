<<<<<<< HEAD
/** A class to represent a Ball
 * @author Lucas Uberti-Bona
 * @version 0.1, 09-03-2018
 *
 */
package com.project.putting_game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Vector3;

public class Ball {
    public Vector3 velocity;
    public Vector3 position;
    public Vector3 prevPosition;
    public Texture ballImage;
    public Ellipse shape;
    public Queue<Vector3> moveHistory;

	/** Constructor of Ball
	 * @param position start position
	 * @param side size of the ball
	 */
    public Ball(Vector3 position, int side) {
        this.velocity = new Vector3(0,0,0);
        moveHistory = new Queue<Vector3>(); //initialise moves output

        this.position = position;
        this.ballImage = new Texture("golfball.png");
        this.prevPosition = null; //no shots played
        this.shape = new Ellipse();
        shape.x = position.x;
        shape.y = position.y;
        shape.height = side;
        shape.width = side;
    }

	/** Shot has been requested/played
	 * @param newVelocity velocity the shot has to start with
	 */
    public void setUserVelocity(Vector3 newVelocity) {
        this.velocity = newVelocity;
        moveHistory.enqueue(newVelocity.cpy()); //add to moves output
    }

    public Vector3 getPosition() {
        return position;
    }
}
=======
/** A class to represent a Ball
 * @author Lucas Uberti-Bona
 * @version 0.1, 09-03-2018
 *
 */
package com.project.putting_game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Vector3;

public class Ball {
    public Vector3 velocity;
    public Vector3 position;
    public Vector3 prevPosition;
    public Sprite ballImage;
    public Ellipse shape;
    public Queue<Vector3> moveHistory;
    public static int c=0;
    private int id;
    public boolean arrived;

    public int getId(){
    	return this.id;
    }

    public Ball(int players,Vector3 position, String path, int side) {
        this.velocity = new Vector3(0,0,0);
        moveHistory = new Queue<Vector3>();

		arrived = false;
	    id = c;
	    c++;
	    this.position = position;
        Texture texture = new Texture(Gdx.files.internal(path));
        this.ballImage = new Sprite(texture);
	    float value = ((float) id+1)/((float)players);//scale color of the ball to number of different colors needed
	    //System.out.println(value);
	    ballImage.setColor(new Color(value, (float)0.2, 1-value, 1f));
        this.prevPosition = position;
        this.shape = new Ellipse();
        shape.x = position.x;
        shape.y = position.y;
        shape.height = side;
        shape.width = side;
	    ballImage.setSize(shape.width, shape.height);
    }

    public void setUserVelocity(Vector3 newVelocity) {
        this.velocity = newVelocity;
        moveHistory.enqueue(newVelocity.cpy());
    }

    }
>>>>>>> master
