package com.rappytv.labychatutils;

import com.rappytv.labychatutils.command.LabyChatUtilsCommand;
import com.rappytv.labychatutils.listeners.LabyChatListener;
import com.rappytv.labychatutils.widgets.UnreadChatCountWidget;
import net.labymod.api.Laby;
import net.labymod.api.addon.LabyAddon;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.event.ClickEvent;
import net.labymod.api.client.component.event.HoverEvent;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.client.component.format.TextDecoration;
import net.labymod.api.models.addon.annotation.AddonMain;
import net.labymod.api.revision.SimpleRevision;
import net.labymod.api.util.version.SemanticVersion;
import java.util.UUID;

@AddonMain
public class LabyChatUtilsAddon extends LabyAddon<LabyChatUtilsConfig> {

    public static final Component prefix = Component.empty()
        .append(Component.text("[", NamedTextColor.DARK_GRAY))
        .append(Component.text("LCU", NamedTextColor.DARK_BLUE)
            .decorate(TextDecoration.BOLD)
        )
        .append(Component.text("] ", NamedTextColor.DARK_GRAY));

    @Override
    protected void preConfigurationLoad() {
        Laby.references().revisionRegistry().register(new SimpleRevision("labychatutils", new SemanticVersion("1.0.1"), "2024-03-08"));
    }

    @Override
    protected void enable() {
        registerSettingCategory();
        registerCommand(new LabyChatUtilsCommand());
        registerListener(new LabyChatListener(this));
        labyAPI().hudWidgetRegistry().register(new UnreadChatCountWidget());
    }

    @Override
    protected Class<? extends LabyChatUtilsConfig> configurationClass() {
        return LabyChatUtilsConfig.class;
    }

    public static Component chatMessage(String name, String message, UUID uuid, boolean elements) {

        Component component = Component
            .empty()
            .append(prefix)
            .append(Component.text(name, NamedTextColor.AQUA))
            .append(Component.text(" » ", NamedTextColor.DARK_GRAY))
            .append(Component.text(message, NamedTextColor.WHITE));

        if(elements) {
            component
                .append(Component
                    .text(" ✔", NamedTextColor.GREEN)
                    .hoverEvent(HoverEvent.showText(
                        Component.translatable("labychatutils.messages.read")
                            .color(NamedTextColor.GREEN)
                    ))
                    .clickEvent(ClickEvent.runCommand("/lcu read " + uuid))
                )
                .append(Component
                    .text(" ➥", NamedTextColor.BLUE)
                    .hoverEvent(HoverEvent.showText(
                        Component.translatable("labychatutils.messages.reply")
                            .color(NamedTextColor.BLUE)
                    ))
                    .clickEvent(ClickEvent.suggestCommand(
                        "/lcu reply " + name + " "
                    ))
                );
        }
        return component;
    }
}
