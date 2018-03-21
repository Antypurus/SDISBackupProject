package MessageHandler;

import FileHandler.FileIDCalculator;

import java.io.IOException;

public class putchunkMessage extends Message {

    private int replicationDeg;

    /**
     *
     * @param senderID
     * @param chunkNO
     * @param FileID
     */
    public putchunkMessage(int senderID,int chunkNO,String FileID,int replicationDeg){
        this.messageType = "PUTCHUNK";
        this.senderID = senderID;
        this.chunkNO = chunkNO;
        this.FileID = FileID;
        this.replicationDeg = replicationDeg;
    }

    /**
     *
     * @param senderID
     * @param chunkNO
     * @param startChunk
     * @param filename
     */
    public putchunkMessage(int senderID,int chunkNO,String startChunk,String filename,int replicationDeg) throws IOException {
        this.messageType = "PUTCHUNK";
        this.senderID = senderID;
        this.chunkNO = chunkNO;
        this.replicationDeg = replicationDeg;
        this.FileID = this.calculateFileID(startChunk,filename);
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

    /**
     *
     * @return
     */
    @Override
    public String toString(){
        String ret = this.messageType + " " + this.protocolVersion;
        ret += " " + this.senderID + " " + this.FileID + " ";
        ret += this.chunkNO + " " + this.replicationDeg;
        return ret;
    }

    /**
     *
     * @return
     */
    public int getReplicationDeg() {
        return replicationDeg;
    }

    /**
     *
     * @param replicationDeg
     */
    public void setReplicationDeg(int replicationDeg) {
        this.replicationDeg = replicationDeg;
    }
}
