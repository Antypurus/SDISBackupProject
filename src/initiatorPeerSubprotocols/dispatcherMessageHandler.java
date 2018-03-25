package initiatorPeerSubprotocols;

import MessageHandler.Message;
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
        if(this.registry!=null && this.message!=null){
            Message msg = Message.ParseMessage(this.message);
            if(msg!=null) {
                System.out.println("Dispatcher:"+msg.toString());
            }
        }
    }
}
