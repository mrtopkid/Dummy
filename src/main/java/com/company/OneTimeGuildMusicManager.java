package com.company;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;

public class OneTimeGuildMusicManager {/**
 * Audio player for the guild.
 */
public final AudioPlayer player;
    /**
     * Track scheduler for the player.
     */
    public final OneTimeTrack scheduler;

    /**
     * Creates a player and a track scheduler.
     * @param manager Audio player manager to use for creating the player.
     */
    public OneTimeGuildMusicManager(AudioPlayerManager manager) {
        player = manager.createPlayer();
        scheduler = new OneTimeTrack(player);
        player.addListener(scheduler);
    }

    /**
     * @return Wrapper around AudioPlayer to use it as an AudioSendHandler.
     */
    public AudioPlayerSendHandler getSendHandler() {
        return new AudioPlayerSendHandler(player);
    }
}