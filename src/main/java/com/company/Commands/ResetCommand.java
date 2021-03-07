package com.company.Commands;

import com.company.Audio.AudioMethods;
import com.company.Audio.TTSMethods;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.api.entities.VoiceChannel;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

public class ResetCommand extends Command {

    public ResetCommand() {
        this.name = "Reset";
        this.help = "Resets the flem counter";
        this.aliases = new String[]{"reset"};
    }

    @Override
    protected void execute(CommandEvent commandEvent) {
        if (commandEvent.getChannel().getId().equals("805854297443139614")) {
            System.out.println("Resetting");
            setFlemTally();
            commandEvent.getChannel().sendMessage("Lmao").queue();
            commandEvent.getChannel().sendMessage("It has been 0 days since flem was last kicked").queue();
        } else {
            commandEvent.getChannel().sendMessage("Gotta reset in the #days-since-last-flem-kick channel").queue();
        }
    }
    private void setFlemTally(){
        try (OutputStream output = new FileOutputStream("flem.properties")) {

            Properties prop = new Properties();

            prop.setProperty("tally", String.valueOf(0));

            prop.store(output, null);

            System.out.println(prop);
        } catch (IOException io) {
            io.printStackTrace();
        }
    }
}
