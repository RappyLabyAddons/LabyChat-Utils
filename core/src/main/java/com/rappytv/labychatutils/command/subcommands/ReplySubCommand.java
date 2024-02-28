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
import java.util.Optional;

public class ReplySubCommand extends SubCommand {

    public ReplySubCommand() {
        super("reply", "r");
    }

    @Override
    public boolean execute(String prefix, String[] arguments) {
        if(arguments.length < 1) {
            LabyChatUtilsAddon.msg(Component.translatable(
                "labychatutils.messages.enterPlayerName",
                NamedTextColor.RED
            ));
            return true;
        }
        LabyConnectSession session = Laby.references().labyConnect().getSession();
        if(session == null) {
            LabyChatUtilsAddon.msg(Component.translatable(
                "labychatutils.messages.notConnected",
                NamedTextColor.RED
            ));
            return true;
        }
        List<Chat> chats = session.getChats();

        if(chats.isEmpty()) {
            LabyChatUtilsAddon.msg(Component.translatable(
                "labychatutils.messages.chats.empty",
                NamedTextColor.RED
            ));
            return true;
        }
        Optional<Chat> chat = chats
            .stream()
            .filter((req) -> {
                List<User> users = req.getParticipants();
                return users
                    .stream()
                    .anyMatch((usr) ->
                        usr.getName().equalsIgnoreCase(arguments[0])
                    );
            })
            .findFirst();

        if(chat.isEmpty()) {
            LabyChatUtilsAddon.msg(Component.translatable(
                "labychatutils.messages.request.notFound",
                NamedTextColor.RED
            ));
            return true;
        }

        String message = String.join(
            " ",
            Arrays.copyOfRange(arguments, 1, arguments.length)
        );

        chat.get().sendMessage(message);
        return true;
    }
}
