package MessageHandler;

import java.io.IOException;

public class deleteMessage extends Message{

    /**
     *
     * @param senderID
     * @param fileID
     */
    public deleteMessage(int senderID,String fileID){
        this.messageType = MessageHandler.messageType.DELETE;
        this.senderID = senderID;
        this.FileID = fileID;
    }

    /**
     *
     * @param senderID
     * @param startChunk
     * @param filename
     * @throws IOException
     */
    public deleteMessage(int senderID,String startChunk,String filename) throws IOException{
        this.messageType = MessageHandler.messageType.DELETE;
        this.senderID = senderID;
        this.FileID = this.calculateFileID(startChunk,filename);
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        String ret=this.messageType.name()+" "+this.protocolVersion+" ";
        ret+=this.senderID+" "+this.FileID+" \r\n \r\n";
        return ret.trim();
    }
}
