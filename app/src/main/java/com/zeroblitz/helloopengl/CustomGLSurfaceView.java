package com.zeroblitz.helloopengl;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

/**
 * Created by Roscaneanu on 22.11.2014.
 */
public class CustomGLSurfaceView extends GLSurfaceView{
    private CustomGLRenderer renderer;

    public CustomGLSurfaceView(Context context){
        super(context);
        initialize();
    }

    public CustomGLSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }
    //CustomGLSurfaceView(Context context, AttributeSet attrs,)
    private void initialize(){
        setEGLContextClientVersion(2);
        renderer = new CustomGLRenderer();
        setRenderer(renderer);
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
    }
}
