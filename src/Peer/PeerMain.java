package Peer;

import Utils.Channel;
import Utils.Constants;
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
        Constants.registry = registry;

        MulticastSocket socket = new MulticastSocket(5151);
        socket.joinGroup(InetAddress.getByName("224.0.1.1"));

        MulticastSocket mdb = new MulticastSocket(5151);
        socket.joinGroup(InetAddress.getByName("224.0.1.2"));

        MulticastSocket mdr = new MulticastSocket(5151);
        socket.joinGroup(InetAddress.getByName("224.0.1.3"));

        Constants.MC = new Channel(socket,InetAddress.getByName("224.0.1.1"),5151);
        Constants.MDB = new Channel(mdb,InetAddress.getByName("224.0.1.2"),5151);
        Constants.MDR = new Channel(mdr,InetAddress.getByName("224.0.1.3"),5151);

        int senderId = 0;
        Scanner scanner = new Scanner(System.in);
        System.out.print("SenderID:");
        senderId = scanner.nextInt();

        System.out.print("send:");
        String s = scanner.next();

        Dispatcher dispatcher = new Dispatcher(Constants.MC.socket, registry, Constants.MC.address, Constants.MC.port, senderId);
        Thread thread = new Thread(dispatcher);
        thread.start();

        Dispatcher dispatcher2 = new Dispatcher(Constants.MDR.socket, registry, Constants.MDR.address, Constants.MDR.port, senderId);
        Thread thread2 = new Thread(dispatcher2);
        thread2.start();

        Dispatcher dispatcher3 = new Dispatcher(Constants.MDB.socket, registry, Constants.MDB.address, Constants.MDB.port, senderId);
        Thread thread3 = new Thread(dispatcher3);
        thread3.start();

        if (s.equals("s")) {
            putchunkSubprotocol put = new putchunkSubprotocol(senderId, "test.txt", registry, Constants.MDB.socket, Constants.MDB.address, Constants.MDB.port, 1);
        }
    }
}
