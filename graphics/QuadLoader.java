package graphics;

import java.util.List;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import misc.Utility;

public class QuadLoader
{
	private static List<Integer> vaos = new ArrayList<>();
	private static List<Integer> vbos = new ArrayList<>();

	public static int createVAO()
	{
		int vaoID = GL30.glGenVertexArrays();
		vaos.add(vaoID);
		GL30.glBindVertexArray(vaoID);

		return vaoID;
	}

	public static RawQuad loadToVAO(Quad quad)
	{
		int vaoID = createVAO();
		bindIndicesBuffer(quad.getIndices());

		// Vertices positions
		storeDataInAttributeList(0, 3, quad.getVerticesCoordinates());
		
		// Texture coordinates
		storeDataInAttributeList(1, 2, quad.getTextureCoordinates());
		
		GL30.glBindVertexArray(0);
		
		return new RawQuad(vaoID, quad.getIndices().length);
	}

	public static void storeDataInAttributeList(int attributeNumber, int attributeSize, float data[])
	{
		int vboID = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
		vbos.add(vboID);

		FloatBuffer buffer = Utility.createFloatBuffer(data);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
		GL20.glVertexAttribPointer(attributeNumber, attributeSize, GL11.GL_FLOAT, false, 0, 0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
	}

	public static void bindIndicesBuffer(int indices[])
	{
		int vboID = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboID);
		vbos.add(vboID);

		IntBuffer buffer = Utility.createIntBuffer(indices);
		GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
	}

	public static void cleanUp()
	{
		for (int vao : vaos)
		{
			GL30.glDeleteVertexArrays(vao);
		}
		for (int vbo : vbos)
		{
			GL15.glDeleteBuffers(vbo);
		}
	}
}
