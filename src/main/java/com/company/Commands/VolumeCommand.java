package com.company.Commands;

import com.company.Audio.AudioMethods;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

public class VolumeCommand extends Command {

    public VolumeCommand() {
        this.name = "Volume";
        this.help = "Sets volume";
        this.aliases = new String[]{"volume"};
    }

    @Override
    protected void execute(CommandEvent commandEvent) {
        String[] command = commandEvent.getMessage().getContentRaw().split(" ",  5);
        AudioMethods audioMethods = AudioMethods.getAudioMethodsHashMap(commandEvent.getGuild().getId());
        audioMethods.setAudioManager(commandEvent.getGuild().getAudioManager());
        audioMethods.setVolume(commandEvent.getTextChannel(), command[2]);
    }
}
