package com.pulse.desktop.view.model;

import java.awt.Graphics;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.Border;

/**
 *
 * @author befast
 */
public class ImagePasswordField extends JPasswordField {

    private String resource;
    
    public ImagePasswordField(String resource) {
        this.resource = resource;
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        try {
            System.out.println(this.resource);
            URL url = this.getClass().getResource(this.resource);
            final BufferedImage image = ImageIO.read(url);
            
            Border border = UIManager.getBorder("TextField.border");
            
            JPasswordField defaultField = new JPasswordField();
            final int x = getWidth() - border.getBorderInsets(defaultField).right - image.getWidth();
            setMargin(new Insets(2, 2, 2, getWidth() - x));
            int y = (getHeight() - image.getHeight()) / 2;
            
            g.drawImage(image, x, y, this);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
