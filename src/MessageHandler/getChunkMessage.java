package MessageHandler;

import java.io.IOException;

public class getChunkMessage extends Message{

    /**
     * constructor for the get chunk message for when the file id is known
     * @param senderID the sender id
     * @param fileID the file id
     * @param chunkNo the chunk number
     */
    public getChunkMessage(int senderID,String fileID,int chunkNo){
        this.messageType = MessageHandler.messageType.GETCHUNK;
        this.senderID = senderID;
        this.FileID = fileID;
        this.chunkNO = chunkNo;
    }

    /**
     * constructor for the get chunk message for when the file id is not knowns
     * @param senderID the sender id
     * @param chunkNo the chunk number
     * @param startChunk string with small chunk of the beggining of the file being sent
     * @param filename filename of the file being sent
     * @throws IOException in case the specified file is not found
     */
    public getChunkMessage(int senderID,int chunkNo,String startChunk,String filename) throws IOException {
        this.messageType = MessageHandler.messageType.GETCHUNK;
        this.senderID = senderID;
        this.chunkNO = chunkNo;
        this.FileID = this.calculateFileID(startChunk,filename);
    }

    /**
     * converts the message to its string representation
     * @return string representing the message
     */
    @Override
    public String toString() {
        String ret = this.messageType.name()+" "+this.protocolVersion+" ";
        ret+=this.senderID+" "+this.FileID+" "+this.chunkNO+" \r\n \r\n";
        return ret;
    }
}
