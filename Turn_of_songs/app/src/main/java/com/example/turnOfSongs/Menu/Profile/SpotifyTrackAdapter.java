package com.example.turnOfSongs.Menu.Profile;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.turnOfSongs.R;
import com.example.turnOfSongs.Menu.Track;

import java.util.ArrayList;

public class SpotifyTrackAdapter extends RecyclerView.Adapter<SpotifyTrackAdapter.ViewHolder> {

    private ArrayList<Track> mTracks;

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.tracks_list_item, parent, false);
        return new ViewHolder(view);
    }

    SpotifyTrackAdapter(ArrayList<Track> tracks){
        mTracks = tracks;
    }

    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.name.setText(mTracks.get(position).getTrackName());
    }

    public int getItemCount() {
        return mTracks.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.search_fragment_textView_track);
        }
    }
}
