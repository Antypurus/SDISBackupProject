package RMI;

import MessageStubs.deleteMessageStub;

import java.net.InetAddress;
import java.net.MulticastSocket;

public class deleteServer implements deleteRemoteInterface {

    private MulticastSocket socket;
    private InetAddress address;
    private int port;
    private int senderID;
    private String delete;

    public deleteServer(MulticastSocket socket,InetAddress address,int port,int senderID){
        this.senderID = senderID;
        this.socket = socket;
        this.address = address;
        this.port = port;
    }

    @Override
    public void delete(String filepath) {
        this.delete = filepath;
        deleteMessageStub stub = new deleteMessageStub(this.socket,this.address,this.port,this.senderID,filepath);
        Thread thread = new Thread(stub);
        thread.start();
    }
}
