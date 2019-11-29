package com.example.aiclock.alarmmanager;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.aiclock.MainActivity;
import com.example.aiclock.R;

import java.util.Random;

public class
imagedisplay extends AppCompatActivity {
    private Vibrator vibrator;

    String[][] items = {

            {"desk","12 th Sept"},
            {"computer","12 th Sept"},
            {"shoe","12 th Sept"},
            {"mouse","12 th Sept"},
            {"electric fan","12 th Sept"},
            {"pen","12 th Sept"},
            {"wallet","12 th Sept"},
            {"electric fan","12 th Sept"},
            {"bottle","12 th Sept"},
    };

        private TextView itemDisplay,test;
        private Button opencamera, nextItem, mathquiz ;
        private String result1, result2,result3, nextobject,object;
        private Integer count=1;
        private View sucessnotice;
        final int min = 0;
        final int max = 8;
        final int random = new Random().nextInt((max - min) + 1) + min;
        boolean checkwon=false;
      public static final String MyPREFERENCES = "MyPrefs" ;
      private String mysong;
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imagedisplay);
        doBindService();
        mysong = this.getIntent().getStringExtra("soundtrack");
        Bundle extras = getIntent().getExtras();
        Intent music = new Intent();
        music.setClass(this,MusicService.class);
        music.putExtra("soundtrack",mysong);
        startService(music);
        final SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(this);
        final SharedPreferences.Editor editor = prefs.edit();

        object = prefs.getString("search","");
        count= prefs.getInt("count",1);

        itemDisplay = findViewById(R.id.require);
        sucessnotice= findViewById(R.id.successnotice);
        opencamera = findViewById(R.id.camera_open);
        nextItem= findViewById(R.id.nextItem);
        mathquiz= findViewById(R.id.mathquiz);
        mathquiz.setVisibility(View.INVISIBLE);
        nextItem.setVisibility(View.INVISIBLE);

        if (extras != null) {
            result1=extras.getString("result1");
            result2=extras.getString("result2");
            result3=extras.getString("result3");
            checkwon=extras.getBoolean("checkwon");

        }





        if(object.equals("")) {
            String found = items[random][0];
            itemDisplay.setText(found);
            editor.putString("search", found);
            editor.putInt("count",count);
            editor.commit();


            opencamera.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(imagedisplay.this, ChooseModel.class);
                    intent.putExtra("soundtrack",mysong);
                    startActivity(intent);
                }
            });

        }
        else {
            if (object.equals(result1) || object.equals(result2) || object.equals(result3)||checkwon==true)
            {
                sucessnotice.setBackgroundResource(R.color.colorGreen);
                itemDisplay.setText(("alarm close success"));
                opencamera.setText("press here close alarm ");
                if(mServ != null) {
                    mServ.stopMusic();

                }
                doUnbindService();
                stopService(music);
                editor.clear();
                editor.commit();





                opencamera.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

//                        android.os.Process.killProcess(android.os.Process.myPid());
//                        System.exit(1);
                        doUnbindService();


                        Intent back = new Intent(imagedisplay.this, MainActivity.class);
                        back.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(back);


                    }
                });
            } else {
                sucessnotice.setBackgroundResource(R.color.colorRed);
                if(count==1)
                {itemDisplay.setText(object);}
                else {
                    itemDisplay.setText("Please try again\n" + object);
                }
                opencamera.setText("Try again");
                count=count+1;
                editor.putInt("count",count);
                editor.commit();
                opencamera.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(imagedisplay.this, ChooseModel.class);
                        intent.putExtra("soundtrack",mysong);
                        startActivity(intent);
                    }
                });


                if(count>=4)
                {
                    nextItem.setVisibility(View.VISIBLE);
                    mathquiz.setVisibility(View.VISIBLE);
                    nextItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        nextItem.setVisibility(View.INVISIBLE);
                        do {

                            nextobject = items[random][0];
                        } while (object.equals(nextobject));
                        itemDisplay.setText(nextobject);
                        count = 1;
                        editor.putInt("count", count);
                        editor.putString("search", nextobject);
                        editor.commit();
                        Intent intent = new Intent(imagedisplay.this, imagedisplay.class);
                        intent.putExtra("soundtrack", mysong);
                        startActivity(intent);

                    }
                });
                    mathquiz.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mathquiz.setVisibility(View.INVISIBLE);

                            Intent intent = new Intent(imagedisplay.this, Splash.class);
                            intent.putExtra("soundtrack",mysong);
                            startActivity(intent);


                        }
                    });
                }

            }
        }








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

    @Override
    public void onBackPressed() {
    }
}
