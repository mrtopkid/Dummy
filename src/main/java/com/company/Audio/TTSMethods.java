package com.company.Audio;

import com.company.TextToSpeechService;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import javax.annotation.Nonnull;

public class TTSMethods {

    private static TextToSpeechService ttss = new TextToSpeechService();

    public static TextToSpeechService getTtss() {
        return ttss;
    }

    public static boolean retardTTS(@Nonnull CommandEvent event, String[] ttsMessage, AudioMethods audioMethods, MessageChannel channel, VoiceChannel currentChannel) {
        if (ttss.generateAndReturnSpeech(ttsMessage[2])){
            System.out.println("wack");
        }

        if (currentChannel == null) {
            if (audioMethods.joinMeMethod(event, channel)) return true;
            return true;
        }

        audioMethods.stop(event.getGuild());
        audioMethods.loadAndPlayNoConfirmation(event.getGuild(), "tts.wav");
        return false;
    }

}
