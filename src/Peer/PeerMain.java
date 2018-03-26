package Peer;

import Utils.Constants;
import Utils.threadRegistry;
import Subprotocols.Dispatcher;
import Subprotocols.putchunkSubprotocol;

import java.io.IOException;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class PeerMain {
  public static void main(String args[]) throws IOException, InterruptedException {
    threadRegistry registry = new threadRegistry();
    MulticastSocket socket = new MulticastSocket(5151);
    socket.joinGroup(InetAddress.getByName("224.0.1.1"));

    Dispatcher dispatcher = new Dispatcher(socket,registry);
    Thread thread = new Thread(dispatcher);
    thread.start();

    putchunkSubprotocol put = new putchunkSubprotocol(0, "test.txt",registry,socket,InetAddress.getByName("224.0.1.1"),5151);

  }
}
