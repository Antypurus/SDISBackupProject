package RMI;

import Subprotocols.putchunkSubprotocol;
import Utils.threadRegistry;

import java.io.IOException;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class backupServer implements backupRemoteInterface,Runnable {

    private MulticastSocket socket;
    private InetAddress     address;
    private int             port;
    private threadRegistry  registry;
    private int             senderID;
    private String          backup;
    private int             repDeg;

    @Override
    public void backup(String filepath,int replicationDegree) {
        this.backup = filepath;
        this.repDeg = replicationDegree;
        Thread thread = new Thread(this);
        thread.start();
        return;
    }

    @Override
    public void run() {
        try {
            putchunkSubprotocol put = new putchunkSubprotocol(this.senderID,this.backup,this.registry,this.socket,this.address,this.port,this.repDeg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public backupServer(int senderID,threadRegistry registry,MulticastSocket socket,InetAddress address,int port){
        this.senderID = senderID;
        this.registry = registry;
        this.socket = socket;
        this.address = address;
        this.port = port;
    }

}
