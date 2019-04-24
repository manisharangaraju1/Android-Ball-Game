package com.androidcourse.manisha.assignment3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements DataTransfer {
     Button startGame;
     Button endGame;
    BallGame ballGame;
    TextView livesCount;
    TextView scoreCount;
    int finalscore;
//State = 0 : Game is in the end state where the game begins
//State = 1 : Game is in the start state where the user can create white circles
//State = 2: Game is in the play state where the white circles are falling down and the black circle can be moved
//State = 3 : Game is in the pause state where position of white circles is retained and black circle cant be moved
//State = 4 : Game is in resume state where white circles continue to fall
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ballGame = (BallGame)findViewById(R.id.ballGame);
        scoreCount = findViewById(R.id.scoreCount);
        livesCount = findViewById(R.id.livesCount);
        startGame = (Button) findViewById(R.id.start);
        endGame = (Button) findViewById(R.id.end);
        ballGame.setDataTransfer((DataTransfer) this);
        State.score = 0;
        if(State.state == 0){
            startGame.setText("START");
        }
        startGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(startGame.getText().equals("START")) {
                    State.state = 1;
                    startGame.setText("PLAY");
                    ballGame.postInvalidate();
                }else if(startGame.getText().equals("PLAY")) {
                    State.state = 2;
                    startGame.setText("PAUSE");
                    ballGame.postInvalidate();
                }else if(startGame.getText().equals("PAUSE") ) {
                    State.state = 3;
                    startGame.setText("RESUME");
                    ballGame.postInvalidate();
                }else if(startGame.getText().equals("RESUME")) {
                    State.state = 4;
                    startGame.setText("PAUSE");
                    ballGame.postInvalidate();
                }

            }
        });
        endGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ballGame.postInvalidate();
                State.state = 0;
                State.score = 0;
                scoreCount.setText(State.score+"");
                livesCount.setText(State.lives+"");
                ballGame.postInvalidate();
                startGame.setText("START");
                Intent intent = new Intent(MainActivity.this,ScoreDisplay.class);
                intent.putExtra("score",finalscore);
                startActivity(intent);
            }
        });
    }

    @Override
    public void setScore(int score) {
        finalscore = score;
        scoreCount.setText(score+"");
    }

    @Override
    public void setLives(int lives) {

        livesCount.setText(lives+"");
    }

    @Override
    public void setState(String state) {
        startGame.setText(state);
    }
}
