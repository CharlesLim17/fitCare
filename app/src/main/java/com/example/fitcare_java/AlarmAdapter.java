package com.example.fitcare_java;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AlarmAdapter extends RecyclerView.Adapter<AlarmAdapter.AlarmViewHolder> {

    Context context;
    ArrayList<AlarmHistory> alarms;

    public AlarmAdapter(Context context, ArrayList<AlarmHistory> alarms) {
        this.context = context;
        this.alarms = alarms;
    }

    @NonNull
    @Override
    public AlarmAdapter.AlarmViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.alarm_item,parent,false);
        return new AlarmViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AlarmAdapter.AlarmViewHolder holder, int position) {
        AlarmHistory alarmHistory = alarms.get(position);

        holder.txtRetrieveTaskName.setText(alarmHistory.getTaskName());
        holder.txtRetrieveTime.setText(alarmHistory.getTime());
    }

    @Override
    public int getItemCount() {
        return alarms.size();
    }

    public class AlarmViewHolder extends RecyclerView.ViewHolder{

        TextView txtRetrieveTaskName, txtRetrieveTime;
        public AlarmViewHolder(@NonNull View itemView) {
            super(itemView);

            txtRetrieveTaskName = itemView.findViewById(R.id.txtRetrieveTaskName);
            txtRetrieveTime = itemView.findViewById(R.id.txtRetrieveTime);
        }
    }
}
