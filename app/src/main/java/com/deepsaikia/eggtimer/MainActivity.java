package com.deepsaikia.eggtimer;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    //VARIABLES
    ImageView hornImage;
    ImageView imageView;
    SeekBar seekBar;
    Button button;
    TextView textView;
    CountDownTimer countDownTimer;
    boolean runTimer=true;


    //ON CREATE
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // Get objects from layout
        imageView=findViewById(R.id.imageView);
        seekBar=findViewById(R.id.seekBar);
        button=findViewById(R.id.button);
        textView=findViewById(R.id.textView);
        hornImage=findViewById(R.id.hornImage);
        seekBar.setMax(600);
        seekBar.setProgress(30);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if(i==0)
                {
                    seekBar.setProgress(1);
                }
                setTime(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(runTimer)
                {
                    startCountdown(seekBar.getProgress());
                    seekBar.setEnabled(false);
                    button.setText("STOP");
                    runTimer=false;

                }
                else
                {
                    resetTimer();
                }

            }
        });

    }

    //FUNCTIONS
    public void playMedia()
    {
        MediaPlayer mediaPlayer=MediaPlayer.create(getApplicationContext(),R.raw.airhorn);
        mediaPlayer.start();
        hornImage.animate().alpha(1);
        textView.setAlpha(0);
        button.setEnabled(false);
        new CountDownTimer(3000, 3000) {
            @Override
            public void onTick(long l) {

            }
            @Override
            public void onFinish() {
                hornImage.animate().alpha(0);
                textView.setAlpha(1);
                resetTimer();
                button.setEnabled(true);
            }
        }.start();

    }
    public void startCountdown(int duration)
    {
        countDownTimer=new CountDownTimer(duration* 1000L +100,1000)
        {
            @Override
            public void onTick(long l) {
                setTime((int)l/1000);
            }

            @Override
            public void onFinish() {
                playMedia();
            }
        }.start();
    }
    public void setTime(int i)
    {
        int minute=i/60;
        String minutes=Integer.toString(minute);
        String seconds=Integer.toString(i-minute*60);
        if((i-minute*60)<10)
        {
            seconds="0"+seconds;
        }
        String setTextTime=minutes+" : "+seconds;
        textView.setText(setTextTime);
    }
    public void resetTimer()
    {
        countDownTimer.cancel();
        seekBar.setEnabled(true);
        button.setText("START");
        seekBar.setProgress(30);
        setTime(30);
        runTimer=true;

    }
}