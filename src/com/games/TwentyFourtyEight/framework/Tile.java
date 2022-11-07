package com.games.TwentyFourtyEight.framework;

import java.awt.*;
import java.awt.geom.AffineTransform;
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

    private Point slideTo;

    // animation properties
    private boolean beginAnim = true;
    private double scaleFirst = 0.1;
    private BufferedImage startImage;

    private boolean combineAnim = false;
    private double scaleCombine = 1.2;
    private BufferedImage combineImage;
    private boolean canCombine = true;

    // ctors
    public Tile (int value, int x, int y){
        setValue(value);
        this.x = x;
        this.y = y;
        slideTo = new Point(x,y);
        tileImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
        startImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
        combineImage = new BufferedImage(WIDTH*2,HEIGHT*2, BufferedImage.TYPE_INT_ARGB);
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

    public void update(){
        if(beginAnim){
            AffineTransform transform = new AffineTransform();
            transform.translate(WIDTH/2-scaleFirst*WIDTH/2, HEIGHT/2 - scaleFirst*HEIGHT/2);
            transform.scale(scaleFirst, scaleFirst);
            Graphics2D g2d = (Graphics2D) startImage.getGraphics();
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
            g2d.setColor(new Color(0,0,0,0));
            g2d.fillRect(0,0, WIDTH,HEIGHT);
            g2d.drawImage(tileImage, transform, null);
            scaleFirst += 0.1;
            g2d.dispose();

            if(scaleFirst >= 1){
                beginAnim = false;
            }
        } else if(combineAnim) {
            AffineTransform transform = new AffineTransform();
            transform.translate(WIDTH / 2 - scaleCombine * WIDTH / 2, HEIGHT / 2 - scaleCombine * HEIGHT / 2);
            transform.scale(scaleCombine, scaleCombine);
            Graphics2D g2d = (Graphics2D) combineImage.getGraphics();
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
            g2d.setColor(new Color(0, 0, 0, 0));
            g2d.fillRect(0, 0, WIDTH, HEIGHT);
            g2d.drawImage(tileImage, transform, null);
            scaleCombine -= 0.1;
            g2d.dispose();

            if (scaleCombine <= 1) {
                combineAnim = false;
            }
        }
    }

    public void render(Graphics2D g){
        if(beginAnim){
            g.drawImage(startImage, x,y,null);
        } else if(combineAnim){
            g.drawImage(combineImage,
                    (int)(x + WIDTH/2-scaleCombine*WIDTH/2),
                    (int)(y + HEIGHT/2-scaleCombine*HEIGHT/2),
                    null);
        } else {
            g.drawImage(tileImage, x, y, null);
        }
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public boolean isCombineAnim() {
        return combineAnim;
    }

    public void setCombineAnim(boolean combineAnim) {
        this.combineAnim = combineAnim;
    }
}