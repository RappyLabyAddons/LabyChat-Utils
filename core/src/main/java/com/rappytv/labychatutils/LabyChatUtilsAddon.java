package com.rappytv.labychatutils;

import com.rappytv.labychatutils.command.LabyChatUtilsCommand;
import com.rappytv.labychatutils.listeners.LabyChatListener;
import net.labymod.api.Laby;
import net.labymod.api.addon.LabyAddon;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.component.format.NamedTextColor;
import net.labymod.api.client.component.format.TextDecoration;
import net.labymod.api.models.addon.annotation.AddonMain;

@AddonMain
public class LabyChatUtilsAddon extends LabyAddon<LabyChatUtilsConfig> {

    private static final Component prefix = Component.empty()
        .append(
            Component
            .text("LCU")
            .color(NamedTextColor.DARK_BLUE)
            .decorate(TextDecoration.BOLD)
        )
        .append(Component.text(" » ", NamedTextColor.DARK_GRAY));

    @Override
    protected void enable() {
        registerSettingCategory();
    }

    @Override
    protected Class<? extends LabyChatUtilsConfig> configurationClass() {
        return LabyChatUtilsConfig.class;
    }

    public static void msg(Component... lines) {
        Component component = Component.empty()
            .color(NamedTextColor.WHITE)
            .append(Component.text("»\n", NamedTextColor.DARK_GRAY));

        for(Component line : lines) {
            component.append(prefix).append(line);
        }

        component.append(Component.text("\n»", NamedTextColor.DARK_GRAY));
        Laby.references().chatExecutor().displayClientMessage(component);
    }
}
