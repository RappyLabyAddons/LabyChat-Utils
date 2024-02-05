package com.rappytv.labychatutils.command.subcommands;

import com.rappytv.labychatutils.LabyChatUtilsAddon;
import net.labymod.api.Laby;
import net.labymod.api.client.chat.command.SubCommand;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.event.HoverEvent;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.client.component.format.TextDecoration;
import net.labymod.api.labyconnect.LabyConnectSession;
import net.labymod.api.labyconnect.protocol.model.request.OutgoingFriendRequest;
import java.util.ArrayList;
import java.util.List;

public class ClearSubCommand extends SubCommand {

    public ClearSubCommand() {
        super("clear");
    }

    @Override
    public boolean execute(String prefix, String[] arguments) {
        LabyConnectSession session = Laby.references().labyConnect().getSession();
        if(session == null) {
            LabyChatUtilsAddon.msg(Component.translatable(
                "labychatutils.messages.notConnected",
                NamedTextColor.RED
            ));
            return true;
        }
        List<OutgoingFriendRequest> requests = session.getOutgoingRequests();

        if(requests.isEmpty()) {
            LabyChatUtilsAddon.msg(Component.translatable(
                "labychatutils.messages.request.outEmpty",
                NamedTextColor.RED
            ));
            return true;
        }

        List<String> names = new ArrayList<>();
        for(OutgoingFriendRequest request : requests) {
            request.withdraw();
            names.add(request.getName());
        }

        LabyChatUtilsAddon.msg(Component.translatable(
            "labychatutils.messages.request.cleared",
            Component.text(names.size())
                .decorate(TextDecoration.UNDERLINED)
                .hoverEvent(HoverEvent.showText(Component.text(String.join(", ", names))))
        ).color(NamedTextColor.GREEN));
        return true;
    }
}
