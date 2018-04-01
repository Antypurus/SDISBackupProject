package RMI;

import java.rmi.Remote;

public interface backupRemoteInterface extends Remote {
    public void backup(String filepath);
}
