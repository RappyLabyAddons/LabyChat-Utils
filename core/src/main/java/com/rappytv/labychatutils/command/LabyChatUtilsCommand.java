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
import java.util.ArrayList;
import java.util.List;

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
        List<String> subcommands = new ArrayList<>();
        for(SubCommand subCommand : getSubCommands())
            if(!subCommand.getPrefix().equals("read")) subcommands.add(subCommand.getPrefix());

        displayMessage(LabyChatUtilsAddon.prefix.copy().append(Component.translatable(
            "labychatutils.messages.usage",
            NamedTextColor.RED,
            Component.text(
                prefix + " <" + String.join("|", subcommands) + ">"
            )
        )));
        return true;
    }
}
