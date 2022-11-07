package com.games.TwentyFourtyEight.framework;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Tile {
    public static final int WIDTH = 80;
    public static final int HEIGHT = 80;
    public static final int SLIDE_SPEED = 20;
    public static final int ARC_WIDTH = 15;
    public static final int ARC_HEIGHT = 15;

    private int value;
    private BufferedImage tileImage;
    private Color background;
    private Color text;
    private int x;
    private int y;

    public Tile (int value, int x, int y){
        setValue(value);
        this.x = x;
        this.y = y;
        tileImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
        drawImage();
    }

    /*
     * Draws tile
     */
    private void drawImage(){
        Graphics2D g = (Graphics2D) tileImage.getGraphics();

        switch (value) {
            case 2: {
                background = new Color(0xe9e9e9);
                text = new Color(0x000000);
            }
            case 4: {
                background = new Color(0xFFC4C4);
                text = new Color(0x000000);
            }
            case 8: {
                background = new Color(0xFF9F9F);
                text = new Color(0x000000);
            }
            case 16: {
                background = new Color(0xFF7272);
                text = new Color(0x000000);
            }
            case 32: {
                background = new Color(0xFF3232);
                text = new Color(0x000000);
            }
            case 64: {
                background = new Color(0xFF0000);
                text = new Color(0x000000);
            }
            case 128: {
                background = new Color(0x82B3FF);
                text = new Color(0x000000);
            }
            case 256: {
                background = new Color(0x3482FF);
                text = new Color(0x000000);
            }
            case 512: {
                background = new Color(0x0055FF);
                text = new Color(0x000000);
            }
            case 1024: {
                background = new Color(0x99FF81);
                text = new Color(0x000000);
            }
            case 2048: {
                background = new Color(0x0AFF00);
                text = new Color(0x000000);
            }
            default:{
                background = new Color(0x000000);
                text = new Color(0xFFFFFF);
            }
        }

        g.setColor(new Color(0,0,0,0));
        g.fillRect(0,0, WIDTH, HEIGHT);

        g.setColor(background);
        g.fillRoundRect(0,0, WIDTH, HEIGHT, ARC_WIDTH, ARC_HEIGHT);

        g.setColor(text);
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}