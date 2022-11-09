package com.games.TwentyFourtyEight.framework;

import java.awt.*;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;

/*
 * Utility class used to format strings to fit correctly in graphics
 */
public class DrawUtils {
    private DrawUtils(){}

    // creates a horizontal bounds depending on message length
    public static int getMessageWidth(String message, Font font, Graphics2D g){
        g.setFont(font);
        Rectangle2D bounds = g.getFontMetrics().getStringBounds(message, g);
        return (int)bounds.getWidth();
    }

    // creates a vertical bounds depending on message length
    public static int getMessageHeight(String message, Font font, Graphics2D g){
        g.setFont(font);
        if(message.length() == 0){
            return 0;
        }
        TextLayout tl = new TextLayout(message, font, g.getFontRenderContext());
        return (int) tl.getBounds().getHeight();
    }
}