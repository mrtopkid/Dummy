package com.company.Commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.channel.voice.GenericVoiceChannelEvent;
import org.jetbrains.annotations.NotNull;

public class ThiccCommand extends Command {

    private final EventWaiter waiter;

    public ThiccCommand(EventWaiter waiter) {
        this.waiter = waiter;
        this.name = "thicc";
        this.help = "test command";
        this.aliases = new String[]{"thicc"};
    }


    @Override
    protected void execute(CommandEvent commandEvent) {
        commandEvent.getChannel().sendMessage("Suck my ass").queue();
    }
}
