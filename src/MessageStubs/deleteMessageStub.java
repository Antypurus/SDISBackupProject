package MessageStubs;

import MessageHandler.deleteMessage;
import fileDatabase.backedUpFileData;
import fileDatabase.backedUpFileDatabase;
import MessageHandler.Message;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class deleteMessageStub implements Runnable{

    private MulticastSocket socket;
    private InetAddress address;
    private int port;
    private int senderId;
    private String filename;

    public  deleteMessageStub(MulticastSocket socket, InetAddress address,int port,int senderId,String filename){
        this.senderId = senderId;
        this.socket = socket;
        this.address = address;
        this.port = port;
        this.filename = filename;
    }


    @Override
    public void run() {
        backedUpFileDatabase db = backedUpFileDatabase.getDatabase();
        backedUpFileData data = db.getRegisteredBackedUpFileData(this.filename);

        if(data!=null) {
            Message message = new deleteMessage(this.senderId, data.getFileId());
            byte[] send = message.toString().getBytes();
            DatagramPacket packet = new DatagramPacket(send,send.length,this.address,this.port);
            try {
                this.socket.send(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
