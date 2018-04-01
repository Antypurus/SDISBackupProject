package RMI;

import java.rmi.Remote;

public interface deleteRemoteInterface extends Remote {
    public void delete(String filepath);
}
