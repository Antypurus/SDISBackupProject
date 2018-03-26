package fileDatabase;

import java.io.Serializable;

public class backedUpFileData implements Serializable{

    private String filename;
    private String filepath;
    private String fileId;
    private int numberOfChunks = 0;

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
}
