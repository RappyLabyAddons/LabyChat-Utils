package com.rappytv.labychatutils.command;

import com.rappytv.labychatutils.command.subcommands.AcceptSubCommand;
import com.rappytv.labychatutils.command.subcommands.DeclineSubCommand;
import net.labymod.api.client.chat.command.Command;

public class LabyChatUtilsCommand extends Command {

    public LabyChatUtilsCommand() {
        super("lcu");

        withSubCommand(new AcceptSubCommand());
        withSubCommand(new DeclineSubCommand());
    }

    @Override
    public boolean execute(String prefix, String[] arguments) {
        displayMessage(String.join(", ", arguments));
        return true;
    }
}
