package teg.coldhammer.Networking.Data;

import java.net.Socket;

public class StateObject {
    public final int BUFFER_SIZE = 256;

    public Socket socket;
    public Packet packet = null;

    public StateObject(Socket socket)
    {
        this.socket = socket;
    }
}
