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
 * @author Vladimir Shin [vladimir.shin@gmail.com]
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
