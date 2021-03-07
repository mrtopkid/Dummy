package com.company.Spotify;

import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.specification.Paging;
import com.wrapper.spotify.model_objects.specification.Playlist;
import com.wrapper.spotify.model_objects.specification.PlaylistTrack;
import com.wrapper.spotify.requests.data.playlists.GetPlaylistRequest;
import com.wrapper.spotify.requests.data.playlists.GetPlaylistsItemsRequest;
import org.apache.hc.core5.http.ParseException;

import java.io.IOException;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

public class GetPlaylist {

    private SpotifyApi spotifyApi;
    private final GetPlaylistRequest getPlaylistRequest;
    private final GetPlaylistsItemsRequest getPlaylistsItemsRequest;

    public GetPlaylist(SpotifyApi spotifyApi) {
        this.spotifyApi = spotifyApi;

        getPlaylistRequest = spotifyApi.getPlaylist(playlistId)
//          .fields("description")
//          .market(CountryCode.SE)
//          .additionalTypes("track,episode")
                .build();
        getPlaylistsItemsRequest = spotifyApi.getPlaylistsItems(playlistId).build();
    }

    private final String playlistId = "15M2g1LzK117tC0Lx9eoe4";

    public void getPlaylist_Sync() {

        try {
            final Playlist playlist = getPlaylistRequest.execute();

            final Paging<PlaylistTrack> playlistPaging = getPlaylistsItemsRequest.execute();

            for (PlaylistTrack track: playlistPaging.getItems()) {
                System.out.println(track);
            }

            System.out.println("Name: " + playlist.getName());

            System.out.println(playlistPaging.getItems()[1].getTrack().getHref());

        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void getPlaylist_Async() {

        try {
            final CompletableFuture<Playlist> playlistFuture = getPlaylistRequest.executeAsync();

            final Playlist playlist = playlistFuture.join();

            System.out.println("Name: " + playlist.getName());
        } catch (CompletionException e) {
            System.out.println("Error: " + e.getCause().getMessage());
        } catch (CancellationException e) {
            System.out.println("Async operation cancelled.");
        }
    }
}
