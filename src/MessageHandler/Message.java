package MessageHandler;

public abstract class Message {

    protected String        protocolVersion = "1.0";
    protected int           senderID;
    protected String        FileID;
    protected int           chunkNO;
    protected String        messageType;

    /**
     *
     * @return
     */
    @Override
    public abstract String toString();

    /**
     *
     * @return
     */
    public String getProtocolVersion() {
        return protocolVersion;
    }

    /**
     *
     * @param protocolVersion
     */
    public void setProtocolVersion(String protocolVersion) {
        this.protocolVersion = protocolVersion;
    }

    /**
     *
     * @return
     */
    public int getSenderID() {
        return senderID;
    }

    /**
     *
     * @param senderID
     */
    public void setSenderID(int senderID) {
        this.senderID = senderID;
    }

    /**
     *
     * @return
     */
    public String getFileID() {
        return FileID;
    }

    /**
     *
     * @param fileID
     */
    public void setFileID(String fileID) {
        FileID = fileID;
    }

    /**
     *
     * @return
     */
    public int getChunkNO() {
        return chunkNO;
    }

    /**
     *
     * @param chunkNO
     */
    public void setChunkNO(int chunkNO) {
        this.chunkNO = chunkNO;
    }

    /**
     *
     * @return
     */
    public String getMessageType() {
        return messageType;
    }

    /**
     *
     * @param messageType
     */
    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    /**
     *
     * @param message
     * @return
     */
    public static Message ParseMessage(String message){
        return null;
    }

}
