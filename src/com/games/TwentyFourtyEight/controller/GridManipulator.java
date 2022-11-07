package com.games.TwentyFourtyEight.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GridManipulator {
    public boolean isGameValid() {
        return gameValid;
    }

    public void setGameValid(boolean gameValid) {
        this.gameValid = gameValid;
    }

    private boolean gameValid = true;
    public int gameScore;

    //called by controller to run logic on passed in board position data
    //recursion algorithm
    /*_________________
     *| 0 | 1 | 2 | 3 |
     *| 4 | 5 | 6 | 7 |
     *| 8 | 9 | 10| 11|
     *| 12| 13| 14| 15|
     * ----------------
     */
    //subtract / add 4 to move up or down layer
    //if between 0 and three when moving right, 0 and three are the left and right limits respectfully
    //need a counter to see how many squares moved
    //takes in board object that is a hashmap of integer position and value
    //board like object
    //comment here
    public HashMap<Integer, Integer> currentBoard = new HashMap<>();

    public void newBoard() {
        currentBoard.put(1, 0);
        currentBoard.put(2, 0);
        currentBoard.put(3, 0);
        currentBoard.put(4, 0);
        currentBoard.put(5, 0);
        currentBoard.put(6, 0);
        currentBoard.put(7, 0);
        currentBoard.put(8, 0);
        currentBoard.put(9, 0);
        currentBoard.put(10, 0);
        currentBoard.put(11, 0);
        currentBoard.put(12, 0);
        currentBoard.put(13, 0);
        currentBoard.put(14, 0);
        currentBoard.put(15, 0);
        currentBoard.put(16, 0);
        firstTwoTilesGenerator();
        firstTwoTilesGenerator();

        System.out.print("-------------------------");
        System.out.println();
        for (Map.Entry<Integer, Integer> square : currentBoard.entrySet()) {
            double tempVar = square.getKey() / 4.0;
            String stringVar = String.valueOf(tempVar);
            if (stringVar.matches("1.0|2.0|3.0|4.0")) {
                System.out.print("|");
                System.out.print(" ");
                if(square.getValue() == 0){
                    System.out.print("    ");
                } else {
                    System.out.print(String.format("%-4d",  square.getValue()));
                }
                System.out.print("|");
                System.out.println();
                System.out.print("-------------------------");
                System.out.println();

            } else {
                System.out.print("|");
                System.out.print(" ");
                if(square.getValue() == 0){
                    System.out.print("    ");
                } else {
                    System.out.print(String.format("%-4d",  square.getValue()));
                }
            }
        }

        System.out.println();
        System.out.println("Score: " + gameScore);
    }

    public void firstTwoTilesGenerator(){

        ArrayList<Integer> zeros = new ArrayList<Integer>();
        for(Map.Entry<Integer, Integer> square : currentBoard.entrySet()){
            if(square.getValue() == 0){
                zeros.add(square.getKey());
            }
        }
        newTwoTile(zeros);
    }

    public void printBoard() {
        System.out.print("-------------------------");
        System.out.println();
        for (Map.Entry<Integer, Integer> square : currentBoard.entrySet()) {
            double tempVar = square.getKey() / 4.0;
            String stringVar = String.valueOf(tempVar);
            if (stringVar.matches("1.0|2.0|3.0|4.0")) {
                System.out.print("|");
                System.out.print(" ");
                if(square.getValue() == 0){
                    System.out.print("    ");
                } else {
                    System.out.print(String.format("%-4d",  square.getValue()));
                }
                System.out.print("|");
                System.out.println();
                System.out.print("-------------------------");

                System.out.println(

                );

            } else {
                System.out.print("|");
                System.out.print(" ");
                if(square.getValue() == 0){
                    System.out.print("    ");
                } else {
                    System.out.print(String.format("%-4d",  square.getValue()));
                }


            }
        }
        System.out.println(gameScore + " gameScore");
        System.out.println();
    }

    public void downArrow() {
        HashMap<Integer, Integer> currentBoards = currentBoard;
        for (Map.Entry<Integer, Integer> square : currentBoards.entrySet()) {
            int currentLevelValue = getValueWithKnownKey(square.getKey());
            int level = (square.getKey() - 1) / 4;
            switch (level) {
                case 0:
                    int nextLevelKey = square.getKey() + 4;
                    int nextLevelValue = getValueWithKnownKey(nextLevelKey);

                    if (nextLevelValue == currentLevelValue && nextLevelValue != 0) {
                        //System.out.println("addition can happen here");
                        gameScore += nextLevelValue * 2;
                        setValueWithKnownKey(nextLevelKey, nextLevelValue * 2);
                        setValueWithKnownKey(square.getKey(), 0);
                        System.out.println();
                        downArrow();

                    }
                    if (nextLevelValue == 0 && square.getValue() != 0) {
                        setValueWithKnownKey(nextLevelKey, square.getValue());
                        setValueWithKnownKey(square.getKey(), 0);
                        downArrow();
                    }
                    break;

                case 1:
                    nextLevelKey = square.getKey() + 4;
                    nextLevelValue = getValueWithKnownKey(nextLevelKey);

                    if (nextLevelValue == currentLevelValue && nextLevelValue != 0) {
                       // System.out.println("addition can happen here with ");
                        gameScore += nextLevelValue * 2;
                        setValueWithKnownKey(nextLevelKey, nextLevelValue * 2);
                        setValueWithKnownKey(square.getKey(), 0);
                        System.out.println();

                        downArrow();
                    }
                    if (nextLevelValue == 0 && square.getValue() != 0) {
                        setValueWithKnownKey(nextLevelKey, square.getValue());
                        setValueWithKnownKey(square.getKey(), 0);
                        downArrow();
                    }
                    break;
                case 2:
                    nextLevelKey = square.getKey() + 4;
                    nextLevelValue = getValueWithKnownKey(nextLevelKey);
                    if (nextLevelValue == currentLevelValue && nextLevelValue != 0) {
                       // System.out.println("addition can happen here");
                        int doubleNext = nextLevelValue * 2;
                        gameScore += doubleNext;
                        setValueWithKnownKey(nextLevelKey, nextLevelValue * 2);
                        setValueWithKnownKey(square.getKey(), 0);
                        System.out.println();
                        downArrow();
                    }
                    if (nextLevelValue == 0 && square.getValue() != 0) {
                        setValueWithKnownKey(nextLevelKey, square.getValue());
                        setValueWithKnownKey(square.getKey(), 0);
                        downArrow();
                    }
                    break;
            }
        }
    }

    public void upArrow() {
        HashMap<Integer, Integer> currentBoards = currentBoard;
        for (Map.Entry<Integer, Integer> square : currentBoards.entrySet()) {
            int currentLevelValue = getValueWithKnownKey(square.getKey());
            int level = (square.getKey() - 1) / 4;
            switch (level) {
                case 1:
                    int nextLevelKey = square.getKey() - 4;
                    int nextLevelValue = getValueWithKnownKey(nextLevelKey);

                    if (nextLevelValue == currentLevelValue && nextLevelValue != 0) {
                        System.out.println("addition can happen here");
                        int doubleNext = nextLevelValue * 2;
                        gameScore += doubleNext;
                        setValueWithKnownKey(nextLevelKey, nextLevelValue * 2);
                        setValueWithKnownKey(square.getKey(), 0);
                        System.out.println();

                        upArrow();

                    }
                    if (nextLevelValue == 0 && square.getValue() != 0) {
                        setValueWithKnownKey(nextLevelKey, square.getValue());
                        setValueWithKnownKey(square.getKey(), 0);
                        upArrow();
                    }
                    break;

                case 2:
                    nextLevelKey = square.getKey() - 4;
                    nextLevelValue = getValueWithKnownKey(nextLevelKey);

                    if (nextLevelValue == currentLevelValue && nextLevelValue != 0) {
                       // System.out.println("addition can happen here with ");
                        int doubleNext = nextLevelValue * 2;
                        gameScore += doubleNext;
                        setValueWithKnownKey(nextLevelKey, nextLevelValue * 2);
                        setValueWithKnownKey(square.getKey(), 0);
                        System.out.println();

                        upArrow();
                    }
                    if (nextLevelValue == 0 && square.getValue() != 0) {
                        setValueWithKnownKey(nextLevelKey, square.getValue());
                        setValueWithKnownKey(square.getKey(), 0);
                        upArrow();
                    }
                    break;
                case 3:
                    nextLevelKey = square.getKey() - 4;
                    nextLevelValue = getValueWithKnownKey(nextLevelKey);
                    if (nextLevelValue == currentLevelValue && nextLevelValue != 0) {
                        gameScore += nextLevelValue * 2;
                        setValueWithKnownKey(nextLevelKey, nextLevelValue * 2);
                        setValueWithKnownKey(square.getKey(), 0);
                        System.out.println();
                        upArrow();
                    }
                    if (nextLevelValue == 0 && square.getValue() != 0) {
                        setValueWithKnownKey(nextLevelKey, square.getValue());
                        setValueWithKnownKey(square.getKey(), 0);
                        upArrow();
                    }
                    break;
            }
        }
    }

    public void rightArrow() {
        HashMap<Integer, Integer> currentBoards = currentBoard;
        ArrayList<Integer> zeros = new ArrayList<Integer>();

        for (Map.Entry<Integer, Integer> square : currentBoards.entrySet()) {
            int currentLevelValue = getValueWithKnownKey(square.getKey());
            boolean rightBorder = false;
            int caseFlag;
            if (square.getKey().equals(4)) {
                continue;
            }
            if (square.getKey().equals(8)) {
                continue;
            }
            if (square.getKey().equals(12)) {
                continue;
            }
            if (square.getKey().equals(16)) {

                continue;
            } else {
                caseFlag = 0;
            }
            switch (caseFlag) {
                case 0:
                    int nextLevelKey = square.getKey() + 1;
                    int nextLevelValue = getValueWithKnownKey(nextLevelKey);

                    if (nextLevelValue == currentLevelValue && nextLevelValue != 0) {
                        int doubleNext = nextLevelValue * 2;
                        gameScore += doubleNext;

                        setValueWithKnownKey(nextLevelKey, nextLevelValue * 2);
                        setValueWithKnownKey(square.getKey(), 0);
                        System.out.println();

                        rightArrow();

                    }
                    if (nextLevelValue == 0 && square.getValue() != 0) {


                        setValueWithKnownKey(nextLevelKey, square.getValue());
                        setValueWithKnownKey(square.getKey(), 0);
                        rightArrow();
                    }
                    break;

                case 1:
                    break;

            }
        }
    }
    public void leftRemoved(){
        leftArrow();
        ArrayList<Integer> zeros = new ArrayList<Integer>();
        for(Map.Entry<Integer, Integer> square : currentBoard.entrySet()){
            if(square.getValue() == 0){
                zeros.add(square.getKey());
            }
        }
        newTwoTile(zeros);
        System.out.println(zeros);
    }
    public void rightRemoved(){
        rightArrow();
        ArrayList<Integer> zeros = new ArrayList<Integer>();
        for(Map.Entry<Integer, Integer> square : currentBoard.entrySet()){
            if(square.getValue() == 0){
                zeros.add(square.getKey());
            }
        }
        newTwoTile(zeros);
        System.out.println(zeros);
    }
    public void upRemoved(){
        upArrow();
        ArrayList<Integer> zeros = new ArrayList<Integer>();
        for(Map.Entry<Integer, Integer> square : currentBoard.entrySet()){
            if(square.getValue() == 0){
                zeros.add(square.getKey());
            }
        }
        newTwoTile(zeros);
        System.out.println(zeros);
    }
    public void downRemoved(){
       downArrow();
        ArrayList<Integer> zeros = new ArrayList<Integer>();
        for(Map.Entry<Integer, Integer> square : currentBoard.entrySet()){
            if(square.getValue() == 0){
                zeros.add(square.getKey());
            }
        }
        newTwoTile(zeros);
    }

    public void leftArrow() {
        HashMap<Integer, Integer> currentBoards = currentBoard;

        for (Map.Entry<Integer, Integer> square : currentBoards.entrySet()) {
            int currentLevelValue = getValueWithKnownKey(square.getKey());
            boolean rightBorder = false;
            int caseFlag;
            if (square.getKey().equals(1)) {

                continue;
            }
            if (square.getKey().equals(5)) {

                continue;
            }
            if (square.getKey().equals(9)) {

                continue;

            }
            if (square.getKey().equals(13)) {

                continue;
            } else {
                caseFlag = 0;
            }
            switch (caseFlag) {
                case 0:
                    int nextLevelKey = square.getKey() - 1;
                    int nextLevelValue = getValueWithKnownKey(nextLevelKey);

                    if (nextLevelValue == currentLevelValue && nextLevelValue != 0) {
                        int doubleNext = nextLevelValue * 2;
                        gameScore += doubleNext;
                        setValueWithKnownKey(nextLevelKey, nextLevelValue * 2);
                        setValueWithKnownKey(square.getKey(), 0);
                        System.out.println();
                        leftArrow();
                    }
                    if (nextLevelValue == 0 && square.getValue() != 0) {
                        setValueWithKnownKey(nextLevelKey, square.getValue());
                        setValueWithKnownKey(square.getKey(), 0);
                        leftArrow();
                    }
                    break;

                case 1:
                    break;
            }
        }

    }
    public void newTwoTile(ArrayList zeros){
        int result = 0;
        if(zeros.size() == 0){
            this.setGameValid(false);
        } else {
            int arrayLength = zeros.size();
            double tempVar = Math.random();
            double randVal = arrayLength * tempVar;
            int randIndex = (int) Math.round(randVal);
            int arrayAdjusted = randIndex - 1;
            if(arrayAdjusted == -1){
                arrayAdjusted =0;
            }
            currentBoard.put((Integer) zeros.get(arrayAdjusted), 2);

        }
    }

    public void setValueWithKnownKey(int key, int value) {
        currentBoard.put(key, value);

    }

    public int getValueWithKnownKey(int key) {
        int value = 0;
        value = (int) currentBoard.get(key);
        return value;
    }
}