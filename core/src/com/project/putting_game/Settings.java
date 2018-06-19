/** An interface containing all the settings for the game
 * @author Lucas Uberti-Bona
 * @version 0.1, 24 Apr 2018
 *
 */

package com.project.putting_game;

import com.badlogic.gdx.math.Vector3;

public class Settings {
    String courseFunction;
    Vector3 startPosition;
    Vector3 goalPosition;
    int goalRadius;

    public Settings(String courseFunction, Vector3 startPosition, Vector3 goalPosition, int goalRadius) {
        this.courseFunction = courseFunction;
        this.startPosition = startPosition;
        this.goalPosition = goalPosition;
        this.goalRadius = goalRadius;
    }
}
