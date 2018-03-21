package MessageHandler;

public class removedMessage extends  Message{

    public removedMessage(int senderID,String fileID,int chunkNo){
        this.messageType = "REMOVED";
        this.chunkNO = chunkNo;
        this.senderID = senderID;
        this.FileID = fileID;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        String ret = this.messageType+" "+this.protocolVersion+" ";
        ret+=this.FileID+" "+this.chunkNO+" \r\n \r\n";
        return ret.trim();
    }
}
