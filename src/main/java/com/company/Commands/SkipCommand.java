package com.company.Commands;

import com.company.Audio.AudioMethods;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

public class SkipCommand extends Command {

    public SkipCommand() {
        this.name = "Skip";
        this.help = "Skip songs";
        this.aliases = new String[]{"skip"};
    }

    @Override
    protected void execute(CommandEvent commandEvent) {
        AudioMethods audioMethods = AudioMethods.getAudioMethodsHashMap(commandEvent.getGuild().getId());
        audioMethods.setAudioManager(commandEvent.getGuild().getAudioManager());
        audioMethods.skipTrack(commandEvent.getTextChannel());
    }
}
