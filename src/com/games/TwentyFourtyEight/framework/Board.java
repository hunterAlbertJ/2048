package com.games.TwentyFourtyEight.framework;

import com.games.TwentyFourtyEight.controller.Controller;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Random;

public class Board {
    public static final int ROWS = 4;
    public static final int COLS = 4;

    private final int startingTiles = 2;

    private Tile[][] board;
    private boolean gameOver;
    private boolean winner;
    private boolean hasStarted;

    private BufferedImage gameBoard;
    private BufferedImage updatedBoard;
    private int x;
    private int y;

    private static int SPACING = 10;
    public static int BOARD_WIDTH = (COLS + 1) * SPACING + COLS * Tile.WIDTH;
    public static int BOARD_HEIGHT = (COLS + 1) * SPACING + COLS * Tile.HEIGHT;

    public Board(int x, int y) {
        this.x = x;
        this.y = y;
        board = new Tile[ROWS][COLS];
        gameBoard = new BufferedImage(BOARD_WIDTH, BOARD_HEIGHT, BufferedImage.TYPE_INT_RGB);
        updatedBoard = new BufferedImage(BOARD_WIDTH, BOARD_HEIGHT, BufferedImage.TYPE_INT_RGB);

        createGameBoard();
        start();
    }

    private void createGameBoard() {
        Graphics2D g = (Graphics2D) gameBoard.getGraphics();
        g.setColor(Color.darkGray);
        g.fillRect(0, 0, BOARD_WIDTH, BOARD_HEIGHT);
        g.setColor(Color.lightGray);

        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                int x = SPACING + SPACING * col + Tile.WIDTH * col;
                int y = SPACING + SPACING * row + Tile.WIDTH * row;
                g.fillRoundRect(x, y, Tile.WIDTH, Tile.HEIGHT, Tile.ARC_WIDTH, Tile.ARC_HEIGHT);
            }
        }
    }

    private void start() {
        for (int i = 0; i < startingTiles; i++) {
            spawnRandom();
        }
    }

    public void update() {
        checkKeys();

        // check for win status
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                Tile currentTile = board[row][col];

                if (currentTile == null) {
                    continue;
                }
                currentTile.update();

                resetPosition(currentTile, row, col);

                if (currentTile.getValue() == 2048) {
                    winner = true;
                }
            }
        }
    }

    private void resetPosition(Tile current, int row, int col){
        if(current == null){
            return;
        }

        int x = getTileX(col);
        int y = getTileY(row);

        int distX = current.getX() - x;
        int distY = current.getY() - y;

        if(Math.abs(distX) < Tile.SLIDE_SPEED){
            current.setX(current.getX() - distX);
        }
        if(Math.abs(distY) < Tile.SLIDE_SPEED){
            current.setY(current.getY() - distY);
        }

        if(distX < 0){
            current.setX(current.getX() + Tile.SLIDE_SPEED);
        }
        if(distY < 0){
            current.setY(current.getY() + Tile.SLIDE_SPEED);
        }
        if(distX > 0){
            current.setX(current.getX() - Tile.SLIDE_SPEED);
        }
        if(distY < 0){
            current.setY(current.getY() - Tile.SLIDE_SPEED);
        }
    }

    public void render(Graphics2D g) {
        Graphics2D g2d = (Graphics2D) updatedBoard.getGraphics();
        g2d.drawImage(gameBoard, 0, 0, null);

        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                Tile current = board[row][col];
                if (current == null) {
                    continue;
                }
                current.render(g2d);
            }
        }

        g.drawImage(updatedBoard, x, y, null);
        g2d.dispose();
    }

    private void checkKeys() {
        if (Controller.typed(KeyEvent.VK_LEFT)) {
            moveTiles(Direction.LEFT);
            if (!hasStarted)
                hasStarted = true;
        }
        if (Controller.typed(KeyEvent.VK_RIGHT)) {
            moveTiles(Direction.RIGHT);
            if (!hasStarted)
                hasStarted = true;
        }
        if (Controller.typed(KeyEvent.VK_UP)) {
            moveTiles(Direction.UP);
            if (!hasStarted)
                hasStarted = true;
        }
        if (Controller.typed(KeyEvent.VK_DOWN)) {
            moveTiles(Direction.DOWN);
            if (!hasStarted)
                hasStarted = true;
        }
    }

    private boolean move(int row, int col, int horDir, int vertDir, Direction dir) {
        boolean canMove = false;

        Tile current = board[row][col];
        if (current == null) {
            return false;
        }

        boolean move = true;
        int newCol = col;
        int newRow = row;
        while (move) {
            newCol += horDir;
            newRow += vertDir;

            if (checkOutOfBounds(dir, newRow, newCol)) {
                break;
            }
            if (board[newRow][newCol] == null) {
                board[newRow][newCol] = current;
                board[newRow - vertDir][newCol - horDir] = null;
                board[newRow][newCol].setSlideTo(new GridPoint(newRow, newCol));
                canMove = true;
            } else if (board[newRow][newCol].getValue() == current.getValue() && board[newRow][newCol].isCanCombine()) {
                board[newRow][newCol].setCanCombine(false);
                board[newRow][newCol].setValue(board[newRow][newCol].getValue() * 2);
                canMove = true;
                board[newRow - vertDir][newCol - horDir] = null;
                board[newRow][newCol].setSlideTo(new GridPoint(newRow, newCol));
                board[newRow][newCol].setCombineAnim(true);
                // add to score here
            } else {
                move = false;
            }
        }
        return canMove;
    }

    private void moveTiles(Direction dir) {
        boolean canMove = false;
        int horDir = 0;
        int vertDir = 0;

        if (dir == Direction.LEFT) {
            horDir = -1;
            for (int row = 0; row < ROWS; row++) {
                for (int col = 0; col < COLS; col++) {
                    if (!canMove) {
                        canMove = move(row, col, horDir, vertDir, dir);
                    } else {
                        move(row, col, horDir, vertDir, dir);
                    }
                }
            }
        } else if (dir == Direction.RIGHT) {
            horDir = 1;
            for (int row = 0; row < ROWS; row++) {
                for (int col = COLS - 1; col >= 0; col--) {
                    if (!canMove) {
                        canMove = move(row, col, horDir, vertDir, dir);
                    } else {
                        move(row, col, horDir, vertDir, dir);
                    }
                }
            }
        } else if (dir == Direction.UP) {
            vertDir = -1;
            for (int row = 0; row < ROWS; row++) {
                for (int col = 0; col < COLS; col++) {
                    if (!canMove) {
                        canMove = move(row, col, horDir, vertDir, dir);
                    } else {
                        move(row, col, horDir, vertDir, dir);
                    }
                }
            }
        } else if (dir == Direction.DOWN) {
            vertDir = 1;
            for (int row = ROWS - 1; row >= 0; row--) {
                for (int col = 0; col < COLS; col++) {
                    if (!canMove) {
                        canMove = move(row, col, horDir, vertDir, dir);
                    } else {
                        move(row, col, horDir, vertDir, dir);
                    }
                }
            }
        } else {
            System.out.println(dir + " is not a valid direction!");
        }

        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                Tile current = board[row][col];

                if (current == null) {
                    continue;
                }
                current.setCanCombine(true);
            }
        }

        if (canMove) {
            spawnRandom();
            checkDead();
        }
    }

    private boolean checkOutOfBounds(Direction dir, int row, int col) {
        if (dir == Direction.LEFT) {
            return col < 0;
        } else if (dir == Direction.RIGHT) {
            return col > COLS - 1;
        } else if (dir == Direction.UP) {
            return row < 0;
        } else if (dir == Direction.DOWN) {
            return row > ROWS - 1;
        }
        return false;
    }

    private void checkDead() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                if (board[row][col] == null) {
                    return;
                }
                if (checkSurroundingTiles(row, col, board[row][col])) {
                    return;
                }
            }
        }
        gameOver = true;
        // set high score here
    }

    private boolean checkSurroundingTiles(int row, int col, Tile current) {
        if (row > 0) {
            Tile check = board[row - 1][col];
            if (check == null) {
                return true;
            }
            if(current.getValue() == check.getValue()){
                return true;
            }
        }
        if(row < ROWS - 1){
            Tile check = board[row + 1][col];
            if(check == null){
                return true;
            }
            if(current.getValue() == check.getValue()){
                return true;
            }
        }
        if (col > 0) {
            Tile check = board[row][col - 1];
            if (check == null) {
                return true;
            }
            if(current.getValue() == check.getValue()){
                return true;
            }
        }
        if(col < COLS - 1){
            Tile check = board[row][col + 1];
            if(check == null){
                return true;
            }
            if(current.getValue() == check.getValue()){
                return true;
            }
        }
        return false;
    }

    private void spawnRandom() {
        Random random = new Random();
        boolean notValid = true;

        while (notValid) {
            int location = random.nextInt(ROWS * COLS);
            int row = location / ROWS;
            int col = location % COLS;

            Tile current = board[row][col];
            if (current == null) {
                int newTileValue = 2; // This could be made into a static variable if necessary
                Tile tile = new Tile(newTileValue, getTileX(col), getTileY(row));
                board[row][col] = tile;
                notValid = false;
            }
        }
    }

    public int getTileX(int col) {
        return SPACING + col * Tile.WIDTH + col * SPACING;
    }

    public int getTileY(int row) {
        return SPACING + row * Tile.HEIGHT + row * SPACING;
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