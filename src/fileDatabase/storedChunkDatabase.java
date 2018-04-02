package fileDatabase;

import Utils.Logging;

import java.io.*;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;

public class storedChunkDatabase implements Serializable {
    public ArrayList<Integer> chunks;
    public String filepath;
    private static storedChunkDatabase singleton = new storedChunkDatabase("fileDatabase.storedChunkDatabase.db");

    public static storedChunkDatabase getDatabase(){
        return singleton;
    }

    private storedChunkDatabase(String filepath){
        this.filepath = filepath;
        this.chunks = new ArrayList<>();
        try {
            this.read();
        } catch (IOException e) {
            try {
                this.save();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public synchronized  void addChunk(int chunk){
        if(!chunks.contains(chunk)){
            Logging.Log("Added Chunk to database of store chunks");
            chunks.add(chunk);
        }
    }

    public synchronized void save() throws IOException {
        FileOutputStream file = new FileOutputStream(this.filepath);
        ObjectOutputStream stream = new ObjectOutputStream(file);
        try{
            stream.writeObject(this);
        }catch (ConcurrentModificationException e){
            Logging.FatalErrorLog("Trying to write at the same time to databse");
        }
        stream.close();
        file.close();
    }

    public synchronized void read() throws IOException, ClassNotFoundException {
        FileInputStream file = new FileInputStream(this.filepath);
        ObjectInputStream stream = new ObjectInputStream(file);
        storedChunkDatabase db = (storedChunkDatabase) stream.readObject();
        this.chunks = db.chunks;
        stream.close();
        file.close();
    }
}
