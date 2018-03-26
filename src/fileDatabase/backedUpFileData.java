package fileDatabase;

import java.io.Serializable;

public class backedUpFileData implements Serializable{

    private String filepath;
    private String fileId;
    private int numberOfChunks = 0;

    /**
     *
     * @param filepath
     * @param fileId
     * @param numberOfChunks
     */
    public backedUpFileData(String filepath,String fileId,int numberOfChunks){
        this.fileId = fileId;
        this.filepath = filepath;
        this.numberOfChunks = numberOfChunks;
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
}
