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
    private final LabyConnectSession session;

    public UnreadChatCountWidget() {
        super("labychatutils_unread_count");
        session = Laby.references().labyConnect().getSession();
    }

    @Override
    public void load(TextHudWidgetConfig config) {
        super.load(config);

        unread = 0;
        line = super.createLine(
            Component.translatable("labychatutils.widgets.unread"),
            unread
        );
    }

    @Override
    public void onTick(boolean isEditorContext) {
        if(session == null) return;
        if(session.getUnreadCount() == unread) return;

        line.updateAndFlush(unread);
    }

    @Override
    public boolean isVisibleInGame() {
        return session.isConnectionEstablished() && unread > 0;
    }
}
