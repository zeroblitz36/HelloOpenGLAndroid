package com.zeroblitz.helloopengl;

import android.app.Activity;
import android.os.Bundle;


public class MainActivity extends Activity {
    private CustomGLSurfaceView customGLSurfaceView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
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
