package MessageHandler;

public class chunkMessage extends Message{

    private String body = null;

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
        ret = ret.trim();
        return ret;
    }
}
