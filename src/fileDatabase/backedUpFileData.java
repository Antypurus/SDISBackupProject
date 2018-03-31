package fileDatabase;

import java.io.Serializable;
import java.util.ArrayList;

public class backedUpFileData implements Serializable{

    private String filename;
    private String filepath;
    private String fileId;
    public int numberOfChunks = 0;
    public ArrayList<Integer> storedChunks;

    /**
     *
     * @param filepath
     * @param fileId
     * @param numberOfChunks
     */
    public backedUpFileData(String filename,String filepath,String fileId,int numberOfChunks){
        this.fileId = fileId;
        this.filepath = filepath;
        this.numberOfChunks = numberOfChunks;
        this.filename = filename;
        this.storedChunks = new ArrayList<>();
    }

    /**
     *
     * @return
     */
    public String getFilepath() {
        return filepath;
    }

    /**
     *
     * @return
     */
    public String getFileId() {
        return fileId;
    }

    /**
     *
     * @return
     */
    public int getNumberOfChunks() {
        return numberOfChunks;
    }

    /**
     *
     * @return
     */
    public String getFilename() {
        return filename;
    }

    /**
     *
     * @param chunkNo
     */
    public synchronized void addStoredChunk(int chunkNo){
        if(!this.storedChunks.contains(chunkNo)){
            this.storedChunks.add(chunkNo);
            this.numberOfChunks = storedChunks.size();
        }
    }
}
