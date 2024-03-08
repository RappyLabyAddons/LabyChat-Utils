package com.rappytv.labychatutils.command.subcommands;

import com.rappytv.labychatutils.LabyChatUtilsAddon;
import net.labymod.api.Laby;
import net.labymod.api.client.chat.command.SubCommand;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.labyconnect.LabyConnectSession;

public class SendSubCommand extends SubCommand {

    public SendSubCommand() {
        super("send");
    }

    @Override
    public boolean execute(String prefix, String[] arguments) {
        if(arguments.length < 1) {
            displayMessage(LabyChatUtilsAddon.prefix.copy().append(Component.translatable(
                "labychatutils.messages.enterPlayerName",
                NamedTextColor.RED
            )));
            return true;
        }
        LabyConnectSession session = Laby.references().labyConnect().getSession();
        if(session == null) {
            displayMessage(LabyChatUtilsAddon.prefix.copy().append(Component.translatable(
                "labychatutils.messages.notConnected",
                NamedTextColor.RED
            )));
            return true;
        }

        session.sendFriendRequest(arguments[0]);
        displayMessage(LabyChatUtilsAddon.prefix.copy().append(Component.translatable(
            "labychatutils.messages.request.sent",
            NamedTextColor.GREEN,
            Component.text(arguments[0])
        )));
        return true;
    }
}
