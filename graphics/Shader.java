package graphics;

import math.Matrix4f;
import math.Transform;
import math.Vector3f;

public class Shader extends ShaderProgram
{
	private static final String VERTEX_FILE = "/graphics/VertexShader.vert";
	private static final String FRAGMENT_FILE = "/graphics/VertexShader.frag";

	private int locationOverrideTextureColor;
	private int locationTransformationMatrix;
	private int locationProjectionMatrix;
	private int locationViewMatrix;
	private int locationColor;

	public Shader()
	{
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	protected void getAllUniformsLocation()
	{
		locationOverrideTextureColor = super.getUniformLocation("overrideTextureColor");
		locationTransformationMatrix = super.getUniformLocation("transformationMatrix");
		locationProjectionMatrix = super.getUniformLocation("projectionMatrix");
		locationViewMatrix = super.getUniformLocation("viewMatrix");
		locationColor = super.getUniformLocation("color");
	}

	@Override
	protected void bindAttributes()
	{
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "textureCoords");
	}

	public void loadTransformationMatrix(Matrix4f matrix)
	{
		super.loadMatrix(locationTransformationMatrix, matrix);
	}

	public void loadProjectionMatrix(Matrix4f projectionMatrix)
	{
		super.loadMatrix(locationProjectionMatrix, projectionMatrix);
	}

	public void loadViewMatrix(Camera camera)
	{
		Matrix4f viewMatrix = Transform.getViewMatrix(camera);
		super.loadMatrix(locationViewMatrix, viewMatrix);
	}

	public void loadColor(Vector3f color)
	{
		if (color == null)
		{
			super.loadBoolean(locationOverrideTextureColor, false);
		}
		else
		{
			super.loadBoolean(locationOverrideTextureColor, true);
			super.loadVector(locationColor, color);
		}
	}
}
