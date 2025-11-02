package com.jkld.hw_3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.SongViewHolder> {
    private List<Songs> songlist;
    private Context context;

    public SongAdapter(List<Songs> songs, Context context) {
        this.context = context;
        this.songlist = songs;
    }

    public static class SongViewHolder extends RecyclerView.ViewHolder {
        ImageView avatarImageView;
        TextView nameTextView, authorTextView, timeTextView;
        public SongViewHolder(View itemView) {
            super(itemView);
            avatarImageView = itemView.findViewById(R.id.list_icon);
            nameTextView = itemView.findViewById(R.id.list_name);
            authorTextView = itemView.findViewById(R.id.list_author);
            timeTextView = itemView.findViewById(R.id.list_time);
        }
    }
    @Override
    public SongViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.music_list_comp, parent, false);
        return new SongViewHolder(view);
    }
    @Override
    public void onBindViewHolder(SongViewHolder holder, int position) {
        Songs item = songlist.get(position);
        holder.avatarImageView.setImageResource(item.icon);
        holder.nameTextView.setText(item.name);
        holder.authorTextView.setText(item.author);
        holder.timeTextView.setText(item.time);
    }
    @Override
    public int getItemCount() {
        return songlist.size();
    }
}
