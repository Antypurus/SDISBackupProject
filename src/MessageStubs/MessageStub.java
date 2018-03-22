package MessageStubs;

import MessageHandler.Message;
import java.util.Queue;

public interface MessageStub{
    Queue<Message> incomingQueu = null;
    void addInboundMessage(Message message);
    Message getInboundMessage();
    int checkUnprocessedMessages();
    String checkStubStatus();
}
