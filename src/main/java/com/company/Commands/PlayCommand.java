package com.company.Commands;

import com.company.Audio.AudioMethods;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

public class PlayCommand extends Command {

    public PlayCommand() {
        this.name = "Play";
        this.help = "Plays songs and stuff in VC";
        this.aliases = new String[]{"play"};
    }

    @Override
    protected void execute(CommandEvent commandEvent) {
        String[] command = commandEvent.getMessage().getContentRaw().split(" ",  5);
        AudioMethods audioMethods = AudioMethods.getAudioMethodsHashMap(commandEvent.getGuild().getId());
        audioMethods.setAudioManager(commandEvent.getGuild().getAudioManager());
        audioMethods.loadAndPlay(commandEvent.getTextChannel(), command[2]);
    }
}
