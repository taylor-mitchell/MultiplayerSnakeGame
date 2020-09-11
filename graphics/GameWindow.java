package graphics;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MAJOR;
import static org.lwjgl.glfw.GLFW.GLFW_CONTEXT_VERSION_MINOR;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_ESCAPE;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_CORE_PROFILE;
import static org.lwjgl.glfw.GLFW.GLFW_OPENGL_PROFILE;
import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwGetKey;
import static org.lwjgl.glfw.GLFW.glfwGetTime;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;
import static org.lwjgl.glfw.GLFW.glfwSetKeyCallback;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.system.MemoryUtil.NULL;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

import InputHandler.Input;

public class GameWindow
{
	private int width;
	private int height;
	private double lastFrameTime;
	private double deltaTime;
	private long window;
	private Input input;
	private GLFWErrorCallback errorCallback;

	public GameWindow(String title, int width, int height)
	{
		glfwSetErrorCallback(errorCallback = GLFWErrorCallback.createPrint(System.err));

		// This basically means, if this glfwInit() doesn't run properly
		// print an error to the console
		if (glfwInit() != true)
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
		if (window == NULL)
		{
			// Throw an Error
			System.err.println("Could not create our Window!");
		}

		// Initialize the input handler
		glfwSetKeyCallback(window, input = new Input());

		// Sets the context of GLFW, this is vital for our program to work.
		glfwMakeContextCurrent(window);
		GL.createCapabilities();

		// Enable v-sync
		// 0 = unlimited framerate
		// 1 = 60 fps
		// 2 = 30 fps
		glfwSwapInterval(1);

		// finally shows our created window in all it's glory.
		// In order to perform OpenGL rendering, a context must be "made
		// current"
		// we can do this by using this line of code:
		glViewport(0, 0, width, height);

		// Clears color buffers and gives us a nice color background.
		glClearColor(1.f, 0.6f, 0.3f, 0);

		// Enables depth testing which will be important to make sure
		// triangles are not rendering in front of each other when they
		// shouldn't be.
		glEnable(GL_DEPTH_TEST);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

		lastFrameTime = glfwGetTime();
	}

	public int getWidth()
	{
		return width;
	}

	public int getHeight()
	{
		return height;
	}

	public double getLastFrameTime()
	{
		return lastFrameTime;
	}

	public double getDeltaTime()
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
		double currentFrameTime = glfwGetTime();
		deltaTime = currentFrameTime - lastFrameTime;
		lastFrameTime = currentFrameTime;
	}

	public void destroy()
	{
		// Free the window callbacks and destroy the window
		glfwFreeCallbacks(window);
		glfwDestroyWindow(window);

		// Terminate GLFW and free the error callback
		glfwTerminate();
		glfwSetErrorCallback(null).free();
	}

	public boolean isKeyPressed(int keycode)
	{
		return input.isKeyPressed(keycode);
	}
}
