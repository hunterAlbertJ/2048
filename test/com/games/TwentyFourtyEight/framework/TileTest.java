package com.games.TwentyFourtyEight.framework;

import org.junit.Test;

import static org.junit.Assert.*;

public class TileTest {

    @Test
    public void shouldReturn_correctRGBValues_whenPassedCorrectTileValues() {
        Tile testTile = new Tile(2,0,0);
        assertEquals("java.awt.Color[r=86,g=255,b=0]", testTile.getBackground().toString());
    }

}