package teg.coldhammer.Networking.Listener;

import teg.coldhammer.Networking.Server.Connection;

public interface OnNewConnectionListener {
    void OnCreation(Object sender, Connection connection);
}
