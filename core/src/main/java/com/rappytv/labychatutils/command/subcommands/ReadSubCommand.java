package com.rappytv.labychatutils.command.subcommands;

import com.rappytv.labychatutils.LabyChatUtilsAddon;
import com.rappytv.labychatutils.listeners.LabyChatListener;
import net.labymod.api.Laby;
import net.labymod.api.client.chat.command.SubCommand;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.labyconnect.protocol.model.chat.TextChatMessage;
import java.util.UUID;

public class ReadSubCommand extends SubCommand {

    public ReadSubCommand() {
        super("read");
    }

    @Override
    public boolean execute(String prefix, String[] arguments) {
        if(Laby.references().labyConnect().getSession() == null) {
            displayMessage(LabyChatUtilsAddon.prefix.copy().append(Component.translatable(
                "labychatutils.messages.notConnected",
                NamedTextColor.RED
            )));
            return true;
        }
        if(arguments.length < 1) {
            displayMessage(LabyChatUtilsAddon.prefix.copy().append(Component.translatable(
                "labychatutils.messages.manual",
                NamedTextColor.RED
            )));
            return true;
        }
        UUID uuid;
        try {
            uuid = UUID.fromString(arguments[0]);
        } catch (IllegalArgumentException e) {
            displayMessage(LabyChatUtilsAddon.prefix.copy().append(Component.translatable(
                "labychatutils.messages.manual",
                NamedTextColor.RED
            )));
            return true;
        }

        TextChatMessage message = LabyChatListener.getMessage(uuid);

        if(message == null) {
            displayMessage(LabyChatUtilsAddon.prefix.copy().append(Component.translatable(
                "labychatutils.messages.manual",
                NamedTextColor.RED
            )));
            return true;
        }
        if(message.isRead()) {
            displayMessage(LabyChatUtilsAddon.prefix.copy().append(Component.translatable(
                "labychatutils.messages.alreadyRead",
                NamedTextColor.RED
            )));
            return true;
        }
        message.markAsRead();
        displayMessage(LabyChatUtilsAddon.prefix.copy().append(Component.translatable("labychatutils.messages.markedRead")));
        return true;
    }
}
