package com.project.putting_game;

import java.util.ArrayList;

public class Test {
    public static void main(String[] args){
        ArrayList<String> one = FunctionAnalyser.ShuntingYard("sinx+y-y^3");
        System.out.println(FunctionAnalyser.reversePolish(one, 0,2));
        System.out.println(FunctionAnalyser.runge_kutta(FunctionAnalyser.ShuntingYard("5*x-3"), 2, 5, 0.2, 1));
    }
}
