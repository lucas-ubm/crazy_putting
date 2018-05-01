/** An interface containing all the settings for the game
 * @author Lucas Uberti-Bona
 * @version 0.1, 24 Apr 2018
 *
 */

package com.project.putting_game;

import com.badlogic.gdx.math.Vector3;

public interface Settings {
    int windowWidth = 800;
    int windowHeight = 480;

    Vector3 ballPosition = new Vector3(80,80,0);
    int ballSide = 32;

    Vector3 holePosition = new Vector3(300,300,0);
    int holeSide = 60;

    int borderLength = 60;



}
