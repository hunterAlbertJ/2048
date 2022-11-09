package com.games.TwentyFourtyEight.framework;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Scoring {
    private static String saveFilePath;
    private static String dataFilePath = "data/board.dat";
    private static final String fileName = "SaveData";

    public Scoring(){};

    public void getFilePath() throws IOException, URISyntaxException, NullPointerException {
        try {
            dataFilePath = Board.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
            createSaveFile();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public void createSaveFile() throws IOException {
//        if(Files.exists(Path.of(dataFilePath))){
//            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(dataFilePath))) {
//                System.out.println("InputStream object: " + in.readObject());
//
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//        }
    }

    public int getHighScore () throws IOException {
        int highScore = 0;
        try {
            File file = new File(dataFilePath, fileName);

            if (!file.isFile()) {
                createSaveFile();
            } else {
                BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
                //System.out.println("READER: "+ reader.readLine());

                highScore = Integer.parseInt(reader.readLine());
                reader.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return highScore;
    }

    public void setHighScore (int currentScore, int highScore) throws IOException {
        FileWriter output = null;
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