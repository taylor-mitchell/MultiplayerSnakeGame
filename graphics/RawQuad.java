package graphics;

public class RawQuad 
{
	private final int vaoID;
	private final int vertexCount;
	
	public RawQuad(int vaoID, int vertexCount)
	{
		super();
		this.vaoID = vaoID;
		this.vertexCount = vertexCount;
	}
	
	public int getVaoID()
	{
		return vaoID;
	}
	
	public int getVertexCount()
	{
		return vertexCount;
	}
}
