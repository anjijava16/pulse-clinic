package com.pulse.desktop.view.panel;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * This is generic panel class, which can hold some 
 * background image. ImagePanel class can be used in 
 * any class, where panel with some background image 
 * is needed.
 * @author befast
 */
public class ImagePanel 
    extends JPanel {

    private Image backgroundImage; // This is the object, which holds some background image.

    /**
     * This is default contructor for the ImagePanel
     * class.
     * @param String imageFile - This is the filename of the image, which will
     *                           be used as background image for current ImagePanel
     *                           instance.
     */
    public ImagePanel(String imageFile) {
        this(new ImageIcon(imageFile).getImage());
    }

    /**
     * This is overloaded constructor for the ImagePanel class.
     * @param Image image - This is the Image instance, which represent the background
     *                      image for the current ImagePanel instance.
     */
    public ImagePanel(Image image) {
        this.backgroundImage = image;
        
        setSize(this.backgroundImage.getWidth(null), this.backgroundImage.getHeight(null));
    }

    /**
     * This function will draw background image in the panel.
     * @param Graphics g - This class is responsiable for the drawing image in the
     *                     current ImagePanel instance.
     */
    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(this.backgroundImage, 0, 0, null);
    }
}
