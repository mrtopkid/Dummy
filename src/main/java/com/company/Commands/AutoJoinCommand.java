package com.company.Commands;

import com.company.Audio.AutoJoin;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

public class AutoJoinCommand extends Command {

    public AutoJoinCommand() {
        this.name = "autojoin";
        this.help = "Allow the bot to auto-join when someone jumps in the VC - 'autojoin true/false'";
        this.aliases = new String[]{"Autojoin"};
    }

    @Override
    protected void execute(CommandEvent commandEvent) {
        String[] command = commandEvent.getMessage().getContentRaw().split(" ",  5);
            switch (command[2]) {
                case "true" -> {
                    AutoJoin.setAutoJoin(true);
                    commandEvent.reply("Bot will auto-join the VC");
                }
                case "false" -> {
                    AutoJoin.setAutoJoin(false);
                    commandEvent.reply("Bot wont auto-join the VC");
                }
                default -> commandEvent.reply("I don't understand, try '/b autojoin true'");
            }
            System.out.println("Good bot trigger");
    }
}
