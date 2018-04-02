package graphics;

import math.Matrix4f;
import math.Vector3f;

public class Shader  extends ShaderProgram
{
	private int locationTransformationMatrix;
	private int locationProjectionMatrix;
	private int locationViewMatrix;
	private int locationSkyColor;
	private int position;
	private int rotation;
	public Shader(String vertexFilename, String fragmentFilename)
	{
		super(vertexFilename, fragmentFilename);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void getAllUniformsLocation()
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void bindAttributes()
	{
		// TODO Auto-generated method stub
		
	}

	public void loadFloat(float value)
	{
		
	}
	
	public void loadTransformationMatrix(Matrix4f mat)
	{
		
	}
	
	public void loadProjectionMatrix(Matrix4f mat)
	{
		
	}
	
	public void loadViewMatrix(Matrix4f mat)
	{
		
	}
	
	public void loadVector(Vector3f vec)
	{
		
	}
	
	public void loadSkyColor(Vector3f rgb)
	{
		
	}
	
	public void loadBoolean(boolean value)
	{
		
	}
}
