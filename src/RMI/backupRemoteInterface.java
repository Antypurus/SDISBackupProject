package RMI;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface backupRemoteInterface extends Remote {
    public void backup(String filepath,int replicationDegree) throws RemoteException;
}
