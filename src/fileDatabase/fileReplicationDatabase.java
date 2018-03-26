package fileDatabase;

import java.io.*;
import java.util.concurrent.ConcurrentHashMap;

public class fileReplicationDatabase implements Serializable{

    private static boolean instantiated = false;
    private static fileReplicationDatabase singleton = null;

    private String databaseFilepath;
    private ConcurrentHashMap<String,fileReplicationData>database = new ConcurrentHashMap<>();

    public static fileReplicationDatabase getDatabase(String filename){
        if(!fileReplicationDatabase.instantiated){
            fileReplicationDatabase.singleton = new fileReplicationDatabase(filename);
        }
        return fileReplicationDatabase.singleton;
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

    public void registerFileReplicationData(String filename,fileReplicationData data){
        if(!this.database.containsKey(filename)){
            this.database.put(filename,data);
        }
    }

    public void unregisterFileReplicationData(String filename){
        if(this.database.containsKey(filename)){
            this.database.remove(filename);
        }
    }

    public fileReplicationData getRegisteredFileReplicationData(String filename){
        if(this.database.containsKey(filename)){
            return this.database.get(filename);
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
