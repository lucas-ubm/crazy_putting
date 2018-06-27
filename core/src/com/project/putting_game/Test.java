package com.project.putting_game;

import com.badlogic.gdx.math.Vector3;

public class Test {
    public static void main(String[] args) {
        Vector3 v1 =new Vector3(300, 40, 0);
        Vector3 v2 =new Vector3(350,300,0);
        Vector3 u1 =new Vector3(60,60,0);
        Vector3 u2 = new Vector3(700, 400,0);

        System.out.println(v1.scl(0.5f).add(u1.scl(0.5f)).dst(v2.scl(0.5f).add(u2.scl(0.5f))));
        System.out.println(v2.scl(0.5f).add(u2.scl(0.5f)));

        System.out.println(new Vector3(60, 60, 0).dst(new Vector3(700,400,0)));
    }

}
