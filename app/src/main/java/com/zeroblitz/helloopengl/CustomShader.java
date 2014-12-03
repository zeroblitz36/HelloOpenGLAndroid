package com.zeroblitz.helloopengl;

import android.opengl.GLES20;

/**
 * Created by Roscaneanu on 22.11.2014.
 */
public class CustomShader {
    //atributes
    public static final String aPosition = "aPosition";
    public static final String aColor = "aColor";
    public static final String aNormal = "aNormal";

    //uniform
    public static final String uColor = "uColor";
    public static final String uMVPMatrix = "uMVPMatrix";
    public static final String uMVMatrix = "uMVMatrix";
    public static final String uLightPos = "uLightPos";

    //varying
    public static final String vColor = "vColor";
    public static final String simpleVertexShaderCode =
            // This matrix member variable provides a hook to manipulate
            // the coordinates of the objects that use this vertex shader
            "uniform mat4 "+uMVPMatrix+";" +
            "attribute vec4 "+ aPosition +";" +
            "void main() {" +
                // the matrix must be included as a modifier of gl_Position
                // Note that the uMVPMatrix factor *must be first* in order
                // for the matrix multiplication product to be correct.
                "gl_Position = "+uMVPMatrix+" * "+ aPosition +";" +
            "}";

    public static final String simpleFragmentShaderCode =
            "precision mediump float;" +
            "uniform vec4 "+ uColor +";" +
            "void main() {" +
                "gl_FragColor = "+ uColor +";" +
            "}";

    public static final String gouradVertexShader =
            "uniform mat4 "+uMVPMatrix+";"+
            "uniform mat4 "+uMVMatrix+";"+
            "uniform vec3 "+uLightPos+";"+
            "attribute vec4 "+aPosition+";"+
            "attribute vec4 "+aColor+";"+
            "attribute vec3 "+aNormal+";"+
            "varying vec4 "+vColor+";"+
            "void main(){"+
                "vec3 modelViewVertex = vec3("+uMVMatrix+" * "+aPosition+");"+
                "vec3 modelViewNormal = vec3("+uMVMatrix+" * vec4("+aNormal+" , 0.0));"+
                "float distance = length("+uLightPos+" - modelViewVertex);"+
                "vec3 lightVector = normalize("+uLightPos+" - modelViewVertex);"+
                "float diffuse = max(dot(modelViewNormal, lightVector),0.1);"+
                "diffuse = diffuse * (1.0 / (1.0 + (0.25 * distance * distance)));"+
                vColor+"="+aColor+" * diffuse;"+
                "gl_Position = "+uMVPMatrix +" * "+aPosition+";"+
            "}";
    public static int createProgram(String vertexShader,String fragmentShader){
        int v = loadShader(GLES20.GL_VERTEX_SHADER, vertexShader);
        int f = loadShader(GLES20.GL_FRAGMENT_SHADER,fragmentShader);
        int p = GLES20.glCreateProgram();
        GLES20.glAttachShader(p, v);
        GLES20.glAttachShader(p, f);
        GLES20.glLinkProgram(p);
        return p;
    }
    public static int loadShader(int type, String shaderCode){

        // create a vertex shader type (GLES20.GL_VERTEX_SHADER)
        // or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
        int shader = GLES20.glCreateShader(type);

        // add the source code to the shader and compile it
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        return shader;
    }
}
