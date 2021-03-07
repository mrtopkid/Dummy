package com.company.Commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

public class FOAASCommand extends Command {

    public FOAASCommand() {
        this.name = "fuckoff";
        this.help = "Fuck off, as a webservice";
        this.aliases = new String[]{"fuckyou", "fuck"};
    }

    @Override
    protected void execute(CommandEvent commandEvent) {


    }
}
