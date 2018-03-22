package MessageStubs;

import MessageHandler.Message;

import java.net.MulticastSocket;
import java.util.Queue;

public interface MessageStub{
    Queue<Message>          incomingQueu = null;
    MulticastSocket         sockre = null;
    String                  status = null;

    void addInboundMessage(Message message);
    Message getInboundMessage();
    int checkUnprocessedMessages();
    String checkStubStatus();
}
