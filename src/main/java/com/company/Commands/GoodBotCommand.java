package com.company.Commands;

import com.company.Audio.AudioMethods;
import com.company.GoodBotBadBot.BotScore;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

public class GoodBotCommand extends Command {

    public GoodBotCommand() {
        this.name = "Good bot";
        this.help = "Decent bot";
        this.aliases = new String[]{"Good", "good"};
    }

    @Override
    protected void execute(CommandEvent commandEvent) {
        String[] command = commandEvent.getMessage().getContentRaw().split(" ",  5);
        BotScore.goodBotMethod(command, commandEvent.getTextChannel());
        System.out.println("Good bot trigger");
    }
}
