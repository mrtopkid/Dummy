package com.company.Audio;

import com.company.GuildMusicManager;
import com.company.SwitchUserOnVCJoin;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.managers.AudioManager;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class AudioMethods {

    // ---- Audio ---- //

    private static HashMap<String, AudioMethods> audioMethodsHashMap = new HashMap<>();
    public static AudioMethods getAudioMethodsHashMap(String ID) {
        if (!audioMethodsHashMap.containsKey(ID)) {
            audioMethodsHashMap.put(ID, new AudioMethods());
        }
        return audioMethodsHashMap.get(ID);
    }

    // ---- Music Methods ---- //

    AudioManager audioManager;

    public AudioManager getAudioManager() {
        return audioManager;
    }

    public void setAudioManager(AudioManager audioManager) {
        this.audioManager = audioManager;
    }

    private final AudioPlayerManager playerManager;

    public AudioMethods() {
        this.playerManager = new DefaultAudioPlayerManager();
        AudioSourceManagers.registerRemoteSources(playerManager);
        AudioSourceManagers.registerLocalSource(playerManager);
    }

    public void setVolume(final TextChannel channel, String volume){
        GuildMusicManager musicManager = getGuildAudioPlayer(channel.getGuild());
        Integer vol = Integer.parseInt(volume);
        if (vol > 100){
            channel.sendMessage("Too loud chief").queue();
        } else {
            try {
                musicManager.player.setVolume(vol);
                channel.sendMessage("volume " + volume + "%").queue();
            } catch (Exception E){
                E.printStackTrace();
                channel.sendMessage("retard boy").queue();
            }
        }
    }

    private void loadAndPlayAndLeave(final TextChannel channel, final String trackUrl) {
        GuildMusicManager musicManager = getGuildAudioPlayer(channel.getGuild());
        musicManager.scheduler.clear();
        musicManager.player.stopTrack();
        System.out.println(trackUrl);
        playerManager.loadItemOrdered(musicManager, trackUrl, new AudioLoadResultHandler() {
            @Override
            public void trackLoaded(AudioTrack track) {
                play(channel.getGuild(), musicManager, track);
            }

            @Override
            public void playlistLoaded(AudioPlaylist playlist) {
                AudioTrack firstTrack = playlist.getSelectedTrack();

                if (firstTrack == null) {
                    firstTrack = playlist.getTracks().get(0);
                }
                play(channel.getGuild(), musicManager, firstTrack);
            }

            @Override
            public void noMatches() {

            }

            @Override
            public void loadFailed(FriendlyException exception) {
                System.out.println(exception.severity);
            }


        });
    }

    public void loadAndPlay(final TextChannel channel, final String trackUrl) {
        GuildMusicManager musicManager = getGuildAudioPlayer(channel.getGuild());
        System.out.println(trackUrl);
        playerManager.loadItemOrdered(musicManager, trackUrl, new AudioLoadResultHandler() {
            @Override
            public void trackLoaded(AudioTrack track) {
                channel.sendMessage("Adding " + track.getInfo().title).queue();

                play(channel.getGuild(), musicManager, track);
            }

            @Override
            public void playlistLoaded(AudioPlaylist playlist) {
                AudioTrack firstTrack = playlist.getSelectedTrack();

                if (firstTrack == null) {
                    firstTrack = playlist.getTracks().get(0);
                }

                channel.sendMessage("Adding to queue " + firstTrack.getInfo().title + " (first track of playlist " + playlist.getName() + ")").queue();

                play(channel.getGuild(), musicManager, firstTrack);
            }

            @Override
            public void noMatches() {
                channel.sendMessage("Nothing found by " + trackUrl).queue();
            }

            @Override
            public void loadFailed(FriendlyException exception) {
                System.out.println("Could not play: " + exception.getMessage());
                channel.sendMessage("I can't play that one - blocked by youtube").queue();
                System.out.println(exception.severity);
            }
        });
    }

    public void loadAndPlayNoConfirmation(final Guild guild, final String trackUrl) {
        GuildMusicManager musicManager = getGuildAudioPlayer(guild);
        System.out.println(trackUrl);
        playerManager.loadItemOrdered(musicManager, trackUrl, new AudioLoadResultHandler() {
            @Override
            public void trackLoaded(AudioTrack track) {
                play(guild, musicManager, track);
            }

            @Override
            public void playlistLoaded(AudioPlaylist playlist) {
                AudioTrack firstTrack = playlist.getSelectedTrack();

                if (firstTrack == null) {
                    firstTrack = playlist.getTracks().get(0);
                }
                play(guild, musicManager, firstTrack);
            }

            @Override
            public void noMatches() {

            }

            @Override
            public void loadFailed(FriendlyException exception) {
                System.out.println("Could not play: " + exception.getMessage());
                System.out.println(exception.severity);
            }
        });
    }

    private void play(Guild guild, GuildMusicManager musicManager, AudioTrack track) {
        //connectToFirstVoiceChannel(guild.getAudioManager());
        audioManager.openAudioConnection(guild.getVoiceChannelById("737793582336966696"));
        musicManager.scheduler.queue(track);
    }

    public void stop(Guild guild) {
        connectToFirstVoiceChannel(guild.getAudioManager());
        GuildMusicManager musicManager = getGuildAudioPlayer(guild);

        musicManager.player.stopTrack();
    }

    public void skipTrack(TextChannel channel) {
        GuildMusicManager musicManager = getGuildAudioPlayer(channel.getGuild());
        musicManager.scheduler.nextTrack();

        channel.sendMessage("Skipped to next track.").queue();
    }

    public boolean joinMeMethod(@Nonnull CommandEvent event, MessageChannel channel) {
        VoiceChannel connectedChannel = event.getMember().getVoiceState().getChannel();
        if (connectedChannel == null) {

            channel.sendMessage("You are not in a VC chief").queue();
            return true;
        }

        if (audioManager.isAttemptingToConnect()) {
            channel.sendMessage("The bot is already trying to connect").queue();
            return true;
        }
        audioManager.openAudioConnection(connectedChannel);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean joinMeMethod(@Nonnull GuildMessageReceivedEvent event, MessageChannel channel) {
        VoiceChannel connectedChannel = event.getMember().getVoiceState().getChannel();
        if (connectedChannel == null) {

            channel.sendMessage("You are not in a VC chief").queue();
            return true;
        }

        if (audioManager.isAttemptingToConnect()) {
            channel.sendMessage("The bot is already trying to connect").queue();
            return true;
        }
        audioManager.openAudioConnection(connectedChannel);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean silentJoin(@Nonnull GuildVoiceJoinEvent event) {
        VoiceChannel connectedChannel = event.getMember().getVoiceState().getChannel();
        if (connectedChannel == null) {
            return true;
        }

        if (audioManager.isAttemptingToConnect()) {
            System.out.println("The bot is already trying to connect");
            return true;
        }
        audioManager.openAudioConnection(connectedChannel);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean bumgMethod(@Nonnull GuildMessageReceivedEvent event) {
        VoiceChannel bumgchannel = event.getMember().getVoiceState().getChannel();
        if (bumgchannel == null) {
            System.out.println("No channel");
            return true;
        }

        if (audioManager.isAttemptingToConnect()) {
            System.out.println("Already connecting");
            return true;
        }
        audioManager.openAudioConnection(bumgchannel);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        loadAndPlayAndLeave(event.getChannel(), "bungmbugs.mp4");
        TimerTask leaveTask = new TimerTask() {
            @Override
            public void run() {
                event.getGuild().getAudioManager().closeAudioConnection();
            }
        };
        Timer timer = new Timer();
        timer.schedule(leaveTask, 15000);
        return false;
    }

    public void stupidJoinMethod(GuildVoiceJoinEvent event){
        audioManager = event.getGuild().getAudioManager();
        VoiceChannel bumgchannel = event.getMember().getVoiceState().getChannel();
        if (bumgchannel == null) {
            return;
        }

        if (audioManager.isAttemptingToConnect()) {
            return;
        }
        System.out.println("User ID: " + event.getMember().getId());
        audioManager.openAudioConnection(event.getGuild().getVoiceChannelById("737793582336966696"));
        loadAndPlayAndLeave(event.getGuild().getTextChannelById("710599559562264640"), SwitchUserOnVCJoin.getURL(event.getMember().getId()));
        TimerTask leaveTask = new TimerTask() {
            @Override
            public void run() {
                event.getGuild().getAudioManager().closeAudioConnection();
            }
        };
        Timer timer = new Timer();
        timer.schedule(leaveTask, 7000);
    }

    public void connectToFirstVoiceChannel(AudioManager audioManager) {
        if (!audioManager.isConnected() && !audioManager.isAttemptingToConnect()) {
            for (VoiceChannel voiceChannel : audioManager.getGuild().getVoiceChannels()) {
                audioManager.openAudioConnection(voiceChannel);
                break;
            }
        }
    }

    private synchronized GuildMusicManager getGuildAudioPlayer(Guild guild) {
        long guildId = Long.parseLong(guild.getId());
        GuildMusicManager musicManager = MusicManagers.getMusicManagers().get(guildId);

        if (musicManager == null) {
            musicManager = new GuildMusicManager(playerManager);
            MusicManagers.getMusicManagers().put(guildId, musicManager);
        }

        guild.getAudioManager().setSendingHandler(musicManager.getSendHandler());

        return musicManager;
    }

}
