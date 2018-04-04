package InputHandler;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallback;

public class Input extends GLFWKeyCallback
{
	// a boolean array of all our keys.
	public static boolean[] keys = new boolean[65535];
	public static boolean[] pKeys = new boolean[65535];
	public static char key;
	public static char pkey;
	// Overrides GLFW's own implementation of the Invoke method
	// This gets called everytime a key is pressed.

	@Override
	public void invoke(long window, int key, int scancode, int action, int mods)
	{
		keys[key] = action != GLFW.GLFW_RELEASE;

		if (action == GLFW.GLFW_RELEASE)
		{
			Input.key = (char) key;
			pKeys[key] = true;
		}
	}

	public static boolean isKeyDown(int keycode)
	{
		return keys[keycode];
	}

	public static boolean isKeyUp(int keycode)
	{
		return pKeys[keycode];
	}
}
