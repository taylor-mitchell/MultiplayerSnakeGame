package graphics;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.GL_COMPILE_STATUS;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glBindAttribLocation;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glDeleteProgram;
import static org.lwjgl.opengl.GL20.glDeleteShader;
import static org.lwjgl.opengl.GL20.glDetachShader;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;
import static org.lwjgl.opengl.GL20.glGetShaderi;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glShaderSource;
import static org.lwjgl.opengl.GL20.glUniform1f;
import static org.lwjgl.opengl.GL20.glUniform1i;
import static org.lwjgl.opengl.GL20.glUniform3f;
import static org.lwjgl.opengl.GL20.glUniformMatrix4fv;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL20.glValidateProgram;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;

import math.Matrix4f;
import math.Vector3f;
import misc.Utility;

public abstract class ShaderProgram
{
	private int programID;
	private int vertexShaderID;
	private int fragmentShaderID;

	public ShaderProgram(String vertexFilename, String fragmentFilename)
	{
		vertexShaderID = loadShader(vertexFilename, GL_VERTEX_SHADER);
		fragmentShaderID = loadShader(fragmentFilename, GL_FRAGMENT_SHADER);
		programID = glCreateProgram();

		glAttachShader(programID, vertexShaderID);
		glAttachShader(programID, fragmentShaderID);

		bindAttributes();
		glLinkProgram(programID);
		glValidateProgram(programID);
		getAllUniformsLocation();
	}

	protected abstract void getAllUniformsLocation();

	protected int getUniformLocation(String uniformName)
	{
		return glGetUniformLocation(programID, uniformName);
	}

	public void start()
	{
		glUseProgram(programID);
	}

	public void stop()
	{
		glUseProgram(0);
	}

	public void cleanUp()
	{
		stop();
		glDetachShader(programID, vertexShaderID);
		glDetachShader(programID, fragmentShaderID);
		glDeleteShader(vertexShaderID);
		glDeleteShader(fragmentShaderID);
		glDeleteProgram(programID);
	}

	protected abstract void bindAttributes();

	protected void bindAttribute(int attribute, String variableName)
	{
		glBindAttribLocation(programID, attribute, variableName);
	}

	protected void loadFloat(int location, float value)
	{
		glUniform1f(location, value);
	}

	protected void loadMatrix(int location, Matrix4f matrix)
	{
		glUniformMatrix4fv(location, true, Utility.createFloatBuffer(matrix));
	}

	protected void loadVector(int location, Vector3f vector)
	{
		glUniform3f(location, vector.getX(), vector.getY(), vector.getZ());
	}

	protected void loadBoolean(int location, boolean value)
	{
		glUniform1i(location, value ? 1 : 0);
	}

	private static int loadShader(String file, int type)
	{
		StringBuilder shaderSource = new StringBuilder();
		try (BufferedReader reader = new BufferedReader(
				new InputStreamReader(ShaderProgram.class.getResourceAsStream(file))))
		{
			String line;
			while ((line = reader.readLine()) != null)
			{
				shaderSource.append(line).append("\n");
			}
			reader.close();
		} catch (Exception e)
		{
			System.err.println("Could not read the file");
			e.printStackTrace();
			System.exit(-1);
		}
		int shaderID = glCreateShader(type);
		glShaderSource(shaderID, shaderSource);
		glCompileShader(shaderID);
		if (glGetShaderi(shaderID, GL_COMPILE_STATUS) == GL_FALSE)
		{
			System.out.println(glGetShaderInfoLog(shaderID, 500));
			System.err.println("Could not compile the shader. ");
			System.exit(-1);
		}
		return shaderID;
	}
}
