package Subprotocols;

import MessageHandler.Message;
import fileDatabase.*;

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
        backedUpFileDatabase db = backedUpFileDatabase.getDatabase();
        backedUpFileData data =  db.getRegisteredBackedUpFileData(this.message.getFileID()+this.message.getChunkNO());

        fileBackUpDatabase dba = fileBackUpDatabase.getFileBackupDatabase();
        fileBackUpData datab = null;
        for(int i=0;i<data.storedChunks.size();++i){
            datab = dba.getRegisteredFileBackupData(this.message.getFileID(),data.storedChunks.get(i));
            try {
                Files.delete(Paths.get(datab.getFilepath()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            dba.unregisterFileBackUpData(this.message.getFileID(),data.storedChunks.get(i));

            try {
                dba.save();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        data.storedChunks.clear();
        db.unregisterBackedUpFile(this.message.getFileID()+this.message.getChunkNO());
        try {
            db.save();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
