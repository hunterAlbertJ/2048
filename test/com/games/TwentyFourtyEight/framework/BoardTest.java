package com.games.TwentyFourtyEight.framework;

import org.junit.Test;

import static org.junit.Assert.*;

public class BoardTest {

   @Test
    public void testCreateGameBoard_shouldStartWithTwoTiles(){
       Board testBoard = new Board(800/2 - Board.BOARD_WIDTH/2, 630 - Board.BOARD_HEIGHT );
       testBoard.createGameBoard();

       int testCount = 0;
       for(int row = 0; row < testBoard.ROWS; row++){
          for(int col = 0; col < testBoard.COLS; col++){
             Tile current = testBoard.board[row][col];

                if(current != null)
                   testCount++;
                else
                   continue;
          }
       }
       assertEquals(2, testCount);
   }

   @Test
   public void testSpawnRandom_shouldFindThreeTiles(){
      Board testBoard = new Board(800/2 - Board.BOARD_WIDTH/2, 630 - Board.BOARD_HEIGHT );
      testBoard.createGameBoard();
      testBoard.spawnRandom();

      int testCount = 0;
      for(int row = 0; row < testBoard.ROWS; row++){
         for(int col = 0; col < testBoard.COLS; col++){
            Tile current = testBoard.board[row][col];

            if(current != null)
               testCount++;
            else
               continue;
         }
      }
      assertEquals(3, testCount);
   }
}