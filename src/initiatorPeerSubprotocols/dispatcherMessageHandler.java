package initiatorPeerSubprotocols;

import MessageHandler.Message;
import MessageStubs.MessageStub;
import Utils.threadRegistry;

public class dispatcherMessageHandler implements Runnable{

    private String              message = null;
    private threadRegistry      registry = null;

    public dispatcherMessageHandler(String message, threadRegistry registry){
        this.registry = registry;
        this.message = message;
    }

    @Override
    public void run() {
        if(this.registry!=null && this.message!=null) {
            Message msg = Message.ParseMessage(this.message);
            MessageStub stub = this.registry.getPutchunkThread(msg.getChunkNO(), msg.getFileID());
            if (stub != null) {
                stub.addInboundMessage(msg);
            }
        }
    }
}
