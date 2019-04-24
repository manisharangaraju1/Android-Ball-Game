package com.androidcourse.manisha.assignment3;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import java.util.ArrayList;
import java.util.List;
import android.os.Handler;
import android.widget.Toast;

//State = 0 : Game is in the end state where the game begins
//State = 1 : Game is in the start state where the user can create white circles
//State = 2: Game is in the play state where the white circles are falling down and the black circle can be moved
//State = 3 : Game is in the pause state where position of white circles is retained and black circle cant be moved
//State = 4 : Game is in resume state where white circles continue to fall
public class BallGame extends View{
    List<WhiteCircles> circles;
    Paint bpaint,wpaint;
    BlackCircle blackCircle;
    GestureDetector gestureListener;
    Handler handler = new Handler();
    Runnable runnable;
    DataTransfer dataTransfer;

    public DataTransfer getDataTransfer() {
        return dataTransfer;
    }

    public void setDataTransfer(DataTransfer dataTransfer) {
        this.dataTransfer = dataTransfer;
    }

    public BallGame(Context context, AttributeSet attrs) {
        super(context, attrs);
        bpaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        wpaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        wpaint.setStyle(Paint.Style.STROKE);
        bpaint.setColor(Color.BLACK);
        circles = new ArrayList<>();
        blackCircle = new BlackCircle(500,1400,30);
        gestureListener = new GestureDetector(context, new Gestures());
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        blackCircle = new BlackCircle(getWidth()/2,getHeight()-blackCircle.getRadius(),blackCircle.getRadius());
        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(State.state == 2 || State.state == 4 || State.state == 3 ){
            canvas.drawCircle(blackCircle.getxCoordinate(),blackCircle.getyCoordinate(),blackCircle.getRadius(),bpaint);
        }
        if(State.state == 0) {
           circles.clear();
           State.score = 0;
            dataTransfer.setScore(0);
           State.lives = 3;
            dataTransfer.setLives(State.lives);
            dataTransfer.setState("START");
        }
        if(circles != null) {
            for(WhiteCircles c : circles){
                canvas.drawCircle(c.getxCoordinate(),c.getyCoordinate(),c.getRadius(),wpaint);
            }
        }
        fallingCircles();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN :
                return  gestureListener.onTouchEvent(event);
            case MotionEvent.ACTION_UP:
                handler.removeCallbacks(runnable);
            case MotionEvent.ACTION_POINTER_DOWN:
                return true;
        }
        return  gestureListener.onTouchEvent(event);
    }

    private void fallingCircles(){
            if(State.state == 2 || State.state == 4 ) {
                for(WhiteCircles c : circles) {
                    if(getHeight() - c.getyCoordinate() < 0){
                        State.score +=c.getScore();
                        dataTransfer.setScore(State.score);
                        c.setScore(c.getScore() + 1);
                        c.setyCoordinate(0);
                            if (c.getScalingFactor() < 12f)
                                c.setScalingFactor( c.getScalingFactor() * 1.25f);

                        }

                    c.setyCoordinate(c.getyCoordinate() + c.scalingFactor);
                    postInvalidate();

                    if(Collision(c)){
                        State.lives -= 1;
                        dataTransfer.setLives(State.lives);
                        if(State.lives == 0) {
                            Toast.makeText(getContext(),"Your Score:"+State.score,Toast.LENGTH_LONG).show();
                            circles = new ArrayList<>();
                            State.state = 0;
                            State.score = 0;
                            State.lives = 3;
                            dataTransfer.setScore(State.score );
                            dataTransfer.setLives(State.lives );
                            dataTransfer.setState("START");
                            postInvalidate();
                        }
                        c.setyCoordinate(0);
                    }
                    postInvalidate();
                }
            }
    }
    private boolean Collision(WhiteCircles circle) {
        float x1 = blackCircle.getxCoordinate();
        float y1 = blackCircle.getyCoordinate();
        float radius1 = blackCircle.getRadius();
               float x2 = circle.getxCoordinate();
               float y2 = circle.getyCoordinate();
               float radius2 = circle.getRadius();
               float dist = (float)Math.sqrt(Math.pow((x1-x2),2) + Math.pow((y1-y2),2));
               if(dist <= (radius1+radius2)){
                   return true;
               }
           return false;
    }

    public class Gestures extends GestureDetector.SimpleOnGestureListener {
        @Override
        public void onLongPress(MotionEvent e) {
            super.onLongPress(e);
            scaling();
        }
        @Override
        public boolean onDown(MotionEvent event) {
            if(State.state == 1){
                float xSelected = event.getX();
                float ySelected = event.getY();
                WhiteCircles circleCreated = new WhiteCircles(xSelected, ySelected, 30);
                circles.add(circleCreated);
                postInvalidate();
            }else if(State.state == 2 || State.state == 4 ) {
                   float selectedX = event.getX();
                   if(selectedX > getWidth()/2){
                       if(blackCircle.getxCoordinate() + 20f < getWidth()){
                           blackCircle.setxCoordinate(blackCircle.getxCoordinate() + 20f);
                           }else {
                           blackCircle.setxCoordinate(blackCircle.getxCoordinate() - 20f);
                       }
                   }else{
                       if(blackCircle.getxCoordinate() - 20f >0){
                           blackCircle.setxCoordinate(blackCircle.getxCoordinate() - 20f);
                       }else{
                           blackCircle.setxCoordinate(blackCircle.getxCoordinate() + 20f);
                       }
                   }
                postInvalidate();
            }
            return true;
        }
    }

    private void scaling() {
        if(State.state == 1) {
            runnable = new Runnable() {
                @Override
                public void run() {
                    if(circles.size() != 0) {
                        WhiteCircles lastCircle = circles.get(circles.size() - 1);
                        if(lastCircle.getyCoordinate() + lastCircle.getRadius() <= getHeight()
                                && lastCircle.getxCoordinate() + lastCircle.getRadius() <= getWidth()
                                && lastCircle.getxCoordinate() - lastCircle.getRadius() >=0
                                && lastCircle.getyCoordinate() - lastCircle.getRadius() >=0 ){

                            lastCircle.setRadius(lastCircle.getRadius() + 2f);
                        }
                        postInvalidate();
                        handler.postDelayed(this, 20);
                    }
                }
            };
            handler.post(runnable);
        }
    }
}
