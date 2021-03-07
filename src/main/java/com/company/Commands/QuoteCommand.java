package com.company.Commands;

import com.company.Audio.AudioMethods;
import com.company.Audio.TTSMethods;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.VoiceChannel;

import java.util.List;
import java.util.Random;

public class QuoteCommand extends Command {

    public QuoteCommand() {
        this.name = "Quote";
        this.help = "Quotes from quotes with friends";
        this.aliases = new String[]{"quote"};
    }

    @Override
    protected void execute(CommandEvent commandEvent) {
        try {
            AudioMethods audioMethods = AudioMethods.getAudioMethodsHashMap(commandEvent.getGuild().getId());
            audioMethods.setAudioManager(commandEvent.getGuild().getAudioManager());
            List<Message> messageList = commandEvent.getGuild().getTextChannelById("794388439914774529").getHistory().retrievePast(99).complete();
            Random rand = new Random();
            Message randomMessage = messageList.get(rand.nextInt(messageList.size()));
            TTSMethods.getTtss().generateAndReturnSpeech(randomMessage.getContentRaw());
            audioMethods.loadAndPlayNoConfirmation(commandEvent.getGuild(), "tts.wav");
        } catch (Exception E){
            E.printStackTrace();
        }
    }
}
