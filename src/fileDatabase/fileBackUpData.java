package fileDatabase;

public class fileBackUpData {

    private String fileId;
    private int chunkNo;
    private String filepath;
    private String filename;

    public fileBackUpData(String fileId,String filepath,int chunkNo){
        this.fileId = fileId;
        this.filepath = filepath;
        this.chunkNo = chunkNo;
        this.filename = this.fileId+this.chunkNo;
    }

    public String getFileId() {
        return fileId;
    }

    public int getChunkNo() {
        return chunkNo;
    }

    public String getFilepath() {
        return filepath;
    }

    public String getFilename() {
        return filename;
    }
}
