package com.praveen.pilani.desiplayer.adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.praveen.pilani.desiplayer.R;
import com.praveen.pilani.desiplayer.models.Album;
import com.praveen.pilani.desiplayer.models.Song;

import java.util.List;

/**
 * Created by praveen on 17/03/18.
 */

public class SongsAdapter extends RecyclerView.Adapter<SongsAdapter.SimpleViewHolder> {

    private List<Song> mSongs;
    private Album mAlbum;
    private final SongSelectedListener mSongSelectedListener;

    public SongsAdapter(Activity activity, Album album) {

        mAlbum = album;
        mSongs = mAlbum.songs;
        mSongSelectedListener = (SongSelectedListener) activity;
    }

    public void swapSongs(Album album) {
        mAlbum = album;
        mSongs = mAlbum.songs;
        notifyDataSetChanged();
    }

    @Override
    @NonNull
    public SimpleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.song_item, parent, false);

        return new SimpleViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SimpleViewHolder holder, int position) {

        Song song = mSongs.get(holder.getAdapterPosition());
        String songTitle = song.title;
        holder.title.setText(songTitle);
        holder.number.setText(Song.formatTrack(song.trackNumber));
        holder.duration.setText(Song.formatDuration(song.duration));
    }

    @Override
    public int getItemCount() {

        return mSongs.size();
    }

    public interface SongSelectedListener {
        void onSongSelected(Song song, Album album);
    }

    class SimpleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        final TextView number, title, duration;
        final ImageView nowPlaying;

        SimpleViewHolder(View itemView) {
            super(itemView);

            number = itemView.findViewById(R.id.track);
            title = itemView.findViewById(R.id.title);
            duration = itemView.findViewById(R.id.duration);
            nowPlaying = itemView.findViewById(R.id.nowPlaying);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Song song = mSongs.get(getAdapterPosition());
            mSongSelectedListener.onSongSelected(song, mAlbum);
        }
    }
}