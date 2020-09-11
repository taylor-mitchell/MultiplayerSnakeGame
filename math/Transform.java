package math;

import graphics.Camera;

public class Transform
{
	public static final float LEFT = -15.f;
	public static final float RIGHT = 15.f;
	public static final float BOTTOM = -10.0f;
	public static final float TOP = 10.0f;
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
