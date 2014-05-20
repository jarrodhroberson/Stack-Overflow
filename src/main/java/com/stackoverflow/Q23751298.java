package com.stackoverflow;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Q23751298
{
    public static void main(String[] args)
    {
        try
        {
            //final BufferedImage img = ImageIO.read(new File("/Users/jhr/Pictures/fu4FM.gif"));
            final BufferedImage img = ImageIO.read(Q23751298.class.getResource("/fu4FM.gif"));
            final int match = new Color(209, 167, 86).getRGB();

            for (int x = 0; x < img.getWidth(); x++)
            {
                for (int y = 0; y < img.getHeight(); y++)
                {
                    final int irgb = img.getRGB(x, y);
                    if (irgb == match)
                    {
                        System.out.format("%d/%d = %d : %d\n", x, y, img.getRGB(x, y), match);
                    }
                }
            }
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
    }
}
