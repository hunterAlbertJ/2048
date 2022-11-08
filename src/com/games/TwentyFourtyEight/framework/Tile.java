package com.games.TwentyFourtyEight.framework;

import com.games.TwentyFourtyEight.controller.Game;

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
    private Font font;
    private int x;
    private int y;

    private GridPoint slideTo;

    // animation properties
    private boolean beginAnim = true;
    private double scaleFirst = 0.1;
    private BufferedImage startImage;

    private boolean combineAnim = false;
    private double scaleCombine = 1.2;
    private BufferedImage combineImage;
    private boolean canCombine = true;

    // ctor
    public Tile (int value, int x, int y){
        this.tileImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
        setValue(value);
        this.x = x;
        this.y = y;
        this.slideTo = new GridPoint(x,y);
        this.startImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
        this.combineImage = new BufferedImage(WIDTH*2,HEIGHT*2, BufferedImage.TYPE_INT_ARGB);
        this.drawImage();
    }

    /*
     * Draws tile
     */
    private void drawImage(){
        Graphics2D g = (Graphics2D) this.tileImage.getGraphics();

        switch (getValue()) {
            case 2: {
               setBackground(new Color(0x56FF00));
                setText(new Color(0xC702FF));
                break;
            }
            case 4: {
                setBackground(new Color(0xFFC4C4));
                setText(new Color(0x000000));
                break;
            }
            case 8: {
                setBackground(new Color(0xFF9F9F));
                setText(new Color(0x000000));
                break;
            }
            case 16: {
                setBackground(new Color(0xFF7272));
                setText(new Color(0x000000));
                break;
            }
            case 32: {
                setBackground(new Color(0xFF3232));
                setText(new Color(0x000000));
                break;
            }
            case 64: {
                setBackground(new Color(0xFF0000));
                setText(new Color(0x000000));
                break;
            }
            case 128: {
                setBackground(new Color(0x82B3FF));
                setText(new Color(0x000000));
                break;
            }
            case 256: {
                setBackground(new Color(0x3482FF));
                setText(new Color(0x000000));
                break;
            }
            case 512: {
                setBackground(new Color(0x0055FF));
                setText(new Color(0x000000));
                break;
            }
            case 1024: {
                setBackground(new Color(0x99FF81));
                setText(new Color(0x000000));
                break;
            }
            case 2048: {
                setBackground(new Color(0x0AFF00));
                setText(new Color(0x000000));
                break;
            }
//            default:{
//                background = new Color(0xFF0000);
//                text = new Color(0xFFFFFF);
//            }
        }

        g.setColor(new Color(0,0,0,0));
        g.fillRect(0,0, WIDTH, HEIGHT);

        g.setColor(getBackground());
        g.fillRoundRect(0,0, WIDTH, HEIGHT, ARC_WIDTH, ARC_HEIGHT);

        g.setColor(getText());

        if(getValue() <= 64){
            font = Game.mainFont.deriveFont(36f);
        } else{
            font = Game.mainFont;
        }
        g.setFont(font);

        int drawX = WIDTH / 2 - DrawUtils.getMessageWidth("" + value, font, g)/2;
        int drawY = HEIGHT / 2 + DrawUtils.getMessageHeight("" + value,font, g)/2;
        g.drawString("" + value, drawX, drawY);

        g.dispose();
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
            scaleCombine -= 0.05;
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

        try{
            this.drawImage();
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isCombineAnim() {
        return combineAnim;
    }

    public void setCombineAnim(boolean combineAnim) {
        this.combineAnim = combineAnim;
    }

    public boolean isCanCombine() {
        return canCombine;
    }

    public void setCanCombine(boolean canCombine) {
        this.canCombine = canCombine;
    }

    public GridPoint getSlideTo() {
        return slideTo;
    }

    public void setSlideTo(GridPoint slideTo) {
        this.slideTo = slideTo;
    }

    public Color getBackground() {
        return background;
    }

    public void setBackground(Color background) {
        this.background = background;
    }

    public Color getText() {
        return text;
    }

    public void setText(Color text) {
        this.text = text;
    }
}