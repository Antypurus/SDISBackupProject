package MessageHandler;

public abstract class Message {

    protected String        protocolVersion = "1.0";
    protected int           senderID;
    protected String        FileID;
    protected int           chunkNO;
    protected String        messageType;

    @Override
    public abstract String toString();

    public String getProtocolVersion() {
        return protocolVersion;
    }

    public void setProtocolVersion(String protocolVersion) {
        this.protocolVersion = protocolVersion;
    }

    public int getSenderID() {
        return senderID;
    }

    public void setSenderID(int senderID) {
        this.senderID = senderID;
    }

    public String getFileID() {
        return FileID;
    }

    public void setFileID(String fileID) {
        FileID = fileID;
    }

    public int getChunkNO() {
        return chunkNO;
    }

    public void setChunkNO(int chunkNO) {
        this.chunkNO = chunkNO;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

}
