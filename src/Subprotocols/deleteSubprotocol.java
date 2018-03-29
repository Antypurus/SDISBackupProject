package Subprotocols;

import MessageHandler.Message;
import fileDatabase.fileBackUpData;
import fileDatabase.fileBackUpDatabase;
import fileDatabase.fileReplicationData;
import fileDatabase.fileReplicationDatabase;

import java.io.IOException;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.nio.file.Files;
import java.nio.file.Paths;

public class deleteSubprotocol {

    private Message message;
    private MulticastSocket socket;
    private InetAddress address;
    private int port;
    private int senderId;

    public deleteSubprotocol(Message message, MulticastSocket socket, InetAddress address, int port, int senderId){
        this.senderId = senderId;
        this.message = message;
        this.socket = socket;
        this.address = address;
        this.port = port;
    }

    public void run(){
        fileReplicationDatabase db = fileReplicationDatabase.getDatabase("fileReplicationDatabase.db");
        fileReplicationData data =  db.getRegisteredFileReplicationData(this.message.getFileID());

        fileBackUpDatabase dba = fileBackUpDatabase.getFileBackupDatabase("fileBackUpDatabase.db");
        fileBackUpData datab = null;
        int ctr = 1;
        for(int i=0;i<data.storedChunks.size();++i){

            datab = dba.getRegisteredFileBackupData((this.message.getFileID()+data.storedChunks.get(i)));
            try {
                Files.delete(Paths.get(datab.getFilepath()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            dba.unregisterFileBackUpData(this.message.getFileID()+ctr);

            try {
                dba.save();
            } catch (IOException e) {
                e.printStackTrace();
            }

            data.storedChunks.remove(i);
        }

        db.unregisterFileReplicationData(this.message.getFileID());
        try {
            db.save();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
