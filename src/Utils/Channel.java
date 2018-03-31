package Utils;

import java.net.InetAddress;
import java.net.MulticastSocket;

public class Channel {
    public MulticastSocket socket = null;
    public InetAddress address =null;
    public int port;

    public Channel(MulticastSocket socket,InetAddress address,int port){
        this.socket = socket;
        this.address = address;
        this.port = port;
    }
}
