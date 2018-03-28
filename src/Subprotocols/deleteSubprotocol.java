package Subprotocols;

import MessageHandler.Message;
import fileDatabase.fileBackUpData;
import fileDatabase.fileBackUpDatabase;

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
        fileBackUpDatabase db = fileBackUpDatabase.getFileBackupDatabase("fileBackUpDatabase.db");
        fileBackUpData data = null;
        int ctr = 1;
        data = db.getRegisteredFileBackupData(this.message.getFileID()+ctr);
        while(data!=null){
            try {
                Files.delete(Paths.get(data.getFilepath()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            db.unregisterFileBackUpData(this.message.getFileID()+ctr);

            try {
                db.save();
            } catch (IOException e) {
                e.printStackTrace();
            }

            ctr++;
            data = db.getRegisteredFileBackupData(this.message.getFileID()+ctr);
        }
    }


}
