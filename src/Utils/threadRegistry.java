package Utils;

import MessageStubs.MessageStub;
import javafx.util.Pair;

import java.util.concurrent.ConcurrentHashMap;

public class threadRegistry {

    private ConcurrentHashMap<Pair<String,Integer>,MessageStub>threadRegistry;

    /**
     *
     */
    public threadRegistry() {
        this.threadRegistry = new ConcurrentHashMap<>();
    }

    /**
     *
     * @param stub
     * @param chunkNo
     * @param fileID
     */
    public void addThread(MessageStub stub,int chunkNo,String fileID){
        System.out.println("Registering thread with <"+chunkNo+","+fileID+">");
        this.threadRegistry.put(new Pair<>(fileID,chunkNo),stub);
    }

    /**
     *
     * @param chunkNo
     * @param fileId
     * @return
     */
    public MessageStub getThread(int chunkNo,String fileId){
        Pair<String,Integer> key = new Pair<>(fileId,chunkNo);
        if(!this.threadRegistry.containsKey(key)){
            return null;
        }
        return this.threadRegistry.get(key);
    }

    /**
     *
     * @return
     */
    public int getNumberOfRegisteredThreads(){
        return this.threadRegistry.size();
    }

}
