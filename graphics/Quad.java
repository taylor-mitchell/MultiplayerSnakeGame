package graphics;

public class Quad
{
	private int indices[] = new int[] { 0, 1, 3, // first triangle
			1, 2, 3 // second triangle
	};

	private float verticesCoordinates[] = new float[] { 0.5f, 0.5f, 0.0f, // top
																			// //
																			// right
			0.5f, -0.5f, 0.0f, // bottom right
			-0.5f, -0.5f, 0.0f, // bottom left
			-0.5f, 0.5f, 0.0f // top left
	};

	private float textureCoordinates[] = new float[] { 0.0f, 0.0f, // lower-left
																	// // corner
			1.0f, 0.0f, // lower-right corner
			0.5f, 1.0f // top-center corner
	};

	public Quad()
	{
		this.indices = new int[] { 0, 1, 3, // first triangle
				1, 2, 3 // second triangle
		};

		this.verticesCoordinates = new float[] { 0.5f, 0.5f, 0.0f, // top //
																	// right
				0.5f, -0.5f, 0.0f, // bottom right
				-0.5f, -0.5f, 0.0f, // bottom left
				-0.5f, 0.5f, 0.0f // top left
		};

		this.textureCoordinates = new float[] { 1.0f, 0.0f, // lower-left //
															// corner
				1.0f, 1.0f, // lower-right corner
				0.0f, 1.0f, 0.0f, 0.0f // top-center corner
		};
	}

	public Quad(int[] indices, float[] verticesCoordinates, float[] textureCoordinates)
	{
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
