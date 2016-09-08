package com.example.polyc.tictacgame;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.*;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.*;
import android.view.animation.Animation.AnimationListener;

public class MainActivity extends AppCompatActivity {

    //declare animations

    Animation fade_out, blink,fade_in;

    //later to add users'deciding who is starting

    //so let 0 = X image, 1 = Circle Image

    int activePlayer = 0; //to change later

    //State of the game, where 2 means means empty slot

    int [] gameState = {2,2,2,2,2,2,2,2,2};
    TextView winMsg;
    String winner;
    //defining the layouts

    LinearLayout lLayout;
    GridLayout gLayout;

    //check if the game has ended

    boolean gameOn = true;
    boolean gameOver = true;

    public void playAgain(View view) {
        gameOn = true;
        fade_in = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in);

        if (lLayout.getVisibility() == View.VISIBLE) {
            gLayout.startAnimation(fade_in);
            lLayout.startAnimation(fade_out);
        }
        lLayout.setVisibility(View.INVISIBLE);
        //reset the game state
        for (int i = 0; i < gameState.length;i++) {
            gameState[i] = 2;
        }
        //reset all the image sources
        for (int i = 0; i < gLayout.getChildCount(); i++) {
            ((ImageView) gLayout.getChildAt(i)).setImageResource(0);
        }

    }


    public void tap(View view) {
        ImageView counter = (ImageView) view;
        int tappedCounter = Integer.parseInt(counter.getTag().toString());
        //storing all the possible winning positions
        int [][] winningPositions = {{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};

        if (gameState[tappedCounter] == 2 && gameOn) {

            //sets the array gamestate to the active player

            gameState[tappedCounter] = activePlayer;

            //moce it top of the screen, applly either circle or c image, then bring it back
            counter.setTranslationY(-1000f);

            if (activePlayer == 0) {

                counter.setImageResource(R.drawable.x);
                activePlayer = 1;
            } else {
                counter.setImageResource(R.drawable.circles);
                activePlayer = 0;

            }


            counter.animate().translationYBy(1000f).rotation(1800).setDuration(600);

            //check for a winner at the end
            for(int[] winPosition : winningPositions) {
                if (gameState[winPosition[0]] == gameState[winPosition[1]] &&
                        gameState[winPosition[1]] == gameState[winPosition[2]] && gameState[winPosition[0]] != 2) {
                    // Testing if the logic is working:System.out.println("There is a winner");
                    //setting that the game has finished

                    gameOn = false;

                    //announcing a winner
                    //some animations
                    blink = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.blink);
                    fade_out = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_out);
                    lLayout = (LinearLayout) findViewById(R.id.lnLayout);
                    gLayout = (GridLayout) findViewById(R.id.grdLayout);

                    //check who has won

                    if (gameState[winPosition[0]] == 0) {
                        winner = "X";
                    } else {
                        winner = "Circles";
                    }
                    //win message
                    winMsg = (TextView) findViewById(R.id.txtWinMsg);

                    winMsg.setText(winner+" has won!");

                    if (lLayout.getVisibility() == View.INVISIBLE) {
                        gLayout.startAnimation(fade_out);
                        lLayout.startAnimation(blink);
                        lLayout.setVisibility(View.VISIBLE);
                    }
                    //lLayout.setVisibility(View.VISIBLE);
                } else {

                    for (int counterState: gameState){
                        if (counterState == 2) gameOver = false;

                    }
                    if (gameOver) {
                        winMsg.setText("It's a draw");
                        if (lLayout.getVisibility() == View.INVISIBLE) {
                            gLayout.startAnimation(fade_out);
                            lLayout.startAnimation(blink);
                            lLayout.setVisibility(View.VISIBLE);
                        }
                    }
                }
            }
        }
    }

    /*@Override
    public void onAnimationRepeat(Animation animation) {
        // Take any action after completing the animation
        // check for fade in animation
        if (animation == blink) {
            Toast.makeText(getApplicationContext(), "End of the game", Toast.LENGTH_LONG).show();
        }

    }*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //blink.setAnimationListener(this);
    }
}
