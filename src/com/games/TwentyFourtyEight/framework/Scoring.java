package com.games.TwentyFourtyEight.framework;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;

/*
 * Handles reading/writing Board.highScore to an outside SaveData file
 */
public class Scoring implements Serializable{

    private static final String dataFilePath = "data/score.dat";

    // CTOR
    public Scoring(){}

    public int getHighScore () {
        int highScore = 0;

        if(Files.exists(Path.of(dataFilePath))){
            try(DataInputStream in = new DataInputStream(new FileInputStream(dataFilePath))){
                highScore = in.readInt();
            }catch(IOException e){
                e.printStackTrace();
            }
        }
        return highScore;
    }

    public void save(int value) {
        try(DataOutputStream out = new DataOutputStream(new FileOutputStream(dataFilePath))){
            out.writeInt(value);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setHighScore (int currentScore, int highScore) {
        if (currentScore > highScore) {
            highScore = currentScore;
        }

        save(highScore);
    }
}