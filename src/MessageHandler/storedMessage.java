package MessageHandler;

public class storedMessage extends Message{

    /**
     * constructor for the stored message
     * @param senderID the sender id
     * @param fileID the file if
     * @param chunkNo the chunknumber
     */
    public storedMessage(int senderID,String fileID,int chunkNo){
        this.messageType = MessageHandler.messageType.STORED;
        this.senderID = senderID;
        this.FileID = fileID;
        this.chunkNO = chunkNo;
    }

    /**
     * Converts the message to its string representation
     * @return the string representing message
     */
    @Override
    public String toString() {
        String ret = this.messageType.name()+" "+this.protocolVersion+" ";
        ret+=this.senderID+" "+this.FileID+" "+this.chunkNO+" \r\n \r\n";
        return ret.trim();
    }
}
