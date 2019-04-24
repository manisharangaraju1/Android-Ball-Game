package com.androidcourse.manisha.assignment3;
//State = 0 : Game is in the end state where the game begins
//State = 1 : Game is in the start state where the user can create white circles
//State = 2: Game is in the play state where the white circles are falling down and the black circle can be moved
//State = 3 : Game is in the pause state where position of white circles is retained and black circle cant be moved
//State = 4 : Game is in resume state where white circles continue to fall
public class State {

    public static int state = 0;
    public static int score = 0;
    public  static int lives = 3;
}
