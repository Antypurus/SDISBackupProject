package Subprotocols;

import MessageHandler.Message;
import MessageHandler.storedMessage;
import Utils.Logging;
import fileDatabase.*;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class storedSubprotocol implements Runnable{

    private MulticastSocket socket;
    private int senderId;
    private Message msg;
    private InetAddress address;
    private int port;
    private Message outMsg;

    public storedSubprotocol(Message msg, MulticastSocket socket, InetAddress address, int port, int senderId){
        this.socket = socket;
        this.msg = msg;
        this.address = address;
        this.port = port;
        this.senderId = senderId;
        this.outMsg = new storedMessage(this.senderId,msg.getFileID(),msg.getChunkNO());
    }

    @Override
    public void run() {
        if(this.msg.getSenderID()!=this.senderId){

            fileBackUpDatabase db = fileBackUpDatabase.getFileBackupDatabase();
            if(db.getRegisteredFileBackupData(this.msg.getFileID(),this.msg.getChunkNO())!=null){
                byte[] send = this.outMsg.toString().getBytes();
                DatagramPacket packet = new DatagramPacket(send,send.length,this.address,this.port);
                try {
                    this.socket.send(packet);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return;
            }

            boolean stored = false;
            try {
                FileOutputStream file = new FileOutputStream("stored/"+this.msg.getFileID()+this.msg.getChunkNO());
                byte[] content = this.msg.getBody().getBytes();
                file.write(content);
                file.close();
                stored = true;

                Logging.LogSuccess("Saved Chunk "+this.msg.getChunkNO());

                db.registerFileBackUpData(new fileBackUpData(this.msg.getFileID(),"stored/"+this.msg.getFileID()+this.msg.getChunkNO(),this.msg.getChunkNO()));
                db.save();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if(stored){

                byte[] send = this.outMsg.toString().getBytes();
                DatagramPacket packet = new DatagramPacket(send,send.length,this.address,this.port);
                try {
                    this.socket.send(packet);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                fileReplicationDatabase dba = fileReplicationDatabase.getDatabase();
                fileReplicationData datab = dba.getRegisteredFileReplicationData(this.msg.getFileID(),this.msg.getChunkNO());
                if(datab==null){
                    datab = new fileReplicationData(this.msg.getReplicationDeg(),this.msg.getFileID(),this.msg.getChunkNO());
                    dba.registerFileReplicationData(datab);
                }
                datab.addPeerWhoStored(this.senderId);
                try {
                    dba.save();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                backedUpFileDatabase dt =  backedUpFileDatabase.getDatabase();
                backedUpFileData dtt = dt.getRegisteredBackedUpFileData(this.msg.getFileID()+this.msg.getChunkNO());
                if(dtt==null){
                    dtt = new backedUpFileData(this.msg.getFileID()+this.msg.getChunkNO(),"/stored"+this.msg.getFileID()+this.msg.getChunkNO(),this.msg.getFileID(),0);
                    dt.registerBackeUpFile(this.msg.getFileID()+this.msg.getChunkNO(),dtt);
                }
                dtt.addStoredChunk(this.msg.getChunkNO());
                try {
                    dt.save();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
