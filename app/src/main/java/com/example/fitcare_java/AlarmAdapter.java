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
    private final RecyclerViewInterface recyclerViewInterface;

    public AlarmAdapter(Context context, ArrayList<AlarmHistory> alarms, RecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.alarms = alarms;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public AlarmAdapter.AlarmViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.alarm_item,parent,false);
        return new AlarmViewHolder(view, recyclerViewInterface);
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

    public static class AlarmViewHolder extends RecyclerView.ViewHolder{

        TextView txtRetrieveTaskName, txtRetrieveTime;
        public AlarmViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);

            txtRetrieveTaskName = itemView.findViewById(R.id.txtRetrieveTaskName);
            txtRetrieveTime = itemView.findViewById(R.id.txtRetrieveTime);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (recyclerViewInterface != null){
                        int pos = getAdapterPosition();

                        if (pos != RecyclerView.NO_POSITION){
                            recyclerViewInterface.onItemClick(pos);
                        }
                    }
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if (recyclerViewInterface != null){
                        int pos = getAdapterPosition();

                        if (pos != RecyclerView.NO_POSITION){
                            recyclerViewInterface.onItemLongClick(pos);
                        }
                    }
                    return true;
                }
            });
        }
    }
}
