package Subprotocols;

import MessageHandler.Message;
import MessageHandler.storedMessage;

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
        //add db entry
        //check if already backed up
        if(this.msg.getSenderID()!=this.senderId){
            boolean stored = false;
            try {
                FileOutputStream file = new FileOutputStream(this.msg.getFileID()+this.msg.getChunkNO());
                byte[] content = this.msg.getBody().getBytes();
                file.write(content);
                file.close();
                stored = true;
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
            }
        }
    }
}
