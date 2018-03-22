package MessageHandler;

import java.io.IOException;

public class getChunkMessage extends Message{

    /**
     *
     * @param senderID
     * @param fileID
     * @param chunkNo
     */
    public getChunkMessage(int senderID,String fileID,int chunkNo){
        this.messageType = MessageHandler.messageType.GETCHUNK;
        this.senderID = senderID;
        this.FileID = fileID;
        this.chunkNO = chunkNo;
    }

    /**
     *
     * @param senderID
     * @param chunkNo
     * @param startChunk
     * @param filename
     * @throws IOException
     */
    public getChunkMessage(int senderID,int chunkNo,String startChunk,String filename) throws IOException {
        this.messageType = MessageHandler.messageType.GETCHUNK;
        this.senderID = senderID;
        this.chunkNO = chunkNo;
        this.FileID = this.calculateFileID(startChunk,filename);
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        String ret = this.messageType.name()+" "+this.protocolVersion+" ";
        ret+=this.senderID+" "+this.FileID+" "+this.chunkNO+" \r\n \r\n";
        return ret.trim();
    }
}
