package com.example.aiclock.view;

import android.content.Context;
import android.app.Activity;
import android.app.Service;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aiclock.R;

public class SelectRingTonePopup implements View.OnClickListener {
    private RadioButton tv_ring1,tv_ring2,tv_ring3,tv_ring4,tv_ring5,tv_ring6,tv_ring7,tv_ring8,tv_ring9
            ,tv_ring10;
    RadioGroup mygrp;
    private TextView tv_ringsure;
    private MediaPlayer mediaPlayer;
    public PopupWindow mPopupWindow;
    private SelectRingTonePopupOnClickListerner selectRingTonePopupOnClickListerner;
    public PopupWindow getmPopupWindow() {
        return mPopupWindow;
    }
    private Context mContext;

    public SelectRingTonePopup(Context context) {
        mContext = context;
        mPopupWindow = new PopupWindow(context);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopupWindow.setWidth(WindowManager.LayoutParams.FILL_PARENT);
        mPopupWindow.setHeight(WindowManager.LayoutParams.FILL_PARENT);
        mPopupWindow.setTouchable(true);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setAnimationStyle(R.style.AnimBottom);
        mPopupWindow.setContentView(initViews());
        mPopupWindow.getContentView().setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mPopupWindow.setFocusable(false);
                // mPopupWindow.dismiss();
                return true;
            }
        });
    }

    public View initViews() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.selectringrtone_pop_window,
                null);


        mygrp = (RadioGroup) view.findViewById(R.id.ring_group) ;
        tv_ring1 = (RadioButton) view.findViewById(R.id.tv_drugring_1);
        tv_ring2 = (RadioButton) view.findViewById(R.id.tv_drugring_2);
        tv_ring3 = (RadioButton) view.findViewById(R.id.tv_drugring_3);
        tv_ring4 = (RadioButton) view.findViewById(R.id.tv_drugring_4);
        tv_ring5 = (RadioButton) view.findViewById(R.id.tv_drugring_5);
        tv_ring6 = (RadioButton) view.findViewById(R.id.tv_drugring_6);
        tv_ring7 = (RadioButton) view.findViewById(R.id.tv_drugring_7);
        tv_ring8 = (RadioButton) view.findViewById(R.id.tv_drugring_8);
        tv_ring9 = (RadioButton) view.findViewById(R.id.tv_drugring_9);
        tv_ring10 = (RadioButton) view.findViewById(R.id.tv_drugring_10);
        tv_ringsure = (TextView) view.findViewById(R.id.tv_drugring_sure);

        tv_ring1.setOnClickListener(this);
        tv_ring2.setOnClickListener(this);
        tv_ring3.setOnClickListener(this);
        tv_ring4.setOnClickListener(this);
        tv_ring5.setOnClickListener(this);
        tv_ring6.setOnClickListener(this);
        tv_ring7.setOnClickListener(this);
        tv_ring8.setOnClickListener(this);
        tv_ring9.setOnClickListener(this);
        tv_ring10.setOnClickListener(this);
        tv_ringsure.setOnClickListener(this);
        mediaPlayer = new MediaPlayer();

        mygrp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId) {
                    case R.id.tv_drugring_1:
                     if(mediaPlayer.isPlaying()) {
                         mediaPlayer.stop();
                         mediaPlayer = MediaPlayer.create(mContext, R.raw.alarm_ring);
                         mediaPlayer.start();
                         selectRingTonePopupOnClickListerner.obtainMessage(1);
                     }
                     else {
                         mediaPlayer = MediaPlayer.create(mContext, R.raw.alarm_ring);
                         mediaPlayer.start();
                         selectRingTonePopupOnClickListerner.obtainMessage(1);
                     }
                            break;
                        case R.id.tv_drugring_2:
                        if(mediaPlayer.isPlaying())
                        {
                            mediaPlayer.stop();
                            mediaPlayer = MediaPlayer.create(mContext, R.raw.loud_alarm_sound);
                            mediaPlayer.start();
                            selectRingTonePopupOnClickListerner.obtainMessage(2);
                        }
                        else {
                            mediaPlayer = MediaPlayer.create(mContext, R.raw.loud_alarm_sound);
                            mediaPlayer.start();
                            selectRingTonePopupOnClickListerner.obtainMessage(2);
                        } break;
//                    case R.id.tv_drugring_3:
//                        if(mediaPlayer.isPlaying())
//                        {
//                            mediaPlayer.stop();
//                            mediaPlayer = MediaPlayer.create(mContext, R.raw.loud_alarm_sound);
//                            mediaPlayer.start();
//                            selectRingTonePopupOnClickListerner.obtainMessage(R.raw.loud_alarm_sound);
//                        }
//                        else {
//                            mediaPlayer = MediaPlayer.create(mContext, R.raw.loud_alarm_sound);
//                            mediaPlayer.start();
//                            selectRingTonePopupOnClickListerner.obtainMessage(R.raw.loud_alarm_sound);
//                        } break;
//                    case R.id.tv_drugring_4:
//                        if(mediaPlayer.isPlaying())
//                        {
//                            mediaPlayer.stop();
//                            mediaPlayer = MediaPlayer.create(mContext, R.raw.loud_alarm_sound);
//                            mediaPlayer.start();
//                            selectRingTonePopupOnClickListerner.obtainMessage(R.raw.loud_alarm_sound);
//                        }
//                        else {
//                            mediaPlayer = MediaPlayer.create(mContext, R.raw.loud_alarm_sound);
//                            mediaPlayer.start();
//                            selectRingTonePopupOnClickListerner.obtainMessage(R.raw.loud_alarm_sound);
//                        } break;
//                    case R.id.tv_drugring_5:
//                        if(mediaPlayer.isPlaying())
//                        {
//                            mediaPlayer.stop();
//                            mediaPlayer = MediaPlayer.create(mContext, R.raw.loud_alarm_sound);
//                            mediaPlayer.start();
//                            selectRingTonePopupOnClickListerner.obtainMessage(R.raw.loud_alarm_sound);
//                        }
//                        else {
//                            mediaPlayer = MediaPlayer.create(mContext, R.raw.loud_alarm_sound);
//                            mediaPlayer.start();
//                            selectRingTonePopupOnClickListerner.obtainMessage(R.raw.loud_alarm_sound);
//                        } break;
//                    case R.id.tv_drugring_6:
//                        if(mediaPlayer.isPlaying())
//                        {
//                            mediaPlayer.stop();
//                            mediaPlayer = MediaPlayer.create(mContext, R.raw.loud_alarm_sound);
//                            mediaPlayer.start();
//                            selectRingTonePopupOnClickListerner.obtainMessage(R.raw.loud_alarm_sound);
//                        }
//                        else {
//                            mediaPlayer = MediaPlayer.create(mContext, R.raw.loud_alarm_sound);
//                            mediaPlayer.start();
//                            selectRingTonePopupOnClickListerner.obtainMessage(R.raw.loud_alarm_sound);
//                        } break;
//                    case R.id.tv_drugring_7:
//                        if(mediaPlayer.isPlaying())
//                        {
//                            mediaPlayer.stop();
//                            mediaPlayer = MediaPlayer.create(mContext, R.raw.loud_alarm_sound);
//                            mediaPlayer.start();
//                            selectRingTonePopupOnClickListerner.obtainMessage(R.raw.loud_alarm_sound);
//                        }
//                        else {
//                            mediaPlayer = MediaPlayer.create(mContext, R.raw.loud_alarm_sound);
//                            mediaPlayer.start();
//                            selectRingTonePopupOnClickListerner.obtainMessage(R.raw.loud_alarm_sound);
//                        } break;
//                    case R.id.tv_drugring_8:
//                        if(mediaPlayer.isPlaying())
//                        {
//                            mediaPlayer.stop();
//                            mediaPlayer = MediaPlayer.create(mContext, R.raw.loud_alarm_sound);
//                            mediaPlayer.start();
//                            selectRingTonePopupOnClickListerner.obtainMessage(R.raw.loud_alarm_sound);
//                        }
//                        else {
//                            mediaPlayer = MediaPlayer.create(mContext, R.raw.loud_alarm_sound);
//                            mediaPlayer.start();
//                            selectRingTonePopupOnClickListerner.obtainMessage(R.raw.loud_alarm_sound);
//                        } break;
//                    case R.id.tv_drugring_9:
//                        if(mediaPlayer.isPlaying())
//                        {
//                            mediaPlayer.stop();
//                            mediaPlayer = MediaPlayer.create(mContext, R.raw.loud_alarm_sound);
//                            mediaPlayer.start();
//                            selectRingTonePopupOnClickListerner.obtainMessage(R.raw.loud_alarm_sound);
//                        }
//                        else {
//                            mediaPlayer = MediaPlayer.create(mContext, R.raw.loud_alarm_sound);
//                            mediaPlayer.start();
//                            selectRingTonePopupOnClickListerner.obtainMessage(R.raw.loud_alarm_sound);
//                        } break;
//                    case R.id.tv_drugring_10:
//                        if(mediaPlayer.isPlaying())
//                        {
//                            mediaPlayer.stop();
//                            mediaPlayer = MediaPlayer.create(mContext, R.raw.loud_alarm_sound);
//                            mediaPlayer.start();
//                            selectRingTonePopupOnClickListerner.obtainMessage(R.raw.loud_alarm_sound);
//                        }
//                        else {
//                            mediaPlayer = MediaPlayer.create(mContext, R.raw.loud_alarm_sound);
//                            mediaPlayer.start();
//                            selectRingTonePopupOnClickListerner.obtainMessage(R.raw.loud_alarm_sound);
//                        } break;
                        default:
                            break;
                    }
                }

        });
        return view;
    }

    @Override
    public void onClick(View v) {

            switch (v.getId()) {
                case R.id.tv_drugring_sure:
                {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    dismiss();
                }
            }

    }

    public interface SelectRingTonePopupOnClickListerner {
        void obtainMessage(int soundtrack);
    }

    public void setOnSelectRingTonePopupOnClickListerner(SelectRingTonePopupOnClickListerner l) {
        this.selectRingTonePopupOnClickListerner = l;
    }

    public void dismiss() {
        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
        }
    }

    public void showPopup(View rootView) {
        // 第一个参数是要将PopupWindow放到的View，第二个参数是位置，第三第四是偏移值
        mPopupWindow.showAtLocation(rootView, Gravity.CENTER, 0, 0);
    }
}
