package MessageStubs;

import MessageHandler.Message;

import java.net.MulticastSocket;
import java.util.Queue;

public interface MessageStub{
    Queue<Message>          incomingQueue = null;
    MulticastSocket         socket = null;
    String                  status = null;

    void addInboundMessage(Message message);
    Message getInboundMessage() throws InterruptedException;
    int checkUnprocessedMessages();
    String checkStubStatus();
}
