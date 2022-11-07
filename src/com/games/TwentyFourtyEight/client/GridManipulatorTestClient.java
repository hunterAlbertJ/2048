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
                System.out.println("WASD");
            }
        }

    }
}