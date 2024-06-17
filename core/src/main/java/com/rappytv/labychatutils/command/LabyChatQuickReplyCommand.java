package com.rappytv.labychatutils.command;

import net.labymod.api.client.chat.command.Command;

public class LabyChatQuickReplyCommand extends Command {

    public LabyChatQuickReplyCommand() {
        super("lcr");
    }

    @Override
    public boolean execute(String prefix, String[] arguments) {
        sendMessage("/lcu reply " + String.join(" ", arguments));
        return true;
    }
}
