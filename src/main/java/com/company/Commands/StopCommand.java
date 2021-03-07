package com.company.Commands;

import com.company.Audio.AudioMethods;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

public class StopCommand extends Command {

    public StopCommand() {
        this.name = "Stop";
        this.help = "Stops anything playing";
        this.aliases = new String[]{"stop", "shutup"};
    }

    @Override
    protected void execute(CommandEvent commandEvent) {
        AudioMethods audioMethods = AudioMethods.getAudioMethodsHashMap(commandEvent.getGuild().getId());
        audioMethods.setAudioManager(commandEvent.getGuild().getAudioManager());
        audioMethods.stop(commandEvent.getGuild());
    }
}
