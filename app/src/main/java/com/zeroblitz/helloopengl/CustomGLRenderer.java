package com.zeroblitz.helloopengl;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Roscaneanu on 22.11.2014.
 */
public class CustomGLRenderer implements GLSurfaceView.Renderer{
    private static final String TAG = "MyGLRenderer";
    private Circle circle;

    private final float[] mvpMatrix = new float[16];
    private final float[] projectionMatrix = new float[16];
    private final float[] cameraPositionMatrix = new float[16];
    private final float[] rotationMatrix = new float[16];

    private final float[] manMatrix0 = new float[16];
    private final float[] manMatrix1 = new float[16];


    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES20.glClearColor(0,0,0,1);
        GLES20.glEnable(GLES20.GL_CULL_FACE);
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);

        circle = new Circle(6);
        circle.moveTo(0,0,-2);

    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);

        if(width>height){
            float ratio = (float) width / height;
            Matrix.frustumM(projectionMatrix, 0, -ratio, ratio, -1, 1, 3, 7);
        }
        else{
            float ratio = (float) height / width;
            Matrix.frustumM(projectionMatrix, 0, -1, 1, -ratio, ratio, 3, 7);
        }
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT|GLES20.GL_DEPTH_BUFFER_BIT);
        Matrix.setLookAtM(cameraPositionMatrix, 0, 0, 0, 4, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
        Matrix.multiplyMM(mvpMatrix, 0, projectionMatrix, 0, cameraPositionMatrix, 0);

        float v = 0.040f;
        long r = (long)(360/v);
        long time = System.currentTimeMillis()%r;
        float angle = v * time;
        circle.rotateAroundAxis(angle,0,0,1);
        circle.draw(mvpMatrix);
    }
}
