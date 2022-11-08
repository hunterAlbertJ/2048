package com.games.TwentyFourtyEight.controller;

import java.awt.event.KeyEvent;
import java.util.Scanner;

public class Controller {
    public static boolean[] pressed = new boolean[256];
    public static boolean[] prev = new boolean[256];

    private Controller(){ }

    public static void update(){
        for(int i = 0; i < 4; i++){
            switch (i){
                case 0: {
                    prev[KeyEvent.VK_LEFT] = pressed[KeyEvent.VK_LEFT];
                }
                case 1: {
                    prev[KeyEvent.VK_RIGHT] = pressed[KeyEvent.VK_RIGHT];
                }
                case 2: {
                    prev[KeyEvent.VK_UP] = pressed[KeyEvent.VK_UP];
                }
                case 3: {
                    prev[KeyEvent.VK_DOWN] = pressed[KeyEvent.VK_DOWN];
                }
            }
        }
    }

    public static void keyPressed(KeyEvent e){
        pressed[e.getKeyCode()] = true;
    }

    public static void keyReleased(KeyEvent e){
        pressed[e.getKeyCode()] = false;
    }

    public static boolean typed(int keyEvent){
        return !pressed[keyEvent] && prev[keyEvent];
    }
}