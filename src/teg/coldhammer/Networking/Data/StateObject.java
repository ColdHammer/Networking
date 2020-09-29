package teg.coldhammer.Networking.Data;

import java.net.Socket;

public class StateObject {
    public final int HEADER_BUFFER_SIZE = 4;
    public final int BUFFER_SIZE = 256;

    public boolean hasData = false;
    public int bufferSize = HEADER_BUFFER_SIZE;
    public Socket socket;
    public Packet packet = null;
    public byte[] buffer;

    public StateObject(Socket socket)
    {
        this.socket = socket;
    }
}
