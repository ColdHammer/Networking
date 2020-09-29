package teg.coldhammer.Networking.Server;

import teg.coldhammer.Networking.Data.Packet;
import teg.coldhammer.Networking.Data.StateObject;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class Connection {

    private Socket socket;

    private long ID;

    public Connection(Socket socket)
    {
        this.socket = socket;
    }

    public void Receive()
    {
        try{
            while (socket.isConnected())
            {
                StateObject stateObject = new StateObject(socket);

                ReceiveCallBack(stateObject);
            }
        }
        catch(IOException e)
        {
            Log("There was an issue with the Input Stream. Debug: Connection{ public void Receive() }. Error: %0$s", e.toString());
        }
    }

    public void ReceiveCallBack(StateObject stateObject) throws IOException
    {

    }

    public void Send(Packet packet)
    {
        try {
            StateObject stateObject = new StateObject(socket);
            while(packet.isAlive())
            {
                SendCallBack(stateObject);
            }
        }catch(IOException ioE)
        {
            Log("There was an issue with the Output stream. Debug: public void Send(Packet packet). Error: %0$s", ioE.toString());
        }

    }

    public void SendCallBack(StateObject stateObject) throws IOException
    {
        Packet packet = stateObject.packet;
        Socket socket = stateObject.socket;
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write(packet.getData());
    }

    public void Log(String message, Object... args)
    {
        String text = String.format(message, args);
        System.out.println(String.format("Connection (ID %0$s)$ %1$s", getID(), text));
    }

    public long getID()
    {
        return ID;
    }

    public boolean isConnected()
    {
        return socket.isConnected();
    }
}
