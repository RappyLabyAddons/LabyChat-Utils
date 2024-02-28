package com.rappytv.labychatutils;

import net.labymod.api.addon.AddonConfig;
import net.labymod.api.client.gui.screen.widget.widgets.input.SwitchWidget.SwitchSetting;
import net.labymod.api.configuration.loader.property.ConfigProperty;
import net.labymod.api.configuration.settings.annotation.SettingRequires;
import net.labymod.api.configuration.settings.annotation.SettingSection;

public class LabyChatUtilsConfig extends AddonConfig {

    @SwitchSetting
    private final ConfigProperty<Boolean> enabled = new ConfigProperty<>(true);
    @SettingSection("requests")
    @SwitchSetting
    private final ConfigProperty<Boolean> showIncomingRequests = new ConfigProperty<>(true);
    @SettingSection("messages")
    @SwitchSetting
    private final ConfigProperty<Boolean> showAnyMessages = new ConfigProperty<>(true);
    @SettingRequires("showAnyMessages")
    @SwitchSetting
    private final ConfigProperty<Boolean> showOwnMessages = new ConfigProperty<>(true);

    @Override
    public ConfigProperty<Boolean> enabled() {
        return enabled;
    }
    public boolean showIncomingRequests() {
        return showIncomingRequests.get();
    }
    public boolean showAnyMessages() {
        return showAnyMessages.get();
    }
    public boolean showOwnMessages() {
        return showOwnMessages.get();
    }
}
