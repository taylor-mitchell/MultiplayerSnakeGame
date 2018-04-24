package math;

public class Matrix4f
{
	private float data[][];

	public Matrix4f()
	{
		data = new float[4][4];
	}

	public Matrix4f(float[][] data)
	{
		if (data == null || data.length != 4 || data[0].length != 4)
		{
			throw new IllegalArgumentException("Matrix4f' constructor expects float[4][4] as argument");
		}

		this.data = data;
	}

	public Matrix4f initIdentity()
	{
		data[0][0] = 1;
		data[0][1] = 0;
		data[0][2] = 0;
		data[0][3] = 0;
		data[1][0] = 0;
		data[1][1] = 1;
		data[1][2] = 0;
		data[1][3] = 0;
		data[2][0] = 0;
		data[2][1] = 0;
		data[2][2] = 1;
		data[2][3] = 0;
		data[3][0] = 0;
		data[3][1] = 0;
		data[3][2] = 0;
		data[3][3] = 1;

		return this;
	}

	public Matrix4f initTranslation(Vector3f translation)
	{
		data[0][0] = 1;
		data[0][1] = 0;
		data[0][2] = 0;
		data[0][3] = translation.getX();
		data[1][0] = 0;
		data[1][1] = 1;
		data[1][2] = 0;
		data[1][3] = translation.getY();
		data[2][0] = 0;
		data[2][1] = 0;
		data[2][2] = 1;
		data[2][3] = translation.getZ();
		data[3][0] = 0;
		data[3][1] = 0;
		data[3][2] = 0;
		data[3][3] = 1;

		return this;
	}

	public Matrix4f initScale(Vector3f scale)
	{
		data[0][0] = scale.getX();
		data[0][1] = 0;
		data[0][2] = 0;
		data[0][3] = 0;
		data[1][0] = 0;
		data[1][1] = scale.getY();
		data[1][2] = 0;
		data[1][3] = 0;
		data[2][0] = 0;
		data[2][1] = 0;
		data[2][2] = scale.getZ();
		data[2][3] = 0;
		data[3][0] = 0;
		data[3][1] = 0;
		data[3][2] = 0;
		data[3][3] = 1;

		return this;
	}

	public Matrix4f initProjection(float left, float right, float bottom, float top, float near, float far)
	{
		data[0][0] = 2.0f / (right - left);
		data[0][1] = 0;
		data[0][2] = 0;
		data[0][3] = (left + right) / (left - right);
		data[1][0] = 0;
		data[1][1] = 2.0f / (top - bottom);
		data[1][2] = 0;
		data[1][3] = (bottom + top) / (bottom - top);
		data[2][0] = 0;
		data[2][1] = 0;
		data[2][2] = 2.0f / (near - far);
		data[2][3] = (far + near) / (far - near);
		data[3][0] = 0;
		data[3][1] = 0;
		data[3][2] = 0;
		data[3][3] = 1;

		return this;
	}

	public Matrix4f mul(Matrix4f rhs)
	{
		Matrix4f result = new Matrix4f();
		for (int i = 0; i < 4; i++)
		{
			for (int j = 0; j < 4; j++)
			{
				result.setEntry(i, j, data[i][0] * rhs.getEntry(0, j) + data[i][1] * rhs.getEntry(1, j)
						+ data[i][2] * rhs.getEntry(2, j) + data[i][3] * rhs.getEntry(3, j));
			}
		}
		return result;
	}

	public Matrix4f mul(float amount)
	{
		Matrix4f result = new Matrix4f();
		for (int i = 0; i < 4; i++)
		{
			for (int j = 0; j < 4; j++)
			{
				result.data[i][j] = data[i][j] * amount;
			}
		}
		return result;
	}

	public float[][] getData()
	{
		return data;
	}

	public float getEntry(int r, int c)
	{
		return data[r][c];
	}

	public void setData(float data[][])
	{
		this.data = data;
	}

	public void setEntry(int r, int c, float value)
	{
		data[r][c] = value;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;

		Matrix4f rhs = (Matrix4f) obj;

		for (int i = 0; i < data.length; i++)
		{
			for (int j = 0; j < data[i].length; j++)
			{
				if (data[i][j] != rhs.data[i][j])
				{
					return false;
				}
			}
		}

		return true;
	}
}
