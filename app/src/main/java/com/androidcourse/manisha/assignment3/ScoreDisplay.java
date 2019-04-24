package com.androidcourse.manisha.assignment3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ScoreDisplay extends Activity {
    TextView finalScore;
    Button playAgain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_score);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int)(width * 0.5),(int)(height * 0.4));

        Intent intent= getIntent();
        Bundle bundle = intent.getExtras();
        int score = bundle.getInt("score");
        finalScore = (TextView)findViewById(R.id.finalScore);
        finalScore.setText(score +"");
        playAgain = (Button) findViewById(R.id.playAgain);
        playAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
