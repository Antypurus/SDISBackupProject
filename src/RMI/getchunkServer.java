package RMI;

import MessageStubs.getChunkMessageStub;
import Utils.Logging;
import fileDatabase.backedUpFileData;
import fileDatabase.backedUpFileDatabase;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.ArrayList;

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
        backedUpFileDatabase db = backedUpFileDatabase.getDatabase();
        backedUpFileData data = db.getRegisteredBackedUpFileData(filepath);
        if(data!=null) {
            int numberOfChunk = data.getNumberOfChunks();
            ArrayList<Thread>threads = new ArrayList<>();
            for(int chunkNo = 0;chunkNo<numberOfChunk;++chunkNo) {
                getChunkMessageStub stub = new getChunkMessageStub(this.socket, this.address, this.port, data.getFileId(), this.senderID, chunkNo);
                Thread thread = new Thread(stub);
                thread.start();
            }

            FileOutputStream file = null;
            try {
                file = new FileOutputStream("restored/"+filepath);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            for(int i=0;i<threads.size();++i){
                try {
                    threads.get(i).join();
                    FileInputStream read = null;
                    try {
                        read = new FileInputStream("restored/"+data.getFileId()+i);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        Logging.FatalErrorLog("Failed To Restore File Missing Chunks:"+i);
                        return;
                    }
                    byte[] buffer = new byte[65000];
                    try {
                        read.read(buffer);
                    } catch (IOException e) {
                        e.printStackTrace();
                        Logging.FatalErrorLog("Failed To Restore File Failed To Read Chunk");
                        return;
                    }
                    try {
                        file.write(buffer);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
