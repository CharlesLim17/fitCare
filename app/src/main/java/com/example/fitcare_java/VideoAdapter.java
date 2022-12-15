package com.example.fitcare_java;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {

    Context context;
    ArrayList<WatchedHistory>list;

    public VideoAdapter(Context context, ArrayList<WatchedHistory> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.watchedhistoryhetrieve, parent, false);

        return new VideoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {

        WatchedHistory watchedHistory = list.get(position);

        holder.txtRetrieveTitle.setText(watchedHistory.getTitle());
        holder.txtRetrieveDate.setText(watchedHistory.getDate());
        holder.txtRetrieveTime.setText(watchedHistory.getTime());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class VideoViewHolder extends RecyclerView.ViewHolder{

        TextView txtRetrieveTitle, txtRetrieveDate, txtRetrieveTime;

        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);

            txtRetrieveTitle = itemView.findViewById(R.id.txtRetrieveTitle);
            txtRetrieveDate = itemView.findViewById(R.id.txtRetrieveDate);
            txtRetrieveTime = itemView.findViewById(R.id.txtRetrieveTime);

        }
    }
}
