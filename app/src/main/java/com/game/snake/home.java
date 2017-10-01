package com.game.snake;

import android.content.Context;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.Arrays;

public class home extends AppCompatActivity implements SensorEventListener{
    // Start with some variables
    private SensorManager sensorMan;
    private Sensor rotationSensor;
    private final float[] mRotationMatrix = new float[16];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        displayText(originalScreenSize());

        // load sensors information
        sensorMan = (SensorManager) getSystemService(SENSOR_SERVICE);
        rotationSensor = sensorMan.getDefaultSensor(Sensor.TYPE_GAME_ROTATION_VECTOR);
        // initialize the rotation matrix to identity
        mRotationMatrix[ 0] = 1;
        mRotationMatrix[ 4] = 1;
        mRotationMatrix[ 8] = 1;
        mRotationMatrix[12] = 1;
    }

    private void displayText(String textData){
        TextView textViewScreenSize = (TextView) findViewById(R.id.textViewScreenSize);
        textViewScreenSize.setText(textData);
    }

    private String originalScreenSize() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getRealSize(size);
        return String.valueOf("Original screen size: " + size.x) + "X" + String.valueOf(size.y);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_GAME_ROTATION_VECTOR) {
            SensorManager.getRotationMatrixFromVector(mRotationMatrix , event.values);
        }
        displayText(Arrays.toString(mRotationMatrix));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // required method
    }

    @Override
    public void onResume() {
        super.onResume();
        sensorMan.registerListener(this, rotationSensor, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public void onPause() {
        super.onPause();
        sensorMan.unregisterListener(this);
    }
}
