package com.project.putting_game;

import java.util.Comparator;

public class CompareShot implements Comparator<Shot> {
    @Override
    public int compare(Shot s1, Shot s2) {
        return s1.compareTo(s2);
    }
}
