package graphics;

import java.util.List;

import game.Entity;

public class Renderer 
{
	private Camera activeCamera;
	private GameWindow activeDisplay;
	private Shader activeShader;
	
	public Renderer(Camera activeCamera, GameWindow activeDisplay, Shader activeShader)
	{
		super();
		this.activeCamera = activeCamera;
		this.activeDisplay = activeDisplay;
		this.activeShader = activeShader;
	}

	private void bindModel(Entity entity)
	{
		
	}
	
	private void unbindModel()
	{
		
	}
	
	private void loadTransformationMatrices(Entity entity)
	{
		
	}
	
	public void renderEntities(List<Entity> entitesToRender)
	{
		
	}
	
	public void setActiveCamera(Camera activeCamera)
	{
		this.activeCamera = activeCamera;
	}

	public void setActiveDisplay(GameWindow activeDisplay)
	{
		this.activeDisplay = activeDisplay;
	}

	public void setActiveShader(Shader activeShader)
	{
		this.activeShader = activeShader;
	}
	
	
}
