package graphics;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.lwjgl.opengl.GL11;

import misc.Utility;

public class Texture
{
	private int width;
	private int height;
	private int id;
	private int data[];
	
	public Texture(String filename)
	{
		data = decodePNG(filename);
        id = GL11.glGenTextures();
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, width, height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, Utility.createIntBuffer(data));
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
	}
	
	public int [] decodePNG(String filename)
    {
        int[] pixels = null;
        try{
            BufferedImage image = ImageIO.read(new FileInputStream(filename));
            width = image.getWidth();
            height = image.getHeight();
            pixels = new int[width * height];
            image.getRGB(0, 0,width, height, pixels, 0, width);
        } 
        catch (IOException e)
        {
            e.printStackTrace();
        }

        int[] data = new int[width * height];
        
        for(int i = 0; i < width * height; i++)
        {
            int a = (pixels[i] & 0xff000000) >> 24;
            int r = (pixels[i] & 0xff0000) >> 16;
            int g = (pixels[i] & 0xff00) >> 8;
            int b = (pixels[i] & 0xff);

            data[i] = a << 24 | b << 16 | g << 8 | r;
        }
        return data;
    }
	
	public int getWidth()
	{
		return width;
	}

	public int getHeight()
	{
		return height;
	}

	public int getId()
	{
		return id;
	}

	public int[] getData()
	{
		return data;
	}
}
