package graphics;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWKeyCallback;

public class GameWindow 
{
	private int width;
	private int height;
	private int fpsLimit;
	private long lastFrameTime;
	private float deltaTime;
	private long window;
	private GLFWErrorCallback errorCallback;
	private GLFWKeyCallback keyCallback;
	
	public GameWindow(String title, int width, int height)
	{
		super();
		this.width = width;
		this.height = height;
	}
	
	public int getWidth()
	{
		return width;
	}
	
	public int getHeight()
	{
		return height;
	}
	
	public long getCurrentTime()
	{
		return 0;
	}
	
	public long getLastFrameTime()
	{
		return lastFrameTime;
	}
	
	public float getDeltaTime()
	{
		return deltaTime;
	}
	
	public long getWindow()
	{
		return window;
	}
	
	public void updateDIsplay()
	{
		
	}
	
	public void destroy()
	{
		
	}
}
