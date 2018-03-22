package MessageHandler;

import java.io.IOException;

public class deleteMessage extends Message{

    /**
     * constructor for the delete message for when the file id is known
     * @param senderID the sender id
     * @param fileID the file id
     */
    public deleteMessage(int senderID,String fileID){
        this.messageType = MessageHandler.messageType.DELETE;
        this.senderID = senderID;
        this.FileID = fileID;
    }

    /**
     * constructor for the delete message for when the fil eid is not known
     * @param senderID the sender id
     * @param startChunk small chunk from the beggining of the file being sent
     * @param filename filename of the file being sent
     * @throws IOException in case the specified file is not found
     */
    public deleteMessage(int senderID,String startChunk,String filename) throws IOException{
        this.messageType = MessageHandler.messageType.DELETE;
        this.senderID = senderID;
        this.FileID = this.calculateFileID(startChunk,filename);
    }

    /**
     * converts the message to its string representation
     * @return string representing the message
     */
    @Override
    public String toString() {
        String ret=this.messageType.name()+" "+this.protocolVersion+" ";
        ret+=this.senderID+" "+this.FileID+" \r\n \r\n";
        return ret.trim();
    }
}
