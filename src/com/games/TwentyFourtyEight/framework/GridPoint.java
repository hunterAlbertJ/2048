package com.games.TwentyFourtyEight.framework;

public class GridPoint {
    public int row;
    public int col;

    /*
     * Utility class, creates a custom "coordinates" class used for locating Tiles in Board.board[][]
     */
    public GridPoint(int row, int col){
        this.row = row;
        this.col = col;
    }
}