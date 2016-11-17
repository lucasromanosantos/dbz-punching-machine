package com.cw14.dbz_punching_machine;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Cristian on 16/11/2016.
 */

public class HighscoresActivity extends AppCompatActivity {
    private ListView highscoresList;
    private ArrayAdapter<String> highscoresAdapter;
    private ArrayList<String> highscoresArray;
    private LinearLayout linearLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscores);

        linearLayout = (LinearLayout) findViewById(R.id.highscoresLayout);
        linearLayout.getBackground().setAlpha(120);

        highscoresList = (ListView) findViewById(R.id.highscoresListView);
        highscoresArray = new ArrayList<String>();
        highscoresAdapter = new ArrayAdapter<String>(this, R.layout.highscore_text_view, R.id.roxTextView, highscoresArray);
        highscoresList.setAdapter(highscoresAdapter);

        // Setando qualquer coisa, e fora de ordem. Com isso testo sort e a exibição.
        /*highscoresArray.add(0, "15");
        highscoresArray.add(0, "37");
        highscoresArray.add(0, "22");
        highscoresArray.add(0, "142");
        highscoresArray.add(0, "88");*/
        setScoresFromFile();
        highscoresAdapter.notifyDataSetChanged();

        highscoresList.setBackgroundColor(Color.TRANSPARENT);
        highscoresList.setCacheColorHint(Color.TRANSPARENT);

        // Sorting
        Collections.sort(highscoresArray, new Comparator<String>() {
            @Override
            public int compare(String str1, String str2)
            {
                return Integer.parseInt(str2) - Integer.parseInt(str1);
            }
        });

        for(int i=0; i<highscoresArray.size(); i++) {
            highscoresArray.set(i, Integer.toString(i+1) + ". " + highscoresArray.get(i));
        }
    }

    private void setScoresFromFile() {
        FileInputStream fin;
        int c;
        String string = "";
        try {
            fin = openFileInput("scores.txt");
            while( (c = fin.read()) != -1) {
                string = string + Character.toString((char) c);
            }
            string = string.trim();
            String tok[] = string.split(" ");
            for(String s:tok) {
                highscoresArray.add(0, s);
            }
            fin.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
