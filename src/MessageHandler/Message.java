package MessageHandler;

public abstract class Message {

    protected String        protocolVersion = "1.0";    // The protocol version 1.0 by default
    protected int           senderID;                   // The Sender ID , Message Specific
    protected String        FileID;                     // The File ID , File specific
    protected int           chunkNO;                    // The Chunk Number of the message, message and file specific
    protected String        messageType;                // The Message Type, Message Specific

    /**
     * Converts the Message class to its string representation
     * @return Message String Representation
     */
    @Override
    public abstract String toString();

    /**
     * Obtain the message protocol version
     * @return The protocol version
     */
    public String getProtocolVersion() {
        return protocolVersion;
    }

    /**
     * Modifies the message protocol version
     * @param protocolVersion value to set the protocol version to
     */
    public void setProtocolVersion(String protocolVersion) {
        this.protocolVersion = protocolVersion;
    }

    /**
     * obtain the sender Id of the message
     * @return the sender id
     */
    public int getSenderID() {
        return senderID;
    }

    /**
     * set the sender id of the message
     * @param senderID value to give the sender id
     */
    public void setSenderID(int senderID) {
        this.senderID = senderID;
    }

    /**
     * obtain the file id of the message
     * @return the file id
     */
    public String getFileID() {
        return FileID;
    }

    /**
     * set the file id of the message
     * @param fileID value to give the file id
     */
    public void setFileID(String fileID) {
        FileID = fileID;
    }

    /**
     * obtain the chunk number of the message if it has one
     * @return the chunk number
     */
    public int getChunkNO() {
        return chunkNO;
    }

    /**
     * set the value of the message chunk number
     * @param chunkNO value to give the chunk number
     */
    public void setChunkNO(int chunkNO) {
        this.chunkNO = chunkNO;
    }

    /**
     * obtain the message type of the message
     * @return the message type
     */
    public String getMessageType() {
        return messageType;
    }

    /**
     * set the message type of the message
     * @param messageType value to give the message type
     */
    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    /**
     * Parse the message and create the appropriate message object
     * @param message the message to be parsed
     * @return message object representing the message
     */
    public static Message ParseMessage(String message){
        return null;
    }

}