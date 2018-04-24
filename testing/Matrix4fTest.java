package testing;

import static org.junit.Assert.*;

import org.junit.Test;

import math.Matrix4f;
import math.Vector3f;

public class Matrix4fTest
{

	@Test
	public void testInitIdentity()
	{
		final float[][] data = new float[][] { { 1, 0, 0, 0 }, { 0, 1, 0, 0 }, { 0, 0, 1, 0 }, { 0, 0, 0, 1 } };
		Matrix4f expected = new Matrix4f(data);
		Matrix4f actual = new Matrix4f().initIdentity();

		assertEquals("Matrix initIndentity test failed", expected, actual);
	}

	@Test
	public void testInitTranslation()
	{
		final float[][] data = new float[][] { { 1, 0, 0, 1 }, { 0, 1, 0, 1 }, { 0, 0, 1, 1 }, { 0, 0, 0, 1 } };
		Matrix4f expected = new Matrix4f(data);
		Matrix4f actual = new Matrix4f().initTranslation(new Vector3f(1, 1, 1));

		assertEquals("Matrix initTranslation test failed", expected, actual);
	}

	@Test
	public void testInitScale()
	{
		final float[][] data = new float[][] { { 2, 0, 0, 0 }, { 0, 2, 0, 0 }, { 0, 0, 2, 0 }, { 0, 0, 0, 1 } };
		Matrix4f expected = new Matrix4f(data);
		Matrix4f actual = new Matrix4f().initScale(new Vector3f(2, 2, 2));
		assertEquals("Matrix initScale test failed", expected, actual);
	}

	@Test
	public void testInitProjection()
	{
		final float params[] = new float[] { 2.0f / (8.f + 8), 0.0f, 2.0f / (4.5f + 4.5f), 0, 2.0f / (-2.f - 3),
				(3.f - 2) / (3 + 2) };
		final float[][] data = new float[][] { { params[0], 0, 0, params[1] }, { 0, params[2], 0, params[3] },
				{ 0, 0, params[4], params[5] }, { 0, 0, 0, 1 } };

		Matrix4f expected = new Matrix4f(data);
		Matrix4f actual = new Matrix4f().initProjection(-8, 8, -4.5f, 4.5f, -2, 3);
		assertEquals("Matrix initProjection test failed", expected, actual);
	}

	@Test
	public void testMulMatrix4f()
	{
		final float[][] data = new float[][] { { 2, 5, 0, 0 }, { 0, 1, 0, 0 }, { 0.5f, 0, 1, 0 }, { 0, 0.9f, 0, 1 } };
		Matrix4f expected = new Matrix4f(data);
		// MAtrix identity property I * A = A
		Matrix4f actual = new Matrix4f().initIdentity().mul(expected);
		assertEquals("Matrix4f to Matrix4f multiplication test failed", expected, actual);
	}

	@Test
	public void testMulFloat()
	{
		final float[][] data = new float[][] { { 5, 0, 0, 0 }, { 0, 5, 0, 0 }, { 0, 0, 5, 0 }, { 0, 0, 0, 5 } };
		Matrix4f expected = new Matrix4f(data);
		Matrix4f actual = new Matrix4f().initIdentity().mul(5);
		assertEquals("Matrix4f to float multiplication test failed", expected, actual);
	}

}
