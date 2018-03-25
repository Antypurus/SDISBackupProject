package Peer;

import MessageStubs.MessageStub;
import Utils.threadRegistry;
import initiatorPeerSubprotocols.initiatorPeerDispatcher;
import initiatorPeerSubprotocols.putchunkSubprotocol;

import java.io.IOException;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

public class PeerMain {
  public static void main(String args[]) throws IOException, InterruptedException {
    threadRegistry registry = new threadRegistry();
    MulticastSocket socket = new MulticastSocket(5151);
    socket.joinGroup(InetAddress.getByName("224.0.1.1"));

    initiatorPeerDispatcher dispatcher = new initiatorPeerDispatcher(socket,registry);
    Thread thread = new Thread(dispatcher);
    thread.start();

    putchunkSubprotocol put = new putchunkSubprotocol(0, "test.txt",registry,socket,InetAddress.getByName("224.0.1.1"),5151);
    /**
    while(true){
        Iterator it = registry.putchunkThreadRegistry.entrySet().iterator();
        while (it.hasNext()) {
            ConcurrentHashMap.Entry pair = (ConcurrentHashMap.Entry)it.next();
            System.out.print(pair.getKey()+" ");
            System.out.println("Status:"+((MessageStub)pair.getValue()).checkStubStatus());
        }
        Thread.sleep(1500);
    }*/
  }
}
