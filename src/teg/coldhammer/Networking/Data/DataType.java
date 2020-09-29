package teg.coldhammer.Networking.Data;

import teg.coldhammer.Networking.Listener.OnDataReceivedListener;

import java.util.ArrayList;
import java.util.HashMap;

public class DataType {
    public static HashMap<Byte, DataType> typeDictionary = new HashMap<Byte, DataType>();

    protected ArrayList<OnDataReceivedListener> onDataReceivedListeners;
    protected byte id;

    public DataType(byte id)
    {
        onDataReceivedListeners = new ArrayList<>();
        this.id = id;
        typeDictionary.put(id, this);
    }

    public byte getId()
    {
        return id;
    }

    public void addOnDataReceivedListeners(OnDataReceivedListener onDataReceivedListener)
    {
        if(onDataReceivedListener!=null)
            onDataReceivedListeners.add(onDataReceivedListener);
    }

    public void putData(byte[] data)
    {
        for(OnDataReceivedListener onDataReceivedListener : onDataReceivedListeners)
        {
            onDataReceivedListener.Do(data);
        }
    }

}
