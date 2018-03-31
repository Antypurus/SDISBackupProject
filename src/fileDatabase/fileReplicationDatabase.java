package fileDatabase;

import javafx.util.Pair;

import java.io.*;
import java.util.concurrent.ConcurrentHashMap;

public class fileReplicationDatabase implements Serializable{

    private static boolean instantiated = false;
    private  static fileReplicationDatabase singleton = new fileReplicationDatabase("fileReplicationDatabase.db");

    private String databaseFilepath;
    private ConcurrentHashMap<Pair<String,Integer>,fileReplicationData>database = new ConcurrentHashMap<>();

    public static fileReplicationDatabase getDatabase(){
        return fileReplicationDatabase.singleton;
    }

    private static synchronized void initer(String filename){
        fileReplicationDatabase.singleton = new fileReplicationDatabase(filename);
        fileReplicationDatabase.instantiated = true;
    }


    private fileReplicationDatabase(String filename){
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

    public String getDatabaseFilepath() {
        return databaseFilepath;
    }

    public void setDatabaseFilepath(String databaseFilepath) {
        this.databaseFilepath = databaseFilepath;
    }

    public void registerFileReplicationData(fileReplicationData data){
        Pair<String,Integer>key = new Pair<>(data.getFileId(),data.getChunkNo());
        if(!this.database.containsKey(key)){
            this.database.put(key,data);
        }
    }

    public void unregisterFileReplicationData(String fileId,int chunkNo){
        Pair<String,Integer>key = new Pair<>(fileId,chunkNo);
        if(this.database.containsKey(key)){
            this.database.remove(key);
        }
    }

    public fileReplicationData getRegisteredFileReplicationData(String fileId,int chunkNo){
        Pair<String,Integer>key = new Pair<>(fileId,chunkNo);
        if(this.database.containsKey(key)){
            return this.database.get(key);
        }
        return null;
    }

    public void save() throws IOException {
        FileOutputStream file = new FileOutputStream(this.databaseFilepath);
        ObjectOutputStream stream = new ObjectOutputStream(file);
        stream.writeObject(this);
        stream.close();
        file.close();
    }

    public void read() throws IOException, ClassNotFoundException {
        FileInputStream file = new FileInputStream(this.databaseFilepath);
        ObjectInputStream stream = new ObjectInputStream(file);
        fileReplicationDatabase db = (fileReplicationDatabase) stream.readObject();
        this.database = db.database;
        this.databaseFilepath = db.databaseFilepath;
        stream.close();
        file.close();
    }
}
