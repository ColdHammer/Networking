package teg.coldhammer.Networking.Data;

import com.sun.istack.internal.NotNull;

import java.nio.ByteBuffer;
import java.util.Arrays;

public class Packet {
    private boolean isValid = false;

    private int bytesLeft = 0;

    private byte packetID;
    private int length;
    private byte[] buffer;
    private boolean alive;

    public Packet(byte[] packetHeader)
    {
        packetID = 0;
        length = -1;
        buffer = null;
    }

    public Packet(byte packetID, byte type, int length, byte[] buffer)
    {
        this.packetID = packetID;
        this.length = length;
        this.buffer = buffer;

        bytesLeft = length;
    }

    public void setBuffer(byte[] buffer)
    {
        this.buffer = buffer;
        bytesLeft -= buffer.length;
    }kak

    public byte[] getBuffer()
    {
        return buffer;
    }

    public byte[] getBufferedData()
    {

    }

    public int getBytesLeft()
    {
        return bytesLeft;
    }

    public byte getPacketID()
    {
        return packetID;
    }

    public int getLength()
    {
        return length;
    }

    public boolean isAlive()
    {
        return alive;
    }

    public byte[] toByteArray()
    {
        byte[] data = new byte[5];
        data[0] = packetID;

        byte[] length = intToByteArray(this.length);
        data = CopyByteArray(length,0, length.length, data, 1);
        return data;
    }

    private static byte[] intToByteArray(int number)
    {
        byte[] bytes = new byte[4];
        for (int i = 0; i < 4; i++) {
            bytes[i] = (byte)(number >>> (i * 8));
        }
        return bytes;
    }


    private static byte[] CopyByteArray(byte[] from, int indexFrom, int indexTo, byte[] to, int indexStart)
    {
        int size = indexTo - indexFrom - 1;
        for(int i = 0; i < to.length; i++)
        {
            if(i > indexStart && size > 0)
            {
                to[i] = from[i - indexStart + indexFrom];
                size--;
            }
        }
        return to;
    }
}
