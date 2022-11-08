package com.games.TwentyFourtyEight.controller;

import com.games.TwentyFourtyEight.framework.Board;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

/*
 * Main game loop class.
 * Manages runtime/update functions.
 *
 */
public class Game extends JPanel implements KeyListener, Runnable {
    private static final long serialVersionUID = 1L;

    // Dimensions of game window
    public static final int WIDTH = 400;
    public static final int HEIGHT = 630;

    public static final Font main = new Font("Bebas Neue Regular", Font.PLAIN, 28);


    private Thread game;
    private boolean running;

    // the image to be rendered
    private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);

    // reference to our game board
    private Board board;

    // system time frame data variables
    private long startTime;
    private long elapsed;
    private boolean set;

    public Game() {

        setFocusable(true);      // sets the game window as a focusable component
        setPreferredSize(new Dimension(WIDTH, HEIGHT)); // set dimensions of game window
        addKeyListener(this);  // add listener for Key commands

        // create new game board using the game window's dimensions
        board = new Board(WIDTH/2 - Board.BOARD_WIDTH/2, HEIGHT - Board.BOARD_HEIGHT - 10);
    }

    // this will call to update our Board and listen for KeyPresses eventually
    private void update() {
        board.update();
        Controller.update();
    }

    /*
     * Renders the board then draws it to the game window.
     */
    private void render() {
        Graphics2D g = (Graphics2D) image.getGraphics(); // gets a reference to the image graphics
        g.setColor(Color.white);                         // set game window background color
        g.fillRect(0, 0, WIDTH, HEIGHT);           // create a rectangle as the game window using the defined dimensions
        board.render(g);                                 // render the board
        g.dispose();                                     // dispose of data so it's not sitting in memory

        Graphics2D g2d = (Graphics2D) getGraphics();      // create a reference to the window graphics
        g2d.drawImage(image, 0, 0, null);   // draw the board image to the game window
        g2d.dispose();                                   // dispose of data so it's not sitting in memory
    }


    // key listener functions
    @Override
    public void keyTyped(KeyEvent e) {
        // unused
    }

    @Override
    public void keyPressed(KeyEvent e) {
        Controller.keyPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        Controller.keyReleased(e);
    }

    /*
     * Defines logic at runtime for update loop.
     */
    @Override
    public void run() {
        int fps = 0, updates = 0;
        long fpsTimer = System.currentTimeMillis();     // set fpsTimer to the current time in runtime memory
        double nsPerUpdate = 1_000_000_000.0 / 60;      // finding frames per second

        double then = System.nanoTime();                // update time in nanoseconds
        double unprocessed = 0;

        // running will remain true until we call Game.stop() or exit the app
        while (running) {
            boolean shouldRender = false;              // is it time to render a new image?
            double now = System.nanoTime();            // system time "now", in nano-seconds
            unprocessed += (now - then) / nsPerUpdate; // assigned to reflect if time has passed since a player last made a decision
            then = now;                                // catch up to current system time

            // update queue
            while (unprocessed >= 1) {
                updates++;
                update();
                unprocessed--;                         // unprocessed will decrement until all updates are complete
                shouldRender = true;                   // should call the render function
            }

            // rendering the image
            if (shouldRender) {                        // calls render() as all updates are finished
                fps++;
                render();
                shouldRender = false;

            } else {                                   // if we should not be rendering...
                try {
                    Thread.sleep(1);              // try to put the Thread to sleep
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        // Resets FPS Timer
        if (System.currentTimeMillis() - fpsTimer > 1000) {
            System.out.println("FPS: " + fps + ", Updates: " + updates);

            fps = 0;
            updates = 0;
            fpsTimer += 1000;                                             // fpsTimer is reset to 1000 repeatedly
        }
    }

    /*
     * Starts our Thread
     */
    public synchronized void start(){
        if(running)                                  // if game is already running, return out early and do nothing
            return;

        running = true;
        game = new Thread(this, "game");
        game.start();
    }

    /*
     * Stops our Thread
     */
    public void stop(){
        if(!running)                                 // if game is already stopped, return out early and do nothing
            return;

        running = false;
        System.exit(0);
    }
}