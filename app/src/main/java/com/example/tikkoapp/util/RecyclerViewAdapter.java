package com.example.tikkoapp.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.tikkoapp.R;
import com.example.tikkoapp.model.Information;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    Context context;
    List<Information> information;

    public RecyclerViewAdapter(Context context, List<Information> TempList) {

        this.information = TempList;

        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.information_single, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Information studentDetails = information.get(position);

        holder.StationTextView.setText(studentDetails.getStation_name());
        holder.TimeTextView.setText(studentDetails.getTime());
        holder.CommentTextView.setText(studentDetails.getComment());

    }

    @Override
    public int getItemCount() {

        return information.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView StationTextView;
        public TextView CommentTextView;
        public TextView TimeTextView;

        public ViewHolder(View itemView) {

            super(itemView);

            StationTextView = (TextView) itemView.findViewById(R.id.station_layout);

            CommentTextView = (TextView) itemView.findViewById(R.id.comment_layout);
            TimeTextView = (TextView) itemView.findViewById(R.id.time_layout);
        }
    }
}