package com.games.TwentyFourtyEight.client;

import com.games.TwentyFourtyEight.controller.Game;
import com.games.TwentyFourtyEight.framework.Board;

import javax.swing.JFrame;

/*
 * Main client.
 * Sets info for application window then starts the game.
 */
public class PlayClient {

    public static void main(String[] args) {
        Game game = new Game();
        JFrame window = new JFrame("2048");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.add(game);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);

        game.start();
    }
}