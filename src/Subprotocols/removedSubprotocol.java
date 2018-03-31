package Subprotocols;

import MessageHandler.Message;
import MessageStubs.putchunkStub;
import Utils.threadRegistry;
import fileDatabase.fileBackUpData;
import fileDatabase.fileBackUpDatabase;
import fileDatabase.fileReplicationData;
import fileDatabase.fileReplicationDatabase;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class removedSubprotocol implements Runnable{

    private MulticastSocket socket;
    private InetAddress address;
    private int port;
    private int senderId;
    private Message message;
    private threadRegistry registry;

    //is all fucking wrong

    public removedSubprotocol(Message msg, MulticastSocket socket, InetAddress address, int port, int senderId, threadRegistry registry){
        this.senderId = senderId;
        this.socket = socket;
        this.port = port;
        this.address = address;
        this.message = msg;
        this.registry = registry;
    }

    @Override
    public void run() {
        fileReplicationDatabase db = fileReplicationDatabase.getDatabase();
        fileReplicationData data = db.getRegisteredFileReplicationData(this.message.getFileID(),this.message.getChunkNO());
        data.removePeerWhoStored(this.message.getSenderID());

        try {
            db.save();
        } catch (IOException e) {
            e.printStackTrace();
        }

        int curr =  data.getActualReplicationDegree();
        int wanted = data.getIntendedReplicationDegree();

        if(curr<wanted) {
            int diff = wanted - curr;
            fileBackUpDatabase dba = fileBackUpDatabase.getFileBackupDatabase();
            fileBackUpData datab = dba.getRegisteredFileBackupData(this.message.getFileID(),this.message.getChunkNO());

            if (datab != null) {
                FileInputStream file = null;
                try {
                    file = new FileInputStream(datab.getFilepath());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                byte[] buffer = new byte[64000];
                try {
                    file.read(buffer);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String body = new String(buffer);

                //should wait but fuck it
                putchunkStub stub = new putchunkStub(this.socket, this.senderId, this.message.getChunkNO(), this.message.getFileID(),body, wanted);
                this.registry.registerPutchunkThread(stub,this.message.getChunkNO(),this.message.getFileID());
                Thread thread = new Thread(stub);
                thread.start();

            }
        }
    }
}
