package com.cw14.dbz_punching_machine;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

/**
 * Created by lucas on 11/7/16.
 */

public class InstructionsActivity extends AppCompatActivity {

    Button voltarBt;
    ListView lvInstructions;
    String[] instructions = {
            "Regra numero UNO: ",
            "Regra numero dois"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);

        voltarBt = (Button) findViewById(R.id.voltarBt) ;
        lvInstructions = (ListView) findViewById(R.id.lvInstructions);

        lvInstructions.setAdapter(new ArrayAdapter<String>(InstructionsActivity.this,
                android.R.layout.simple_list_item_1, instructions));

        voltarBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

}
