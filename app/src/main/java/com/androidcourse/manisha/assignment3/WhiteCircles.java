package com.androidcourse.manisha.assignment3;

public class WhiteCircles {
    float xCoordinate;
    float yCoordinate;
    float radius;
    int score;
    float scalingFactor;

    public WhiteCircles(float xCoordinate, float yCoordinate, float radius) {
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.radius = radius;
        this.score = 1;
        this.scalingFactor = 3;
    }

    public float getScalingFactor() {
        return scalingFactor;
    }

    public void setScalingFactor(float scalingFactor) {
        this.scalingFactor = scalingFactor;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public float getxCoordinate() {
        return xCoordinate;
    }

    public void setxCoordinate(float xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    public float getyCoordinate() {
        return yCoordinate;
    }

    public void setyCoordinate(float yCoordinate) {
        this.yCoordinate = yCoordinate;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }
}
