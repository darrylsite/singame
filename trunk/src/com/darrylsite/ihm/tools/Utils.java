package com.darrylsite.ihm.tools;

import java.awt.Image;
import java.awt.Toolkit;

/**
 *
 * @author nabster
 */
public class Utils
{
     public Image loadImage(String img)
     {
        return Toolkit.getDefaultToolkit().getImage(getClass().getClassLoader().getResource("images/" + img));
    }

}
