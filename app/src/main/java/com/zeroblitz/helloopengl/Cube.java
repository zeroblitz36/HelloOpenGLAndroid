package com.zeroblitz.helloopengl;


import android.opengl.GLES20;
import android.opengl.Matrix;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * Created by Roscaneanu on 03.12.2014.
 */
public class Cube {
    private static final int SIZE_OF_FLOAT = 4;

    private final FloatBuffer vertexBuffer;
    private final FloatBuffer colorBuffer;
    private final FloatBuffer normalBuffer;

    private int mProgram;
    private int uMVPMatrixHandle;
    private int uMVMatrixHandle;
    private int uLightPosHandle;

    private int aPositionHandle;
    private int aColorHandle;
    private int aNormalHandle;

    private LightPoint lightPoint;

    private static final int COORDS_PER_VERTEX = 3;
    private final float coords[] = {
            // Front face
            -1.0f, 1.0f, 1.0f,
            -1.0f, -1.0f, 1.0f,
            1.0f, 1.0f, 1.0f,
            -1.0f, -1.0f, 1.0f,
            1.0f, -1.0f, 1.0f,
            1.0f, 1.0f, 1.0f,

            // Right face
            1.0f, 1.0f, 1.0f,
            1.0f, -1.0f, 1.0f,
            1.0f, 1.0f, -1.0f,
            1.0f, -1.0f, 1.0f,
            1.0f, -1.0f, -1.0f,
            1.0f, 1.0f, -1.0f,

            // Back face
            1.0f, 1.0f, -1.0f,
            1.0f, -1.0f, -1.0f,
            -1.0f, 1.0f, -1.0f,
            1.0f, -1.0f, -1.0f,
            -1.0f, -1.0f, -1.0f,
            -1.0f, 1.0f, -1.0f,

            // Left face
            -1.0f, 1.0f, -1.0f,
            -1.0f, -1.0f, -1.0f,
            -1.0f, 1.0f, 1.0f,
            -1.0f, -1.0f, -1.0f,
            -1.0f, -1.0f, 1.0f,
            -1.0f, 1.0f, 1.0f,

            // Top face
            -1.0f, 1.0f, -1.0f,
            -1.0f, 1.0f, 1.0f,
            1.0f, 1.0f, -1.0f,
            -1.0f, 1.0f, 1.0f,
            1.0f, 1.0f, 1.0f,
            1.0f, 1.0f, -1.0f,

            // Bottom face
            1.0f, -1.0f, -1.0f,
            1.0f, -1.0f, 1.0f,
            -1.0f, -1.0f, -1.0f,
            1.0f, -1.0f, 1.0f,
            -1.0f, -1.0f, 1.0f,
            -1.0f, -1.0f, -1.0f
    };
    private static final int COLOR_DATA_SIZE = 4;
    private final float[] colors ={
            // Front face (red)
            1.0f, 0.0f, 0.0f, 1.0f,
            1.0f, 0.0f, 0.0f, 1.0f,
            1.0f, 0.0f, 0.0f, 1.0f,
            1.0f, 0.0f, 0.0f, 1.0f,
            1.0f, 0.0f, 0.0f, 1.0f,
            1.0f, 0.0f, 0.0f, 1.0f,

            // Right face (green)
            0.0f, 1.0f, 0.0f, 1.0f,
            0.0f, 1.0f, 0.0f, 1.0f,
            0.0f, 1.0f, 0.0f, 1.0f,
            0.0f, 1.0f, 0.0f, 1.0f,
            0.0f, 1.0f, 0.0f, 1.0f,
            0.0f, 1.0f, 0.0f, 1.0f,

            // Back face (blue)
            0.0f, 0.0f, 1.0f, 1.0f,
            0.0f, 0.0f, 1.0f, 1.0f,
            0.0f, 0.0f, 1.0f, 1.0f,
            0.0f, 0.0f, 1.0f, 1.0f,
            0.0f, 0.0f, 1.0f, 1.0f,
            0.0f, 0.0f, 1.0f, 1.0f,

            // Left face (yellow)
            1.0f, 1.0f, 0.0f, 1.0f,
            1.0f, 1.0f, 0.0f, 1.0f,
            1.0f, 1.0f, 0.0f, 1.0f,
            1.0f, 1.0f, 0.0f, 1.0f,
            1.0f, 1.0f, 0.0f, 1.0f,
            1.0f, 1.0f, 0.0f, 1.0f,

            // Top face (cyan)
            0.0f, 1.0f, 1.0f, 1.0f,
            0.0f, 1.0f, 1.0f, 1.0f,
            0.0f, 1.0f, 1.0f, 1.0f,
            0.0f, 1.0f, 1.0f, 1.0f,
            0.0f, 1.0f, 1.0f, 1.0f,
            0.0f, 1.0f, 1.0f, 1.0f,

            // Bottom face (magenta)
            1.0f, 0.0f, 1.0f, 1.0f,
            1.0f, 0.0f, 1.0f, 1.0f,
            1.0f, 0.0f, 1.0f, 1.0f,
            1.0f, 0.0f, 1.0f, 1.0f,
            1.0f, 0.0f, 1.0f, 1.0f,
            1.0f, 0.0f, 1.0f, 1.0f
    };
    private final static int NORMAL_DATA_SIZE = 3;
    final float[] normals ={
            // Front face
            0.0f, 0.0f, 1.0f,
            0.0f, 0.0f, 1.0f,
            0.0f, 0.0f, 1.0f,
            0.0f, 0.0f, 1.0f,
            0.0f, 0.0f, 1.0f,
            0.0f, 0.0f, 1.0f,

            // Right face
            1.0f, 0.0f, 0.0f,
            1.0f, 0.0f, 0.0f,
            1.0f, 0.0f, 0.0f,
            1.0f, 0.0f, 0.0f,
            1.0f, 0.0f, 0.0f,
            1.0f, 0.0f, 0.0f,

            // Back face
            0.0f, 0.0f, -1.0f,
            0.0f, 0.0f, -1.0f,
            0.0f, 0.0f, -1.0f,
            0.0f, 0.0f, -1.0f,
            0.0f, 0.0f, -1.0f,
            0.0f, 0.0f, -1.0f,

            // Left face
            -1.0f, 0.0f, 0.0f,
            -1.0f, 0.0f, 0.0f,
            -1.0f, 0.0f, 0.0f,
            -1.0f, 0.0f, 0.0f,
            -1.0f, 0.0f, 0.0f,
            -1.0f, 0.0f, 0.0f,

            // Top face
            0.0f, 1.0f, 0.0f,
            0.0f, 1.0f, 0.0f,
            0.0f, 1.0f, 0.0f,
            0.0f, 1.0f, 0.0f,
            0.0f, 1.0f, 0.0f,
            0.0f, 1.0f, 0.0f,

            // Bottom face
            0.0f, -1.0f, 0.0f,
            0.0f, -1.0f, 0.0f,
            0.0f, -1.0f, 0.0f,
            0.0f, -1.0f, 0.0f,
            0.0f, -1.0f, 0.0f,
            0.0f, -1.0f, 0.0f
    };

    private int vertexCount = coords.length / COORDS_PER_VERTEX;
    private int vertexStride = COORDS_PER_VERTEX * SIZE_OF_FLOAT;

    private final float scratch[] = new float[16];
    private final float scratch2[] = new float[16];
    public final float modelMatrix[] = new float[16];
    public final float positionMatrix[] = new float[16];
    public final float rotationMatrix[] = new float[16];
    public final float scaleMatrix[] = new float[16];
    public Cube(){
        ByteBuffer bb = ByteBuffer
                .allocateDirect(coords.length * SIZE_OF_FLOAT);
        bb.order(ByteOrder.nativeOrder());
        vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(coords).position(0);

        bb = ByteBuffer.allocateDirect(colors.length * SIZE_OF_FLOAT);
        bb.order(ByteOrder.nativeOrder());
        colorBuffer = bb.asFloatBuffer();
        colorBuffer.put(colors).position(0);

        bb = ByteBuffer.allocateDirect(normals.length * SIZE_OF_FLOAT);
        bb.order(ByteOrder.nativeOrder());
        normalBuffer = bb.asFloatBuffer();
        normalBuffer.put(normals).position(0);

        mProgram = CustomShader.createProgram(CustomShader.gouradVertexShader,
                CustomShader.gouradFragmentShader,new String[]{CustomShader.vColor});
        uMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, CustomShader.uMVPMatrix);
        uMVMatrixHandle = GLES20.glGetUniformLocation(mProgram, CustomShader.uMVMatrix);
        uLightPosHandle = GLES20.glGetUniformLocation(mProgram, CustomShader.uLightPos);
        aPositionHandle = GLES20.glGetAttribLocation(mProgram, CustomShader.aPosition);
        aColorHandle = GLES20.glGetAttribLocation(mProgram, CustomShader.aColor);
        aNormalHandle = GLES20.glGetAttribLocation(mProgram, CustomShader.aNormal);

        Matrix.setIdentityM(positionMatrix, 0);
        Matrix.setIdentityM(rotationMatrix, 0);
        Matrix.setIdentityM(scaleMatrix,0);
    }

    public void draw(float[]viewMatrix,float[] projectionMatrix){
        if(lightPoint==null)return;
        GLES20.glUseProgram(mProgram);
        vertexBuffer.position(0);
        GLES20.glEnableVertexAttribArray(aPositionHandle);
        GLES20.glVertexAttribPointer(aPositionHandle, COORDS_PER_VERTEX,
                GLES20.GL_FLOAT, false,
                0,vertexBuffer);

        colorBuffer.position(0);
        GLES20.glEnableVertexAttribArray(aColorHandle);
        GLES20.glVertexAttribPointer(aColorHandle, COLOR_DATA_SIZE,
                GLES20.GL_FLOAT, false,
                0,colorBuffer);

        normalBuffer.position(0);
        GLES20.glEnableVertexAttribArray(aNormalHandle);
        GLES20.glVertexAttribPointer(aNormalHandle, NORMAL_DATA_SIZE,
                GLES20.GL_FLOAT,false,
                0,normalBuffer);

        Matrix.multiplyMM(scratch,0,rotationMatrix, 0,scaleMatrix, 0);
        Matrix.multiplyMM(modelMatrix,0,positionMatrix, 0,scratch,0);
        //Matrix.setIdentityM(modelMatrix,0);
        //calculate model view matrix
        Matrix.multiplyMM(scratch,0,viewMatrix,0,modelMatrix,0);
        GLES20.glUniformMatrix4fv(uMVMatrixHandle,1,false,scratch,0);
        //calculate model view projection matrix
        Matrix.multiplyMM(scratch2,0,projectionMatrix,0,scratch,0);
        GLES20.glUniformMatrix4fv(uMVPMatrixHandle,1,false,scratch2,0);


        GLES20.glUniform3f(uLightPosHandle,
                lightPoint.positionVector[0],
                lightPoint.positionVector[1],
                lightPoint.positionVector[2]);

        GLES20.glDrawArrays(GLES20.GL_TRIANGLES,0,36);
    }

    public void moveTo(float x,float y,float z){
        Matrix.setIdentityM(positionMatrix,0);
        Matrix.translateM(positionMatrix,0,x,y,z);
    }
    public void rotateAroundAxis(float angle,float x,float y,float z){
        Matrix.setRotateM(rotationMatrix,0,angle,x,y,z);
    }
    public void scale(float x,float y,float z){
        Matrix.setIdentityM(scaleMatrix,0);
        Matrix.scaleM(scaleMatrix,0,x,y,z);
    }
    public LightPoint getLightPoint() {
        return lightPoint;
    }

    public void setLightPoint(LightPoint lightPoint) {
        this.lightPoint = lightPoint;
    }
}
