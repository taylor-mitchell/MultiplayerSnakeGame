package graphics;

import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import game.Entity;
import math.Transform;

public class Renderer 
{
	private Camera activeCamera;
	private GameWindow activeDisplay;
	private Shader activeShader;
	
	public Renderer(Camera activeCamera, GameWindow activeDisplay, Shader activeShader)
	{
		this.activeCamera = activeCamera;
		this.activeDisplay = activeDisplay;
		this.activeShader = activeShader;
	}

	private void bindModel(Entity entity)
	{
		GL30.glBindVertexArray(entity.getRawQuad().getVaoID());
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
	}
	
	private void unbindModel()
	{
		GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL30.glBindVertexArray(0);
	}
	
	private void loadTransformationMatrices(Entity entity)
	{
		activeShader.loadTransformationMatrix(Transform.getTransformation(entity.getPosition(), entity.getScale()));
		activeShader.loadProjectionMatrix(Transform.getProjectionMatrix());
		activeShader.loadViewMatrix(activeCamera);
	}
	
	public void renderEntities(List<Entity> entitiesToRender)
	{
		if (entitiesToRender.isEmpty())
			return;
		
		activeShader.start();
		//Batch rendering
		for (Entity entity : entitiesToRender)
		{
			bindModel(entity);
			loadTransformationMatrices(entity);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, entity.getTexture().getId());
			GL11.glDrawElements(GL11.GL_TRIANGLES, entity.getRawQuad().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
			unbindModel();
		}
		activeShader.stop();
	}
	
	public void prepare()
	{
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
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
