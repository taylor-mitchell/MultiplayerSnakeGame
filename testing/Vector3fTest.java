package testing;

import static org.junit.Assert.*;

import org.junit.Test;

import math.Vector3f;

public class Vector3fTest
{
  
  @Test
  public void testAdd() 
  {        
   Vector3f expected = new Vector3f(1,1,1);
    Vector3f actual = new Vector3f (0.5f, 0.5f,0.5f).add(new Vector3f(0.5f, 0.5f,0.5f));
    assertEquals(expected , actual); 

  }

  @Test
  public void testSub() 
  {
    Vector3f expected = new Vector3f(0,0,0);
    Vector3f actual = new Vector3f (0.9f, 0.9f,0.9f).sub(new Vector3f(0.9f, 0.9f,0.9f));
    assertEquals(expected , actual); 
    

  }

  @Test
  public void testMul() 
  {
    Vector3f expected = new Vector3f(6.25f,6.25f,6.25f);
    Vector3f actual = new Vector3f (2.5f, 2.5f,2.5f).mul(new Vector3f(2.5f, 2.5f,2.5f));
    assertEquals(expected , actual); 
    

  }
  
  
  @Test
  public void testClone() 
  {
	  Vector3f expected = new Vector3f(6.25f,6.25f,6.25f);
	  Vector3f actual = expected.clone();
	  assertEquals(expected, actual);
  }
  
  @Test
  public void testToString() 
  {
	  Vector3f expected = new Vector3f(6.25f,6.25f,6.25f);
	  
	  assertNotNull(expected.toString());
  }

}
