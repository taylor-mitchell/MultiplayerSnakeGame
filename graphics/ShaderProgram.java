package graphics;

public abstract class ShaderProgram 
{
	private int programID;
	private int vertexShaderID;
	private int fragmentShaderID;
	
	public ShaderProgram(String vertexFilename, String fragmentFilename)
	{
		
	}
	
	protected abstract void getAllUniformsLocation();
	
	protected int getUniformLocation(String uniformName)
	{
		return 0;
	}
	
	public void start()
	{
		
	}
	
	public void stop()
	{
		
	}
	
	public void cleanUp()
	{
		
	}
	
	protected abstract void bindAttributes();
	
	protected void bindAttribute(int attributeNumber, String attributeName)
	{
		
	}
	
	protected void loadFloat(int location, float value)
	{
		
	}
	
	protected void loadMatrix(int location, float value)
	{
		
	}
	
	protected void loadVector(int location, float value)
	{
		
	}
	
	protected void loadBoolean(int location, float value)
	{
		
	}
	
	private void loadShaderFromFile(String filename, int ShaderType, int value)
	{
		
	}
}
