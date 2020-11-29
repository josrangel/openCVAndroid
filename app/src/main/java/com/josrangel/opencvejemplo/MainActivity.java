package com.josrangel.opencvejemplo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.SurfaceView;
import android.widget.Toast;

import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;
/**
 * Tutorial para incorporar OpenCV: http://acodigo.blogspot.com/2018/02/programacion-opencv-para-android.html
 * Tutorial de flavors: https://www.youtube.com/watch?v=mBoNVTjMAtA
* */
public class MainActivity extends AppCompatActivity implements CameraBridgeViewBase.CvCameraViewListener2 {

    private CameraBridgeViewBase cameraView = null;
    private static boolean initOpenCV = false;

    static { initOpenCV = OpenCVLoader.initDebug(); }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cameraView = (CameraBridgeViewBase) findViewById(R.id.cameraview);
        cameraView.setVisibility(SurfaceView.VISIBLE);
        cameraView.setCvCameraViewListener(this);

        Toast.makeText(this, BuildConfig.CONST_PRUEBA,Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (initOpenCV) { cameraView.enableView(); }
    }

    @Override
    public void onPause() {
        super.onPause();

        // Release the camera.
        if (cameraView != null) {
            cameraView.disableView();
            cameraView = null;
        }
    }

    @Override
    public void onCameraViewStarted(int width, int height) { }

    @Override
    public void onCameraViewStopped() { }



    @Override
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {

        Mat src = inputFrame.gray(); // convertir a escala de grises
        Mat cannyEdges = new Mat();  // objeto para almacenar el resultado

        // aplicar el algoritmo canny para detectar los bordes
        Imgproc.Canny(src, cannyEdges, 10, 100);

        // devolver el objeto Mat procesado
        //return inputFrame.rgba();
        return cannyEdges;
    }
}