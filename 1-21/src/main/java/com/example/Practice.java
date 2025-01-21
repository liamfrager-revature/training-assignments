package com.example;

public class Practice {
    
    public static void swapVariables(int x, int y) {
        System.out.println("x = " + x);
        System.out.println("y = " + y);
        x += y;
        y = x - y;
        x -= y;
        System.out.println("// SWAP //");
        System.out.println("x = " + x);
        System.out.println("y = " + y);
        System.out.println();
    }
}
