package com.praveen.pilani.desiplayer.adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.praveen.pilani.desiplayer.R;
import com.praveen.pilani.desiplayer.models.Album;
import com.praveen.pilani.desiplayer.models.Artist;
import com.praveen.pilani.desiplayer.playback.PlayerAdapter;

import java.util.List;

/**
 * Created by praveen on 18/03/18.
 */

public class AlbumsAdapter extends RecyclerView.Adapter<AlbumsAdapter.SimpleViewHolder> {

    private final Activity mActivity;

    private Album mSelectedAlbum;

    private Pair<Artist, List<Album>> mAlbumsForArtist;

    private List<Album> mAlbums;

    private final AlbumSelectedListener mAlbumSelectedListener;

    private final PlayerAdapter mPlayerAdapter;

    private int mSelectedPosition;

    private final RecyclerView mAlbumsRecyclerView;

    public AlbumsAdapter(Activity activity, RecyclerView albumsRecyclerView, Pair<Artist, List<Album>> albumsForArtist, PlayerAdapter playerAdapter) {

        mActivity = activity;

        mAlbumsRecyclerView = albumsRecyclerView;

        mAlbumSelectedListener = (AlbumSelectedListener) mActivity;

        mAlbumsForArtist = albumsForArtist;

        mPlayerAdapter = playerAdapter;

        mAlbums = mAlbumsForArtist.second;

        updateAlbumsForArtist();
    }

    public void swapArtist(Pair<Artist, List<Album>> albumsForArtist) {
        mAlbumsForArtist = albumsForArtist;
        mAlbums = mAlbumsForArtist.second;
        notifyDataSetChanged();
        updateAlbumsForArtist();
    }

    private void updateAlbumsForArtist() {

        Artist artist = mAlbumsForArtist.first;

        mSelectedAlbum = mPlayerAdapter != null && mPlayerAdapter.getSelectedAlbum() != null ? mPlayerAdapter.getSelectedAlbum() : artist.getFirstAlbum();

        if (mPlayerAdapter != null && mPlayerAdapter.getPlayingAlbum() != null && artist.getName().equals(mPlayerAdapter.getPlayingAlbum().getArtistName())) {
            mSelectedAlbum = mPlayerAdapter.getPlayingAlbum();
        }
        mSelectedPosition = mSelectedAlbum.position;
        mAlbumsRecyclerView.smoothScrollToPosition(mSelectedPosition);
        mAlbumSelectedListener.onAlbumSelected(mSelectedAlbum);
    }

    @Override
    @NonNull
    public SimpleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(mActivity)
                .inflate(R.layout.album_item, parent, false);

        return new SimpleViewHolder(itemView);
    }

    private String getYear(int year) {
        return year != 0 ? String.valueOf(year) : mActivity.getString(R.string.unknown_year);
    }

    @Override
    public void onBindViewHolder(@NonNull SimpleViewHolder holder, int position) {

        Album album = mAlbums.get(holder.getAdapterPosition());

        int visibility = !mSelectedAlbum.getTitle().equals(album.getTitle()) ? View.GONE : View.VISIBLE;
        holder.nowPlaying.setVisibility(visibility);

        String albumTitle = album.getTitle();
        holder.title.setText(albumTitle);
        holder.year.setText(getYear(album.getYear()));
    }

    @Override
    public int getItemCount() {

        return mAlbums.size();
    }

    public interface AlbumSelectedListener {
        void onAlbumSelected(Album album);
    }

    class SimpleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        final TextView title, year;
        final ImageView nowPlaying;

        SimpleViewHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.album);
            year = itemView.findViewById(R.id.year);
            nowPlaying = itemView.findViewById(R.id.nowPlaying);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            //update songs list only if the album is updated
            if (getAdapterPosition() != mSelectedPosition) {

                notifyItemChanged(mSelectedPosition);
                mSelectedPosition = getAdapterPosition();

                mSelectedAlbum = mAlbums.get(getAdapterPosition());
                nowPlaying.setVisibility(View.VISIBLE);
                mPlayerAdapter.setSelectedAlbum(mSelectedAlbum);
                mAlbumSelectedListener.onAlbumSelected(mSelectedAlbum);
            }
        }
    }
}