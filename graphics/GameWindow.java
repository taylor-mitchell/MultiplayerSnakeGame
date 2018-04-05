package graphics;

import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import InputHandler.Input;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

import java.nio.IntBuffer;

public class GameWindow
{
	private int width;
	private int height;
	private long lastFrameTime;
	private float deltaTime;
	private long window;
	private GLFWKeyCallback keyCallback;
	private GLFWErrorCallback errorCallback;

	public GameWindow(String title, int width, int height)
	{
		glfwSetErrorCallback(errorCallback = GLFWErrorCallback.createPrint(System.err));
		
        // This basically means, if this glfwInit() doesn't run properly
        // print an error to the console
        if(glfwInit() != true)
        {
            System.err.println("GLFW initialization failed!");
        }
        
        // Set the dimension of our window in pixels
        this.width = width;
        this.height = height;
        
        // Doesn't allows our window to be resizable
        glfwWindowHint(GLFW_RESIZABLE, GL_FALSE);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE); 

        // Creates our window.
        window = glfwCreateWindow(width, height, title, NULL, NULL);
        
        // This code performs the appropriate window was successfully created.
        if(window == NULL)
        {
            // Throw an Error
            System.err.println("Could not create our Window!");
        }
        
        //Initialize the input handler
        glfwSetKeyCallback(window, keyCallback = new Input());
        
        // Sets the context of GLFW, this is vital for our program to work.
        glfwMakeContextCurrent(window);
        GL.createCapabilities();
        
        // Enable v-sync
        glfwSwapInterval(1);
        
        // finally shows our created window in all it's glory.
        // In order to perform OpenGL rendering, a context must be "made current"
        // we can do this by using this line of code:
        glViewport(0, 0, width, height);
        
        // Clears color buffers and gives us a nice color background.
        glClearColor(1.f, 0.6f, 0.3f, 0);
        
        // Enables depth testing which will be important to make sure
        // triangles are not rendering in front of each other when they
        // shouldn't be.
        glEnable(GL_DEPTH_TEST);
        glEnable (GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        
        lastFrameTime = getCurrentTime();
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
		return System.nanoTime()/1000000;
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

	public boolean windowShouldClose()
	{
		return glfwGetKey(window, GLFW_KEY_ESCAPE) == GL_TRUE || glfwWindowShouldClose(window);
	}
	
	public void updateDisplay()
	{
		glfwSwapBuffers(window);
        glfwPollEvents(); 
        long currentFrameTime = getCurrentTime();
        deltaTime = (currentFrameTime - lastFrameTime)/1000f;
        lastFrameTime = currentFrameTime;
	}

	public void destroy()
	{
		glfwDestroyWindow(window);
	}
	
	public boolean isRightPressed() {
		return glfwGetKey(window, GLFW_KEY_RIGHT) == GL_TRUE;
	}
	
	public boolean isLeftPressed() {
		return glfwGetKey(window, GLFW_KEY_LEFT) == GL_TRUE;
	}
	
	public boolean isUpPressed() {
		return glfwGetKey(window, GLFW_KEY_UP) == GL_TRUE;
	}
}
