package com.rappytv.labychatutils.listeners;

import com.rappytv.labychatutils.LabyChatUtilsAddon;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.event.ClickEvent;
import net.labymod.api.client.component.event.HoverEvent;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.client.component.format.TextDecoration;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.labymod.labyconnect.session.request.LabyConnectIncomingFriendRequestAddEvent;

public class LabyChatListener {

    @Subscribe
    public void onFriendRequestReceive(LabyConnectIncomingFriendRequestAddEvent event) {
        LabyChatUtilsAddon.msg(
            Component.empty()
                .append(Component.translatable(
                    "labychatutils.messages.request.incoming",
                    Component.text(event.request().getName(), NamedTextColor.AQUA)
                )),
            Component.empty()
                .append(
                    Component.translatable("labychatutils.messages.request.accept")
                        .color(NamedTextColor.GREEN)
                        .decorate(TextDecoration.BOLD)
                        .hoverEvent(HoverEvent.showText(
                            Component.translatable("labychatutils.messages.clickable")
                                .color(NamedTextColor.AQUA)
                        ))
                        .clickEvent(ClickEvent.runCommand(
                            "/lcu accept " + event.request().getName()
                        ))
                ).append(Component.text(" • ", NamedTextColor.DARK_GRAY))
                .append(
                    Component.translatable("labychatutils.messages.request.decline")
                        .color(NamedTextColor.RED)
                        .decorate(TextDecoration.BOLD)
                        .hoverEvent(HoverEvent.showText(
                            Component.translatable("labychatutils.messages.clickable")
                                .color(NamedTextColor.AQUA)
                        ))
                        .clickEvent(ClickEvent.runCommand(
                            "/lcu decline " + event.request().getName()
                        ))
                )
        );
    }

}
