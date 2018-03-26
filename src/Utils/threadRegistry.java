package Utils;

import MessageHandler.Message;
import MessageStubs.MessageStub;
import javafx.util.Pair;

import java.util.concurrent.ConcurrentHashMap;

public class threadRegistry {

    public ConcurrentHashMap<Pair<String,Integer>,MessageStub> putchunkThreadRegistry;
    public ConcurrentHashMap<Pair<String,Integer>,MessageStub> getchunkThreadRegistry;

    /**
     *
     */
    public threadRegistry() {
        this.putchunkThreadRegistry = new ConcurrentHashMap<>();
        this.getchunkThreadRegistry = new ConcurrentHashMap<>();
    }

    /**
     *
     * @param stub
     * @param chunkNo
     * @param fileID
     */
    public void registerPutchunkThread(MessageStub stub, int chunkNo, String fileID){
        Logging.Log("[LOG]@putchunkThreadRegistry:registerPutchunkThread-Registering thread with <"+chunkNo+","+fileID+">");
        Pair<String,Integer> key = new Pair<>(fileID,chunkNo);
        if(!this.putchunkThreadRegistry.containsKey(key)) {
            this.putchunkThreadRegistry.put(key, stub);
        }
    }

    /**
     *
     * @param chunkNo
     * @param fileId
     * @return
     */
    public MessageStub getPutchunkThread(int chunkNo, String fileId){
        Pair<String,Integer> key = new Pair<>(fileId,chunkNo);
        if(!this.putchunkThreadRegistry.containsKey(key)){
            return null;
        }
        return this.putchunkThreadRegistry.get(key);
    }

    /**
     *
     * @param fileId
     * @param chunkNo
     */
    public void removePutchunkThread(String fileId,int chunkNo){
        Pair<String,Integer> key = new Pair<>(fileId,chunkNo);
        if(this.putchunkThreadRegistry.containsKey(key)){
            this.putchunkThreadRegistry.remove(key);
        }
    }

    /**
     *
     * @return
     */
    public int getRegisteredPutchunkThreadCount(){
        return this.putchunkThreadRegistry.size();
    }

    /**
     *
     * @param stub
     * @param fileId
     */
    public void registerGetchunkThread(MessageStub stub,String fileId,int chunkNo){
        Logging.Log("[LOG]@putchunkThreadRegistry:registerPutchunkThread-Registering thread with <"+fileId+">");
        Pair<String,Integer>key = new Pair<>(fileId,chunkNo);
        if(!this.getchunkThreadRegistry.containsKey(key)){
            this.getchunkThreadRegistry.put(key,stub);
        }
    }

    /**
     *
     * @param fileId
     * @return
     */
    public MessageStub getGetchunkThread(String fileId,int chunkNo){
        Pair<String,Integer>key = new Pair<>(fileId,chunkNo);
        if(!this.getchunkThreadRegistry.containsKey(key)){
            return null;
        }
        return this.getchunkThreadRegistry.get(key);
    }

    /**
     *
     * @param fileId
     */
    public void removeGetchunkThread(String fileId,int chunkNo){
        Pair<String,Integer>key = new Pair<>(fileId,chunkNo);
        if(this.getchunkThreadRegistry.containsKey(key)){
            this.getchunkThreadRegistry.remove(key);
        }
    }

    /**
     *
     * @return
     */
    public int getRegisteredGetchunkThreadCount(){
        return this.getchunkThreadRegistry.size();
    }

}
