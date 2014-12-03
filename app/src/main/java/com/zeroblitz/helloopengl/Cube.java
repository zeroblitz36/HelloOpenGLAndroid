package com.zeroblitz.helloopengl;


import android.opengl.GLES20;
import android.opengl.Matrix;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

/**
 * Created by Roscaneanu on 03.12.2014.
 */
public class Cube {
    private static final int SIZE_OF_FLOAT = 4;
    private static final int SIZE_OF_SHORT = 2;

    private final FloatBuffer vertexBuffer;
    private final ShortBuffer drawListBuffer;

    private int mProgram;
    private int uMVPMatrixHandle;
    private int uMVMatrixHandle;
    private int uLightPosHandle;

    private int aPositionHandle;
    private int aColorHandle;
    private int aNormalHandle;

    private static final int COORDS_PER_VERTEX = 3;
    private final float coords[] = {
            -0.5f, -0.5f, -0.5f,
             0.5f, -0.5f, -0.5f,
             0.5f,  0.5f, -0.5f,
            -0.5f,  0.5f, -0.5f,
            -0.5f, -0.5f,  0.5f,
             0.5f, -0.5f,  0.5f,
             0.5f,  0.5f,  0.5f,
            -0.5f,  0.5f,  0.5f
    };
    private final short drawOrder[] = {
            // Down
            0, 1, 2, 0, 2, 3,
            // West
            0, 4, 7, 0, 7, 3,
            // North
            3, 7, 6, 3, 6, 2,
            // East
            2, 5, 1, 2, 6, 5,
            // South
            0, 5, 4, 0, 1, 5,
            // Up
            4, 6, 7, 4, 5, 6 };
    private int vertexCount = coords.length / COORDS_PER_VERTEX;
    private int vertexStride = COORDS_PER_VERTEX * SIZE_OF_FLOAT;

    private final float scratch[] = new float[16];
    private final float scratch2[] = new float[16];
    public final float position[] = new float[16];
    public final float rotation[] = new float[16];
    public final float lightPosition[] = new float[16];
    public Cube(){
        ByteBuffer bb = ByteBuffer
                .allocateDirect(coords.length * SIZE_OF_FLOAT);
        bb.order(ByteOrder.nativeOrder());
        vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(coords).position(0);

        bb = ByteBuffer.allocateDirect(drawOrder.length * SIZE_OF_SHORT);
        bb.order(ByteOrder.nativeOrder());
        drawListBuffer = bb.asShortBuffer();
        drawListBuffer.put(drawOrder).position(0);

        mProgram = CustomShader.createProgram(CustomShader.gouradVertexShader,
                CustomShader.simpleFragmentShaderCode);
        uMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, CustomShader.uMVPMatrix);
        uMVMatrixHandle = GLES20.glGetUniformLocation(mProgram, CustomShader.uMVMatrix);
        uLightPosHandle = GLES20.glGetUniformLocation(mProgram, CustomShader.uLightPos);
        aPositionHandle = GLES20.glGetAttribLocation(mProgram, CustomShader.aPosition);
        aColorHandle = GLES20.glGetAttribLocation(mProgram, CustomShader.aColor);
        aNormalHandle = GLES20.glGetAttribLocation(mProgram, CustomShader.aNormal);

        Matrix.setIdentityM(position, 0);
        Matrix.setIdentityM(rotation, 0);
        Matrix.setIdentityM(lightPosition, 0);
    }
}
