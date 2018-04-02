package RMI;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface getchunkRemoteInterface extends Remote {
    public void getChunk(String filepath,int chunkNo) throws RemoteException;
    public void restore(String filepath) throws RemoteException;
}
