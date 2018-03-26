package Subprotocols;

import MessageHandler.Message;
import MessageHandler.messageType;
import MessageStubs.MessageStub;
import Utils.threadRegistry;

import java.net.InetAddress;
import java.net.MulticastSocket;

import static MessageHandler.messageType.*;

public class dispatcherMessageHandler implements Runnable{

    private String              message = null;
    private threadRegistry      registry = null;
    private MulticastSocket     socket;
    private InetAddress         address;
    private int                 port;
    private int                 senderId;

    public dispatcherMessageHandler(String message, threadRegistry registry){
        this.registry = registry;
        this.message = message;
    }

    @Override
    public void run() {
        if(this.registry!=null && this.message!=null) {
            Message msg = Message.ParseMessage(this.message);
            switch (msg.getMessageType()){
                case PUTCHUNK: {
                    storedSubprotocol stored = new storedSubprotocol(msg,this.socket,this.address,this.port,this.senderId);
                    stored.run();
                    break;
                }
                case STORED: {
                    MessageStub stub = this.registry.getPutchunkThread(msg.getChunkNO(), msg.getFileID());
                    if (stub != null) {
                        stub.addInboundMessage(msg);
                    }
                    break;
                }
                case CHUNK:
                    break;
                case DELETE:
                    break;
                case REMOVED:
                    break;
                case GETCHUNK:
                    break;
            }
        }
    }
}
