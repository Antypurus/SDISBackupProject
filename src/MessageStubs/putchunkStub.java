package MessageStubs;

import MessageHandler.Message;
import MessageHandler.messageType;
import MessageHandler.putchunkMessage;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.Queue;

import static MessageStubs.MessageStub.incomingQueue;

public class putchunkStub implements MessageStub,Runnable {
    public Thread                   thread = null;

    private Queue<Message>          incomingQueue = null;
    private MulticastSocket         socket = null;
    private String                  status = null;
    private int                     counter=0;

    private int                     senderId;
    private int                     chunkNo;
    private String                  fileId;
    private String                  body;
    private int                     replicationDeg;

    private putchunkMessage         message;

    public putchunkStub(MulticastSocket socket,int senderId,int chunkNo,String fileId,String body,int replicationDeg){
        this.status = "initializing";
        this.socket = socket;
        this.senderId = senderId;
        this.chunkNo = chunkNo;
        this.fileId = fileId;
        this.replicationDeg = replicationDeg;
        this.body = body;
        this.message = new putchunkMessage(this.senderId,this.chunkNo,this.fileId);
        this.message.setBody(this.body);
        this.message.setReplicationDeg(this.replicationDeg);
        this.incomingQueue = new LinkedList<>();
        this.status = "Ready to transmit";
    }

    @Override
    public synchronized void addInboundMessage(Message message) {
        if(this.incomingQueue != null) {
                this.incomingQueue.add(message);
                this.notify();
        }
    }

    @Override
    public synchronized  Message getInboundMessage() throws InterruptedException {
        while (this.incomingQueue.isEmpty()){
            this.status = "WAITING FOR MESSAGES";
            this.wait(3500);
            if(this.incomingQueue.isEmpty()){
                break;
            }
        }
        if(this.incomingQueue.isEmpty()) {
            return null;
        }
        return this.incomingQueue.remove();
    }

    @Override
    public int checkUnprocessedMessages() {
        return this.incomingQueue.size();
    }

    @Override
    public String checkStubStatus() {
        return this.status;
    }

    @Override
    public void run() {
        String msg = this.message.toString();
        byte[] buffer = msg.getBytes();
        DatagramPacket packet = null;
        try {
            packet = new DatagramPacket(buffer,buffer.length, InetAddress.getByName("224.0.0.15"),5151);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        try {
            this.socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        while(true){
            Message inMsg=null;
            try {
                inMsg = this.getInboundMessage();
            } catch (InterruptedException e){
                if(inMsg==null){
                    e.printStackTrace();
                }
            }
            if(inMsg==null){
                System.out.println("Empty Message Found");
                this.status="ENDED WITH ERROR";
                this.run();
                return;
            }
            if(inMsg.getMessageType()== messageType.STORED){
                if(inMsg.getFileID().equals(this.fileId)){
                    if(inMsg.getChunkNO()==this.chunkNo){
                        this.counter++;
                    }
                }
            }

            if(counter>=replicationDeg){
                return;
            }
        }
    }
}
