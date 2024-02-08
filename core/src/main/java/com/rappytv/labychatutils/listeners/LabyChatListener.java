package com.rappytv.labychatutils.listeners;

import com.rappytv.labychatutils.LabyChatUtilsAddon;
import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.event.ClickEvent;
import net.labymod.api.client.component.event.HoverEvent;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.client.component.format.TextDecoration;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.labymod.labyconnect.session.chat.LabyConnectChatMessageEvent;
import net.labymod.api.event.labymod.labyconnect.session.request.LabyConnectIncomingFriendRequestAddEvent;
import net.labymod.api.labyconnect.protocol.model.chat.TextChatMessage;

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

    @SuppressWarnings("ConstantConditions")
    @Subscribe
    public void onChatReceive(LabyConnectChatMessageEvent event) {
        TextChatMessage message = (TextChatMessage) event.message();
        if(event.labyConnect().getSession() == null) return;
        if(message.sender() == event.labyConnect().getSession().self()) return;
        Laby.references().chatExecutor().displayClientMessage(
            Component.empty()
                .append(Component.text("[", NamedTextColor.DARK_GRAY))
                .append(Component.text("LCU", NamedTextColor.DARK_BLUE)
                    .decorate(TextDecoration.BOLD)
                )
                .append(Component.text("] ", NamedTextColor.DARK_GRAY))
                .append(Component.text(message.sender().getName(), NamedTextColor.AQUA))
                .append(Component.text(" » ", NamedTextColor.DARK_GRAY))
                .append(Component.text(message.getRawMessage(), NamedTextColor.WHITE))
                .append(Component.text(" ✔", NamedTextColor.GREEN)
                    .hoverEvent(HoverEvent.showText(
                        Component.translatable("labychatutils.messages.read")
                            .color(NamedTextColor.GREEN)
                    ))
                    .clickEvent(ClickEvent.runCommand("/lcu read " + "id"))
                )
                .append(Component.text(" ➥", NamedTextColor.BLUE)
                    .hoverEvent(HoverEvent.showText(
                        Component.translatable("labychatutils.messages.reply")
                            .color(NamedTextColor.BLUE)
                    ))
                    .clickEvent(ClickEvent.suggestCommand(
                        "/lcu reply " + message.sender().getName() + " "
                    ))
                )
        );
    }

}
