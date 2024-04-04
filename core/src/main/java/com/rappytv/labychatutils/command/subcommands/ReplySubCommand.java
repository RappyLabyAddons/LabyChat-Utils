package com.rappytv.labychatutils.command.subcommands;

import com.rappytv.labychatutils.LabyChatUtilsAddon;
import net.labymod.api.Laby;
import net.labymod.api.client.chat.command.SubCommand;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.labyconnect.LabyConnectSession;
import net.labymod.api.labyconnect.protocol.model.User;
import net.labymod.api.labyconnect.protocol.model.chat.Chat;
import java.util.Arrays;
import java.util.List;

public class ReplySubCommand extends SubCommand {

    public ReplySubCommand() {
        super("reply", "r");
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
        List<Chat> chats = session.getChats();

        if(chats.isEmpty()) {
            displayMessage(LabyChatUtilsAddon.prefix.copy().append(Component.translatable(
                "labychatutils.messages.chats.empty",
                NamedTextColor.RED
            )));
            return true;
        }
        for(Chat chat : chats) {
            boolean containsUser = false;
            for(User user : chat.getParticipants()) {
                if(user.getName().equalsIgnoreCase(arguments[0])) containsUser = true;
            }
            if(!containsUser) continue;
            String message = String.join(
                " ",
                Arrays.copyOfRange(arguments, 1, arguments.length)
            );

            chat.sendMessage(message);
            return true;
        }
        displayMessage(LabyChatUtilsAddon.prefix.copy().append(Component.translatable(
            "labychatutils.messages.notFound",
            NamedTextColor.RED
        )));
        return true;
    }
}
