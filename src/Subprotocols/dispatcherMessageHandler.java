package Subprotocols;

import MessageHandler.Message;
import MessageHandler.messageType;
import MessageStubs.MessageStub;
import Utils.Constants;
import Utils.Logging;
import Utils.threadRegistry;
import fileDatabase.fileReplicationData;
import fileDatabase.fileReplicationDatabase;

import java.io.IOException;
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

    public dispatcherMessageHandler(String message, threadRegistry registry,MulticastSocket socket,InetAddress address,int port,int senderId){
        this.registry = registry;
        this.message = message;
        this.senderId = senderId;
        this.socket = socket;
        this.address = address;
        this.port = port;
    }

    @Override
    public void run() {
        if(this.registry!=null && this.message!=null) {
            Message msg = Message.ParseMessage(this.message);
            if(msg.getSenderID()!=this.senderId) {
                switch (msg.getMessageType()) {
                    case PUTCHUNK: {
                        storedSubprotocol stored = new storedSubprotocol(msg, Constants.MC.socket, Constants.MC.address, Constants.MC.port, this.senderId);
                        stored.run();
                        break;
                    }
                    case STORED: {
                        MessageStub stub = this.registry.getPutchunkThread(msg.getChunkNO(), msg.getFileID());
                        if (stub != null) {
                            stub.addInboundMessage(msg);
                        }

                        fileReplicationDatabase db = fileReplicationDatabase.getDatabase();
                        fileReplicationData data = db.getRegisteredFileReplicationData(msg.getFileID(),msg.getChunkNO());
                        if(data==null) {
                            db.registerFileReplicationData(new fileReplicationData(msg.getReplicationDeg(), msg.getFileID(), msg.getChunkNO()));
                            data = db.getRegisteredFileReplicationData(msg.getFileID(), msg.getChunkNO());
                        }
                        data.addPeerWhoStored(msg.getSenderID());

                        try {
                            db.save();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        break;
                    }
                    case CHUNK: {
                        MessageStub stub = Constants.registry.getGetchunkThread(msg.getFileID(), msg.getChunkNO());
                        if (stub != null) {
                            Logging.Log("Adding Message for chunk "+msg.getChunkNO()+"and file id:"+msg.getFileID()+" to inbound queue "+msg.getBody());
                            stub.addInboundMessage(msg);
                        }else{
                            Logging.FatalErrorLog("No Thread Registered For This Chunk:"+msg.getChunkNO()+" with fileID:"+msg.getFileID());
                        }
                        break;
                    }
                    case DELETE:
                        deleteSubprotocol del = new deleteSubprotocol(msg,this.socket,this.address,this.port,this.senderId);
                        del.run();
                    case REMOVED:
                        removedSubprotocol rem = new removedSubprotocol(msg,Constants.MDB.socket,Constants.MDB.address,Constants.MDB.port,this.senderId,this.registry);
                        rem.run();
                        break;
                    case GETCHUNK:
                        chunkSubprotocol chunk = new chunkSubprotocol(msg,Constants.MDR.socket,Constants.MDR.address,Constants.MDR.port,this.senderId);
                        chunk.run();
                        break;
                }
            }
        }
    }
}
