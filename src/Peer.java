import RMI.*;
import Utils.Channel;
import Utils.Constants;
import Utils.Logging;
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
import java.rmi.AlreadyBoundException;
import java.util.Scanner;

import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Peer {

    public static void engageDatabases() throws IOException {
        fileReplicationDatabase dba = fileReplicationDatabase.getDatabase();
        fileBackUpDatabase db = fileBackUpDatabase.getFileBackupDatabase();
        backedUpFileDatabase dbs = backedUpFileDatabase.getDatabase();
        dba.save();
        db.save();
        dbs.save();
    }

    public static void engageSockets(String MCaddress,int MCport,String MDBaddress,int MDBport,String MDRaddress,int MDRport) throws IOException {
        MulticastSocket socket = new MulticastSocket(MCport);
        socket.joinGroup(InetAddress.getByName(MCaddress));

        MulticastSocket mdb = new MulticastSocket(MDBport);
        mdb.joinGroup(InetAddress.getByName(MDBaddress));

        MulticastSocket mdr = new MulticastSocket(MDRport);
        mdr.joinGroup(InetAddress.getByName(MDRaddress));

        Constants.MC = new Channel(socket,InetAddress.getByName(MCaddress),MCport);
        Constants.MDB = new Channel(mdb,InetAddress.getByName(MDBaddress),MDBport);
        Constants.MDR = new Channel(mdr,InetAddress.getByName(MDRaddress),MDRport);
    }

    public static void engageDispatchers(int senderID){
        Dispatcher dispatcher = new Dispatcher(Constants.MC.socket, Constants.registry, Constants.MC.address, Constants.MC.port, senderID);
        Thread thread = new Thread(dispatcher);
        thread.start();

        Dispatcher dispatcher2 = new Dispatcher(Constants.MDR.socket, Constants.registry, Constants.MDR.address, Constants.MDR.port, senderID);
        Thread thread2 = new Thread(dispatcher2);
        thread2.start();

        Dispatcher dispatcher3 = new Dispatcher(Constants.MDB.socket, Constants.registry, Constants.MDB.address, Constants.MDB.port, senderID);
        Thread thread3 = new Thread(dispatcher3);
        thread3.start();
    }

    public static void main(String args[]) throws IOException, AlreadyBoundException {
        File dir = new File("stored");
        dir.mkdir();
        dir = new File("restored");
        dir.mkdir();

        engageDatabases();

        threadRegistry registry = new threadRegistry();
        Constants.registry = registry;

        engageSockets("224.0.1.1",5151,"224.0.1.1",5152,"224.0.1.3",5151);

        int senderId = 0;
        Scanner scanner = new Scanner(System.in);
        System.out.print("SenderID:");
        senderId = scanner.nextInt();

        System.out.print("send:");
        String s = scanner.next();

        engageDispatchers(senderId);

        if(s.equals("s")) {
            backupServer bck = new backupServer(senderId, registry, Constants.MDB.socket, Constants.MDB.address, Constants.MDB.port);
            backupRemoteInterface backup = (backupRemoteInterface) UnicastRemoteObject.exportObject(bck, 0);

            getchunkServer get = new getchunkServer(Constants.MC.socket, Constants.MC.address, Constants.MC.port, senderId);
            getchunkRemoteInterface getchunk = (getchunkRemoteInterface) UnicastRemoteObject.exportObject(get, 0);

            deleteServer del = new deleteServer(Constants.MC.socket, Constants.MC.address, Constants.MC.port, senderId);
            deleteRemoteInterface delete = (deleteRemoteInterface) UnicastRemoteObject.exportObject(del, 0);

            Registry registry1 = LocateRegistry.getRegistry();
            registry1.rebind("backup", backup);
            registry1.rebind("getchunk", getchunk);
            registry1.rebind("delete", delete);

            Logging.FatalSuccessLog("Started RMI Servers");
        }

        /**
        if (s.equals("s")) {
            putchunkSubprotocol put = new putchunkSubprotocol(senderId, "test.txt", registry, Constants.MDB.socket, Constants.MDB.address, Constants.MDB.port, 1);
        }**/
    }
}
