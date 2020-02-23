package com.example.awakego.Controlers.Fragments;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.awakego.Models.Alarm;
import com.example.awakego.Views.AlarmReceiver;
import com.example.awakego.Models.AlarmViewModel;
import com.example.awakego.Controlers.Activities.MainActivity;
import com.example.awakego.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.ALARM_SERVICE;

public class AlarmFragment extends Fragment {
    //We define elements that we need for our fragment
    private AlarmManager mAlarmManager;
    private Ringtone ringtone;
    private AlarmViewModel alarmViewModel;
    private Uri songUri;
    private  Bundle args;
    private List<CheckBox> day_week_list;
    private boolean repeat = false;
    private NotificationManagerCompat mNotificationManager;
    private TimePicker mTimePicker;
    private CheckBox mondayCheckBox, tuesdayCheckBox, wednesdayCheckBox, thursdayCheckBox, fridayCheckBox,
            saturdayCheckBox, sundayCheckBox, isRepeat;
    private TextView mTextView;
    private Button cancel_alarm, save_alarm;
    private EditText alarm_title, ringtone_title;
    public Calendar calendar;
    private int day_week = -1;
    public static final int ALARM_REQUEST_CODE = 39;
    public static final String EXTRA_ID = "com.example.awakego.EXTRA_ID";
    public static final String EXTRA_TITLE = "com.example.awakego.EXTRA_TITLE";
    public static final String EXTRA_DAY = "com.example.awakego.EXTRA_DAY";
    public static final String EXTRA_HOUR = "com.example.awakego.EXTRA_HOUR";
    public static final String EXTRA_MINUTE = "com.example.awakego.EXTRA_MINUTE";
    public static final String EXTRA_SONG = "com.example.awakego.EXTRA_SONG";


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNotificationManager = NotificationManagerCompat.from(getActivity());
        day_week_list = new ArrayList<>();
        calendar = Calendar.getInstance();
        alarmViewModel = new ViewModelProvider(getActivity()).get(AlarmViewModel.class);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_create_alarm, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mTextView = view.findViewById(R.id.ringtone_alarm);
        mTimePicker = view.findViewById(R.id.alarm_time_picker);
        alarm_title = view.findViewById(R.id.alarm_title);
        ringtone_title = view.findViewById(R.id.ringtone_title);
        cancel_alarm = view.findViewById(R.id.cancel_alarm);
        save_alarm = view.findViewById(R.id.save_alarm);


        //Days of week
        mondayCheckBox = view.findViewById(R.id.monday);
        tuesdayCheckBox = view.findViewById(R.id.tuesday);
        wednesdayCheckBox = view.findViewById(R.id.wednesday);
        thursdayCheckBox = view.findViewById(R.id.thursday);
        fridayCheckBox = view.findViewById(R.id.friday);
        saturdayCheckBox = view.findViewById(R.id.saturday);
        sundayCheckBox = view.findViewById(R.id.sunday);
        //To repeat our alarm
        isRepeat = view.findViewById(R.id.repeat);
        //To handle notifications




        //We declare instance of our calendar
        //Days----------------------------------------------------
        day_week_list.add(0, sundayCheckBox);
        day_week_list.add(1, mondayCheckBox);
        day_week_list.add(2, tuesdayCheckBox);
        day_week_list.add(3, wednesdayCheckBox);
        day_week_list.add(4, thursdayCheckBox);
        day_week_list.add(5, fridayCheckBox);
        day_week_list.add(6, saturdayCheckBox);


        mondayCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (mondayCheckBox.isChecked()) {
                    Toast.makeText(getActivity(), "Monday!", Toast.LENGTH_SHORT).show();
                    day_week = 2;

                }
            }
        });
        tuesdayCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (tuesdayCheckBox.isChecked()) {
                    Toast.makeText(getActivity(), "Tuesday!", Toast.LENGTH_SHORT).show();
                    day_week = 3;
                }
            }
        });
        wednesdayCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (wednesdayCheckBox.isChecked()) {
                    Toast.makeText(getActivity(), "Wednesday!", Toast.LENGTH_SHORT).show();
                    day_week = 4;
                }
            }
        });
        thursdayCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (thursdayCheckBox.isChecked()) {
                    Toast.makeText(getActivity(), "Thursday!", Toast.LENGTH_SHORT).show();
                    day_week = 5;
                }
            }
        });
        fridayCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (fridayCheckBox.isChecked()) {
                    Toast.makeText(getActivity(), "Friday!", Toast.LENGTH_SHORT).show();
                    day_week = 6;
                }
            }
        });
        saturdayCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (saturdayCheckBox.isChecked()) {
                    Toast.makeText(getActivity(), "Saturday!", Toast.LENGTH_SHORT).show();
                    day_week = 7;
                }
            }
        });
        sundayCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (sundayCheckBox.isChecked()) {
                    Toast.makeText(getActivity(), "Sunday!", Toast.LENGTH_SHORT).show();
                    day_week = 1;
                }
            }
        });
        isRepeat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Toast.makeText(getActivity(), "Repeat On!", Toast.LENGTH_SHORT).show();
                repeat = true;
            }
        });

        alarmViewModel.getInsert_alarm().observe(getViewLifecycleOwner(), new Observer<Long>() {
            @Override
            public void onChanged(Long along) {
                startAlarm(calendar, along);

                Toast.makeText(getActivity(), String.valueOf(alarmViewModel.getInsert_alarm().getValue()), Toast.LENGTH_LONG).show();
                //Toast.makeText(getActivity(), "Ready to insert", Toast.LENGTH_SHORT).show();
            }
        });
        alarmViewModel.getUpdate_alarm().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer aLong) {
                startAlarm(calendar, args.getLong(EXTRA_ID));

                //Toast.makeText(getActivity(), String.valueOf(alarmViewModel.getUpdate_alarm().getValue()), Toast.LENGTH_LONG).show();
                //Toast.makeText(getActivity(), "Ready to update", Toast.LENGTH_SHORT).show();

            }
        });
        alarmViewModel.getDelete_alarm().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer aLong) {
                cancel_alarm(aLong);
            }
        });

        //Set Click Alarm on
        save_alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                create_alarm(calendar);
                //getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Alarm_List()).commit();
            }
        });
        //Set Cancel button
        cancel_alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //cancel_alarm();
            }

        });
        //Set our ringtone click
        ringtone_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent songIntent = new Intent();
                songIntent.setType("audio/*");
                songIntent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(
                        Intent.createChooser(songIntent, "Choose Sound File"), ALARM_REQUEST_CODE);
            }
        });
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        args = getArguments();
        if (args != null) {
            save_alarm.setText("Update");
            Toast.makeText(getActivity(), String.valueOf(args.getLong(EXTRA_ID, 0)), Toast.LENGTH_LONG).show();
            alarm_title.setText(args.getString(EXTRA_TITLE));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                mTimePicker.setHour(args.getInt(EXTRA_HOUR));
                mTimePicker.setMinute(args.getInt(EXTRA_MINUTE));
            } else {
                mTimePicker.setCurrentHour(args.getInt(EXTRA_HOUR));
                mTimePicker.setCurrentMinute(args.getInt(EXTRA_MINUTE));
            }
            final CheckBox day = day_week_list.get(args.getInt(EXTRA_DAY) - 1);
            day.setChecked(true);

            ringtone_title.setText(args.getString(EXTRA_SONG));

        }


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (RESULT_OK == resultCode && ALARM_REQUEST_CODE == requestCode) {
            songUri = data.getData();  // the selected audio
            ringtone = RingtoneManager.getRingtone(getActivity(), songUri);
            ringtone_title.setText(ringtone.getTitle(getActivity()));
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void startAlarm(Calendar calendar, long id_alarm) {



        //Time---------------------------------------------------
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            calendar.set(Calendar.HOUR_OF_DAY, mTimePicker.getHour());
            calendar.set(Calendar.MINUTE, mTimePicker.getMinute());
        }
        //below marshmallow's version
        else {
            //Setting calendar with values that we've picked on the time picker
            calendar.set(Calendar.HOUR_OF_DAY, mTimePicker.getCurrentHour());
            calendar.set(Calendar.MINUTE, mTimePicker.getCurrentMinute());
        }
        if (day_week == -1) {
            day_week = calendar.get(Calendar.DAY_OF_WEEK);
        }
        calendar.set(Calendar.DAY_OF_WEEK, day_week);
        calendar.set(Calendar.SECOND, 0);

        if (calendar.before(Calendar.getInstance())) {
            calendar.add(Calendar.DATE, 1);
        }
        long alarmStartTime = calendar.getTimeInMillis();

        Intent intent = new Intent(getActivity(), AlarmReceiver.class);
        intent.putExtra("extra", "alarm on");
        intent.putExtra("song", songUri);
        intent.putExtra("alarmName", alarm_title.getText().toString());
        PendingIntent alarmIntent = PendingIntent.getBroadcast(getActivity(), (int) id_alarm, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        String idChannel = "Channel1";
        Intent activityIntent = new Intent(getActivity(), MainActivity.class);
        //--------------------------------------------------------------------------------
        PendingIntent contentIntent = PendingIntent.getActivity(getActivity(), (int) id_alarm, activityIntent, 0);

        Notification notification = new NotificationCompat.Builder(getActivity(), idChannel)
                .setSmallIcon(R.drawable.ic_timer)
                .setContentTitle("Awake&Go")
                .setContentText(alarm_title.getText().toString().trim())
                .setCategory(NotificationCompat.CATEGORY_ALARM)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(contentIntent)
                .setAutoCancel(true)
                .build();
        //getActivity().startForeground(NOTIFICATION_ID, notification);
        mNotificationManager.notify((int) id_alarm, notification);
        mAlarmManager = (AlarmManager) getActivity().getSystemService(ALARM_SERVICE);
        if (mAlarmManager != null) {
            if (repeat) {
                mAlarmManager.setRepeating(AlarmManager.RTC_WAKEUP, alarmStartTime,
                        7 * 24 * 60 * 60 * 1000,
                        alarmIntent);
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    mAlarmManager.setExact(AlarmManager.RTC_WAKEUP, alarmStartTime,
                            alarmIntent);
                } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    mAlarmManager.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, alarmStartTime,
                            alarmIntent);
                } else {
                    mAlarmManager.set(AlarmManager.RTC_WAKEUP, alarmStartTime, alarmIntent);
                }
            }
        }
    }

    private void cancel_alarm(long id_alarm) {

        mAlarmManager = (AlarmManager) getActivity().getSystemService(ALARM_SERVICE);
        //Send Notification and text
        Intent intent = new Intent(getActivity(), AlarmReceiver.class);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(getActivity(), (int) id_alarm, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        intent.putExtra("extra", "alarm off");
        //getBroadcast
        mAlarmManager.cancel(alarmIntent);
        getActivity().sendBroadcast(intent);
        Toast.makeText(getActivity(), "Canceled", Toast.LENGTH_SHORT).show();
    }

    // TO INSERT DATA INTO DATABASE
    private void create_alarm(Calendar calendar) {


        // Here we take out data to insert in our database
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            calendar.set(Calendar.HOUR_OF_DAY, mTimePicker.getHour());
            calendar.set(Calendar.MINUTE, mTimePicker.getMinute());
        }
        //below marshmallow's version
        else {
            //Setting calendar with values that we've picked on the time picker
            calendar.set(Calendar.HOUR_OF_DAY, mTimePicker.getCurrentHour());
            calendar.set(Calendar.MINUTE, mTimePicker.getCurrentMinute());
        }
        calendar.set(Calendar.SECOND, 0);

        if (day_week == -1) {
            day_week = calendar.get(Calendar.DAY_OF_WEEK);
        }
        calendar.set(Calendar.DAY_OF_WEEK, day_week);

        if (calendar.before(Calendar.getInstance())) {
            calendar.add(Calendar.DATE, 1);
        }

        long alarmStartTime = calendar.getTimeInMillis();
        String name_alarm = alarm_title.getText().toString().trim();

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm"); // time
        String current_time = sdf.format(alarmStartTime);

        SimpleDateFormat sdf_ = new SimpleDateFormat("EEE");//day
        String dayName = sdf_.format(calendar.getTime());

        if (name_alarm.trim().isEmpty()) {
            Toast.makeText(getActivity(), "Please insert Name", Toast.LENGTH_SHORT).show();
            return;
        }

        if (args !=null) {
            Alarm alarm_updated = new Alarm(args.getString(EXTRA_TITLE),args.getInt(EXTRA_HOUR),args.getInt(EXTRA_MINUTE),args.getString(EXTRA_SONG),args.getInt(EXTRA_DAY));
            long id = args.getLong(EXTRA_ID, -1);
              alarm_updated.setAlarm_name(name_alarm);
              alarm_updated.setAlarm_time(current_time);
              alarm_updated.setAlarm_date(dayName);
              alarm_updated.setAlarm_song(songUri.toString());
              alarm_updated.setDay(day_week);


                alarm_updated.setAlarm_id(id);
                alarmViewModel.update(alarm_updated);
                 Toast.makeText(getActivity(), String.valueOf(id), Toast.LENGTH_LONG).show();
                Toast.makeText(getActivity(), "Alarm updated!", Toast.LENGTH_SHORT).show();
                ringtone_title.setText("");
        }
        else
        {
            Alarm alarm = new Alarm(name_alarm, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE),songUri.toString(), day_week);
            // Insert data into database
            alarm.setAlarm_date(dayName);
            alarm.setAlarm_time(current_time);
            alarmViewModel.insert(alarm);
            Toast.makeText(getActivity(), "Alarm added", Toast.LENGTH_LONG).show();
            //alarm_title.setText("");
            ringtone_title.setText("");

        }
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
