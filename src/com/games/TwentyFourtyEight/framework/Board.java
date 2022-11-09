package com.games.TwentyFourtyEight.framework;

import com.games.TwentyFourtyEight.client.PlayClient;
import com.games.TwentyFourtyEight.controller.Controller;
import com.games.TwentyFourtyEight.controller.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.NoSuchElementException;
import java.util.Random;

/*
 * Defines all graphical updates for the game Board
 * Calls graphics updates for Board and Tile each frame
 * Handles Tile movement through memory.
 * Checks for moves remaining
 */
public class Board {
    // you can create whatever size board you want, as long as it is a square (2x2, 3x3, 4x4, 5x5, etc)
    public static final int ROWS = 4;
    public static final int COLS = 4;
    public int WIN_VALUE = 2048;

    // number of random Tiles to start with
    private final int startingTiles = 2;

    // a 2dArray which represents internal memory for the game Board
    private final Tile[][] board;

    private boolean gameOver;
    private boolean winner;
    private boolean winNotice = false;
    private boolean loseNotice = false;

    // these will hold references to the graphics for the game Board
    private BufferedImage gameBoard;
    private BufferedImage updatedBoard;
    private int x;
    private int y;

    // for saving data
    private int currentScore = 0;
    private int highScore = 0;
    private Font displayFont = new Font("American Typewriter", Font.PLAIN, 26);
    Scoring scoreMethods = new Scoring();
//    private static String saveFilePath;
//    private static final String dataFilePath = "data/board.dat";
//    private static final String fileName = "SaveData";

    // reference to keyboard manager
    Controller controller = new Controller();

    // defines spacing between Tiles
    private static int SPACING = 20;

    // formula for Board dimensions
    public static int BOARD_WIDTH = (COLS + 1) * SPACING + COLS * Tile.WIDTH;
    public static int BOARD_HEIGHT = (COLS + 1) * SPACING + COLS * Tile.HEIGHT;

    // CTOR
    public Board(int x, int y) {
        this.x = x;
        this.y = y;
        board = new Tile[ROWS][COLS];
        gameBoard = new BufferedImage(BOARD_WIDTH, BOARD_HEIGHT, BufferedImage.TYPE_INT_RGB);
        updatedBoard = new BufferedImage(BOARD_WIDTH, BOARD_HEIGHT, BufferedImage.TYPE_INT_RGB);

        // begin Board logic here
        try {
            scoreMethods.getFilePath();
        } catch (Exception e) {
            e.printStackTrace();
        }

        createGameBoard();

        try {
            highScore = scoreMethods.getHighScore();
        } catch (IOException e) {
            e.printStackTrace();
        }
        start();
    }

        /*
         * Creates the graphics for a new Board
         */
        private void createGameBoard () {
            // create a reference to the Board's graphics, define background colors and draw a rectangle
            Graphics2D g = (Graphics2D) gameBoard.getGraphics();
            g.setColor(Color.darkGray);
            g.fillRect(0, 0, BOARD_WIDTH, BOARD_HEIGHT);
            g.setColor(Color.lightGray);

            // create the grid of rounded squares, each with proper spacing
            for (int row = 0; row < ROWS; row++) {
                for (int col = 0; col < COLS; col++) {
                    int x = SPACING + SPACING * col + Tile.WIDTH * col;
                    int y = SPACING + SPACING * row + Tile.WIDTH * row;
                    g.fillRoundRect(x, y, Tile.WIDTH, Tile.HEIGHT, Tile.ARC_WIDTH, Tile.ARC_HEIGHT);
                }
            }
        }

        /*
         * starts Board logic
         */
        private void start () {
            for (int i = 0; i < startingTiles; i++) {
                spawnRandom();
            }
        }

        /*
         * Update() function runs continuously until Thread is stopped.
         * listens for user input from keyboard
         * continuously redraws and updates all Tile positions in board[][]
         * declares win state once a tile has reached 2048
         */
        public void update () {
            // waits for user input to move tiles
            checkKeys();

            // setting highScore
            if (currentScore > highScore) {
                highScore = currentScore;
            }

            // traversing through the board[][]
            for (int row = 0; row < ROWS; row++) {
                for (int col = 0; col < COLS; col++) {
                    Tile currentTile = board[row][col];

                    // ignore null Tiles (grey squares)
                    if (currentTile == null) {
                        continue;
                    }
                    // update Tile graphics
                    currentTile.update();

                    // constantly updates Tile positions
                    resetPosition(currentTile, row, col);

                    // check for win status
                    if (currentTile.getValue() == WIN_VALUE) {
                        setWinner(true);

                        // try to set high score
                        try {
                            scoreMethods.setHighScore(currentScore, highScore);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

        /*
         * Traverses through board[][] and renders the current Tile to the Board, then renders the Board to the GUI
         */
        public void render (Graphics2D g){
            // get reference to the Board's updated graphics
            Graphics2D g2d = (Graphics2D) updatedBoard.getGraphics();

            g2d.drawImage(gameBoard, 0, 0, null);       // draw updated board to the GUI

            // renders all updated Tiles
            for (int row = 0; row < ROWS; row++) {
                for (int col = 0; col < COLS; col++) {
                    Tile current = board[row][col];
                    if (current == null) {
                        continue;
                    }
                    current.render(g2d);
                }
            }
            g.drawImage(updatedBoard, x, y, null);  // draw new board with updated Tiles to GUI
            g2d.dispose();                                  // dispose of old board from memory

            g.setColor(Color.lightGray);
            g.setFont(displayFont);
            g.drawString("" + currentScore, 30, 40);
            g.setColor(Color.red);
            g.drawString("HighScore: " + highScore, Game.WIDTH - DrawUtils.getMessageWidth("HighScore: " + highScore, displayFont, g), 40);
        }

        /*
         * Moves the current Tile through the board[][], simulating movement on a grid
         * Also handles combining of Tile values into a single Tile
         * Handles movement of Tiles through memory, refer to resetPosition() for Tile positional movement
         * inputs: int row, col=coordinates
         *         int horDir, vertDir=indicate 'left or right' along the 'Columns' and 'up and down' along the 'Rows' (-1, 0, 1)
         *         Direction dir=user selected Directional enum
         * return: boolean
         */
        private boolean move ( int row, int col, int horDir, int vertDir, Direction dir){
            boolean canMove = false;

            // a reference to the current tile being examined in the board[][]
            Tile current = board[row][col];
            if (current == null) {
                return false;
            }

            boolean move = true;
            int newCol = col;
            int newRow = row;

            // while move is true, will continuously try to move the Tile until it reaches an obstacle
            while (move) {
                newCol += horDir; // moves the tile by column
                newRow += vertDir;// moves tile by row

                // check to see if destination is inside grid coordinates, break loop if true
                if (checkOutOfBounds(dir, newRow, newCol)) {
                    break;
                }

                // if there is no tile at the target location...
                if (board[newRow][newCol] == null) {

                    board[newRow][newCol] = current;                                 // have the current Tile take the new position
                    board[newRow - vertDir][newCol - horDir] = null;                 // remove the current Tile from the old position
                    board[newRow][newCol].setSlideTo(new GridPoint(newRow, newCol)); // set the slideTo for the current Tile to begin Tile movement
                    canMove = true;

                    // if there is a tile, and we can combine it with the current tile...
                } else if (board[newRow][newCol].getValue() == current.getValue() && board[newRow][newCol].isCanCombine()) {
                    board[newRow][newCol].setCanCombine(false);                            // set Tile.canCombine to false on target Tile so combining only happens once
                    board[newRow][newCol].setValue(board[newRow][newCol].getValue() * 2);  // set Tile.value to double original value (tiles have 'merged')
                    board[newRow - vertDir][newCol - horDir] = null;                       // destroy the old Tile that was combined
                    board[newRow][newCol].setSlideTo(new GridPoint(newRow, newCol));       // set the slideTo for the current Tile to begin Tile movement
                    board[newRow][newCol].setCombineAnim(true);                            // set Tile.combineAnim to true on target to begin the combining animation
                    canMove = true;

                    // add to score here
                    currentScore += board[newRow][newCol].getValue();

                    // if there is no free space to move or a tile that can be combined with, do nothing
                } else {
                    move = false;
                }
            }

            // return false if the Tile can't be moved any longer
            return canMove;
        }

        /*
         * Assigns a movement "vector" according to the Direction entered by the user
         * then calls move(Direction) to check if current Tile can move
         */
        private void moveTiles (Direction dir){
            boolean canMove = false;
            int horDir = 0;
            int vertDir = 0;

            // checks for user input Direction...
            if (dir == Direction.LEFT) {
                horDir = -1; // assigns a horizontalDirection (-1=left, 1=right) for LEFT/RIGHT

                // checks each Tile on the board[][]...
                for (int row = 0; row < ROWS; row++) {
                    for (int col = 0; col < COLS; col++) {
                        // if canMove is false, this means the user has run out of valid movements and the game ends
                        if (!canMove) {
                            // first checks to see if combining will create more moves
                            canMove = move(row, col, horDir, vertDir, dir);

                            // else move like normal
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
                throw new NoSuchElementException("Invalid DIRECTION: " + dir);
            }

            // after all movement, reset Tile.canCombine for all Tiles in the board[][]
            for (int row = 0; row < ROWS; row++) {
                for (int col = 0; col < COLS; col++) {
                    Tile current = board[row][col];

                    if (current == null) {
                        continue;
                    }
                    current.setCanCombine(true);
                }
            }

            // as long as there are still moves left spawn a new Tile then check if that new Tile would end the game
            if (canMove) {
                spawnRandom();
                checkDead();
            }
        }

        /*
         * Moves the current Tile into their new position each frame via update()
         * Handles the animated movement of Tiles
         */
        private void resetPosition (Tile current,int row, int col){
            // if the current Tile is null do nothing
            if (current == null) {
                return;
            }

            int x = getTileX(col); // get Tile x position + spacing
            int y = getTileY(row); // get Tile y position + spacing

            // get distance from original pos to new spacing,
            // since this happens in realtime, it appears to be movement by the Tile
            int distX = current.getX() - x;
            int distY = current.getY() - y;

            // continues to set Tile position until Tile has moved entire distance
            if (Math.abs(distX) < Tile.SLIDE_SPEED) {
                current.setX(current.getX() - distX);
            }
            if (Math.abs(distY) < Tile.SLIDE_SPEED) {
                current.setY(current.getY() - distY);
            }

            // depending on the direction, SLIDE_SPEED is added or subtracted
            if (distX < 0) {
                current.setX(current.getX() + Tile.SLIDE_SPEED);
            }
            if (distY < 0) {
                current.setY(current.getY() + Tile.SLIDE_SPEED);
            }
            if (distX > 0) {
                current.setX(current.getX() - Tile.SLIDE_SPEED);
            }
            if (distY > 0) {
                current.setY(current.getY() - Tile.SLIDE_SPEED);
            }
        }

        // CHECK METHODS

        /*
         * Monitors user inputs via the controller, calls controller for keyboard manager
         * Runs inside update()
         */
        private void checkKeys () {
            if (controller.typed(KeyEvent.VK_LEFT)) {
                moveTiles(Direction.LEFT);
            }
            if (controller.typed(KeyEvent.VK_RIGHT)) {
                moveTiles(Direction.RIGHT);
            }
            if (controller.typed(KeyEvent.VK_UP)) {
                moveTiles(Direction.UP);
            }
            if (controller.typed(KeyEvent.VK_DOWN)) {
                moveTiles(Direction.DOWN);
            }
        }

        /*
         * Checks if the destination of the current Tile being moved is a valid area inside the "grid" of board[][]
         * return: boolean
         */
        private boolean checkOutOfBounds (Direction dir,int row, int col){
            if (dir == Direction.LEFT) {
                return col < 0;
            } else if (dir == Direction.RIGHT) {
                return col > COLS - 1;            // COLS/ROWS used as dynamic constraints for the board[][]
            } else if (dir == Direction.UP) {
                return row < 0;
            } else if (dir == Direction.DOWN) {
                return row > ROWS - 1;
            }
            return false;
        }

        /*
         * Check if the game is over by calling checkSurroundingTiles() to look for valid movements for each Tile in the game
         * if one can be found, the game continues
         * if not, set the game as over and record the high score
         */
        private void checkDead () {
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
            if(loseNotice == false){
                String loseMessage = new StringBuffer().append("There are no more valid moves.")
                        .append("\nTry again!").toString();

                JOptionPane.showMessageDialog(null, loseMessage);
                loseNotice = true;
                gameOver = true;
            }

            // try to set high score
            try {
                scoreMethods.setHighScore(currentScore, highScore);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        /*
         * Checks the positions in memory to each side of the current Tile on the board[][]
         * If a valid move can be made return true
         */
        private boolean checkSurroundingTiles ( int row, int col, Tile current){
            if (row > 0) {
                Tile check = board[row - 1][col];
                if (check == null) {
                    return true;
                }
                if (current.getValue() == check.getValue()) {
                    return true;
                }
            }
            if (row < ROWS - 1) {
                Tile check = board[row + 1][col];
                if (check == null) {
                    return true;
                }
                if (current.getValue() == check.getValue()) {
                    return true;
                }
            }
            if (col > 0) {
                Tile check = board[row][col - 1];
                if (check == null) {
                    return true;
                }
                if (current.getValue() == check.getValue()) {
                    return true;
                }
            }
            if (col < COLS - 1) {
                Tile check = board[row][col + 1];
                if (check == null) {
                    return true;
                }
                if (current.getValue() == check.getValue()) {
                    return true;
                }
            }
            return false;
        }

        /*
         * Spawn a Tile into memory in a random null location in the board[][]
         */
        private void spawnRandom () {
            Random random = new Random();
            boolean notValid = true;

            // assumes the location is not a valid one until proven
            while (notValid) {
                // gets a random int between 0 - 16
                int location = random.nextInt(ROWS * COLS);
                // creates a row/col value based on the location
                int row = location / ROWS;
                int col = location % COLS;

                // creates a reference to that location in the board[][]
                Tile current = board[row][col];

                // when an empty spot in the board[][] is found, fill it with the new Tile
                if (current == null) {
                    int newTileValue = 2; // This could be made into a static variable if necessary, or randomized to add new values

                    Tile tile = new Tile(newTileValue, getTileX(col), getTileY(row));
                    board[row][col] = tile;// adds the new Tile to memory
                    notValid = false;
                }
            }
        }

        // ACCESSOR METHODS
        /*
         * Helper function meant to help create horizontal spacing for newly positioned Tiles
         */
        public int getTileX ( int col){
            return SPACING + col * Tile.WIDTH + col * SPACING;
        }

        /*
         * Helper function meant to help create vertical spacing for newly positioned Tiles
         */
        public int getTileY ( int row){
            return SPACING + row * Tile.HEIGHT + row * SPACING;
        }

    public boolean isWinner() {
        return winner;
    }

    public void setWinner(boolean winner) {
        this.winner = winner;
        if(winNotice == false){
            String winMessage = new StringBuffer().append("You have reached 2048!")
                            .append("\nThe game is won, but feel free to max your high score!")
                            .append("\n\nGo for the ALL TIME BEST!").toString();

            JOptionPane.showMessageDialog(null, winMessage);
            winNotice = true;
        }

    }
}