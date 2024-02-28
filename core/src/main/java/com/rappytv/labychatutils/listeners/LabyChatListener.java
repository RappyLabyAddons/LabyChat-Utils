package com.rappytv.labychatutils.listeners;

import com.rappytv.labychatutils.LabyChatUtilsAddon;
import com.rappytv.labychatutils.LabyChatUtilsConfig;
import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.event.ClickEvent;
import net.labymod.api.client.component.event.HoverEvent;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.client.component.format.TextDecoration;
import net.labymod.api.client.gui.icon.Icon;
import net.labymod.api.event.Subscribe;
import net.labymod.api.event.labymod.labyconnect.session.chat.LabyConnectChatMessageEvent;
import net.labymod.api.event.labymod.labyconnect.session.friend.LabyConnectFriendRemoveEvent;
import net.labymod.api.event.labymod.labyconnect.session.request.LabyConnectIncomingFriendRequestAddEvent;
import net.labymod.api.labyconnect.protocol.model.chat.TextChatMessage;
import net.labymod.api.notification.Notification;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class LabyChatListener {

    private static final Map<UUID, TextChatMessage> messages = new HashMap<>();
    private final LabyChatUtilsConfig config;

    public LabyChatListener(LabyChatUtilsAddon addon) {
        this.config = addon.configuration();
    }

    @Subscribe
    public void onFriendRequestReceive(LabyConnectIncomingFriendRequestAddEvent event) {
        if(!config.showIncomingRequests()) return;
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

    @Subscribe
    public void onFriendRemove(LabyConnectFriendRemoveEvent event) {
        if(!config.showRemovedFriends()) return;
        Laby.labyAPI().notificationController().push(
            Notification.builder()
                .title(Component.translatable("labychatutils.messages.remove.title"))
                .text(Component.translatable(
                    "labychatutils.messages.remove.description",
                    Component.text(event.friend().getName())
                ))
                .icon(Icon.head(event.friend().getUniqueId(), true))
                .duration(10000)
                .build()
        );
    }

    @SuppressWarnings("ConstantConditions")
    @Subscribe
    public void onChatReceive(LabyConnectChatMessageEvent event) {
        if(!config.showAnyMessages()) return;
        TextChatMessage message = (TextChatMessage) event.message();
        if(event.labyConnect().getSession() == null) return;
        boolean isSelf = message.sender() == event.labyConnect().getSession().self();
        UUID uuid = UUID.randomUUID();
        messages.put(uuid, message);
        if(isSelf && !config.showOwnMessages()) return;
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
