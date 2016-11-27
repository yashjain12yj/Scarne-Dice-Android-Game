package com.scarnedice.yashj.scarnedice;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import java.util.Random;

public class MainActivity extends AppCompatActivity {


    TextView score;
    int cond = 0;
    TextView turn;
    Button reset;
    Button roll;
    Button hold;
    ImageView imgdice;
    int playerCurrentScore = 0, computerCurrentScore = 0, playerTotalScore = 0, computerTotalScore = 0, playerLastScore = 0, computerLastScore = 0;
    boolean isUserTurn = true;
    Handler handler = new Handler();
    int img[] = {R.drawable.dice1, R.drawable.dice2, R.drawable.dice3, R.drawable.dice4, R.drawable.dice5, R.drawable.dice6};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MobileAds.initialize(getApplicationContext(), "ca-app-pub-6079836905206610~6628969884");

        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        roll = (Button) findViewById(R.id.roll);
        hold = (Button) findViewById(R.id.hold);
        reset = (Button) findViewById(R.id.reset);
        imgdice = (ImageView) findViewById(R.id.dice);
        score = (TextView) findViewById(R.id.score);
        turn = (TextView) findViewById(R.id.turn);
        showTurn();
        showScore();

        roll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                roll();
            }
        });

        hold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hold();
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();
            }
        });

    }

    private void showScore() {
        score.setText("Your Score : " + playerTotalScore + "\nComputer Score : " + computerTotalScore);
    }

    private void showTurn() {
        if (isUserTurn) {
            turn.setText("Your turn");
        } else {
            turn.setText("Computer turn");
        }
    }

    private void roll() {
        if (isUserTurn) {
            showTurn();
            Random random = new Random();
            playerCurrentScore = random.nextInt(6) + 1;
            imgdice.setImageResource(img[playerCurrentScore - 1]);
            //Toast.makeText(this,"Player Current value is " + playerCurrentScore , Toast.LENGTH_LONG).show();
//            updateScore();
//            showScore();
            if (playerCurrentScore == 1) {
                updateScore();
                hold();
            }
            updateScore();
            showScore();
            playerCurrentScore = 0;
        }
    }

    private void updateScore() {

        if (isUserTurn) {
            if (playerCurrentScore == 1) {
                playerTotalScore = playerLastScore;

            } else {
                playerTotalScore += playerCurrentScore;
            }
        } else {
            if (computerCurrentScore == 1) {
                computerTotalScore = computerLastScore;

            } else {
                computerTotalScore += computerCurrentScore;
            }

        }
        if (computerTotalScore >= 100 || playerTotalScore >= 100) {
            if (playerTotalScore >= 100) {
                Toast.makeText(MainActivity.this,"YOU WON :)",Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(MainActivity.this,"YOU LOOSE :(",Toast.LENGTH_LONG).show();
            }
            reset();
        }

    }

    private void hold() {
        if (isUserTurn) {
            playerCurrentScore = 0;
            computerCurrentScore = 0;
            playerLastScore = playerTotalScore;
            isUserTurn = !isUserTurn;
            showTurn();
            if (!isUserTurn)
                computerTurn();
        }
    }

    private void computerTurn() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (cond < 15) {
                    showTurn();
                    Random random = new Random();
                    computerCurrentScore = random.nextInt(6) + 1;
                    imgdice.setImageResource(img[computerCurrentScore - 1]);
                    cond += computerCurrentScore;
                    //Toast.makeText(MainActivity.this,"Computer Current value is " + computerCurrentScore , Toast.LENGTH_SHORT).show();
                    updateScore();
                    showScore();
                    if (computerCurrentScore == 1) {
                        updateScore();
                        {
                            playerCurrentScore = 0;
                            computerCurrentScore = 0;
                            computerLastScore = computerTotalScore;
                            isUserTurn = !isUserTurn;
                            showTurn();
                        }
                        return;
                    }
                    if (computerTotalScore >= 100) {
                        isUserTurn = true;
                        reset();
                        return;
                    }
                    computerTurn();
                } else {
                    computerCurrentScore = 0;
                    cond = 0;
                    computerLastScore = computerTotalScore;
                    isUserTurn = !isUserTurn;
                    showTurn();
                    showScore();
                }
            }
        }, 1000);
    }


    private void reset() {
        if (isUserTurn) {
            computerTotalScore = 0;
            playerTotalScore = 0;
            computerCurrentScore = 0;
            playerCurrentScore = 0;
            playerLastScore = 0;
            computerLastScore = 0;
            showScore();
            showTurn();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflate = new MenuInflater(this);
        inflate.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about_us:
                Intent intent = new Intent(MainActivity.this, AboutUs.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}