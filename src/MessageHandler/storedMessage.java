package MessageHandler;

public class storedMessage extends Message{

    /**
     *
     * @param senderID
     * @param fileID
     * @param chunkNo
     */
    public storedMessage(int senderID,String fileID,int chunkNo){
        this.messageType = MessageHandler.messageType.STORED;
        this.senderID = senderID;
        this.FileID = fileID;
        this.chunkNO = chunkNo;
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
