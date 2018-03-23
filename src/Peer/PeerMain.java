package Peer;

import Utils.threadRegistry;
import initiatorPeerSubprotocols.putchunkSubprotocol;

import java.io.IOException;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class PeerMain {
  public static void main(String args[]) throws IOException {
    threadRegistry registry = new threadRegistry();
    MulticastSocket socket = new MulticastSocket(5151);
    socket.joinGroup(InetAddress.getByName("224.0.0.15"));
    putchunkSubprotocol put = new putchunkSubprotocol(0, "test.txt",registry,socket);
    while(true){
    }
  }
}
