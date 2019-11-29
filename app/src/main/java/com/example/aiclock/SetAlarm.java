package com.example.aiclock;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.example.aiclock.view.SelectRemindCyclePopup;
import com.example.aiclock.view.SelectRemindWayPopup;
import com.example.aiclock.view.SelectRingTonePopup;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SetAlarm extends AppCompatActivity implements View.OnClickListener {
    private TextView date_tv,test_sound;
    private TimePickerView pvTime;
    private RelativeLayout repeat_rl, ring_rl,label_rl,soundtrack_rl;
    private TextView tv_repeat_value, tv_ring_value,tv_alarm_label,tv_soundtrack_value;
    private LinearLayout allLayout;
    private Button set_btn,btn_cancel_set,view_data,delete_data;
    private String time;
    private int cycle;
    private int ring;
    private int rt;
    private static final int request_code= 0;
    public static final String EXTRA_REPLY = "com.example.aiclock.extra.REPLY";
    public static Uri uri;
    private static int alarmid;
    myDbAdapter db;
    private SharedPreferences alarm_ID;
    private String sharedPrefFile = "com.example.aiclock";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_alarm);
        alarm_ID = getSharedPreferences(sharedPrefFile,MODE_PRIVATE);
        delete_data = (Button) findViewById(R.id.delete_data);
        delete_data.setOnClickListener(this);
        view_data = (Button) findViewById(R.id.btn_viewdata);
        view_data.setOnClickListener(this);
        test_sound = (TextView) findViewById(R.id.test_sound);
        allLayout = (LinearLayout) findViewById(R.id.all_layout);
        set_btn = (Button) findViewById(R.id.set_btn);
        set_btn.setOnClickListener(this);
        btn_cancel_set = (Button) findViewById(R.id.btn_cancel_set);
        btn_cancel_set.setOnClickListener(this);
        date_tv = (TextView) findViewById(R.id.date_tv);
        repeat_rl = (RelativeLayout) findViewById(R.id.repeat_rl);
        repeat_rl.setOnClickListener(this);
        ring_rl = (RelativeLayout) findViewById(R.id.ring_rl);
        ring_rl.setOnClickListener(this);
        label_rl = (RelativeLayout) findViewById(R.id.alarm_label);
        label_rl.setOnClickListener(this);
        soundtrack_rl = (RelativeLayout) findViewById(R.id.alarm_soundtrack);
        soundtrack_rl.setOnClickListener(this);
        tv_repeat_value = (TextView) findViewById(R.id.tv_repeat_value);
        tv_ring_value = (TextView) findViewById(R.id.tv_ring_value);
        tv_alarm_label = (TextView) findViewById(R.id.label_value);
        tv_soundtrack_value = (TextView) findViewById(R.id.label_soundValue);
        alarmid = alarm_ID.getInt("alarmid",alarmid);
        db = new myDbAdapter(this);
//        pvTime = new TimePickerView(this, TimePickerView.Type.HOURS_MINS);
//        pvTime.setTime(new Date());
//        pvTime.setCyclic(false);
//        pvTime.setCancelable(true);
        pvTime = new TimePickerBuilder(SetAlarm.this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                time = getTime(date);
                date_tv.setText(time);
            }
        })
                .setType(new boolean[]{false,false,false,true,true,false})// 默认全部显示
        .isCyclic(true)
                .setLabel("","","","","","")
                .setTitleText("Choose your time")
                .setTextColorCenter(getResources().getColor(R.color.colorLightBlue))
                .setContentTextSize(24)
                .setCancelColor(getResources().getColor(R.color.colorRed))


                .build();
        //时间选择后回调
//        pvTime.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {
//
//            @Override
//            public void onTimeSelect(Date date) {
//                time = getTime(date);
//                date_tv.setText(time);
//            }
//        });

        date_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pvTime.show();
            }
        });

    }

    public static String getTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        return format.format(date);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.repeat_rl:
                selectRemindCycle();
                break;
            case R.id.ring_rl:
                selectRingWay();
                break;
            case R.id.alarm_label:
                inputLabel();
                break;
            case R.id.set_btn:
                setClock();
                break;
            case R.id.alarm_soundtrack:
                choosesound();
//                selectRingtone();
                break;
            case R.id.btn_cancel_set:
                finish();
                break;
            case R.id.btn_viewdata:
                SharedPreferences.Editor preferencesEditor = alarm_ID.edit();
                preferencesEditor.clear();
                preferencesEditor.apply();
                break;
            case R.id.delete_data:
                db.deleteall(this);
                Toast.makeText(this, "Data cleared", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }



    private void choosesound(){
        Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE,RingtoneManager.TYPE_ALARM);
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_DEFAULT,true);
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI,RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM));
        startActivityForResult(intent,0);


}

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode==0)
        {
            if(data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI) != null)
            {

                uri = data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
                if (uri!=null)
                {

                       String ringtone = RingtoneManager.getRingtone(this,uri).getTitle(this);
                       tv_soundtrack_value.setText(ringtone);
                }
                else
                {
                    Toast.makeText(this, "no ringtone", Toast.LENGTH_SHORT).show();
                }
            }
            else
            {
                finish();
            }
//            if(data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI) != null){
//                Uri uri = data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
//                if(uri != null)
//                {
//                    RingtoneManager.setActualDefaultRingtoneUri(this,RingtoneManager.TYPE_ALARM,uri);
////                    String reply = uri.toString();
////                    Intent replyIntent = new Intent();
////                    replyIntent.putExtra(EXTRA_REPLY,reply);
////                    setResult(RESULT_OK,replyIntent);
////                    finish();
//                }
//            }


        }
    }

        private void inputLabel() {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            LayoutInflater li = LayoutInflater.from(this);
            View promptsView = li.inflate(R.layout.label_dialog,null);
            builder.setView(promptsView);
            final EditText label = (EditText) promptsView.findViewById(R.id.alarm_label);
//// Set up the input
//            final EditText input;
//            input = (EditText) findViewById(R.id.alarm_label);
//// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
//            input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
//            builder.setView(input);

// Set up the buttons
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String m_text = label.getText().toString();
                    tv_alarm_label.setText(m_text);
                    dialog.dismiss();
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            builder.show();
        }

        private int checknull(){
        int hasnull = 0;
            String alarmdate,alarmlabel,alarmmode;
            alarmdate = date_tv.getText().toString();
            alarmlabel=tv_alarm_label.getText().toString();
            alarmmode =tv_ring_value.getText().toString();
            if(alarmlabel == "") {
                tv_alarm_label.setError("error");
                hasnull = 1;
            }
            else
            {
                tv_alarm_label.setError(null);
                hasnull = 0;

            }
            if (alarmdate == "")
            {
                date_tv.setError("error");
                hasnull = 1;
            }
            else
            {
                date_tv.setError(null);
                hasnull = 0;
            }
            if (alarmmode == "")
            {
                tv_ring_value.setError("error");
                hasnull = 1;
            }
            else
            {
                tv_ring_value.setError(null);
                hasnull =0;
            }
            if (time == null)
            {
                Toast.makeText(this, "Please Select a Time.", Toast.LENGTH_SHORT).show();
                hasnull = 1;
            }
            else
            {
                hasnull = 0;
            }

            return hasnull;
        }
    private void setClock() {
        String weeklength = tv_repeat_value.getText().toString();
       if (checknull() == 0) {
           if (time != null && time.length() > 0) {
               String[] times = time.split(":");
               if (cycle == 0) {//EveryDay
                   alarmid++;
                   if(db.insertData(alarmid,Integer.parseInt(times[0]), Integer.parseInt(times[1]),tv_alarm_label.getText().toString(),"0",ring,uri.toString(),1,0,weeklength,0)){
//                       AlarmManagerUtil.setAlarm(this, 0, Integer.parseInt(times[0]), Integer.parseInt
//                               (times[1]), 0, 0, tv_alarm_label.getText().toString(), ring,uri);
                       Toast.makeText(this, uri.toString(), Toast.LENGTH_SHORT).show();
                       SharedPreferences.Editor preferencesEditor = alarm_ID.edit();
                       preferencesEditor.putInt("alarmid",alarmid);
                       preferencesEditor.apply();
                   }

               }
              else if (cycle == -1) {//Only Once
                  alarmid++;
                    if(db.insertData(alarmid,Integer.parseInt(times[0]), Integer.parseInt(times[1]),tv_alarm_label.getText().toString(),"0",ring,uri.toString(),1,1,weeklength,0))
                   {
                       Toast.makeText(this, "Alarm on", Toast.LENGTH_SHORT).show();
//                      AlarmManagerUtil.setAlarm(this, 1, Integer.parseInt(times[0]), Integer.parseInt
//                               (times[1]), 0, 0, tv_alarm_label.getText().toString(), ring,uri);
                       SharedPreferences.Editor preferencesEditor = alarm_ID.edit();
                       preferencesEditor.putInt("alarmid",alarmid);
                       preferencesEditor.apply();
                   }
                    else{
                        Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
                    }

               } else {//Specific Date
                  alarmid++;
                   String weeksStr = parseRepeat(cycle, 1);
                   String[] weeks = weeksStr.split(",");
                   ArrayList<String> week = new ArrayList<>();
                   week.add(weeksStr);
                   if(db.insertData(alarmid,Integer.parseInt(times[0]), Integer.parseInt(times[1]),tv_alarm_label.getText().toString(),weeksStr,ring,uri.toString(),1,2,weeklength,0))
                   {

                       SharedPreferences.Editor preferencesEditor = alarm_ID.edit();
                       preferencesEditor.putInt("alarmid",alarmid++);
                       preferencesEditor.apply();
                   }
//                   for (int i = 0; i < weeks.length; i++) {
//
//                       if(db.insertData(alarmid,Integer.parseInt(times[0]), Integer.parseInt(times[1]),tv_alarm_label.getText().toString(),Integer.parseInt(weeks[i]),ring,String.valueOf(uri),1,2))
//                       {
//                           Toast.makeText(this, "set", Toast.LENGTH_SHORT).show();
//                       }
//                           AlarmManagerUtil.setAlarm(this, 2, Integer.parseInt(times[0]), Integer
//                               .parseInt(times[1]), i, Integer.parseInt(weeks[i]), tv_alarm_label.getText().toString(), ring,uri);
//                   }
               }
//               Toast.makeText(this, uri.toString(), Toast.LENGTH_LONG).show();

               finish();

           }
       }


    }


    public void selectRemindCycle() {
        final SelectRemindCyclePopup fp = new SelectRemindCyclePopup(this);
        fp.showPopup(allLayout);
        fp.setOnSelectRemindCyclePopupListener(new SelectRemindCyclePopup
                .SelectRemindCyclePopupOnClickListener() {

            @Override
            public void obtainMessage(int flag, String ret) {
                switch (flag) {
                    // Mon
                    case 0:

                        break;
                    // Tue
                    case 1:

                        break;
                    // Wed
                    case 2:

                        break;
                    // Thu
                    case 3:

                        break;
                    // Fri
                    case 4:

                        break;
                    // Sat
                    case 5:

                        break;
                    // Sun
                    case 6:

                        break;
                    // 确定
                    case 7:
                        int repeat = Integer.valueOf(ret);
                        tv_repeat_value.setText(parseRepeat(repeat, 0));
                        cycle = repeat;
                        fp.dismiss();
                        break;
                    case 8:
                        tv_repeat_value.setText("Everyday");
                        cycle = 0;
                        fp.dismiss();
                        break;
                    case 9:
                        tv_repeat_value.setText("Only Once");
                        cycle = -1;
                        fp.dismiss();
                        break;
                    default:
                        break;
                }
            }
        });
    }


    public void selectRingWay() {
        SelectRemindWayPopup fp = new SelectRemindWayPopup(this);
        fp.showPopup(allLayout);
        fp.setOnSelectRemindWayPopupListener(new SelectRemindWayPopup
                .SelectRemindWayPopupOnClickListener() {

            @Override
            public void obtainMessage(int flag) {
                switch (flag) {
                    // Vibrate
                    case 0:
                        tv_ring_value.setText("Vibrate");
                        ring = 0;
                        break;
                    // Sound
                    case 1:
                        tv_ring_value.setText("Sound");
                        ring = 1;
                        break;
                    default:
                        break;
                }
            }
        });
    }

    public void selectRingtone() {
        SelectRingTonePopup fp = new SelectRingTonePopup(this);
        fp.showPopup(allLayout);
        fp.setOnSelectRingTonePopupOnClickListerner(new SelectRingTonePopup.SelectRingTonePopupOnClickListerner()
                 {

            @Override
            public void obtainMessage(int soundtrack) {
                switch (soundtrack) {

                    case 1:
                        tv_soundtrack_value.setText("Ringtone 1");
                        rt = R.raw.alarm_ring;
                        break;
                    case 2:
                        tv_soundtrack_value.setText("Ringtone 2");
                        rt = R.raw.loud_alarm_sound;
                        break;
                    default:
                        break;
                }
            }
        });
    }
    /**
     * @param repeat
     * @param flag
     * @return
     */
    public static String parseRepeat(int repeat, int flag) {
        String cycle = "";
        String weeks = "";
        if (repeat == 0) {
            repeat = 127;
        }
        if (repeat % 2 == 1) {
            cycle = "Mon";
            weeks = "1";
        }
        if (repeat % 4 >= 2) {
            if ("".equals(cycle)) {
                cycle = "Tue";
                weeks = "2";
            } else {
                cycle = cycle + "," + "Tue";
                weeks = weeks + "," + "2";
            }
        }
        if (repeat % 8 >= 4) {
            if ("".equals(cycle)) {
                cycle = "Wed";
                weeks = "3";
            } else {
                cycle = cycle + "," + "Wed";
                weeks = weeks + "," + "3";
            }
        }
        if (repeat % 16 >= 8) {
            if ("".equals(cycle)) {
                cycle = "Thu";
                weeks = "4";
            } else {
                cycle = cycle + "," + "Thu";
                weeks = weeks + "," + "4";
            }
        }
        if (repeat % 32 >= 16) {
            if ("".equals(cycle)) {
                cycle = "Fri";
                weeks = "5";
            } else {
                cycle = cycle + "," + "Fri";
                weeks = weeks + "," + "5";
            }
        }
        if (repeat % 64 >= 32) {
            if ("".equals(cycle)) {
                cycle = "Sat";
                weeks = "6";
            } else {
                cycle = cycle + "," + "Sat";
                weeks = weeks + "," + "6";
            }
        }
        if (repeat / 64 == 1) {
            if ("".equals(cycle)) {
                cycle = "Sun";
                weeks = "7";
            } else {
                cycle = cycle + "," + "Sun";
                weeks = weeks + "," + "7";
            }
        }

        return flag == 0 ? cycle : weeks;
    }

}
