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
            "1. Clique em PUNCH!: ",
            "2. Use toda sua força e soque o ar",
            "3. Impressione seus amigos e o Majinboo",
            "4. Descubra quem é o mais forte do grupo"
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
