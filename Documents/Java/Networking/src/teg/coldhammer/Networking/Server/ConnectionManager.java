package teg.coldhammer.Networking.Server;

import teg.coldhammer.Networking.Listener.OnConnectionRemovedListener;
import teg.coldhammer.Networking.Listener.OnNewConnectionListener;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;

public class ConnectionManager implements Runnable {
    public HashMap<Long, Connection> connections;

    private boolean active;
    private int port;
    private ServerSocket serverSocket;
    private ArrayList<OnNewConnectionListener> onNewConnectionListeners;
    private ArrayList<OnConnectionRemovedListener> onConnectionRemovedListeners;
    public ConnectionManager(int port)
    {
        this.port = port;
        this.onNewConnectionListeners = new ArrayList<>();
        this.onConnectionRemovedListeners = new ArrayList<>();
        connections = new HashMap<>();
        serverSocket = null;
        try{
            serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(1000);
        }catch(IOException ex)
        {
            Log(ex.toString());
        }

    }

    @Override
    public void run()
    {
        while(active) {
            try {
                Socket socket = serverSocket.accept();
                Connection connection = new Connection(socket);
                Log("Connection (ID %1$s) Connected", String.valueOf(connection.getID()));
                connection.Receive();
                for (OnNewConnectionListener onNewConnectionListener: onNewConnectionListeners)
                    onNewConnectionListener.OnCreation(this,connection);
                connections.put(connection.getID(), connection);
            } catch (SocketTimeoutException secEx){}
            catch(IOException ex)
            {

                Log(ex.toString());
            }
            for(Long key : connections.keySet())
            {
                if(!connections.get(key).isConnected())
                {
                    for(OnConnectionRemovedListener onConnectionRemovedListener : onConnectionRemovedListeners)
                        onConnectionRemovedListener.OnRemove(this, connections.get(key));
                    Log("Connection (ID %1$s) Removed",connections.get(key).getID());
                    connections.remove(key);
                }
            }
        }
        try {
            serverSocket.close();
        }catch(IOException ex)
        {
            ex.getStackTrace();
        }
    }

    public void addOnNewConnectionListener(OnNewConnectionListener onNewConnectionListener)
    {
        onNewConnectionListeners.add(onNewConnectionListener);
    }

    public void addOnConnectionRemovedListener(OnConnectionRemovedListener onConnectionRemovedListener)
    {
        onConnectionRemovedListeners.add(onConnectionRemovedListener);
    }

    public void Log(String log, Object... args)
    {
        String text = String.format(log, args);
        System.out.println(String.format("%0$s$ %1$s"));
    }

    public boolean isActive()
    {
        return active;
    }

    public void Start()
    {
        active = true;
        Thread thread = new Thread(this);
        thread.start();
    }

    public void Stop()
    {
        active = false;
    }

    public int getPort()
    {
        return port;
    }
}
