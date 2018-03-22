package MessageStubs;

import MessageHandler.Message;

public class putchunkStub implements MessageStub,Runnable {
    @Override
    public synchronized void addInboundMessage(Message message) {

    }

    @Override
    public synchronized  Message getInboundMessage() {
        return null;
    }

    @Override
    public int checkUnprocessedMessages() {
        return 0;
    }

    @Override
    public String checkStubStatus() {
        return null;
    }

    @Override
    public void run() {

    }
}
