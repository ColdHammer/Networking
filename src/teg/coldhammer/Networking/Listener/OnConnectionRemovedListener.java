package teg.coldhammer.Networking.Listener;

import teg.coldhammer.Networking.Server.Connection;

public interface OnConnectionRemovedListener {
    void OnRemove(Object sender, Connection connection);
}
