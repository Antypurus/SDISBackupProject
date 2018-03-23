package initiatorPeerSubprotocols;

import FileHandler.FileIDCalculator;
import FileHandler.FileStreamer;
import MessageStubs.putchunkStub;
import Utils.threadRegistry;

import java.io.IOException;
import java.net.MulticastSocket;

public class putchunkSubprotocol {

    private final int CHUNK_BODY_SIZE = 64000;

    private MulticastSocket socket;
    private String          startChunk;
    private String          filepath;
    private int             senderID;
    private String          fileID;
    private threadRegistry  registry;

    /**
     *
     * @param senderID
     * @param filepath
     * @param registry
     * @param socket
     * @throws IOException
     */
    public putchunkSubprotocol(int senderID, String filepath, threadRegistry registry, MulticastSocket socket) throws IOException {
        this.filepath = filepath;
        this.senderID = senderID;
        this.registry = registry;
        this.socket = socket;

        FileStreamer stream = new FileStreamer(this.CHUNK_BODY_SIZE,filepath);
        this.startChunk = stream.read();

        FileIDCalculator cal = new FileIDCalculator(this.startChunk,filepath,senderID);
        this.fileID = cal.calculateFileID();

        putchunkStub stub = new putchunkStub(this.socket,this.senderID,0,this.fileID,this.startChunk,1);
        this.registry.addThread(stub,0,this.fileID);
        Thread trd = new Thread(stub);
        stub.thread = trd;
        trd.run();

        String read="";
        boolean stop = false;
        int ctr = 0;
        while(!stop){
            read = stream.read();
            if(read==null){
                stop=true;
                continue;
            }
            ctr++;
            putchunkStub stube = new putchunkStub(this.socket,this.senderID,ctr,this.fileID,read,1);
            this.registry.addThread(stube,ctr,this.fileID);
            Thread trdd = new Thread(stube);
            stub.thread = trdd;
            trdd.run();
        }
    }

}
