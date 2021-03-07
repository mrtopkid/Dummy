package com.company;

import com.company.Audio.AudioMethods;
import com.company.Audio.AutoJoin;
import com.company.DiscordLevelSystem.UserScorePropertiesManager;
import com.company.FlemRandomKick.FlemKickService;
import com.google.api.client.util.Lists;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceLeaveEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.io.*;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EventListener extends ListenerAdapter {

    TextToSpeechService ttss = new TextToSpeechService();

    Integer timeTriggered = 0;

    Guild guild;
    static GuildMessageReceivedEvent event;

    String[] ttsMessage;
    MessageChannel channel;
    VoiceChannel currentChannel;
    AudioMethods audioMethods;

    FlemKickService kickService;

    List<String> helloList = Arrays.asList(
            "hello my son",
            "Hello",
            "fuckoff",
            "Hello mister",
            "Konichiwa",
            "Have you seen my friend lenny",
            "Yes now ow are we",
            "ey up me duck cum an ave a brew withers and siddown ill make you a cuppa");

    HashMap<String, AudioMethods> audioMethodsHashMap = new HashMap<>();

    public static int getRandomInteger(int maximum, int minimum){
        return ((int) (Math.random()*(maximum - minimum))) + minimum;
    }

    public EventListener() {
        EventListenerWeakReference.updateInstance(this);
    }

    @Override
    public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent event) {

        UserScorePropertiesManager.addCommentScore(event.getAuthor().getId());
        System.out.println(UserScorePropertiesManager.getScoreForUser(event.getAuthor().getId()));

        String[] command = event.getMessage().getContentRaw().split(" ",  5);

        ttsMessage = event.getMessage().getContentRaw().split(" " , 3);
        System.out.println(ttsMessage[0] + " + " + ttsMessage[1]);
        EventListener.event = event;
        JDA jda = event.getJDA();

        audioMethods = null;

        if (audioMethodsHashMap.containsKey(event.getGuild().getId())){
            audioMethods = audioMethodsHashMap.get(event.getGuild().getId());
        } else {
            audioMethodsHashMap.put(event.getGuild().getId(), new AudioMethods());
            audioMethods = audioMethodsHashMap.get(event.getGuild().getId());
        }

        User author = event.getAuthor();
        Message message = event.getMessage();

        channel = event.getChannel();

        event.getJDA().getPresence().setActivity(Activity.playing("your mother"));

        currentChannel = event.getGuild().getSelfMember().getVoiceState().getChannel();

        audioMethods.setAudioManager(event.getGuild().getAudioManager());

        if (!("722546466278604852".equals(author.getId()))) {
            if ("<@!407995681853407232>".equals(command[0])) {
                channel.sendMessage("gay").queue();
            } else {
                if ("<@!722546466278604852>".equals(command[0])) {
                    timeTriggered++;
                    System.out.println("time triggered: " + timeTriggered);
                    System.out.println("Command: " + command[1]);
                    switch (command[1]) {
                        /*
                        case "join":
                            audioMethods.connectToFirstVoiceChannel(event.getGuild().getAudioManager());
                            break;



                        case "joinme":
                            if (audioMethods.joinMeMethod(event, channel)) return;

                            break;



                        case "leave":
                            if (currentChannel == null) {
                                channel.sendMessage("I am not in a voice channel retard").queue();
                                return;
                            }
                            event.getGuild().getAudioManager().closeAudioConnection();
                            break;





                        case "play":
                            audioMethods.loadAndPlay(event.getChannel(), command[2]);

                            break;





                        case "skip":
                            audioMethods.skipTrack(event.getChannel());
                            break;



                        case "volume":
                            audioMethods.setVolume(event.getChannel(), command[2]);



                        case "add":

                            break;



                        case "stop":
                            audioMethods.stop(event.getGuild());
                            break;



                        case "bumg":
                            if (audioMethods.bumgMethod(event)) return;
                            break;




                        case "good":
                            goodBotMethod(command, channel);
                            break;



                        case "bad":
                            badBotMethod(command, channel);
                            break;


                         */

                        case "set":
                            switch (command[2]){
                                case "sound":
                                    try {
                                        System.out.println(event.getMessage().getMentions().get(1).getId());
                                        SwitchUserOnVCJoin.editUserSounds(event.getMessage().getMentions().get(1).getId(), command[4]);
                                        event.getChannel().sendMessage("Set " + command[3] + " VC entry sound to " + command[4]).queue();
                                    } catch (Exception E) {
                                        event.getChannel().sendMessage("Dumbass").queue();
                                        E.printStackTrace();
                                    }
                                    break;
                            }
                            break;

/*
                        case "say":

                        case "Say":
                            if (retardTTS(event, ttsMessage, audioMethods, channel, currentChannel)) return;
                            break;



                        case "reset":
                            if (event.getChannel().getId().equals("805854297443139614")) {
                                System.out.println("Resetting");
                                setFlemTally();
                                event.getChannel().sendMessage("Lmao").queue();
                                event.getChannel().sendMessage("It has been 0 days since flem was last kicked").queue();
                            } else {
                                event.getChannel().sendMessage("Gotta reset in the #days-since-last-flem-kick channel").queue();
                            }
                            break;



                        case "kickvc":
                            try {
                                List<Member> members = event.getMessage().getMentionedMembers();
                                for (Member member: members) {
                                    System.out.println(member.getUser().getName());
                                }
                                event.getGuild().kickVoiceMember(members.get(1)).complete();
                                ttss.generateAndReturnSpeech("Thot removed");
                                audioMethods.loadAndPlayNoConfirmation(event.getGuild(), "tts.wav");
                            } catch (Exception E){
                                E.printStackTrace();
                            }
                            break;



                        case "quote":
                            try {
                                List<Message> messageList = event.getGuild().getTextChannelById("794388439914774529").getHistory().retrievePast(99).complete();
                                Random rand = new Random();
                                Message randomMessage = messageList.get(rand.nextInt(messageList.size()));
                                ttss.generateAndReturnSpeech(randomMessage.getContentRaw());
                                audioMethods.loadAndPlayNoConfirmation(event.getGuild(), "tts.wav");
                            } catch (Exception E){
                                E.printStackTrace();
                            }
                            break;

 */
                        case "help":
                            channel.sendMessage("Check your dm's for a list of commands :)").queue();
                            break;

                        default:
                            //channel.sendMessage("I don't understand :(").queue();
                            break;
                    }
                }

            }
        }
        //super.onGuildMessageReceived(event);
    }

    private boolean retardTTS(@Nonnull GuildMessageReceivedEvent event, String[] ttsMessage, AudioMethods audioMethods, MessageChannel channel, VoiceChannel currentChannel) {
        if (ttss.generateAndReturnSpeech(ttsMessage[2])){
            System.out.println("wack");
        }

        if (currentChannel == null) {
            if (audioMethods.joinMeMethod(event, channel)) return true;
            return true;
        }

        audioMethods.loadAndPlayNoConfirmation(event.getGuild(), "tts.wav");
        return false;
    }


    // ---- Extracted Switch Methods ---- //

    /*

    private void setFlemTally(){
        try (OutputStream output = new FileOutputStream("flem.properties")) {

            Properties prop = new Properties();

            prop.setProperty("tally", String.valueOf(0));

            prop.store(output, null);

            System.out.println(prop);
        } catch (IOException io) {
            io.printStackTrace();
        }
    }



     */

    // ---- Misc Methods ---- //


    public static void leaveVC(){
        try {
            EventListener.event.getGuild().getAudioManager().closeAudioConnection();
        } catch (Exception E) {
            E.printStackTrace();
        }
    }

    public void sayDumbShit(String message){
        if (ttss.generateAndReturnSpeech(message)){
            System.out.println("wack");
        }
        audioMethods.loadAndPlayNoConfirmation(event.getGuild(), "tts.wav");
    }


    // ---- Overrides ---- //


    @Override
    public void onPrivateMessageReceived(@Nonnull PrivateMessageReceivedEvent event) {
        String[] command = event.getMessage().getContentRaw().split(" ",  5);
        switch (command[0]) {
            case "rename":
                switch (command[1]){
                    case "you":
                        event.getJDA().getGuildById("710599559562264637").getMemberById("722546466278604852").modifyNickname(command[2]).complete();
                        break;
                    case "flem":
                        event.getJDA().getGuildById("710599559562264637").getMemberById("572870594232582154").modifyNickname(command[2]).complete();
                        break;
                }
                break;

        }
        System.out.println(command[0] + " " + command[1]);
        super.onPrivateMessageReceived(event);
    }

    private String getRandom() {
        Random rand = new Random();
        return helloList.get(rand.nextInt(helloList.size()));
    }

    @Override
    public void onGuildVoiceJoin(@Nonnull GuildVoiceJoinEvent event) {
        if ("722546466278604852".equals(event.getMember().getId())){
            String randString = getRandom();
            if ("hello my son".equals(randString)){
                ExecutorService service = Executors.newSingleThreadExecutor();
                service.execute(this::dumbHelloSonMethod);
            } else if ("fuckoff".equals(randString)) {

            } else {
                ttss.generateAndReturnSpeech(getRandom());
                audioMethods.loadAndPlayNoConfirmation(event.getGuild(), "tts.wav");
            }
        } else if ("325747500822560770".equals(event.getMember().getId())) {
            ttss.generateAndReturnSpeech("A faggot has joined");
            audioMethods.loadAndPlayNoConfirmation(event.getGuild(), "tts.wav");
        } else {
            try {
                if (AutoJoin.getAutoJoin()) {
                    audioMethods.silentJoin(event);
                }
                if ("572870594232582154".equals(event.getMember().getId())) { //If flem, start flem service
                    kickService = new FlemKickService(this);
                    System.out.println("Started flem service");
                }
                if (!(event.getMember().getNickname() == null)) {
                    ttss.generateAndReturnSpeech(event.getMember().getNickname() + " has joined");
                } else {
                    ttss.generateAndReturnSpeech(event.getMember().getUser().getName() + " has joined");
                }
                audioMethods.loadAndPlayNoConfirmation(event.getGuild(), "tts.wav");
            } catch (Exception E) {
                System.err.println("Event listener fail");
                E.printStackTrace();
            }
        }
        super.onGuildVoiceJoin(event);
    }

    private void dumbHelloSonMethod(){
        ttss.generateAndReturnSpeechSetValues("Hello my son", 30, 140);
        audioMethods.loadAndPlayNoConfirmation(event.getGuild(), "tts.wav");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ttss.generateAndReturnSpeechSetValues("Hello father", 150, 150);
        audioMethods.loadAndPlayNoConfirmation(event.getGuild(), "tts.wav");
    }

    @Override
    public void onGuildVoiceLeave(@NotNull GuildVoiceLeaveEvent event) {
        if (event.getChannelLeft().getMembers().size() == 1){
            System.out.println("Empty VC");
            event.getGuild().getAudioManager().closeAudioConnection();


        }
        if (!("722546466278604852".equals(event.getMember().getId()))){
            if ("407995681853407232".equals(event.getMember().getId())){ //If flem, stop flem service
                if (!(kickService == null)){
                    kickService.setKillLoop(true);
                }
                kickService = null;
            }
            if (!(event.getMember().getNickname() == null)) {
                ttss.generateAndReturnSpeech(event.getMember().getNickname() + " has left");
            } else {
                ttss.generateAndReturnSpeech(event.getMember().getUser().getName() + " has left");
            }
            audioMethods.loadAndPlayNoConfirmation(event.getGuild(), "tts.wav");
        }
        super.onGuildVoiceLeave(event);
    }



    @Override
    public void onMessageReceived(@Nonnull MessageReceivedEvent event) {
        super.onMessageReceived(event);
    }


     /*
        if (currentChannel == null) {
            if (!("722546466278604852".equals(event.getMember().getId()))) {
                System.out.println(event.getEntity().getNickname());
                AudioMethods audioMethods = null;
                if (audioMethodsHashMap.containsKey(event.getGuild().getId())) {
                    audioMethods = audioMethodsHashMap.get(event.getGuild().getId());
                } else {
                    audioMethodsHashMap.put(event.getGuild().getId(), new AudioMethods());
                    audioMethods = audioMethodsHashMap.get(event.getGuild().getId());
                }
                try {
                    audioMethods.stupidJoinMethod(event);
                } catch (Exception E) {
                    E.printStackTrace();
                    System.err.println("Failed to do the dumb join noise");
                }
            }
        } else {

        }

         */

}
