package com.games.TwentyFourtyEight.controller;

import java.util.HashMap;
import java.util.Map;

public class GridManipulator {

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
    public void newBoard(){
        currentBoard.put(1,2);
        currentBoard.put(2,0);
        currentBoard.put(3,0);
        currentBoard.put(4,2);
        currentBoard.put(5,0);
        currentBoard.put(6,0);
        currentBoard.put(7,0);
        currentBoard.put(8,0);
        currentBoard.put(9,2);
        currentBoard.put(10,0);
        currentBoard.put(11,0);
        currentBoard.put(12,16);
        currentBoard.put(13,4);
        currentBoard.put(14,0);
        currentBoard.put(15,0);
        currentBoard.put(16,0);

        System.out.print("-----------");
        System.out.println();
        for(Map.Entry<Integer,Integer> square : currentBoard.entrySet()){
            double tempVar = square.getKey() / 4.0;
            String stringVar =  String.valueOf(tempVar);
//           System.out.println(stringVar);
            if(stringVar.matches("1.0|2.0|3.0|4.0")){
                System.out.print(square.getValue());
                System.out.print("|");
                System.out.println();

            } else {
                System.out.print("|");
                System.out.print(square.getValue());
                System.out.print("|");
            }
        }
        System.out.print("-----------");
        System.out.println();


    }
    public void printBoard(){
        for(Map.Entry<Integer,Integer> square : currentBoard.entrySet()){
            double tempVar = square.getKey() / 4.0;
            String stringVar =  String.valueOf(tempVar);
//           System.out.println(stringVar);
            if(stringVar.matches("1.0|2.0|3.0|4.0")){
                System.out.print(square.getValue());
                System.out.print("|");
                System.out.println();

            } else {
                System.out.print("|");
                System.out.print(square.getValue());
                System.out.print("|");
            }
        }
        System.out.print("-----------");
        System.out.println();
    }

   public void downArrow(){
       HashMap<Integer, Integer> currentBoards = currentBoard;
       for(Map.Entry<Integer,Integer> square : currentBoards.entrySet()) {
           int currentLevelValue = getValueWithKnownKey(square.getKey());
               int level = (square.getKey() - 1) / 4;
               switch (level) {
                   case 0:
                           int nextLevelKey = square.getKey() + 4;
                           int nextLevelValue = getValueWithKnownKey(nextLevelKey);

                           if (nextLevelValue == currentLevelValue && nextLevelValue != 0) {
                               System.out.println("addition can happen here");
                               setValueWithKnownKey(nextLevelKey, nextLevelValue * 2);
                               setValueWithKnownKey(square.getKey(), 0);
                               System.out.println();
                              downArrow();

                           }
                           if(nextLevelValue == 0 && square.getValue() != 0){
                              setValueWithKnownKey(nextLevelKey, square.getValue());
                             setValueWithKnownKey(square.getKey(), 0);
                             downArrow();
                          }
                       break;

                   case 1:
                           nextLevelKey = square.getKey() + 4;
                           nextLevelValue = getValueWithKnownKey(nextLevelKey);

                           if (nextLevelValue == currentLevelValue && nextLevelValue != 0) {
                               System.out.println("addition can happen here with " );
                               setValueWithKnownKey(nextLevelKey, nextLevelValue * 2);
                               setValueWithKnownKey(square.getKey(), 0);
                               System.out.println();

                               downArrow();
                           }
                           if(nextLevelValue == 0 && square.getValue() != 0){
                              setValueWithKnownKey(nextLevelKey, square.getValue());
                             setValueWithKnownKey(square.getKey(), 0);
                             downArrow();
                       }
                       break;
                   case 2:
                           nextLevelKey = square.getKey() + 4;
                           nextLevelValue = getValueWithKnownKey(nextLevelKey);
                           if (nextLevelValue == currentLevelValue && nextLevelValue != 0) {
                               System.out.println("addition can happen here");
                               setValueWithKnownKey(nextLevelKey, nextLevelValue * 2);
                               setValueWithKnownKey(square.getKey(), 0);
                               System.out.println();
                               downArrow();
                           }
                           if(nextLevelValue == 0 && square.getValue() != 0) {
                               setValueWithKnownKey(nextLevelKey, square.getValue());
                               setValueWithKnownKey(square.getKey(), 0);
                               downArrow();
                           }
                       break;
               }
       }
   }

   public void upArrow(){
       HashMap<Integer, Integer> currentBoards = currentBoard;
       for(Map.Entry<Integer,Integer> square : currentBoards.entrySet()) {
           int currentLevelValue = getValueWithKnownKey(square.getKey());
           int level = (square.getKey() - 1) / 4;
           switch (level) {
               case 1:
                   int nextLevelKey = square.getKey() - 4;
                   int nextLevelValue = getValueWithKnownKey(nextLevelKey);

                   if (nextLevelValue == currentLevelValue && nextLevelValue != 0) {
                       System.out.println("addition can happen here");
                       setValueWithKnownKey(nextLevelKey, nextLevelValue * 2);
                       setValueWithKnownKey(square.getKey(), 0);
                       System.out.println();

                       upArrow();

                   }
                   if(nextLevelValue == 0 && square.getValue() != 0){
                       setValueWithKnownKey(nextLevelKey, square.getValue());
                       setValueWithKnownKey(square.getKey(), 0);
                       upArrow();
                   }
                   break;

               case 2:
                   nextLevelKey = square.getKey() - 4;
                   nextLevelValue = getValueWithKnownKey(nextLevelKey);

                   if (nextLevelValue == currentLevelValue && nextLevelValue != 0) {
                       System.out.println("addition can happen here with " );
                       setValueWithKnownKey(nextLevelKey, nextLevelValue * 2);
                       setValueWithKnownKey(square.getKey(), 0);
                       System.out.println();

                       upArrow();
                   }
                   if(nextLevelValue == 0 && square.getValue() != 0){
                       setValueWithKnownKey(nextLevelKey, square.getValue());
                       setValueWithKnownKey(square.getKey(), 0);
                       upArrow();
                   }
                   break;
               case 3:
                   nextLevelKey = square.getKey()- 4;
                   nextLevelValue = getValueWithKnownKey(nextLevelKey);
                   if (nextLevelValue == currentLevelValue && nextLevelValue != 0) {
                       System.out.println("addition can happen here");
                       setValueWithKnownKey(nextLevelKey, nextLevelValue * 2);
                       setValueWithKnownKey(square.getKey(), 0);
                       System.out.println();
                       upArrow();
                   }
                   if(nextLevelValue == 0 && square.getValue() != 0) {
                       setValueWithKnownKey(nextLevelKey, square.getValue());
                       setValueWithKnownKey(square.getKey(), 0);
                       upArrow();
                   }
                   break;
           }
       }
   }
    public void rightArrow(){
        HashMap<Integer, Integer> currentBoards = currentBoard;
        for(Map.Entry<Integer,Integer> square : currentBoards.entrySet()) {
            int currentLevelValue = getValueWithKnownKey(square.getKey());
            boolean rightBorder = false;
            int caseFlag;
            if(square.getKey().equals(4)){
                System.out.println(square.getKey());
                caseFlag = 1;
            }
            if(square.getKey().equals(8)){
                System.out.println(square.getKey());
                caseFlag = 1;
            }
            if(square.getKey().equals(12)){
                System.out.println(square.getKey());
                caseFlag = 1;

            }
            if(square.getKey().equals(16)){
                System.out.println(square.getKey());
                caseFlag = 1;
            }else {
                caseFlag = 0;
            }
            switch (caseFlag) {
                case 0:
                    int nextLevelKey = square.getKey() + 1;
                    int nextLevelValue = getValueWithKnownKey(nextLevelKey);

                    if (nextLevelValue == currentLevelValue && nextLevelValue != 0) {
                        System.out.println("addition can happen here");
                        setValueWithKnownKey(nextLevelKey, nextLevelValue * 2);
                        setValueWithKnownKey(square.getKey(), 0);
                        System.out.println();

                        rightArrow();

                    }
                    if(nextLevelValue == 0 && square.getValue() != 0){
                        setValueWithKnownKey(nextLevelKey, square.getValue());
                        setValueWithKnownKey(square.getKey(), 0);
                        rightArrow();
                    }
                    break;

                case 1:
                    continue;

            }
        }
    }



   public void setValueWithKnownKey(int key, int value){
        currentBoard.put(key,value);
//       System.out.println(currentBoard.entrySet());


   }
   public int getValueWithKnownKey(int key){
        int value = 0;
        value = (int) currentBoard.get(key);

        return value;
   }



}