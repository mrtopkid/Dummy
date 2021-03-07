package com.company.Commands;

import com.company.GoodBotBadBot.BotScore;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

public class BadBotCommand extends Command {

    public BadBotCommand() {
        this.name = "Bad bot";
        this.help = "Decent bot";
        this.aliases = new String[]{"Bad", "bad"};
    }

    @Override
    protected void execute(CommandEvent commandEvent) {
        String[] command = commandEvent.getMessage().getContentRaw().split(" ",  5);
        BotScore.badBotMethod(command, commandEvent.getTextChannel());
        System.out.println("Bad bot trigger");
    }
}
