package com.rappytv.labychatutils.command;

import net.labymod.api.client.chat.command.Command;

public class LabyChatUtilsCommand extends Command {

    public LabyChatUtilsCommand() {
        super("lcu");
    }

    @Override
    public boolean execute(String prefix, String[] arguments) {
        displayMessage(String.join(", ", arguments));
        return true;
    }
}
