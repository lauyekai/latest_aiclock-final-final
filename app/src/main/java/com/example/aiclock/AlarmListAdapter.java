package com.example.aiclock;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;


import com.example.aiclock.alarmmanager.AlarmManagerUtil;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;


public class AlarmListAdapter extends ArrayAdapter<Alarm> {

    myDbAdapter db = new myDbAdapter(getContext());
    private List<Alarm> alarms = new ArrayList<>();
    private Context context;
    private SharedPreferences alarm_ID;
    private String sharedPrefFile = "com.example.aiclock";
    int alarmid;
    String[] weeks;
    String myweek;
   Alarm mAlarm;

    public AlarmListAdapter(Context context,int resource, List<Alarm> objects) {
        super(context, resource, objects);
        this.context = context;
        this.alarms = objects;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v==null)
        {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.alarm_card,null);
        }
        mAlarm = alarms.get(position);
        myweek = mAlarm.getWeek();
        weeks = myweek.split(",");
        alarm_ID = context.getSharedPreferences(sharedPrefFile,MODE_PRIVATE);

        alarmid = alarm_ID.getInt("alarmid",alarmid);


        TextView time = (TextView) v.findViewById(R.id.alarm_time);
        time.setTextColor(v.getResources().getColor(R.color.colorBlack));
        TextView status = (TextView) v.findViewById(R.id.alarm_setting);
        TextView alarmweek = (TextView) v.findViewById(R.id.alarmweek);
        final Switch mySwitch = (Switch) v.findViewById(R.id.alarm_switch);


            alarmweek.setText(mAlarm.getWeeklength());



        if(mAlarm.getMin() < 10)
        {
            time.setText(mAlarm.getHour() + ":0" + mAlarm.getMin());

        }
        else {
            time.setText(mAlarm.getHour() + ":" + mAlarm.getMin());
        }
        if(mAlarm.getTips() != "")
        {
            status.setText(mAlarm.getTips());

        }
        else {
            status.setText("Alarm");
        }


        if(mAlarm.getStatus() == 1 && myweek.equals("0") )
        {
            mySwitch.setChecked(true);
            Log.d("alarm checking",myweek);
            AlarmManagerUtil.setAlarm(getContext(), mAlarm.getFlag(), mAlarm.getHour(), mAlarm.getMin(), mAlarm.getAlarmid(), Integer.parseInt(myweek), mAlarm.getTips(), mAlarm.getSoundorvibrator(),mAlarm.getSoundtrack());
            Log.d("alarm setted","Setted");
            db.updateSetting(1,mAlarm.getAlarmid());
//            Toast.makeText(getContext(), "Alarm on", Toast.LENGTH_SHORT).show();
//            Toast.makeText(context, "Alarm set", Toast.LENGTH_SHORT).show();
        }
        else if(mAlarm.getStatus()== 1 && weeks.length > 0 && !myweek.equals("0")) {
            mySwitch.setChecked(true);
            Log.d("alarm checking",myweek);
            for (int i = 0; i < weeks.length-1; i++) {
            AlarmManagerUtil.setAlarm(getContext(), mAlarm.getFlag(), mAlarm.getHour(), mAlarm.getMin(), mAlarm.getAlarmid()+i, Integer.parseInt(weeks[i]), mAlarm.getTips(), mAlarm.getSoundorvibrator(), mAlarm.getSoundtrack());

                alarmid++;
            SharedPreferences.Editor preferencesEditor = alarm_ID.edit();
            preferencesEditor.putInt("alarmid",alarmid);
            preferencesEditor.apply();



            }
        }
        else
        {
            mySwitch.setChecked(false);
        }


        mySwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAlarm = alarms.get(position);
                myweek = mAlarm.getWeek();
                weeks = myweek.split(",");

                if(mAlarm.getStatus() == 1 && myweek.equals("0"))
                {
                    Log.d("alarm off",myweek);
                    AlarmManagerUtil.cancelAlarm(getContext(),mAlarm.getAlarmid());
                    mAlarm.setStatus(0);
                    db.updateStatus(0,mAlarm.getAlarmid());
                    Toast.makeText(getContext(), "Alarm Off: Single", Toast.LENGTH_SHORT).show();
                    mAlarm.setStatus(0);
                    //            Toast.makeText(context, "Alarm set", Toast.LENGTH_SHORT).show();
                }
                else if(mAlarm.getStatus()== 1 && weeks.length > 0 && !myweek.equals("0")) {
                    Log.d("alarm off",myweek);
                    for (int i = 0; i < weeks.length-1; i++) {
                        AlarmManagerUtil.cancelAlarm(getContext(),mAlarm.getAlarmid()+i);
                        mAlarm.setStatus(0);
                        db.updateStatus(0,mAlarm.getAlarmid());
                        Toast.makeText(getContext(), "Alarm Off: Multiple", Toast.LENGTH_SHORT).show();

                    }
                }
                else if (mAlarm.getStatus() == 0 && myweek.equals("0"))
                {
                    AlarmManagerUtil.setAlarm(getContext(), mAlarm.getFlag(), mAlarm.getHour(), mAlarm.getMin(), mAlarm.getAlarmid(), Integer.parseInt(myweek), mAlarm.getTips(), mAlarm.getSoundorvibrator(),mAlarm.getSoundtrack());
                    mAlarm.setStatus(1);

                    db.updateStatus(1,mAlarm.getAlarmid());
                    Toast.makeText(getContext(), "Alarm On: Single", Toast.LENGTH_SHORT).show();
                }
                else if (mAlarm.getStatus() == 0 && !myweek.equals("0") && weeks.length>0) {
                    for (int i = 0; i < weeks.length - 1; i++) {
                        AlarmManagerUtil.setAlarm(getContext(), mAlarm.getFlag(), mAlarm.getHour(), mAlarm.getMin(), mAlarm.getAlarmid() + i, Integer.parseInt(weeks[i]), mAlarm.getTips(), mAlarm.getSoundorvibrator(), mAlarm.getSoundtrack());
                    }
                    mAlarm.setStatus(1);
                    db.updateStatus(1,mAlarm.getAlarmid());
                    Toast.makeText(getContext(), "Alarm On: Multiple", Toast.LENGTH_SHORT).show();
                    notifyDataSetChanged();
                }


            }
        });
        return v;
    }

}
