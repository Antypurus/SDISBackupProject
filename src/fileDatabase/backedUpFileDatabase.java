package fileDatabase;

import java.io.*;
import java.util.concurrent.ConcurrentHashMap;

public class backedUpFileDatabase implements Serializable{

    private static backedUpFileDatabase singleton = null;
    private static boolean instantiated = false;

    private String databaseFilepath;
    public ConcurrentHashMap<String,backedUpFileData> database = new ConcurrentHashMap<>();

    public static backedUpFileDatabase getDatabase(String file){
        if(!backedUpFileDatabase.instantiated){
            backedUpFileDatabase.singleton = new backedUpFileDatabase(file);
        }
        return backedUpFileDatabase.singleton;
    }

    private backedUpFileDatabase(String file){
        this.databaseFilepath = file;
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

    public void registerBackeUpFile(String filename, backedUpFileData data){
        if(!this.database.containsKey(filename)) {
            this.database.put(filename, data);
        }
    }

    public void unregisterBackedUpFile(String filename){
        if(this.database.containsKey(filename)){
            this.database.remove(filename);
        }
    }

    public backedUpFileData getRegisteredBackedUpFileData(String filename){
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
        backedUpFileDatabase db = (backedUpFileDatabase) stream.readObject();
        this.databaseFilepath = db.databaseFilepath;
        this.database = db.database;
        stream.close();
        file.close();
    }
}
