package MessageHandler;

import java.io.IOException;

public class putchunkMessage extends Message {

    private int replicationDeg = -1;
    private String body = null;

    /**
     * Constructor for the message for when we already have the file id
     * @param senderID the sender id
     * @param chunkNO the chunk number
     * @param FileID the file id
     */
    public putchunkMessage(int senderID,int chunkNO,String FileID){
        this.messageType = "PUTCHUNK";
        this.senderID = senderID;
        this.chunkNO = chunkNO;
        this.FileID = FileID;
    }

    /**
     * Constructor for when we do not have the file id, we pass it extra information because of this so that it can
     * calculate the file id
     * @param senderID the sender id
     * @param chunkNO the chunk number
     * @param startChunk chunk of data from the beggining of the file
     * @param filename filename of the file we are sending
     * @throws IOException In case the specified file is not found
     */
    public putchunkMessage(int senderID,int chunkNO,String startChunk,String filename) throws IOException {
        this.messageType = "PUTCHUNK";
        this.senderID = senderID;
        this.chunkNO = chunkNO;
        this.FileID = this.calculateFileID(startChunk,filename);
    }

    /**
     * Converts the class to its tring message representation
     * @return string with the message in string formate ready to be sent through the network
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
     * obtain the specified replication degree
     * @return the replication degree
     */
    public int getReplicationDeg() {
        return replicationDeg;
    }

    /**
     * sets the desired replication degree
     * @param replicationDeg the value to give the replication degree
     */
    public void setReplicationDeg(int replicationDeg) {
        this.replicationDeg = replicationDeg;
    }

    /**
     * Obtain the body contents of this message
     * @return the body
     */
    public String getBody() {
        return body;
    }

    /**
     * sets the body content of this message
     * @param body the value to give the body
     */
    public void setBody(String body) {
        this.body = body;
    }

}
