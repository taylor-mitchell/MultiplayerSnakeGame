package misc;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import math.Matrix4f;

public class Utility
{
	private final static int SIZE4F = 4 * 4;

	public static ByteBuffer createByteBuffer(byte array[])
	{
		ByteBuffer result = ByteBuffer.allocateDirect(array.length).order(ByteOrder.nativeOrder());
		result.put(array).flip();
		return result;
	}

	public static FloatBuffer createFloatBuffer(float array[])
	{
		FloatBuffer result = ByteBuffer.allocateDirect(array.length << 2).order(ByteOrder.nativeOrder())
				.asFloatBuffer();
		result.put(array).flip();
		return result;
	}

	public static FloatBuffer createFloatBuffer(Matrix4f mat)
	{
		FloatBuffer buffer = ByteBuffer.allocateDirect(SIZE4F << 2).order(ByteOrder.nativeOrder()).asFloatBuffer();

		for (int i = 0; i < 4; i++)
			for (int j = 0; j < 4; j++)
				buffer.put(mat.getEntry(i, j));

		buffer.flip();

		return buffer;
	}

	public static IntBuffer createIntBuffer(int array[])
	{
		IntBuffer result = ByteBuffer.allocateDirect(array.length << 2).order(ByteOrder.nativeOrder()).asIntBuffer();
		result.put(array).flip();
		return result;
	}
}
