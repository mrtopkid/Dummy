package com.company.Commands;

import com.company.Audio.AudioMethods;
import com.company.Audio.TTSMethods;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.api.entities.VoiceChannel;

public class SayCommand extends Command {

    public SayCommand() {
        this.name = "Say";
        this.help = "Say some dumb shit in VC";
        this.aliases = new String[]{"say"};
    }

    @Override
    protected void execute(CommandEvent commandEvent) {
        try {
            String[] ttsMessage = commandEvent.getMessage().getContentRaw().split(" ", 3);
            AudioMethods audioMethods = AudioMethods.getAudioMethodsHashMap(commandEvent.getGuild().getId());
            audioMethods.setAudioManager(commandEvent.getGuild().getAudioManager());
            VoiceChannel currentChannel = commandEvent.getGuild().getSelfMember().getVoiceState().getChannel();
            TTSMethods.retardTTS(commandEvent, ttsMessage, audioMethods, commandEvent.getTextChannel(), currentChannel);
        } catch (Exception E){
            E.printStackTrace();
        }
    }
}
