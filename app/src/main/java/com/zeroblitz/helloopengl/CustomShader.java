package com.zeroblitz.helloopengl;

import android.opengl.GLES20;

/**
 * Created by Roscaneanu on 22.11.2014.
 */
public class CustomShader {
    public static final String vPosition = "vPosition";
    public static final String vColor = "vColor";
    public static final String uMVPMatrix = "uMVPMatrix";
    public static final String simpleVertexShaderCode =
            // This matrix member variable provides a hook to manipulate
            // the coordinates of the objects that use this vertex shader
            "uniform mat4 uMVPMatrix;" +
                    "attribute vec4 vPosition;" +
                    "void main() {" +
                    // the matrix must be included as a modifier of gl_Position
                    // Note that the uMVPMatrix factor *must be first* in order
                    // for the matrix multiplication product to be correct.
                    "  gl_Position = uMVPMatrix * vPosition;" +
                    "}";

    public static final String simpleFragmentShaderCode =
            "precision mediump float;" +
                    "uniform vec4 vColor;" +
                    "void main() {" +
                    "  gl_FragColor = vColor;" +
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
