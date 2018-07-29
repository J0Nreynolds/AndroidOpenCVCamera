package net.jonreynolds.androidopencvcamera;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import org.opencv.android.CameraBridgeViewBase;

public class CameraActivity extends Activity {

    // Used to load the 'native-lib' and 'opencv' libraries on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    private MyGLSurfaceView mView;
    private Switch mSwitch = null;
    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 1337;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Check for camera permissions
        final Context context = getApplicationContext();
        if(checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            // Should we show an explanation?
            if (this.shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                // Show some text
                Toast.makeText(context, "Need access to your camera to proceed", Toast.LENGTH_LONG).show();
                this.finish();
            } else {
                // No explanation needed; request the permission
                this.requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);
            }
        }
        else {
            setupApplication();
        }
    }

    private void setupApplication(){
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        setContentView(R.layout.activity);

        // Setup switch to swap between back and front camera
        mSwitch = (Switch) findViewById(R.id.camera_switch);
        mSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    mView.setFrontFacing(true);
                    mView.setCameraIndex(CameraBridgeViewBase.CAMERA_ID_FRONT);
                }
                else {
                    mView.setFrontFacing(false);
                    mView.setCameraIndex(CameraBridgeViewBase.CAMERA_ID_BACK);
                }
            }
        });

        mView = (MyGLSurfaceView) findViewById(R.id.my_gl_surface_view);
        mView.setMaxCameraPreviewSize(1280, 920);
        mView.setCameraTextureListener(mView);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CAMERA: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    this.setupApplication();
                }
            }
        }
    }

    @Override
    protected void onResume() {
        mView.onResume();
        super.onResume();
    }


    @Override
    public void onPause() {
        mView.onPause();
        super.onPause();
    }

    public static int checkSelfPermission(Context context, String permission) {
        if (permission == null) {
            throw new IllegalArgumentException("permission is null");
        }
        return context.checkPermission(permission, android.os.Process.myPid(), android.os.Process.myUid());
    }



}
