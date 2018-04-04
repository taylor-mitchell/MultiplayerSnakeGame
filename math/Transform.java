package math;

import graphics.Camera;

public class Transform
{
	private static final float LEFT = -8.f;
	private static final float RIGHT = 8.f;
	private static final float BOTTOM = -4.5f;
	private static final float TOP = 4.5f;
	private static final float NEAR = -8.f;
	private static final float FAR = 20.f;

	public static Matrix4f getTransformation(Vector3f translation, Vector3f scale)
	{
		Matrix4f translationMatrix = new Matrix4f().initTranslation(translation);
		Matrix4f scaleMatrix = new Matrix4f().initScale(scale);
		return translationMatrix.mul(scaleMatrix);
	}

	public static Matrix4f getViewMatrix(Camera camera)
	{
		Vector3f negativeCameraPos = new Vector3f(camera.getPosition().flip());
        return new Matrix4f().initTranslation(negativeCameraPos);
	}

	public static Matrix4f getProjectionMatrix()
	{
		return new Matrix4f().initProjection(LEFT, RIGHT, BOTTOM, TOP, NEAR, FAR);
	}
}
