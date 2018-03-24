package MessageStubs;

import MessageHandler.Message;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Queue;

public interface MessageStub{
    Queue<Message>          incomingQueue = null;
    MulticastSocket         socket = null;
    String                  status = null;
    InetAddress             address = null;
    int                     port = 0;

    void addInboundMessage(Message message);
    Message getInboundMessage() throws InterruptedException;
    int checkUnprocessedMessages();
    String checkStubStatus();
    void setAddressAndPort(InetAddress addr,int port);
    int sendMessage(Message message);
    int sendPacket(DatagramPacket packet);
}
