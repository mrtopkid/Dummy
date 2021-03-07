package com.company.Spotify;

import com.sedmelluq.discord.lavaplayer.source.youtube.YoutubeAudioSourceManager;
import com.sedmelluq.discord.lavaplayer.source.youtube.YoutubeAudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;

public class SpotifyTrack extends YoutubeAudioTrack {
    public SpotifyTrack(AudioTrackInfo trackInfo, YoutubeAudioSourceManager sourceManager) {
        super(trackInfo, sourceManager);
    }

}
