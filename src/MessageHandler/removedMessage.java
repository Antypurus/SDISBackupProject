package MessageHandler;

public class removedMessage extends  Message{

    /**
     *  constructor for the removed message
     * @param senderID the sender id
     * @param fileID the file id
     * @param chunkNo the chunk number
     */
    public removedMessage(int senderID,String fileID,int chunkNo){
        this.messageType = MessageHandler.messageType.REMOVED;
        this.chunkNO = chunkNo;
        this.senderID = senderID;
        this.FileID = fileID;
    }

    /**
     * converts the message to its string representation
     * @return string represeting message
     */
    @Override
    public String toString() {
        String ret = this.messageType.name()+" "+this.protocolVersion+" ";
        ret+=this.FileID+" "+this.chunkNO+" \r\n \r\n";
        return ret.trim();
    }
}
