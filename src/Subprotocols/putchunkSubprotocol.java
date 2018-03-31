package Subprotocols;

import FileHandler.FileIDCalculator;
import FileHandler.FileStreamer;
import MessageStubs.putchunkStub;
import Utils.Constants;
import Utils.threadRegistry;
import fileDatabase.backedUpFileData;
import fileDatabase.backedUpFileDatabase;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.sql.Connection;
import java.sql.DriverManager;

public class putchunkSubprotocol {

    private final int CHUNK_BODY_SIZE = 64000;

    private MulticastSocket socket;
    private String          startChunk;
    private String          filepath;
    private int             senderID;
    private String          fileID;
    private threadRegistry  registry;
    private InetAddress     address;
    private int             port;

    /**
     *
     * @param senderID
     * @param filepath
     * @param registry
     * @param socket
     * @throws IOException
     */
    public putchunkSubprotocol(int senderID, String filepath, threadRegistry registry, MulticastSocket socket, InetAddress addr,int port) throws IOException {
        this.filepath   = filepath;
        this.senderID   = senderID;
        this.registry   = registry;
        this.socket     = socket;
        this.address    = addr;
        this.port       = port;

        FileStreamer stream = new FileStreamer(this.CHUNK_BODY_SIZE,filepath);
        this.startChunk = stream.read();

        FileIDCalculator cal = new FileIDCalculator(this.startChunk,filepath,senderID);
        this.fileID = cal.calculateFileID();

        putchunkStub stub = new putchunkStub(this.socket,this.senderID,0,this.fileID,this.startChunk,1);
        stub.setAddressAndPort(this.address,this.port);
        this.registry.registerPutchunkThread(stub,0,this.fileID);
        Thread trd = new Thread(stub);
        stub.thread = trd;
        trd.start();

        String read="";
        boolean stop = false;
        int ctr = 0;
        while(!stop){
            read = stream.read();
            if(read==null){
                stop=true;
                break;
            }
            ctr++;
            putchunkStub stube = new putchunkStub(this.socket,this.senderID,ctr,this.fileID,read,1);
            stube.setAddressAndPort(this.address,this.port);
            this.registry.registerPutchunkThread(stube,ctr,this.fileID);
            Thread trdd = new Thread(stube);
            stube.thread = trdd;
            trdd.start();
        }

        backedUpFileDatabase db = backedUpFileDatabase.getDatabase();
        db.registerBackeUpFile(fileID,new backedUpFileData(fileID,filepath,fileID,ctr+1));
        db.save();
    }

}
