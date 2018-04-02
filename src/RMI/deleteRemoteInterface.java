package RMI;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface deleteRemoteInterface extends Remote {
    public void delete(String filepath) throws RemoteException;
}
