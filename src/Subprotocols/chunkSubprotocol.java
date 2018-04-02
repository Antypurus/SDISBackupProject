package Subprotocols;

import MessageHandler.Message;
import MessageHandler.chunkMessage;
import Utils.Logging;
import fileDatabase.backedUpFileData;
import fileDatabase.backedUpFileDatabase;
import fileDatabase.fileBackUpData;
import fileDatabase.fileBackUpDatabase;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class chunkSubprotocol implements Runnable{

    public boolean send = true;

    private Message msg = null;
    private MulticastSocket socket = null;
    private InetAddress address = null;
    private int port;
    private int senderId;

    public chunkSubprotocol(Message msg,MulticastSocket socket, InetAddress address, int port, int senderId){
        this.msg = msg;
        this.socket = socket;
        this.address = address;
        this.port = port;
        this.senderId = senderId;
    }

    @Override
    public void run() {
        fileBackUpDatabase db = fileBackUpDatabase.getFileBackupDatabase();
        fileBackUpData data = db.getRegisteredFileBackupData(this.msg.getFileID(),this.msg.getChunkNO());
        if(data!=null) {
            String filepath = data.getFilepath();

            FileInputStream file = null;
            try {
                file = new FileInputStream(filepath);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return;
            }

            byte[] buffer = new byte[65000];
            try {
                file.read(buffer);
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }

            String body = new String(buffer);
            Message message = new chunkMessage(this.senderId,this.msg.getChunkNO(),this.msg.getFileID());
            message.setBody(body);

            //this thread should already be registered to send
            //wait period

            if(this.send) {
                buffer = message.toString().getBytes();
                DatagramPacket pakcet = new DatagramPacket(buffer, buffer.length, this.address, this.port);
                try {
                    this.socket.send(pakcet);
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }
            }
        }else{
            Logging.FatalErrorLog("No Data Found For The Requested FileID:"+ msg.getFileID() + " and Chunk:"+msg.getChunkNO());
            return;
        }
    }
}
