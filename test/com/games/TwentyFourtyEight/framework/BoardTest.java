package com.games.TwentyFourtyEight.framework;

import org.junit.Test;

import static org.junit.Assert.*;

public class BoardTest {

   @Test
    public void testCreateGameBoard(){
       Board testBoard = new Board(2,0);
       assertEquals(220.0, testBoard.getTileX(2), testBoard.getTileY(0));
   }
}