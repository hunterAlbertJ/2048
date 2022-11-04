package com.games.TwentyFourtyEight.controller;

import java.util.HashMap;
import java.util.Map;

class GridManipulator {
    public static void main(String[] args) {
        System.out.println("test");
    }
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

  public void initializeBoard(){
       HashMap<Integer, Integer> currentBoard = new HashMap<>();
       currentBoard.put(0,0);
       currentBoard.put(1,0);
       currentBoard.put(2,0);
       currentBoard.put(3,0);
       currentBoard.put(4,0);
       currentBoard.put(5,0);
       currentBoard.put(6,0);
       currentBoard.put(7,0);
       currentBoard.put(8,0);
       currentBoard.put(9,0);
       currentBoard.put(10,0);
       currentBoard.put(11,0);
       currentBoard.put(12,0);
       currentBoard.put(13,0);
       currentBoard.put(14,0);
       currentBoard.put(15,0);
       for(Map.Entry<Integer,Integer> square : currentBoard.entrySet()){
           double tempVar = square.getKey();
           if(tempVar / 4 == 1.0){
               System.out.println();
           } else {
               System.out.println(square.getValue());
           }
       }

   }



}