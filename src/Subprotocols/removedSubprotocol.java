package Subprotocols;

import java.net.InetAddress;
import java.net.MulticastSocket;

public class removedSubprotocol implements Runnable{

    private String fileId;
    private MulticastSocket socket;
    private InetAddress address;
    private int port;
    private int senderId;

    //is all fucking wrong

    public removedSubprotocol(String fileID, MulticastSocket socket, InetAddress address,int port,int senderId){
        this.fileId = fileID;
        this.senderId = senderId;
        this.socket = socket;
        this.port = port;
        this.address = address;
    }

    @Override
    public void run() {
        //get all files where
    }
}
