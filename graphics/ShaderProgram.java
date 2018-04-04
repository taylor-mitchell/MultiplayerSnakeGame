package graphics;

import math.Matrix4f;
import math.Vector3f;
import misc.Utility;

import static org.lwjgl.opengl.GL20.*;

import java.io.BufferedReader;
import java.io.FileReader;

import static org.lwjgl.opengl.GL11.*;

public abstract class ShaderProgram 
{
	private int programID;
	private int vertexShaderID;
	private int fragmentShaderID;
	
	public ShaderProgram(String vertexFilename, String fragmentFilename){
        vertexShaderID = loadShader(vertexFilename, GL_VERTEX_SHADER);
        fragmentShaderID = loadShader(fragmentFilename, GL_FRAGMENT_SHADER);
        programID = glCreateProgram();
        
        glAttachShader(programID, vertexShaderID);
        glAttachShader(programID, fragmentShaderID);
        
        bindAttributes();
        glLinkProgram(programID);
        glValidateProgram(programID);
        getAllUniformsLocation();
    }
    
    protected abstract void getAllUniformsLocation();
    
    protected int getUniformLocation(String uniformName){
        return glGetUniformLocation(programID, uniformName);
    }
    
    public void start(){
        glUseProgram(programID);
    }
    
    public void stop (){
        glUseProgram(0);
    }
    
    public void cleanUp(){
        stop();
        glDetachShader(programID, vertexShaderID);
        glDetachShader(programID, fragmentShaderID);
        glDeleteShader(vertexShaderID);
        glDeleteShader(fragmentShaderID);
        glDeleteProgram(programID);
    }
    
    protected abstract void bindAttributes();
    
    protected void bindAttribute(int attribute, String variableName){
        glBindAttribLocation(programID, attribute, variableName);
    }
    
    protected void loadFloat(int location, float value){
        glUniform1f(location, value);
    }
    
    protected void loadMatrix (int location, Matrix4f matrix){
        glUniformMatrix4fv(location, true, Utility.createFloatBuffer(matrix));
    } 
    
    protected void loadVector (int location, Vector3f vector){
        glUniform3f(location, vector.getX(), vector.getY(), vector.getZ());
    } 
    
    protected void loadBoolean (int location, boolean value){
        float toLoad = 0;
        if(value){
            toLoad = 1;
        }
        glUniform1f(location, toLoad);
    }
    
    private static int loadShader (String file, int type){
        StringBuilder shaderSource = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while((line = reader.readLine())!= null){
                shaderSource.append(line).append("\n");
            }
        reader.close();
        } catch (Exception e) {
            System.err.println("Could not read the file");
            e.printStackTrace();
            System.exit(-1);
        }
        int shaderID = glCreateShader(type);
        glShaderSource(shaderID,shaderSource);
        glCompileShader(shaderID);
        if (glGetShaderi(shaderID,GL_COMPILE_STATUS)==GL_FALSE){
            System.out.println(glGetShaderInfoLog(shaderID,500));
            System.err.println("Could not compile the shader. ");
            System.exit(-1);
        }
        return shaderID;
    }
}
