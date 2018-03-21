package MessageHandler;

import FileHandler.FileIDCalculator;

import java.io.IOException;

public class putchunkMessage extends Message {

    private int replicationDeg = -1;
    private String body = null;

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
        if(this.body==null || this.replicationDeg==-1){
            return null;
        }
        String ret = this.messageType + " " + this.protocolVersion;
        ret += " " + this.senderID + " " + this.FileID + " ";
        ret += this.chunkNO + " " + this.replicationDeg + "\r\n"+"\r\n"+" "+this.body;
        ret = ret.trim();
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

    /**
     *
     * @return
     */
    public String getBody() {
        return body;
    }

    /**
     *
     * @param body
     */
    public void setBody(String body) {
        this.body = body;
    }

}
