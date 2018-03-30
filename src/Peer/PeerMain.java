package Peer;

import Utils.Constants;
import Utils.threadRegistry;
import Subprotocols.Dispatcher;
import Subprotocols.putchunkSubprotocol;
import fileDatabase.backedUpFileData;
import fileDatabase.backedUpFileDatabase;

import java.io.IOException;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Scanner;

public class PeerMain {
  public static void main(String args[]) throws IOException, InterruptedException {
    threadRegistry registry = new threadRegistry();
    MulticastSocket socket = new MulticastSocket(5151);
    socket.joinGroup(InetAddress.getByName("224.0.1.1"));

      int senderId = 0;
      Scanner scanner = new Scanner(System.in);
      System.out.print("SenderID:");
      senderId = scanner.nextInt();

      System.out.print("send:");
      String s = scanner.next();

    Dispatcher dispatcher = new Dispatcher(socket,registry,InetAddress.getByName("224.0.1.1"),5151,senderId);
    Thread thread = new Thread(dispatcher);
    thread.start();

    if(s.equals("s")) {
        putchunkSubprotocol put = new putchunkSubprotocol(0, "test.txt", registry, socket, InetAddress.getByName("224.0.1.1"), 5151);
    }
  }
}
