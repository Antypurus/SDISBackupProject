package RMI;

import Utils.Constants;

import java.rmi.RemoteException;

public class Server implements ServerInterface {

    int senderID;

    @Override
    public void backup(String filepath, int replicationDegree) throws RemoteException {
        backupServer bck = new backupServer(senderID, Constants.registry, Constants.MDB.socket, Constants.MDB.address, Constants.MDB.port);
        bck.backup(filepath,replicationDegree);
    }

    @Override
    public void getChunk(String filepath, int chunkNo) throws RemoteException {
        getchunkServer get = new getchunkServer(Constants.MC.socket, Constants.MC.address, Constants.MC.port, this.senderID);
        get.getChunk(filepath,chunkNo);
    }

    @Override
    public void restore(String filepath) throws RemoteException {
        getchunkServer get = new getchunkServer(Constants.MC.socket, Constants.MC.address, Constants.MC.port, this.senderID);
        get.restore(filepath);
    }

    @Override
    public void delete(String filepath) throws RemoteException {
        deleteServer del = new deleteServer(Constants.MC.socket, Constants.MC.address, Constants.MC.port, this.senderID);
        del.delete(filepath);
    }

    public Server(int senderID){
        this.senderID = senderID;
    }
}
