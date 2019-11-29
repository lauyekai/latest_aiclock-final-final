package com.example.aiclock.alarmmanager;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.aiclock.R;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;


public class QuestionActivity extends Activity {
    List<Question> quesList;
    int score = 0;
    int qid = 0;
    boolean timeUP=false,checkwon=false;


    Question currentQ;
    TextView txtQuestion, times, scored;
    Button button1, button2, button3;
    private String mysong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_show);
        mysong = this.getIntent().getStringExtra("soundtrack");
        doBindService();
        Intent music = new Intent();
        music.setClass(this,MusicService.class);
        music.putExtra("soundtrack",mysong);
        startService(music);
        QuizHelper db = new QuizHelper(this);
        quesList = db.getAllQuestions();
        currentQ = quesList.get(qid);

        txtQuestion = (TextView) findViewById(R.id.txtQuestion);
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);





        scored = (TextView) findViewById(R.id.score);


        times = (TextView) findViewById(R.id.timers);


        setQuestionView();
        times.setText("00:02:00");


        CounterClass timer = new CounterClass(60000, 1000);
        timer.start();





        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                getAnswer(button1.getText().toString());
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAnswer(button2.getText().toString());
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAnswer(button3.getText().toString());
            }
        });
    }

    public void getAnswer(String AnswerString) {
        if (currentQ.getANSWER().equals(AnswerString)) {


            score++;
            scored.setText("Score : " + score+"/10");
             if(score==10)
             {
                 Intent intent = new Intent(QuestionActivity.this,
                         imagedisplay.class);

                 checkwon=true;
                 Bundle b = new Bundle();

                 b.putBoolean("checkwon", checkwon);
                 b.putBoolean("timeup", timeUP);
                 intent.putExtra("soundtrack",mysong);
                 intent.putExtras(b);

                 startActivity(intent);
                 finish();

             }





        } else {



            Intent intent = new Intent(QuestionActivity.this,
                    ResultActivity.class);


            Bundle b = new Bundle();
            b.putInt("score", score);
            intent.putExtras(b);
            intent.putExtra("soundtrack",mysong);
            startActivity(intent);
            finish();
        }
        if (qid < 21) {


            currentQ = quesList.get(qid);
            setQuestionView();
        }


    }


    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    @SuppressLint("NewApi")
    public class CounterClass extends CountDownTimer {

        public CounterClass(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
            // TODO Auto-generated constructor stub
        }


        @Override
        public void onFinish() {
            if (checkwon==false) {
                times.setText("Time is up");
                Intent intent = new Intent(QuestionActivity.this,
                        ResultActivity.class);
                timeUP = true;
                Bundle b = new Bundle();
                b.putBoolean("timeup", timeUP);
                b.putInt("score", score);
                intent.putExtra("soundtrack",mysong);
//                intent.putExtra("soundtrack",mysound);
                intent.putExtras(b);
                startActivity(intent);
                finish();

                startActivity(intent);

//                final MediaPlayer mp = MediaPlayer.create(QuestionActivity.this, R.raw.sound);
//                AudioManager audio = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
//                int currentVolume = audio.getStreamVolume(AudioManager.STREAM_MUSIC);
//                int maxVolume = audio.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
//                float percent = 1.0f;
//                int seventyVolume = (int) (maxVolume * percent);
//                audio.setStreamVolume(AudioManager.STREAM_MUSIC, seventyVolume, 0);
//                mp.start();
            }

        }

      @Override
        public void onTick(long millisUntilFinished) {
            // TODO Auto-generated method stub

            long millis = millisUntilFinished;
            String hms = String.format(
                    "%02d:%02d:%02d",
                    TimeUnit.MILLISECONDS.toHours(millis),
                    TimeUnit.MILLISECONDS.toMinutes(millis)
                            - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS
                            .toHours(millis)),
                    TimeUnit.MILLISECONDS.toSeconds(millis)
                            - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS
                            .toMinutes(millis)));
            System.out.println(hms);
            times.setText(hms);
        }


    }

    private void setQuestionView() {
        Random rand = new Random();

        int randomposition = rand.nextInt((3 - 1) + 1) +1;
        // the method which will put all things together
        txtQuestion.setText(currentQ.getQUESTION());

        if(randomposition==1)
        {
            button1.setText(currentQ.getOPTA());
            button2.setText(currentQ.getOPTB());
            button3.setText(currentQ.getOPTC());
        }
        if (randomposition==2)
        {
            button2.setText(currentQ.getOPTA());
            button1.setText(currentQ.getOPTB());
            button3.setText(currentQ.getOPTC());
        }
        if(randomposition==3)
        {
            button2.setText(currentQ.getOPTA());
            button3.setText(currentQ.getOPTB());
            button1.setText(currentQ.getOPTC());
        }





        qid++;
    }
    //Bind/Unbind music service
    private boolean mIsBound = false;
    private MusicService mServ;
    private ServiceConnection Scon =new ServiceConnection(){

        public void onServiceConnected(ComponentName name, IBinder
                binder) {
            mServ = ((MusicService.ServiceBinder)binder).getService();
        }

        public void onServiceDisconnected(ComponentName name) {
            mServ = null;
        }
    };

    void doBindService(){
        bindService(new Intent(this, MusicService.class),
                Scon, Context.BIND_AUTO_CREATE);
        mIsBound = true;
    }

    void doUnbindService()
    {
        if(mIsBound)
        {
            unbindService(Scon);
            mIsBound = false;
        }
    }
    @Override
    protected void onResume(){
        super.onResume();
        if(mServ != null)
        {
            mServ.resumeMusic();
        }
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();

        doUnbindService();
        Intent music = new Intent();
        music.setClass(this, MusicService.class);
        stopService(music);
    }

}

