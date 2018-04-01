package RMI;

import java.rmi.Remote;

public interface getchunkRemoteInterface extends Remote {
    public void getChunk(String filepath,int chunkNo);
    public void restore(String filepath);
}
