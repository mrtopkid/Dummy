package com.company.Spotify;

import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.PlaylistItem;
import com.google.api.services.youtube.model.SearchResult;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManager;
import com.sedmelluq.discord.lavaplayer.source.youtube.YoutubeAudioSourceManager;
import com.sedmelluq.discord.lavaplayer.tools.io.HttpConfigurable;
import com.sedmelluq.discord.lavaplayer.track.AudioItem;
import com.sedmelluq.discord.lavaplayer.track.AudioReference;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import com.sun.source.tree.BinaryTree;
import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.credentials.ClientCredentials;
import com.wrapper.spotify.model_objects.specification.Album;
import com.wrapper.spotify.model_objects.specification.Playlist;
import com.wrapper.spotify.model_objects.specification.PlaylistTrack;
import com.wrapper.spotify.model_objects.specification.Track;
import com.wrapper.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;
import com.wrapper.spotify.requests.data.tracks.GetTrackRequest;
import org.apache.hc.core5.http.ParseException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SpotifyAudioService implements AudioSourceManager, HttpConfigurable {

    private static String PROTOCOL_REGEX = "?:spotify:(track:)|(http://|https://)[a-z]+\\.";
    private static String DOMAIN_REGEX = "spotify\\.com/";
    private static String TRACK_REGEX = "track/";
    private static String ALBUM_REGEX = "album/";
    private static String PLAYLIST_REGEX = "user/(.*)/playlist/";
    private static String REST_REGEX = "(.*)";
    private static String SPOTIFY_BASE_REGEX = PROTOCOL_REGEX + DOMAIN_REGEX;

    private SpotifyApi api;
    private ClientCredentialsRequest clientCredentialsRequest;
    private YoutubeAudioSourceManager youtubeAudioSourceManager;
    private ScheduledExecutorService service;
    private YouTube youtube;
    private String youtubeApiKey = "AIzaSyAp4ClImsStjmyqKkIzHu8oU6aS6Zlbpi0";

    private static Pattern SPOTIFY_TRACK_REGEX = Pattern
            .compile("^(" + SPOTIFY_BASE_REGEX + TRACK_REGEX + ")" + REST_REGEX + "$");
    private static Pattern SPOTIFY_ALBUM_REGEX = Pattern
            .compile("^(" + SPOTIFY_BASE_REGEX + ALBUM_REGEX + ")" + REST_REGEX + "$");
    private static Pattern SPOTIFY_PLAYLIST_REGEX = Pattern
            .compile("^(" + SPOTIFY_BASE_REGEX + ")" + PLAYLIST_REGEX + REST_REGEX + "$");

    public SpotifyAudioService(String clientID, String secret, YoutubeAudioSourceManager youtubeAudioSourceManager) {
        api = new SpotifyApi.Builder()
                .setClientId(clientID)
                .setClientSecret(secret)
                .build();
        clientCredentialsRequest = api.clientCredentials().build();
        service = Executors.newScheduledThreadPool(1, r -> new Thread(r, "STUT"));
        service.scheduleAtFixedRate(this::updateAccessToken, 0, 1, TimeUnit.HOURS);

    }

    private void updateAccessToken() {
        try {
            Future<ClientCredentials> clientCredentialsFuture = clientCredentialsRequest.executeAsync();
            ClientCredentials credentials = clientCredentialsFuture.get();
            api.setAccessToken(credentials.getAccessToken());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    public void getPlayList(DefaultAudioPlayerManager manager, String playlistID) {
        try {
            Playlist playlist = api.getPlaylist(playlistID)
                    .build().execute();
            for (PlaylistTrack playlistTrack : playlist.getTracks().getItems()) {
                GetTrackRequest trackRequest = api.getTrack(playlistTrack.getTrack().getId()).build();
                Track track = trackRequest.execute();
                System.out.println(Arrays.toString(track.getArtists()) + " " + track.getName() );
            }
        } catch (ParseException | SpotifyWebApiException | IOException e) {
            e.printStackTrace();
        }


    }

    public AudioItem load (DefaultAudioPlayerManager manager, AudioReference audioReference){
        if (isSpotifyPlaylist(audioReference.identifier)) {
            if (youtube == null) return null;
            Matcher matcher = SPOTIFY_PLAYLIST_REGEX.matcher(audioReference.identifier);
            if (matcher.matches()) {
/*
                try {

                    List<AudioTrack> finalPlaylist = new ArrayList<>();
                    Playlist spotifyPlaylist = api
                            .getPlaylist(matcher.group(matcher.groupCount())).build()
                            .executeAsync().get();
                    for (PlaylistTrack playlistTrack : spotifyPlaylist.getTracks().getItems()) {
                        

                        List<SearchResult> results = searchYoutube(playlistTrack.getTrack().getArtists()[0].getName()
                                + " - " + playlistTrack.getTrack().getName());
                        finalPlaylist.addAll(doThingWithPlaylist(results));


                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }



 */


            }
        }
        return null;
    }

    @Override
    public String getSourceName() {
        return null;
    }

    @Override
    public AudioItem loadItem(DefaultAudioPlayerManager defaultAudioPlayerManager, AudioReference audioReference) {
        return null;
    }

    @Override
    public boolean isTrackEncodable(AudioTrack audioTrack) {
        return false;
    }

    @Override
    public void encodeTrack(AudioTrack audioTrack, DataOutput dataOutput) throws IOException {

    }

    @Override
    public AudioTrack decodeTrack(AudioTrackInfo audioTrackInfo, DataInput dataInput) throws IOException {
        return null;
    }

    @Override
    public void shutdown() {

    }

    @Override
    public void configureRequests(Function<RequestConfig, RequestConfig> function) {

    }

    @Override
    public void configureBuilder(Consumer<HttpClientBuilder> consumer) {

    }

    private List<SearchResult> searchYoutube(String query) throws IOException {
        return youtube.search().list("id,snippet").setKey(youtubeApiKey).setQ(query).setType("video")
                .setFields("items(id/kind,id/videoId,snippet/title,snippet/thumbnails/default/url)").setMaxResults(1L)
                .execute().getItems();
    }

    private boolean isSpotifyTrack(String input) {
        return SPOTIFY_TRACK_REGEX.matcher(input).matches();
    }

    private boolean isSpotifyAlbum(String input) {
        return SPOTIFY_ALBUM_REGEX.matcher(input).matches();
    }

    private boolean isSpotifyPlaylist(String input) {
        return SPOTIFY_PLAYLIST_REGEX.matcher(input).matches();
    }

}
