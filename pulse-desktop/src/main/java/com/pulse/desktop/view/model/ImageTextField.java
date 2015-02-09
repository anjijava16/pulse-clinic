package com.pulse.desktop.view.model;

import java.awt.Graphics;
import java.awt.Insets;
import java.io.IOException;
import java.net.URL;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.Border;

/**
 *
 * @author befast
 */
public class ImageTextField extends JTextField {

    private String resource;
    
    public ImageTextField(String resource) {
        this.resource = resource;
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        try {
            URL url = this.getClass().getResource("D:/1.png");
            final java.awt.image.BufferedImage image = javax.imageio.ImageIO.read(url);
            
            Border border = UIManager.getBorder("TextField.border");
            
            JTextField defaultField = new JTextField();
            final int x = getWidth() - border.getBorderInsets(defaultField).right - image.getWidth();
            setMargin(new Insets(2, 2, 2, getWidth() - x));
            int y = (getHeight() - image.getHeight()) / 2;
            
            g.drawImage(image, x, y, this);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
