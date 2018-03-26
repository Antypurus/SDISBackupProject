package MessageStubs;

import MessageHandler.Message;
import MessageHandler.getChunkMessage;
import MessageHandler.messageType;
import Utils.Logging;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.LinkedList;
import java.util.Queue;

public class getChunkMessageStub implements Runnable,MessageStub {

    private DatagramPacket packet;
    private String status = "INITIALIZED";
    private Queue<Message> inboundQueue;
    private int timeoutPerior = 1000;
    private MulticastSocket socket;
    private InetAddress address;
    private int port;
    private String fileId;
    private int senderId;
    private int chunkNo;

    private Message msg;

    public getChunkMessageStub(MulticastSocket socket,InetAddress address,int port,String fileId,int senderId,int chunkNo){
        this.socket = socket;
        this.address = address;
        this.port = port;
        this.senderId = senderId;
        this.fileId = fileId;
        this.chunkNo = chunkNo;

        this.inboundQueue = new LinkedList<>();
        msg = new getChunkMessage(this.senderId,this.fileId,this.chunkNo);
    }

    @Override
    public void addInboundMessage(Message message) {
        if(this.inboundQueue!=null){
            this.inboundQueue.add(message);
            this.notify();
        }
    }

    @Override
    public Message getInboundMessage() throws InterruptedException {
        while(this.inboundQueue.isEmpty()){
            this.wait(this.timeoutPerior);
            if(this.inboundQueue.isEmpty()){
                return null;
            }
            break;
        }
        if(this.inboundQueue.isEmpty()){
            return null;
        }
        return this.inboundQueue.remove();
    }

    @Override
    public int checkUnprocessedMessages() {
        return this.inboundQueue.size();
    }

    @Override
    public String checkStubStatus() {
        return this.status;
    }

    @Override
    public void setAddressAndPort(InetAddress addr, int port) {
        this.address = addr;
        this.port = port;
    }

    @Override
    public int sendMessage(Message message) {
        byte[] send = message.toString().getBytes();
        DatagramPacket packet = new DatagramPacket(send,send.length,this.address,this.port);
        try {
            this.socket.send(packet);
            return 1;
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public int sendPacket(DatagramPacket packet) {
        return 0;
    }

    private boolean messageValidator(Message message){
        if(message.getMessageType()== messageType.CHUNK){
            if(message.getSenderID()!=this.senderId){
                if(message.getFileID().equals(this.fileId)){
                    if(message.getChunkNO()==this.chunkNo){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public void run() {
        int ret = this.sendMessage(this.msg);
        if(ret==-1){
            Logging.FatalErrorLog("[ERROR]@getChunkMessageStub-Run:Failed to send Getchunk Message Killing Thread");
        }

        Message msg = null;
        int counter = 0;
        while(true){
            try {
                msg = this.getInboundMessage();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(msg!=null && this.messageValidator(msg)){
                try {
                    FileOutputStream file = new FileOutputStream(msg.getFileID()+msg.getChunkNO());
                    file.write(msg.getBody().getBytes());
                    file.close();
                    return;
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else{
                this.timeoutPerior = this.timeoutPerior*2;
                counter++;
                if(counter>=5){
                    Logging.FatalErrorLog("[ERROR]@getChunkMessageStub-Run:Timeout Limit Exceeded Killing Thread");
                    return;
                }
            }
        }
    }
}
