package com.cw14.dbz_punching_machine;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

/**
 * Created by lucas on 11/5/16.
 */

public class GraphActivity extends AppCompatActivity {
    private GraphView graphView;
    private Button voltarBt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        Bundle extras = getIntent().getExtras();
        graphView = (GraphView) findViewById(R.id.graphView);
        voltarBt = (Button) findViewById(R.id.voltarBt);

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

    }
}