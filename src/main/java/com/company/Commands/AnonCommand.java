package com.company.Commands;

import com.company.Audio.AudioMethods;
import com.company.Audio.TTSMethods;
import com.company.TextToSpeechService;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.VoiceChannel;

import java.io.File;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class AnonCommand extends Command {

    public AnonCommand() {
        this.name = "Anon";
        this.help = "Allows you to anonymously put messages in gen, PM me 'Anon' followed by your message";
        this.aliases = new String[]{"anon"};
        this.guildOnly = false;
    }

    @Override
    protected void execute(CommandEvent commandEvent) {
        JDA jda = commandEvent.getJDA();
        var guildID = "710599559562264637";
        var channelID = "710599559562264640";
            String[] ttsMessage = commandEvent.getMessage().getContentRaw().split(" ", 3);
            if (commandEvent.getMessage().getAttachments().isEmpty()) {
                jda.getGuildById(guildID).getTextChannelById(channelID).sendMessage(ttsMessage[2]).queue();
                jda.getGuildById(guildID).getTextChannelById(channelID).sendMessage("*This message was sent anonymously*").queue();
            } else {
                var attathments = commandEvent.getMessage().getAttachments();
                CompletableFuture<File> fileFuture = attathments.get(0).downloadToFile();
                File file = null;
                try {
                    file = fileFuture.get();
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
                if (ttsMessage[2] == null) {
                    jda.getGuildById(guildID).getTextChannelById(channelID).sendMessage("").addFile(file).queue();
                    jda.getGuildById(guildID).getTextChannelById(channelID).sendMessage("*This message was sent anonymously*").queue();
                } else {
                    jda.getGuildById(guildID).getTextChannelById(channelID).sendMessage(ttsMessage[2]).addFile(file).queue();
                    jda.getGuildById(guildID).getTextChannelById(channelID).sendMessage("*This message was sent anonymously*").queue();
                }
        }
    }
}
