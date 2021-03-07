package com.company.Commands;

import com.company.Audio.AudioMethods;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

public class JoinCommand extends Command {

    public JoinCommand() {
        this.name = "Join";
        this.help = "Joins the same VC as the caller";
        this.aliases = new String[]{"Join", "Joinme", "joinme"};
    }

    @Override
    protected void execute(CommandEvent commandEvent) {
        AudioMethods audioMethods = AudioMethods.getAudioMethodsHashMap(commandEvent.getGuild().getId());
        audioMethods.setAudioManager(commandEvent.getGuild().getAudioManager());
        audioMethods.joinMeMethod(commandEvent, commandEvent.getTextChannel());
        System.out.println("Joined");
    }
}
