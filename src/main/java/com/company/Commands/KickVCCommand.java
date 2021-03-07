package com.company.Commands;

import com.company.Audio.AudioMethods;
import com.company.Audio.TTSMethods;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.VoiceChannel;

import java.util.List;

public class KickVCCommand extends Command {

    public KickVCCommand() {
        this.name = "KickVC";
        this.help = "When a cunt's being annoying";
        this.ownerCommand = true;
        this.aliases = new String[]{"Kick", "kick"};
    }

    @Override
    protected void execute(CommandEvent commandEvent) {
        try {
            AudioMethods audioMethods = AudioMethods.getAudioMethodsHashMap(commandEvent.getGuild().getId());
            audioMethods.setAudioManager(commandEvent.getGuild().getAudioManager());
            List<Member> members = commandEvent.getMessage().getMentionedMembers();
            for (Member member: members) {
                System.out.println(member.getUser().getName());
            }
            commandEvent.getGuild().kickVoiceMember(members.get(1)).complete();
            TTSMethods.getTtss().generateAndReturnSpeech("Thot removed");
            audioMethods.loadAndPlayNoConfirmation(commandEvent.getGuild(), "tts.wav");
        } catch (Exception E){
            E.printStackTrace();
        }
    }
}
