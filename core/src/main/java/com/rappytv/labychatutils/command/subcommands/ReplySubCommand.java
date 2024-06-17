package com.rappytv.labychatutils.command.subcommands;

import com.rappytv.labychatutils.LabyChatUtilsAddon;
import net.labymod.api.Laby;
import net.labymod.api.client.chat.command.SubCommand;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.labyconnect.LabyConnectSession;
import net.labymod.api.labyconnect.protocol.model.chat.Chat;
import net.labymod.api.labyconnect.protocol.model.chat.ChatMessage;

public class ReplySubCommand extends SubCommand {

    private static Chat recentChat = null;

    public ReplySubCommand() {
        super("reply", "r");
    }

    @Override
    public boolean execute(String prefix, String[] arguments) {
        LabyConnectSession session = Laby.references().labyConnect().getSession();
        if(session == null) {
            displayMessage(LabyChatUtilsAddon.prefix.copy().append(Component.translatable(
                "labychatutils.messages.notConnected",
                NamedTextColor.RED
            )));
            return true;
        }

        if(recentChat == null) {
            displayMessage(LabyChatUtilsAddon.prefix.copy().append(Component.translatable(
                "labychatutils.messages.noRecentChat",
                NamedTextColor.RED
            )));
            return true;
        }
        if(arguments.length < 1) {
            displayMessage(LabyChatUtilsAddon.prefix.copy().append(Component.translatable(
                "labychatutils.messages.enterText",
                NamedTextColor.RED
            )));
            return true;
        }

        recentChat.sendMessage(String.join(" ", arguments));
        for(ChatMessage msg : recentChat.getMessages()) {
            if(!msg.isRead())
                msg.markAsRead();
        }
        return true;
    }

    public static void setRecentChat(Chat chat) {
        recentChat = chat;
    }
}
