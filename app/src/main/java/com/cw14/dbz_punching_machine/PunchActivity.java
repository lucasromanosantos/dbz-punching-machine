package com.cw14.dbz_punching_machine;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.Image;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by lucas on 11/5/16.
 */

public class PunchActivity extends AppCompatActivity  {

    private SensorEventListener myAccelerometerListener;
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private Boolean mInitialized = false;
    private float mLastX, mLastY, mLastZ;

    TableLayout afterPunchPanel;
    Button repetirBt;
    Button exibirGraficoBt;
    Button voltarBt;
    ImageView goku;

    List<Double> results = new ArrayList<Double>();
    Double resultado;
    Boolean fim;

    TextView countdown;
    // Get norm given a vector.
    protected double getNorm(float x, float y, float z) {
        double sum = x*x + y*y + z*z;
        return Math.sqrt(sum);
    }

    // Accelerator stuff.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_punch);

        fim = false;
        countdown = (TextView) findViewById(R.id.countdown);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(myAccelerometerListener, mAccelerometer,
                SensorManager.SENSOR_DELAY_NORMAL);

        afterPunchPanel = (TableLayout) findViewById(R.id.afterPunchPanel);
        repetirBt = (Button) findViewById(R.id.repetirBt);
        exibirGraficoBt = (Button) findViewById(R.id.showGraphFadeBt);
        voltarBt = (Button) findViewById(R.id.voltarBt);
        goku = (ImageView) findViewById(R.id.majinboo);

        myAccelerometerListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                float NOISE = 0.2f;
                TextView tvX = (TextView) findViewById(R.id.x_axis);
                TextView tvY = (TextView) findViewById(R.id.y_axis);
                TextView tvZ = (TextView) findViewById(R.id.z_axis);
                //ImageView iv = (ImageView) findViewById(R.id.image);
                float x = event.values[0]; // X
                float y = event.values[1]; // Y
                float z = event.values[2]; // Z
                if (!mInitialized) {
                    mLastX = x;
                    mLastY = y;
                    mLastZ = z;
                    tvX.setText("0.0");
                    tvY.setText("0.0");
                    tvZ.setText("0.0");
                    mInitialized = true;
                } else {
                    float deltaX = Math.abs(mLastX - x);
                    float deltaY = Math.abs(mLastY - y);
                    float deltaZ = Math.abs(mLastZ - z);
                    if (deltaX < NOISE) deltaX = (float) 0.0;
                    if (deltaY < NOISE) deltaY = (float) 0.0;
                    if (deltaZ < NOISE) deltaZ = (float) 0.0;
                    mLastX = x;
                    mLastY = y;
                    mLastZ = z;
                    tvX.setText(Float.toString(deltaX));
                    tvY.setText(Float.toString(deltaY));
                    tvZ.setText(Float.toString(deltaZ));
                    results.add(getNorm(deltaX, deltaY, deltaZ));
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        };

        fim = false;
        startFirstCountDown();

        exibirGraficoBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(fim) {
                    Intent intent = new Intent(PunchActivity.this, GraphActivity.class);
                    float[] array = new float[results.size()];
                    for (int i = 0; i < results.size(); i++)
                        array[i] = results.get(i).floatValue();
                    Bundle b = new Bundle();
                    b.putFloatArray("ptsY", array);
                    intent.putExtras(b);
                    startActivity(intent);
                }
            }
        });

        repetirBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                results.clear();
                resultado = 0d;
                mSensorManager.registerListener(myAccelerometerListener, mAccelerometer,
                        SensorManager.SENSOR_DELAY_NORMAL);
                startFirstCountDown();
                afterPunchPanelFadeOut();

            }
        });

        voltarBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void startFirstCountDown() {
        new CountDownTimer(4000, 1000) {
            public void onTick(long millisUntilFinished) {
                countdown.setText("" + millisUntilFinished / 1000);
            }

            public void onFinish() {
                fim = true;
                countdown.setText("Soque!");
                resultado = Collections.max(results);
                //countdown.setText("" + resultado);
                startCountAnimation();
                mSensorManager.unregisterListener(myAccelerometerListener);

                afterPunchPanelFadeIn();
            }
        }.start();

    }

    private void afterPunchPanelFadeIn() {
        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(new AccelerateInterpolator()); //add this
        fadeIn.setDuration(1000);
        fadeIn.start();

        AnimationSet animation = new AnimationSet(false);
        animation.addAnimation(fadeIn);
        afterPunchPanel.setAnimation(animation);
        afterPunchPanel.setAlpha(1);

        Animation fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fadein);
        goku.startAnimation(fadeInAnimation);

    }

    private void afterPunchPanelFadeOut() {
        Animation fadeIn = new AlphaAnimation(1, 0);
        fadeIn.setInterpolator(new AccelerateInterpolator()); //add this
        fadeIn.setDuration(500);
        fadeIn.start();

        AnimationSet animation = new AnimationSet(false);
        animation.addAnimation(fadeIn);
        afterPunchPanel.setAnimation(animation);
        afterPunchPanel.setAlpha(0);
    }

    private void startCountAnimation() {
        ValueAnimator animator = new ValueAnimator();
        animator.setObjectValues(0, resultado.intValue());
        animator.setDuration(1000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                countdown.setText("" + (int) animation.getAnimatedValue());
            }
        });
        animator.start();
    }

    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(myAccelerometerListener, mAccelerometer,
                SensorManager.SENSOR_DELAY_NORMAL);
    }
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(myAccelerometerListener);
    }

}
