package Subprotocols;

import MessageStubs.getChunkMessageStub;
import Utils.threadRegistry;

import java.net.InetAddress;
import java.net.MulticastSocket;

public class getChunkSubprotocol{

    private MulticastSocket socket;
    private InetAddress address;
    private int port;
    private int senderId;
    private String fileId;
    private int chunkNo;
    private threadRegistry registry;

    public getChunkSubprotocol(MulticastSocket socket, InetAddress address, int port, int senderId, String fileId, int chunkNo, threadRegistry registry){
        this.socket = socket;
        this.address = address;
        this.port = port;
        this.senderId = senderId;
        this.fileId = fileId;
        this.chunkNo = chunkNo;
        this.registry = registry;

        getChunkMessageStub stub = new getChunkMessageStub(this.socket,this.address,this.port,this.fileId,this.senderId,this.chunkNo);
        Thread thread = new Thread(stub);
        this.registry.registerGetchunkThread(stub,this.fileId,this.chunkNo);
        thread.start();
    }
}
