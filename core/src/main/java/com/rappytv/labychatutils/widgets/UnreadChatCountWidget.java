package com.rappytv.labychatutils.widgets;

import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidget;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidgetConfig;
import net.labymod.api.client.gui.hud.hudwidget.text.TextLine;
import net.labymod.api.labyconnect.LabyConnectSession;

public class UnreadChatCountWidget extends TextHudWidget<TextHudWidgetConfig> {

    private TextLine line;
    private int unread;

    public UnreadChatCountWidget() {
        super("labychatutils_unread_count");
    }

    @Override
    public void load(TextHudWidgetConfig config) {
        super.load(config);

        unread = 0;
        line = super.createLine(
            Component.translatable("labychatutils.hudWidget.labychatutils_unread_count.key"),
            unread
        );
    }

    @Override
    public void onTick(boolean isEditorContext) {
        line.updateAndFlush(unread);
    }

    @Override
    public boolean isVisibleInGame() {
        LabyConnectSession session = Laby.references().labyConnect().getSession();
        if(session == null) return false;
        if(session.getUnreadCount() != unread) unread = session.getUnreadCount();
        return session.isConnectionEstablished() && unread > 0;
    }
}
