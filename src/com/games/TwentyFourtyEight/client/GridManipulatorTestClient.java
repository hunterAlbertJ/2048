package com.games.TwentyFourtyEight.client;

import com.games.TwentyFourtyEight.controller.GridManipulator;

import java.util.Scanner;

public class GridManipulatorTestClient {


    public static void main(String[] args) {
        final Scanner scanner = new Scanner(System.in);
        GridManipulator grid = new GridManipulator();
        grid.newBoard();
        boolean gameOver = false;
        while(!gameOver){
            if(grid.isGameValid() == false){
                gameOver = true;
            }
            System.out.println("Use WASD keys to move tiles");
            String input = scanner.nextLine().trim().toUpperCase();
            if(input.matches("W|A|S|D")){
                switch(input){
                    case "W":
                        grid.upRemoved();
                        grid.printBoard();
                        break;
                    case "S":

                        grid.downRemoved();
                        grid.printBoard();
                        break;
                    case "D":

                        grid.rightRemoved();
                        grid.printBoard();
                        break;
                    case "A":

                        grid.leftRemoved();
                        grid.printBoard();
                        break;
                }

            }else{
                grid.printBoard();
                System.out.println("READ: Use WASD keys to move tiles");
            }
        }
        System.out.println("NO MOVES REMAIN. GAME OVER");

    }
}