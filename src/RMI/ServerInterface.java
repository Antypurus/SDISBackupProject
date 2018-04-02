package RMI;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerInterface extends Remote {
    public void backup(String filepath,int replicationDegree) throws RemoteException;
    public void getChunk(String filepath,int chunkNo) throws RemoteException;
    public void restore(String filepath) throws RemoteException;
    public void delete(String filepath) throws RemoteException;
}
