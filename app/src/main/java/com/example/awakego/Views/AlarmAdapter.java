package com.example.awakego.Views;


import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.awakego.Models.Alarm;
import com.example.awakego.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lucas on 30.01.2020.
 */
public class AlarmAdapter extends RecyclerView.Adapter<AlarmAdapter.AlarmHolder> {
    public interface OnItemClickListener {
        void onItemClick(Alarm alarm);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }
    private List<Alarm> alarms = new ArrayList<>();
    private OnItemClickListener mListener;

    public AlarmAdapter(List<Alarm> alarms)
    {
        this.alarms = alarms;

    }

    @NonNull
    @Override
    public AlarmHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View alarm_items = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_items, parent, false);

        return new AlarmHolder(alarm_items);
    }

    @NonNull
    @Override
    public void onBindViewHolder(@NonNull AlarmHolder holder, int position) {
        Alarm current_alarm = alarms.get(position);
        Uri songUri = Uri.parse(current_alarm.getAlarm_song());

       // RingtoneManager.getRingtone(this, songUri);
        holder.alarm_title.setText(current_alarm.getAlarm_name());
        holder.date_alarm.setText(current_alarm.getAlarm_date());
        holder.time_alarm.setText(current_alarm.getAlarm_time());
        holder.song_title.setText(current_alarm.getAlarm_song());

    }


    @Override
    public int getItemCount() {
        return alarms.size();
    }

    public Alarm getAlarmAt(int position) {
        return alarms.get(position);
    }

    public void setAlarms(List<Alarm> alarms) {
        this.alarms = alarms;
        notifyDataSetChanged();
    }

    class AlarmHolder extends RecyclerView.ViewHolder {
        private TextView alarm_title;
        private TextView date_alarm;
        private TextView time_alarm;
        private TextView song_title;


        public AlarmHolder(@NonNull View itemView) {
            super(itemView);
            alarm_title = itemView.findViewById(R.id.text_view_title);
            date_alarm = itemView.findViewById(R.id.text_view_date);
            time_alarm = itemView.findViewById(R.id.text_view_time);
            song_title = itemView.findViewById(R.id.text_view_song);

            // Here, we click on our cardView
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position_alarm = getAdapterPosition(); // We get cardView Position
                    if (mListener != null && position_alarm != RecyclerView.NO_POSITION) {
                        mListener.onItemClick(alarms.get(position_alarm));
                    }
                }
            });
        }
    }


}
