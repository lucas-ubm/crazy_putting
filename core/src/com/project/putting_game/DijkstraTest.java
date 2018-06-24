package com.project.putting_game;

public class DijkstraTest {

    public static void main(String args[]) {

        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Crazy Putting";
		config.width = 800;
		config.height = 480;
		new LwjglApplication(new Project2(), config);

    }
}