package com.zeroblitz.helloopengl;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;


public class MainActivity extends ActionBarActivity {
    private CustomGLSurfaceView customGLSurfaceView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private void initViews(){
        customGLSurfaceView = (CustomGLSurfaceView)findViewById(R.id.customGlSurfaceView);
    }
    @Override
    protected void onPause() {
        super.onPause();
        customGLSurfaceView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        customGLSurfaceView.onResume();
    }
}
