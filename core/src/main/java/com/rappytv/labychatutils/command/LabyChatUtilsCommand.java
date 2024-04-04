package com.rappytv.labychatutils.command;

import com.rappytv.labychatutils.LabyChatUtilsAddon;
import com.rappytv.labychatutils.command.subcommands.AcceptSubCommand;
import com.rappytv.labychatutils.command.subcommands.DeclineSubCommand;
import com.rappytv.labychatutils.command.subcommands.ReadSubCommand;
import com.rappytv.labychatutils.command.subcommands.ReplySubCommand;
import com.rappytv.labychatutils.command.subcommands.SendSubCommand;
import net.labymod.api.client.chat.command.Command;
import net.labymod.api.client.chat.command.SubCommand;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.NamedTextColor;
import java.util.stream.Collectors;

public class LabyChatUtilsCommand extends Command {

    public LabyChatUtilsCommand() {
        super("lcu");

        withSubCommand(new AcceptSubCommand());
        withSubCommand(new DeclineSubCommand());
        withSubCommand(new ReplySubCommand());
        withSubCommand(new ReadSubCommand());
        withSubCommand(new SendSubCommand());
    }

    @Override
    public boolean execute(String prefix, String[] arguments) {
        String subcommands = getSubCommands()
            .stream()
            .map(SubCommand::getPrefix)
            .collect(Collectors.joining("|"));

        displayMessage(LabyChatUtilsAddon.prefix.copy().append(Component.translatable(
            "labychatutils.messages.usage",
            NamedTextColor.RED,
            Component.text(
                prefix + " <" + subcommands + ">"
            )
        )));
        return true;
    }
}
