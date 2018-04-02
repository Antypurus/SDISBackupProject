package RMI;

import MessageStubs.getChunkMessageStub;
import fileDatabase.backedUpFileData;
import fileDatabase.backedUpFileDatabase;

import java.net.InetAddress;
import java.net.MulticastSocket;

public class getchunkServer implements  getchunkRemoteInterface{

    MulticastSocket socket;
    InetAddress address;
    int port;
    int senderID;

    public getchunkServer(MulticastSocket socket, InetAddress address,int port,int senderID){
        this.senderID = senderID;
        this.socket = socket;
        this.address = address;
        this.port = port;
    }

    @Override
    public void getChunk(String filepath, int chunkNo) {
        backedUpFileDatabase db = backedUpFileDatabase.getDatabase();
        backedUpFileData data = db.getRegisteredBackedUpFileData(filepath);
        if(data!=null) {
            if (chunkNo < data.getNumberOfChunks()) {
                getChunkMessageStub stub = new getChunkMessageStub(this.socket, this.address, this.port, data.getFileId(), this.senderID, chunkNo);
                Thread thread = new Thread(stub);
                thread.start();
            }
        }
    }

    @Override
    public void restore(String filepath) {

    }
}
