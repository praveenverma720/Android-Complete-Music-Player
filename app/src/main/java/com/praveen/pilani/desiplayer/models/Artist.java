package com.praveen.pilani.desiplayer.models;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by praveen on 16/03/18.
 */

public class Artist {

    public final List<Album> albums;

    public Artist(List<Album> albums) {
        this.albums = albums;
    }

    public Artist() {
        this.albums = new ArrayList<>();
    }

    public int getId() {
        return getFirstAlbum().getArtistId();
    }

    public String getName() {
        return getFirstAlbum().getArtistName();
    }

    @NonNull
    public Album getFirstAlbum() {
        return albums.isEmpty() ? new Album() : albums.get(0);
    }
}
