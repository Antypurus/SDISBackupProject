package MessageHandler;

public class chunkMessage extends Message{

    /**
     * constructor for the chunk message it
     * @param senderID the sender id
     * @param chunkNO the chunk number
     * @param FileID the file id
     */
    public chunkMessage(int senderID,int chunkNO,String FileID){
        this.messageType = MessageHandler.messageType.CHUNK;
        this.senderID = senderID;
        this.chunkNO = chunkNO;
        this.FileID = FileID;
    }

    /**
     * convert the message to its string representation
     * @return string representing the message
     */
    @Override
    public String toString() {
        if(this.body==null){
            return null;
        }
        String ret = this.messageType.name() + " " + this.protocolVersion;
        ret += " " + this.senderID + " " + this.FileID + " ";
        ret += this.chunkNO  + " \r\n"+" \r\n"+" "+this.body;
        return ret;
    }
}
