package graphics;

import math.Matrix4f;
import math.Transform;
import math.Vector3f;

public class Shader extends ShaderProgram
{
	private static final String VERTEX_FILE = "graphics/VertexShader.vert";
	private static final String FRAGMENT_FILE = "graphics/VertexShader.frag";

	private int locationTransformationMatrix;
	private int locationProjectionMatrix;
	private int locationViewMatrix;
	private int locationSkyColor;
	private int locationPosition;

	public Shader()
	{
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	protected void getAllUniformsLocation()
	{
		locationTransformationMatrix = super.getUniformLocation("transformationMatrix");
		locationProjectionMatrix = super.getUniformLocation("projectionMatrix");
		locationViewMatrix = super.getUniformLocation("viewMatrix");
		locationSkyColor = super.getUniformLocation("skyColour");
		locationPosition = super.getUniformLocation("position");
	}

	@Override
	protected void bindAttributes()
	{
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "textureCoords");
	}

    public void loadTransformationMatrix(Matrix4f matrix){
        super.loadMatrix(locationTransformationMatrix, matrix);
    }
    
    public void loadProjectionMatrix(Matrix4f projectionMatrix){
        super.loadMatrix(locationProjectionMatrix, projectionMatrix);
    }
    
    public void loadViewMatrix(Camera camera){
        Matrix4f viewMatrix = Transform.getViewMatrix(camera);
        super.loadMatrix(locationViewMatrix, viewMatrix);
    }
    
    public void loadSkyColour(float r, float g, float b){
        super.loadVector(locationSkyColor, new Vector3f(r, g, b));
    }
    
    public void loadPostion(Vector3f position){
        super.loadVector(locationPosition, position);
    }
}
