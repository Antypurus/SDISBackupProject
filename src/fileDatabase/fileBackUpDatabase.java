package fileDatabase;

import javafx.util.Pair;

import java.io.*;
import java.util.concurrent.ConcurrentHashMap;

public class fileBackUpDatabase implements Serializable{

    private static boolean instantiated = false;
    private static fileBackUpDatabase singleton = null;

    private ConcurrentHashMap<Pair<String,Integer>,fileBackUpData>database = new ConcurrentHashMap<>();
    private String databaseFilepath;

    public static fileBackUpDatabase getFileBackupDatabase(String filename){
        if(!fileBackUpDatabase.instantiated){
            fileBackUpDatabase.singleton = new fileBackUpDatabase(filename);
            fileBackUpDatabase.instantiated = true;
        }
        return fileBackUpDatabase.singleton;
    }

    private fileBackUpDatabase(String filename){
        this.databaseFilepath = filename;
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

    public void registerFileBackUpData(fileBackUpData data){
        Pair<String,Integer>key = new Pair<>(data.getFileId(),data.getChunkNo());
        if(!this.database.containsKey(key)){
            this.database.put(key,data);
        }
    }

    public void unregisterFileBackUpData(String fileId,int chunkNo){
        Pair<String,Integer>key = new Pair<>(fileId,chunkNo);
        if(this.database.containsKey(key)){
            this.database.remove(key);
        }
    }

    public fileBackUpData getRegisteredFileBackupData(String fileId,int chunkNo){
        Pair<String,Integer>key = new Pair<>(fileId,chunkNo);
        if(this.database.containsKey(key)){
            return this.database.get(key);
        }
        return null;
    }

    public synchronized void save() throws IOException {
        FileOutputStream file = new FileOutputStream(this.databaseFilepath);
        ObjectOutputStream stream = new ObjectOutputStream(file);
        stream.writeObject(this);
        stream.close();
        file.close();
    }

    public synchronized void read() throws IOException, ClassNotFoundException {
        FileInputStream file = new FileInputStream(this.databaseFilepath);
        ObjectInputStream stream = new ObjectInputStream(file);
        fileBackUpDatabase db = (fileBackUpDatabase) stream.readObject();
        this.database = db.database;
        this.databaseFilepath = db.databaseFilepath;
        stream.close();
        file.close();
    }
}
