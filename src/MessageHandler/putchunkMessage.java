package MessageHandler;

import FileHandler.FileIDCalculator;

import java.io.IOException;

public class putchunkMessage extends Message {

    /**
     *
     * @param senderID
     * @param chunkNO
     * @param FileID
     */
    public putchunkMessage(int senderID,int chunkNO,String FileID){
        this.messageType = "PUTCHUNK";
        this.senderID = senderID;
        this.chunkNO = chunkNO;
        this.FileID = FileID;
    }

    /**
     *
     * @param senderID
     * @param chunkNO
     * @param startChunk
     * @param filename
     */
    public putchunkMessage(int senderID,int chunkNO,String startChunk,String filename) throws IOException {
        this.messageType = "PUTCHUNK";
        this.senderID = senderID;
        this.chunkNO = chunkNO;
        this.FileID = this.calculateFileID(startChunk,filename);
    }

    @Override
    public String toString() {
        return null;
    }

    /**
     *
     * @param startChunk
     * @param Filename
     * @return
     * @throws IOException
     */
    private String calculateFileID(String startChunk,String Filename) throws IOException {
        FileIDCalculator cal = new FileIDCalculator(startChunk,Filename,this.senderID);
        return cal.calculateFileID();
    }
}
