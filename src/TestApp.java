import RMI.backupRemoteInterface;
import RMI.deleteRemoteInterface;
import RMI.getchunkRemoteInterface;

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


        deleteRemoteInterface del = (deleteRemoteInterface)registry.lookup("delete");
        if(del!=null) {
            del.delete("test.txt");
        }


        /**
        getchunkRemoteInterface get = (getchunkRemoteInterface)registry.lookup("getchunk");
        if(get!=null) {
            System.out.println("Restore Initiated");
            get.restore("test.txt");
        }
        **/


        /**
        backupRemoteInterface bk = (backupRemoteInterface)registry.lookup("backup");
        if(bk!=null) {
            bk.backup("test.txt", 1);
        }
        **/
    }

}
