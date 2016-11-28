package com.cw14.dbz_punching_machine;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by lucas on 11/5/16.
 */

public class GraphActivity extends AppCompatActivity {
    private GraphView graphView;
    private Button voltarBt;
    private RelativeLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        Bundle extras = getIntent().getExtras();

        voltarBt = (Button) findViewById(R.id.voltarBt);
        graphView = (GraphView) findViewById(R.id.graphView);
        linearLayout = (RelativeLayout) findViewById(R.id.graphLayout);

        final float[] results;

        results = extras.getFloatArray("ptsY");
        if (results != null) graphView.setPtsY(results);

        voltarBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        graphView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                float max = 0;
                float scaleMax = 0;
                int marginTop = 70;
                int marginLeft = 90;
                int marginRight = 70;
                int marginBottom = 70;
                int measuredWidth = graphView.getMeasuredWidth();
                int measuredHeight = graphView.getMeasuredHeight();
                int xAxisSize = measuredWidth - marginLeft - marginRight;
                int yAxisSize = measuredHeight - marginTop - marginBottom;

                for(int i=0; i<results.length; i++) {
                    if(results[i] > max) {
                        max = results[i];
                    }
                }
                // Get next full hundred bigger than n (if n = 35, returns 100; if n = 1399, returns 1400).
                while(scaleMax <= max)
                    scaleMax += 100;

                RelativeLayout.LayoutParams params;

                TextView xScaleTextView0 = (TextView) findViewById(R.id.tvscalex0);
                TextView xScaleTextView1 = (TextView) findViewById(R.id.tvscalex1);
                TextView xScaleTextView2 = (TextView) findViewById(R.id.tvscalex2);
                TextView xScaleTextView3 = (TextView) findViewById(R.id.tvscalex3);

                TextView yScaleTextView0 = (TextView) findViewById(R.id.tvscaley0);
                TextView yScaleTextView1 = (TextView) findViewById(R.id.tvscaley1);
                TextView yScaleTextView2 = (TextView) findViewById(R.id.tvscaley2);
                TextView yScaleTextView3 = (TextView) findViewById(R.id.tvscaley3);

                xScaleTextView0.setText("0");
                xScaleTextView1.setText("1");
                xScaleTextView2.setText("2");
                xScaleTextView3.setText("3");

                yScaleTextView0.setText(Integer.toString((int) (1 * scaleMax / 4)));
                yScaleTextView1.setText(Integer.toString((int) (2 * scaleMax / 4)));
                yScaleTextView2.setText(Integer.toString((int) (3 * scaleMax / 4)));
                yScaleTextView3.setText(Integer.toString((int) (4 * scaleMax / 4)));

                params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                params.leftMargin = marginLeft - 5; //XCOORD
                params.topMargin = measuredHeight - marginBottom + 20; //YCOORD
                xScaleTextView0.setLayoutParams(params);

                params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                params.leftMargin = marginLeft - 5 + (1 * xAxisSize / 3); //XCOORD
                params.topMargin = measuredHeight - marginBottom + 20; //YCOORD
                xScaleTextView1.setLayoutParams(params);

                params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                params.leftMargin = marginLeft - 5 + (2 * xAxisSize / 3); //XCOORD
                params.topMargin = measuredHeight - marginBottom + 20; //YCOORD
                xScaleTextView2.setLayoutParams(params);

                params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                params.leftMargin = marginLeft - 5 + (3 * xAxisSize / 3); //XCOORD
                params.topMargin = measuredHeight - marginBottom + 20; //YCOORD
                xScaleTextView3.setLayoutParams(params);

                params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                params.leftMargin = 10; //XCOORD
                params.topMargin = measuredHeight - marginBottom - 20 - (1 * yAxisSize / 4); //YCOORD
                yScaleTextView0.setLayoutParams(params);

                params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                params.leftMargin = 10; //XCOORD
                params.topMargin = measuredHeight - marginBottom - 20 - (2 * yAxisSize / 4); //YCOORD
                yScaleTextView1.setLayoutParams(params);

                params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                params.leftMargin = 10; //XCOORD
                params.topMargin = measuredHeight - marginBottom - 20 - (3 * yAxisSize / 4); //YCOORD
                yScaleTextView2.setLayoutParams(params);

                params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                params.leftMargin = 10; //XCOORD
                params.topMargin = measuredHeight - marginBottom - 20 - (4 * yAxisSize / 4); //YCOORD
                yScaleTextView3.setLayoutParams(params);

                graphView.invalidate();
                graphView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }
}