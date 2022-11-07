package com.games.TwentyFourtyEight.client;

import com.games.TwentyFourtyEight.controller.GridManipulator;

import java.util.Scanner;

class GridManipulatorTestClient {


    public static void main(String[] args) {
        final Scanner scanner = new Scanner(System.in);
        GridManipulator grid = new GridManipulator();
        grid.newBoard();
        boolean gameOver = false;
        while(!gameOver){
            System.out.println("WASD");
            String input = scanner.nextLine().trim().toUpperCase();
            if(input.matches("W|A|S|D")){
                switch(input){
                    case "W":
                        grid.upArrow();
                        grid.printBoard();
                        break;
                    case "S":

                        grid.downArrow();
                        grid.printBoard();
                        break;
                    case "D":

                        grid.rightArrow();
                        grid.printBoard();
                        break;
                }

            }
        }

    }
}