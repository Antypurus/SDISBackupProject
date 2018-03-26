package Peer;

import Utils.Constants;
import Utils.threadRegistry;
import Subprotocols.Dispatcher;
import Subprotocols.putchunkSubprotocol;

import java.io.IOException;
import java.net.InetAddress;
import java.net.MulticastSocket;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.*;

public class PeerMain {
  public static void main(String args[]) throws IOException, InterruptedException {
      /**
      Connection c = null;

      try {
          Class.forName("org.sqlite.JDBC");
          c = DriverManager.getConnection("jdbc:sqlite:test.db");
      } catch ( Exception e ) {
          System.err.println( e.getClass().getName() + ": " + e.getMessage() );
          System.exit(-5);
      }
      System.out.println(Constants.ANSI_CYAN+"Opened database successfully"+Constants.ANSI_RESET);
       **/

    threadRegistry registry = new threadRegistry();
    MulticastSocket socket = new MulticastSocket(5151);
    socket.joinGroup(InetAddress.getByName("224.0.1.1"));

    Dispatcher dispatcher = new Dispatcher(socket,registry);
    Thread thread = new Thread(dispatcher);
    thread.start();

    putchunkSubprotocol put = new putchunkSubprotocol(0, "test.txt",registry,socket,InetAddress.getByName("224.0.1.1"),5151);

  }
}
