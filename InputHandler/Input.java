package InputHandler;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWKeyCallback;

public class Input extends GLFWKeyCallback
{
	private boolean keys[] = new boolean[65536]; // 65536 = Maximum number of keys( maximum unsigned short value)
	
	/**
	 *  Overrides GLFW's own implementation of the Invoke method.
	 *  This gets called everytime a key is pressed.
	 */
	public void invoke(long window, int key, int scancode, int action, int mods)
	{
		keys[key] = action != GLFW.GLFW_RELEASE;
	}

	/**
	 * 
	 * @param keycode - The the GLFW code of the key to test in [0 - 65535]
	 * @return True if the specified key is pressed, false otherwise
	 */
	public boolean isKeyPressed(int keycode)
	{
		return keys[keycode];
	}
}
