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
    public boolean winner = false;

    public static void main(String[] args) {
        String x = new StringBuffer().append("HOW TO PLAY")
                .append("\n\nCONTROLS: USING THE FOUR ARROW KEYS, all numbered tiles slide in the direction a player moves them.")
                .append("\n\n\nTiles slide as far as possible in the chosen direction until they are stopped by either another tile or the edge of the grid.")
                .append("\nEvery turn, a new tile randomly appears in an empty spot on the board with a value of 2.")
                .append("\nIf two tiles of the same number collide while moving, they will combine totals into a single tile.")
                .append("\nThe resulting tile cannot merge with another tile again in the same move.")
                .append("\nHigher-scoring tiles change colors.")
                .append("\nIf a move causes three consecutive tiles of the same value to slide together, only the two farthest Tiles will combine.")
                .append("\nIf all four spaces in a row or column are filled with the same value, a move parallel to that row/column will combine the first two and last two.")
                .append("\n\nThe game is won when a tile with a value of 2048 appears on the board.")
                .append("\n\nWhen the player has no legal moves (there are no empty spaces and no adjacent tiles with the same value), the game is over.")
                .toString();

        Game game = new Game();
        JFrame window = new JFrame("Welcome to 2048");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setLayout(new BorderLayout());
        window.add(game, BorderLayout.AFTER_LINE_ENDS);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        game.start();
        JOptionPane.showMessageDialog(null, x);
    }
}