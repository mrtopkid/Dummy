package com.company;

import com.company.Commands.*;
import com.company.FlemKickCounter.FlemTimer;
import com.jagrosh.jdautilities.command.CommandClientBuilder;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import com.jagrosh.jdautilities.examples.command.AboutCommand;
import com.jagrosh.jdautilities.examples.command.ServerinfoCommand;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.model_objects.credentials.ClientCredentials;
import com.wrapper.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Activity;
import org.quartz.SchedulerException;

import javax.security.auth.login.LoginException;
import java.awt.*;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

public class Main {

    private static final SpotifyApi spotifyApi = new SpotifyApi.Builder()
            .setClientId("e879e51b46af47b8aadc43013a40746e")
            .setClientSecret("d4c856743a1d47959e7d456d77d9abd7")
            .build();
    private static final ClientCredentialsRequest clientCredentialsRequest = spotifyApi.clientCredentials()
            .build();
    static JDA jda;
    public AudioPlayerManager playerManager;
    public AudioPlayer player;
    private String youtubeApiKey = "AIzaSyAp4ClImsStjmyqKkIzHu8oU6aS6Zlbpi0";

    public Main() throws LoginException {

        CommandClientBuilder client = new CommandClientBuilder();
        EventWaiter waiter = new EventWaiter();

        client.setActivity(Activity.playing("your dad"));

        client.setOwnerId("407995681853407232");

        client.setEmojis("\uD83D\uDE03", "\uD83D\uDE2E", "\uD83D\uDE26");

        client.setCoOwnerIds("692404409790890125");

        client.setPrefix("/b ");
        client.setAlternativePrefix("<@!722546466278604852>");

        client.addCommands(new JoinCommand(),new LeaveCommand(),new PlayCommand(),new SkipCommand(), new StopCommand(),new VolumeCommand() ,new GoodBotCommand(),new BadBotCommand(),new SayCommand(),new ResetCommand(), new KickVCCommand(),new QuoteCommand(), new ThiccCommand(waiter),new ServerinfoCommand(), new AnonCommand(), new AutoJoinCommand(), new AboutCommand(Color.RED, "Dummy thicc (V.2.1)",
                new String[]{"Discord leveling system" ,"Speaking dumb shit", "Annoying flem", "Sending anonymous messages", "cuuuum"},
                Permission.ADMINISTRATOR));

        jda = JDABuilder.createDefault("NzIyNTQ2NDY2Mjc4NjA0ODUy.Xukp1Q.utnl3cZW1sl-DuBEyjBbAgmwQQg").build();
        playerManager = new DefaultAudioPlayerManager();
        AudioSourceManagers.registerRemoteSources(playerManager);
        player = playerManager.createPlayer();
        player.addListener(new AudioEventListener());

        try {

        final CompletableFuture<ClientCredentials> clientCredentialsFuture = clientCredentialsRequest.executeAsync();

        final ClientCredentials clientCredentials = clientCredentialsFuture.join();

        spotifyApi.setAccessToken(clientCredentials.getAccessToken());

        System.out.println(spotifyApi.getAccessToken());

        System.out.println("Access Token Expires in: " + clientCredentials.getExpiresIn());
    } catch (CompletionException e) {
        System.out.println("Error: " + e.getCause().getMessage());
    } catch (CancellationException e) {
        System.out.println("Async operation cancelled.");
    }

        //SpotifyAudioService audioService = new SpotifyAudioService();

        jda.addEventListener(new EventListener());
        jda.addEventListener(waiter, client.build());
        updateInstance();



        //
        //waiter, client.build()

        FlemTimer flemTimer = new FlemTimer();
        try {
            flemTimer.startTrigger();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public static JDA getJda() {
        return jda;
    }

    public static void main(String[] args) throws LoginException { Main main = new Main();}

    public void updateInstance(){
        MainReference.updateInstance(this);
    }

}
