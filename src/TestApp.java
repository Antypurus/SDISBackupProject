import RMI.ServerInterface;
import RMI.backupRemoteInterface;
import RMI.deleteRemoteInterface;
import RMI.getchunkRemoteInterface;
import Utils.Logging;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class TestApp {

    public static void main(String[] args) throws RemoteException, NotBoundException {
        Registry registry = null;
        try {
            // //ip/id
            registry = LocateRegistry.getRegistry();
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        ServerInterface server = (ServerInterface) registry.lookup(args[0]);
        if (server != null) {
            switch (args[1]) {
                case ("BACKUP"): {
                    Logging.LogSuccess("BACKUP SUBPROCCESS SELECTED");
                    server.backup(args[2], Integer.parseInt(args[3]));
                    break;
                }
                case ("RESTORE"): {
                    Logging.LogSuccess("RESTORE SUBPROCCESS SELECTED");
                    server.restore(args[2]);
                    break;
                }
                case ("DELETE"): {
                    Logging.LogSuccess("DELETE SUBPROCCESS SELECTED");
                    server.delete(args[2]);
                    break;
                }
                default:
                    break;
            }
            server.delete("test.txt");
        }
    }
}
