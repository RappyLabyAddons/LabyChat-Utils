package com.rappytv.labychatutils;

import net.labymod.api.addon.LabyAddon;
import net.labymod.api.models.addon.annotation.AddonMain;

@AddonMain
public class LabyChatUtilsAddon extends LabyAddon<LabyChatUtilsConfig> {

    @Override
    protected void enable() {
        registerSettingCategory();
    }

    @Override
    protected Class<? extends LabyChatUtilsConfig> configurationClass() {
        return LabyChatUtilsConfig.class;
    }
}
