package fileDatabase;

import java.io.Serializable;
import java.util.ArrayList;

public class fileReplicationData implements Serializable{

    private int intendedReplicationDegree = 0;
    private ArrayList<Integer>peersWhoStored;
    public ArrayList<Integer>storedChunks;
    private int actualReplicationDegree = 0;
    private String fileId;
    private int chunkNo;

    public fileReplicationData(int intendedReplicationDegree,String fileId,int chunkNo){
        this.intendedReplicationDegree = intendedReplicationDegree;
        this.peersWhoStored = new ArrayList<>();
        this.actualReplicationDegree = 0;
        this.fileId = fileId;
        this.chunkNo = chunkNo;
    }

    public void addPeerWhoStored(int senderId){
        if(!this.peersWhoStored.contains(senderId)){
            this.peersWhoStored.add(senderId);
        }
    }

    public boolean hasPeerStored(int senderId){
        return this.peersWhoStored.contains(senderId);
    }

    public void removePeerWhoStored(int senderId){
        if(this.peersWhoStored.contains(senderId)){
            this.peersWhoStored.remove(senderId);
        }
    }

    public int getCurrentReplicationDegree(){
        this.actualReplicationDegree = this.peersWhoStored.size();
        return this.actualReplicationDegree;
    }

    public int getIntendedReplicationDegree() {
        return intendedReplicationDegree;
    }

    public ArrayList<Integer> getPeersWhoStored() {
        return peersWhoStored;
    }

    public int getActualReplicationDegree() {
        return actualReplicationDegree;
    }

    public String getFileId() {
        return fileId;
    }

    public int getChunkNo() {
        return chunkNo;
    }
}
