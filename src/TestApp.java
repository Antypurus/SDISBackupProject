import RMI.backupRemoteInterface;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class TestApp {

    public static void main(String[] args) throws RemoteException, NotBoundException {
        Registry registry = null;
        try {
            registry = LocateRegistry.getRegistry(null);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        backupRemoteInterface bk = (backupRemoteInterface)registry.lookup("backup");
        if(bk!=null) {
            bk.backup("test.txt", 1);
        }
    }

}
