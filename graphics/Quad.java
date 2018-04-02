package graphics;

public class Quad 
{
	private int indices[];
	private float verticesCoordinates[];
	private float textureCoordinates[];
	
	public Quad(int[] indices, float[] verticesCoordinates, float[] textureCoordinates)
	{
		super();
		this.indices = indices;
		this.verticesCoordinates = verticesCoordinates;
		this.textureCoordinates = textureCoordinates;
	}

	public int[] getIndices()
	{
		return indices;
	}

	public float[] getVerticesCoordinates()
	{
		return verticesCoordinates;
	}

	public float[] getTextureCoordinates()
	{
		return textureCoordinates;
	}
}
