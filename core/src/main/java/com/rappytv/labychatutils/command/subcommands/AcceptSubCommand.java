package com.rappytv.labychatutils.command.subcommands;

import com.rappytv.labychatutils.LabyChatUtilsAddon;
import net.labymod.api.Laby;
import net.labymod.api.client.chat.command.SubCommand;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.labyconnect.LabyConnectSession;
import net.labymod.api.labyconnect.protocol.model.request.IncomingFriendRequest;
import java.util.List;

public class AcceptSubCommand extends SubCommand {

    public AcceptSubCommand() {
        super("accept");
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
        List<IncomingFriendRequest> requests = session.getIncomingRequests();

        if(requests.isEmpty()) {
            displayMessage(LabyChatUtilsAddon.prefix.copy().append(Component.translatable(
                "labychatutils.messages.request.empty",
                NamedTextColor.RED
            )));
            return true;
        }
        for(IncomingFriendRequest request : requests) {
            if(!request.getName().equalsIgnoreCase(arguments[0])) continue;
            request.accept();
            displayMessage(LabyChatUtilsAddon.prefix.copy().append(Component.translatable(
                "labychatutils.messages.request.accepted",
                Component.text(request.getName(), NamedTextColor.AQUA)
            ).color(NamedTextColor.GREEN)));
            return true;
        }
        displayMessage(LabyChatUtilsAddon.prefix.copy().append(Component.translatable(
            "labychatutils.messages.request.notFound",
            NamedTextColor.RED
        )));
        return true;
    }
}
