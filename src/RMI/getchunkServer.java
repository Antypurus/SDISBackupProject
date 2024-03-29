package RMI;

import MessageStubs.getChunkMessageStub;
import Utils.Constants;
import Utils.Logging;
import fileDatabase.backedUpFileData;
import fileDatabase.backedUpFileDatabase;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.io.*;

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
    public void getChunk(String filepath, int chunkNo) throws RemoteException {
        backedUpFileDatabase db = backedUpFileDatabase.getDatabase();
        backedUpFileData data = db.getRegisteredBackedUpFileData(filepath);
        if(data!=null) {
            if (chunkNo < data.getNumberOfChunks()) {
                getChunkMessageStub stub = new getChunkMessageStub(this.socket, this.address, this.port, data.getFileId(), this.senderID, chunkNo);
                Thread thread = new Thread(stub);
                Constants.registry.registerGetchunkThread(stub,data.getFileId(),chunkNo);
                thread.start();
            }
        }
    }

    @Override
    public void restore(String filepath) throws RemoteException{
        backedUpFileDatabase db = backedUpFileDatabase.getDatabase();
        backedUpFileData data = db.getRegisteredBackedUpFileData(filepath);
        if(data!=null) {
            Logging.LogSuccess("Starting Restore For FileID:"+data.getFileId());
            int numberOfChunk = data.getNumberOfChunks();
            Logging.LogSuccess("Has "+numberOfChunk+" chunks");
            ArrayList<Thread>threads = new ArrayList<>();
            for(int chunkNo = 0;chunkNo<numberOfChunk;++chunkNo) {
                File file = new File("restored/"+data.getFileId()+chunkNo);
                if(!file.exists()) {
                    getChunkMessageStub stub = new getChunkMessageStub(this.socket, this.address, this.port, data.getFileId(), this.senderID, chunkNo);
                    Thread thread = new Thread(stub);
                    Constants.registry.registerGetchunkThread(stub, data.getFileId(), chunkNo);
                    threads.add(thread);
                    thread.start();
                }
            }

            FileOutputStream file = null;
            try {
                file = new FileOutputStream("restored/"+filepath);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            for(int i=0;i<numberOfChunk;++i){
                Logging.Log("Adding Chunk "+i+" to file restoration");
                try {
                    if(i<threads.size()) {
                        threads.get(i).join();
                    }
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
