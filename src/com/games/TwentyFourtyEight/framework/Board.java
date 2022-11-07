package com.games.TwentyFourtyEight.framework;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public class Board {
    public static final int ROWS = 4;
    public static final int COLS = 4;

    private final int startingTiles = 2;

    private Tile[][] board;
    private boolean gameOver;
    private boolean winner;

    private BufferedImage gameBoard;
    private BufferedImage updatedBoard;
    private int x;
    private int y;

    private static int SPACING = 10;
    public static int BOARD_WIDTH = (COLS + 1)*SPACING + COLS*Tile.WIDTH;
    public static int BOARD_HEIGHT = (COLS + 1)*SPACING + COLS*Tile.HEIGHT;

    public Board(int x, int y){
        this.x = x;
        this.y = y;
        board = new Tile[ROWS][COLS];
        gameBoard = new BufferedImage(BOARD_WIDTH, BOARD_HEIGHT, BufferedImage.TYPE_INT_RGB);
        updatedBoard = new BufferedImage(BOARD_WIDTH, BOARD_HEIGHT, BufferedImage.TYPE_INT_RGB);

        createGameBoard();
    }

    private void createGameBoard(){
        Graphics2D g = (Graphics2D) gameBoard.getGraphics();
        g.setColor(Color.darkGray);
        g.fillRect(0,0, BOARD_WIDTH, BOARD_HEIGHT);
        g.setColor(Color.lightGray);

        for(int row = 0; row < ROWS; row++){
            for(int col = 0; col < COLS; col++){
                int x = SPACING + SPACING*col + Tile.WIDTH*col;
                int y = SPACING + SPACING*row + Tile.WIDTH*row;
                g.fillRoundRect(x, y, Tile.WIDTH, Tile.HEIGHT, Tile.ARC_WIDTH, Tile.ARC_HEIGHT);
            }
        }
    }

    public void update(){
        // TODO check for buttons pressed

        // check for win status
        for(int row = 0; row < ROWS; row++){
            for(int col = 0; col < COLS; col++){
                Tile currentTile = board[row][col];

                if(currentTile == null){
                    continue;
                }
                currentTile.update();

                if(currentTile.getValue() == 2048){
                    winner = true;
                }
            }
        }
    }

    public void render(Graphics2D g){
        Graphics2D g2d = (Graphics2D) updatedBoard.getGraphics();
        g2d.drawImage(gameBoard, 0, 0, null);

        // draw tiles
        g.drawImage(updatedBoard, x, y, null);
        g2d.dispose();
    }

    //4x4 grid that starts with two "2" tiles and nothing else
//    HashMap<Integer, Squares> board = new HashMap<>();
    /*
      * (position on board)     (value of square)
      *  Int                    Square
      *   0                         2
      *   1                         0
      *   2                         0
      *   3                         2
      *   ect.
     */
    /*_________________
     *| 0 | 1 | 2 | 3 |
     *| 4 | 5 | 6 | 7 |
     *| 8 | 9 | 10| 11|
     *| 12| 13| 14| 15|
     * ----------------
     */

    /*random number generator determine starting two positions
     *while loop triggered by two boolean flags
     *when two valid numbers that are not the same between 0 and 16 are found,
     */

}