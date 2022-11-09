package com.games.TwentyFourtyEight.client;

import com.games.TwentyFourtyEight.controller.Game;
import com.games.TwentyFourtyEight.framework.Board;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import java.awt.*;

/*
 * Main client.
 * Sets info for application window then starts the game.
 */
public class PlayClient {
    public static void main(String[] args) {
        JLabel instructions = new JLabel(String.format("Controls - all numbered tiles slide in the direction a player moves them using the four arrow keys."
                + "\n" + "Tiles slide as far as possible in the chosen direction until they are stopped by either another tile or the edge of the grid."
                + "\n" + "Every turn, a new tile randomly appears in an empty spot on the board with a value of 2."
                + "\n" + "If two tiles of the same number collide while moving, they will combine totals into a single tile."
                + "\n" + "The resulting tile cannot merge with another tile again in the same move."
                + "\n" + "Higher-scoring tiles change colors."
                + "\n" + "If a move causes three consecutive tiles of the same value to slide together, only the two farthest Tiles will combine."
                + "\n" + "If all four spaces in a row or column are filled with the same value, a move parallel to that row/column will combine the first two and last two."
                + "\n\n" + "The game is won when a tile with a value of 2048 appears on the board."
                + "\n\n" + "When the player has no legal moves (there are no empty spaces and no adjacent tiles with the same value), the game is over."));


        Game game = new Game();
        JFrame window = new JFrame("Welcome to 2048");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setLayout(new BorderLayout());
//        window.add(instructions, BorderLayout.PAGE_START);
        window.add(game, BorderLayout.AFTER_LINE_ENDS);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        game.start();
    }
}