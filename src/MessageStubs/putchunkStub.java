package MessageStubs;

import MessageHandler.Message;
import MessageHandler.messageType;
import MessageHandler.putchunkMessage;
import Utils.Constants;
import Utils.Logging;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.Queue;

public class putchunkStub implements MessageStub,Runnable {
    public Thread                   thread = null;

    private Queue<Message>          incomingQueue;
    private MulticastSocket         socket;
    private String                  status;
    private int                     counter=0;
    private InetAddress             address = null;
    private int                     port = 0;

    private DatagramPacket          packet = null;

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

    /**
     *
     * @param message
     */
    @Override
    public synchronized void addInboundMessage(Message message) {
        if(this.incomingQueue != null) {
                this.incomingQueue.add(message);
                this.notify();
        }
    }

    /**
     *
     * @return
     * @throws InterruptedException
     */
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

    /**
     *
     * @return
     */
    @Override
    public int checkUnprocessedMessages() {
        return this.incomingQueue.size();
    }

    /**
     *
     * @return
     */
    @Override
    public String checkStubStatus() {
        return this.status;
    }

    /**
     *
     * @param addr
     * @param port
     */
    @Override
    public void setAddressAndPort(InetAddress addr, int port) {
        this.address = addr;
        this.port = port;
    }

    /**
     *
     * @param message
     * @return
     */
    @Override
    public int sendMessage(Message message) {
        if(this.address==null||this.port==0){
            return -1;
        }
        if(this.packet==null){
            String msg = this.message.toString();
            byte[] bytes = msg.getBytes();
            this.packet = new DatagramPacket(bytes,bytes.length,this.address,this.port);
        }else if(this.packet.getData()!=message.toString().getBytes()){
            String msg = this.message.toString();
            byte[] bytes = msg.getBytes();
            this.packet.setData(bytes);
        }
        if(this.packet!=null) {
            return this.sendPacket(this.packet);
        }
        return -1;
    }

    /**
     *
     * @param packet
     * @return
     */
    @Override
    public int sendPacket(DatagramPacket packet) {
        if(packet == null){
            return -1;
        }
        try {
            Logging.Log("[LOG]@putchunkStub:sendPacket-Thread<"+this.thread.getId()+">: sending chunk "+this.chunkNo);
            this.socket.send(packet);
            return 0;
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public void run() {
        this.sendMessage(this.message);
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
                Logging.LogError("[ERROR]@putchunkStub:Run-Thread<" + this.thread.getId() + ">No Response Message Found");
                this.status="ENDED WITH ERROR";
                this.counter = 0;
                this.sendPacket(this.packet);
                continue;
            }
            if(inMsg!=null) {
                if (inMsg.getMessageType() == messageType.STORED) {
                    if (inMsg.getFileID().equals(this.fileId)) {
                        if (inMsg.getChunkNO() == this.chunkNo) {
                            this.counter++;
                        }
                    }
                }
            }

            if(counter>=replicationDeg){
                return;
            }
        }
    }
}
