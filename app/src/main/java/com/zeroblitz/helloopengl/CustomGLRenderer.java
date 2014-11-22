package com.zeroblitz.helloopengl;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

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
    private final float[] cameraPositinoMatrix = new float[16];
    private final float[] rotationMatrix = new float[16];

    private final float[] manMatrix0 = new float[16];
    private final float[] manMatrix1 = new float[16];


    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES20.glClearColor(0,0,0,1);
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);
        GLES20.glDepthFunc(GLES20.GL_LEQUAL);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {

    }

    @Override
    public void onDrawFrame(GL10 gl) {

    }
}
