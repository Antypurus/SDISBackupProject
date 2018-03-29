package MessageStubs;

import java.net.InetAddress;
import java.net.MulticastSocket;

public class deleteMessageStub {

    private MulticastSocket socket;
    private InetAddress address;
    private int port;
    private int senderId;
    private String fileId;

    public  deleteMessageStub(MulticastSocket socket, InetAddress address,int port,int senderId,String fileId){
        this.senderId = senderId;
        this.socket = socket;
        this.address = address;
        this.port = port;
        this.fileId = fileId;
    }

}
