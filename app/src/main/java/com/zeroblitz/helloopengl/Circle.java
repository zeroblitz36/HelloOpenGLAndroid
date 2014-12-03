package com.zeroblitz.helloopengl;

import android.opengl.GLES20;
import android.opengl.Matrix;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * Created by Roscaneanu on 22.11.2014.
 */
public class Circle {
    private static final float radius = 1;
    private static final int SIZE_OF_FLOAT = 4;
    private final FloatBuffer vertexBuffer;
    private int mProgram;
    private int aPositionHandle;
    private int uColorHandle;
    private int uMVPMatrixHandle;
    private static final int COORDS_PER_VERTEX = 3;
    private int vertexCount;
    private int vertexStride = COORDS_PER_VERTEX * SIZE_OF_FLOAT;

    float color[] = { 1.0f, 0.2666666f, 0.2666666f, 0.0f };

    private final float scratch[] = new float[16];
    private final float scratch2[]= new float[16];
    public final float position[] = new float[16];
    public final float rotation[] = new float[16];
    public Circle(int n){
        if(n<3) n = 3;
        vertexCount = n+2;

        float coords[] = new float[vertexCount*COORDS_PER_VERTEX];
        coords[0]=0;
        coords[1]=0;
        coords[2]=0;
        for(int i=1;i<n+1;i++)
        {
            coords[i*3] 	= (float) Math.cos(2*Math.PI*(i-1)/n);
            coords[i*3+1] 	= (float) Math.sin(2*Math.PI*(i-1)/n);
            coords[i*3+2]  = 0;
        }
        coords[(n+1)*3]   = coords[3];
        coords[(n+1)*3+1] = coords[4];
        coords[(n+1)*3+2] = coords[5];

        ByteBuffer bb = ByteBuffer.allocateDirect(coords.length * SIZE_OF_FLOAT);
        bb.order(ByteOrder.nativeOrder());
        vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(coords);
        vertexBuffer.position(0);

        mProgram = CustomShader.createProgram(CustomShader.simpleVertexShaderCode, CustomShader.simpleFragmentShaderCode);
        aPositionHandle = GLES20.glGetAttribLocation(mProgram, CustomShader.aPosition);
        uColorHandle = GLES20.glGetUniformLocation(mProgram, CustomShader.uColor);
        uMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, CustomShader.uMVPMatrix);
        Matrix.setIdentityM(position, 0);
        Matrix.setIdentityM(rotation, 0);
    }

    public void draw(float[] mvpMatrix){
        GLES20.glUseProgram(mProgram);

        GLES20.glEnableVertexAttribArray(aPositionHandle);
        GLES20.glVertexAttribPointer(aPositionHandle, COORDS_PER_VERTEX,
                GLES20.GL_FLOAT, false,
                vertexStride, vertexBuffer);

        GLES20.glUniform4fv(uColorHandle, 1, color,0);

        Matrix.multiplyMM(scratch, 0, mvpMatrix, 0, position, 0);
        Matrix.multiplyMM(scratch2, 0, scratch, 0, rotation, 0);
        GLES20.glUniformMatrix4fv(uMVPMatrixHandle, 1, false, scratch2,0);

        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, vertexCount);
    }
    public void moveTo(float x,float y,float z){
        Matrix.setIdentityM(position, 0);
        Matrix.translateM(position, 0, x, y, z);
    }
    public void rotateAroundAxis(float angle,float x,float y,float z){
        Matrix.setRotateM(rotation, 0, angle, x, y, z);
    }
}
