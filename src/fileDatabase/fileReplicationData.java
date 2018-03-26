package fileDatabase;

import java.io.Serializable;
import java.util.ArrayList;

public class fileReplicationData implements Serializable{

    private int intendedReplicationDegree = 0;
    private ArrayList<Integer>peersWhoStored;
    private int actualReplicationDegree = 0;

    public fileReplicationData(int intendedReplicationDegree){
        this.intendedReplicationDegree = intendedReplicationDegree;
        this.peersWhoStored = new ArrayList<>();
        this.actualReplicationDegree = 0;
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

}
