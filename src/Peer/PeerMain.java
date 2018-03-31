package Peer;

import Utils.threadRegistry;
import Subprotocols.Dispatcher;
import Subprotocols.putchunkSubprotocol;
import fileDatabase.backedUpFileDatabase;
import fileDatabase.fileBackUpDatabase;
import fileDatabase.fileReplicationDatabase;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Scanner;

public class PeerMain {
  public static void main(String args[]) throws IOException, InterruptedException {
      fileReplicationDatabase dba = fileReplicationDatabase.getDatabase();
      fileBackUpDatabase db = fileBackUpDatabase.getFileBackupDatabase();
      backedUpFileDatabase dbs = backedUpFileDatabase.getDatabase();
        dba.save();
        db.save();
        dbs.save();

      File dir = new File("stored");
      dir.mkdir();
      dir = new File("restored");
      dir.mkdir();

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
