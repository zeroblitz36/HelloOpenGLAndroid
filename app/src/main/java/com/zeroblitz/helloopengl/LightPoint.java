package com.zeroblitz.helloopengl;

import android.opengl.GLES20;
import android.opengl.Matrix;

/**
 * Created by Roscaneanu on 04.12.2014.
 */
public class LightPoint {
    private final float[] mvpMatrix = new float[16];

    private int mProgram;
    private int aPositionHandle;
    private int uMVPMatrixHandle;

    public final float positionVector[] = new float[]{0,0,0,1};
    public final float position[] = new float[16];
    private final float dummy[] = new float[16];
    private final float dummy2[] = new float[16];
    private final float dummyVector[] = new float[4];

    public LightPoint(){
        mProgram = CustomShader.createProgram(CustomShader.pointVertexShader,CustomShader.pointFragmentShader);
        aPositionHandle = GLES20.glGetAttribLocation(mProgram, CustomShader.aPosition);
        uMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, CustomShader.uMVPMatrix);

        Matrix.setIdentityM(position,0);
    }

    public void draw(float[] mvpMatrix){
        GLES20.glUseProgram(mProgram);

        //GLES20.glVertexAttrib3f(aPositionHandle,dummyVector[0],dummyVector[1],dummyVector[2]);
        GLES20.glVertexAttrib4f(aPositionHandle,positionVector[0],positionVector[1],positionVector[2],1);
        GLES20.glDisableVertexAttribArray(aPositionHandle);

        Matrix.multiplyMM(dummy,0,mvpMatrix,0,position,0);
        //Matrix.multiplyMV(dummy2, 0, dummy, 0, positionVector, 0);
        GLES20.glUniformMatrix4fv(uMVPMatrixHandle,1,false,dummy,0);
        GLES20.glDrawArrays(GLES20.GL_POINTS,0,1);
    }

    public void moveTo(float x,float y,float z){
        Matrix.setIdentityM(position,0);
        Matrix.translateM(position,0,x,y,z);
    }
}
