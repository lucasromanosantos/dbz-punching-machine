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

        if (extras != null) {
            float[] results = extras.getFloatArray("ptsY");
            if (results != null) graphView.setPtsY(results);
        }

        voltarBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        graphView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int marginTop = 70;
                int marginLeft = 70;
                int marginRight = 70;
                int marginBottom = 40;
                //int measuredWidth = graphView.getMeasuredWidth();
                //int measuredHeight = graphView.getMeasuredHeight();
                int measuredWidth = 720;
                int measuredHeight = 926;
                int xAxisSize = measuredWidth - marginLeft - marginRight;

                TextView xScaleTextView1 = (TextView) findViewById(R.id.tvscalex1);
                TextView xScaleTextView2 = (TextView) findViewById(R.id.tvscalex2);
                TextView xScaleTextView3 = (TextView) findViewById(R.id.tvscalex3);

                xScaleTextView1.setText("1");
                xScaleTextView2.setText("2");
                xScaleTextView3.setText("3");

                System.out.println(measuredHeight);
                System.out.println(measuredHeight - marginBottom);
/*
                xScaleTextView1.setTop(measuredHeight - marginBottom);
                xScaleTextView2.setTop(measuredHeight - marginBottom);
                xScaleTextView3.setTop(measuredHeight - marginBottom);

                xScaleTextView1.setLeft(marginLeft + (0 * xAxisSize / 4));
                xScaleTextView2.setLeft(marginLeft + (1 * xAxisSize / 4));
                xScaleTextView3.setLeft(marginLeft + (2 * xAxisSize / 4));
*/

                RelativeLayout.LayoutParams params;

                params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                params.leftMargin = marginLeft + (0 * xAxisSize / 4); //XCOORD
                params.topMargin = measuredHeight - marginBottom; //YCOORD
                xScaleTextView1.setLayoutParams(params);

                params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                params.leftMargin = marginLeft + (1 * xAxisSize / 4); //XCOORD
                params.topMargin = measuredHeight - marginBottom; //YCOORD
                xScaleTextView1.setLayoutParams(params);

                params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                params.leftMargin = marginLeft + (2 * xAxisSize / 4); //XCOORD
                params.topMargin = measuredHeight - marginBottom; //YCOORD
                xScaleTextView1.setLayoutParams(params);


                System.out.println(marginLeft);

                graphView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }

    void addTextView() {
        System.out.println(123);
    }
}