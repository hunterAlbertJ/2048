package com.games.TwentyFourtyEight.framework;

import java.io.*;
import java.net.URISyntaxException;

/*
 * Handles reading/writing Board.highScore to an outside SaveData file
 */
public class Scoring {
    private static String dataFilePath = "";
    private static final String fileName = "SaveData";

    public Scoring(){}

    public void getFilePath() throws URISyntaxException, NullPointerException {
        try {
            dataFilePath = Board.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public int getHighScore () throws IOException {
        int highScore = 0;
        try {
            File file = new File(dataFilePath, fileName);

            if (!file.isFile()) {
                throw new NullPointerException("No SaveData found!");
            } else {
                BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
                highScore = Integer.parseInt(reader.readLine());
                reader.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return highScore;
    }

    public void setHighScore (int currentScore, int highScore) throws IOException {
        FileWriter output;
        if (currentScore > highScore) {
            highScore = currentScore;
        }

        try {
            File file = new File(dataFilePath, fileName);
            output = new FileWriter(file);
            BufferedWriter writer = new BufferedWriter(output);
            writer.write("" + highScore);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}