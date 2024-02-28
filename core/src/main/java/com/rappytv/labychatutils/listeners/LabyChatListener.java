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
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class LabyChatListener {

    private static final Map<UUID, TextChatMessage> messages = new HashMap<>();

    @Subscribe
    public void onFriendRequestReceive(LabyConnectIncomingFriendRequestAddEvent event) {
        Laby.references().chatExecutor().displayClientMessage(
            Component.empty()
                    .append(LabyChatUtilsAddon.prefix)
                    .append(Component.translatable(
                        "labychatutils.messages.request.incoming",
                        Component.text(event.request().getName(), NamedTextColor.AQUA)
                    ))
                    .append(Component.translatable("labychatutils.messages.request.accept")
                        .color(NamedTextColor.GREEN)
                        .decorate(TextDecoration.BOLD)
                        .hoverEvent(HoverEvent.showText(
                            Component
                                .translatable("labychatutils.messages.clickable")
                                .color(NamedTextColor.AQUA)
                        ))
                        .clickEvent(ClickEvent.runCommand(
                            "/lcu accept " + event.request().getName()
                        )))
                    .append(Component.text(" • ", NamedTextColor.DARK_GRAY))
                    .append(Component.translatable("labychatutils.messages.request.decline")
                        .color(NamedTextColor.RED)
                        .decorate(TextDecoration.BOLD)
                        .hoverEvent(HoverEvent.showText(
                            Component
                                .translatable("labychatutils.messages.clickable")
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
        boolean isSelf = message.sender() == event.labyConnect().getSession().self();
        UUID uuid = UUID.randomUUID();
        messages.put(uuid, message);
        Laby.references().chatExecutor().displayClientMessage(LabyChatUtilsAddon.chatMessage(
            message.sender().getName(),
            message.getRawMessage(),
            uuid,
            !isSelf
        ));
    }

    public static TextChatMessage getMessage(UUID uuid) {
        return messages.get(uuid);
    }
}
