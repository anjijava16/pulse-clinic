/*
 * Copyright 2015 Vladimir Shin
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.pulse.desktop.view.panel;


import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;


/**
 * This is generic panel class, which can hold some background image. ImagePanel class can be used in
 * any class, where panel with some background image is needed.
 *
 * @author Vladimir Shin [vladimir.shin@gmail.com]
 */
public class ImagePanel
        extends JPanel {

    private Image backgroundImage; // This is the object, which holds some background image.

    /**
     * This is default constructor for the ImagePanel
     * class.
     * @param imageFile - This is the filename of the image, which will
     *                    be used as background image for current ImagePanel
     *                    instance.
     */
    public ImagePanel(String imageFile) {
        this(new ImageIcon(imageFile).getImage());
    }

    /**
     * This is overloaded constructor for the ImagePanel class.
     * @param image - This is the Image instance, which represent the background
     *                image for the current ImagePanel instance.
     */
    public ImagePanel(Image image) {
        this.backgroundImage = image;

        setSize(this.backgroundImage.getWidth(null), this.backgroundImage.getHeight(null));
    }

    /**
     * This function will draw background image in the panel.
     * @param g - This class is responsible for the drawing image in the
     *           current ImagePanel instance.
     */
    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(this.backgroundImage, 0, 0, null);
    }
}