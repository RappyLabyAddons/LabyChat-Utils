package com.rappytv.labychatutils.widgets;

import net.labymod.api.Laby;
import net.labymod.api.client.component.Component;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidget;
import net.labymod.api.client.gui.hud.hudwidget.text.TextHudWidgetConfig;
import net.labymod.api.client.gui.hud.hudwidget.text.TextLine;
import net.labymod.api.labyconnect.LabyConnectSession;

public class IncomingFriendRequestCountWidget extends TextHudWidget<TextHudWidgetConfig> {

    private TextLine line;
    private int requests;

    public IncomingFriendRequestCountWidget() {
        super("labychatutils_incoming_friend_request_count");
    }

    @Override
    public void load(TextHudWidgetConfig config) {
        super.load(config);

        requests = 0;
        line = super.createLine(
            Component.translatable("labychatutils.hudWidget.labychatutils_incoming_friend_request_count.key"),
            requests
        );
    }

    @Override
    public void onTick(boolean isEditorContext) {
        line.updateAndFlush(requests);
    }

    @Override
    public boolean isVisibleInGame() {
        LabyConnectSession session = Laby.references().labyConnect().getSession();
        if(session == null) return false;
        if(session.getIncomingRequests().size() != requests)
            requests = session.getIncomingRequests().size();
        return session.isConnectionEstablished() && requests > 0;
    }
}
