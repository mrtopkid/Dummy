package com.company.Commands;

import com.company.Audio.AudioMethods;
import com.company.Audio.TTSMethods;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.api.entities.VoiceChannel;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class LeaveCommand extends Command {

    List<String> byelist = Arrays.asList("Bye", "oyasuminasai", "in a bit mukka", "byeeeeeeeeeeeeeeeeeeeeeeeee", "goodbye my son");

    public LeaveCommand() {
        this.name = "Leave";
        this.help = "Leaves the VC";
        this.aliases = new String[]{"leave", "quit", "bounce"};
    }

    @Override
    protected void execute(CommandEvent commandEvent) {
        Random rand = new Random();
        String kek = byelist.get(rand.nextInt(byelist.size()));
        VoiceChannel currentChannel = commandEvent.getGuild().getSelfMember().getVoiceState().getChannel();
        if (currentChannel == null) {
            commandEvent.getTextChannel().sendMessage("I am not in a voice channel retard").queue();
            return;
        }
        AudioMethods audioMethods = AudioMethods.getAudioMethodsHashMap(commandEvent.getGuild().getId());
        if (TTSMethods.getTtss().generateAndReturnSpeech(kek)){
            System.out.println("wack");
        }

        audioMethods.stop(commandEvent.getGuild());
        audioMethods.loadAndPlayNoConfirmation(commandEvent.getGuild(), "tts.wav");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        commandEvent.getGuild().getAudioManager().closeAudioConnection();
        System.out.println("Left");
    }
}
