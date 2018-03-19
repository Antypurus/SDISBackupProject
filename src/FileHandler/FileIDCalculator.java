package FileHandler;

import java.io.IOException;
import Utils.SHA256Hash;

public class FileIDCalculator {

    protected static SHA256Hash         hasher = null;
    protected int                       senderID;
    protected String                    referenceChunk;
    protected FileMetadataObtainer      meta=null;
    protected String                    hashedID = null;

    protected FileIDCalculator(){}

    /**
     * Computes a hash of the file creation time and last alter time metadata
     * @return int with the hash code representing the relevant file metadata
     */
    protected long computerMetadataHash(){
        int ret = 0;
        if(this.meta!=null) {
            ret = meta.getFileCreationTime().hashCode() * meta.getFileAlterTime().hashCode();
            ret += meta.getFileSize();
        }
        return ret;
    }

    /**
     * Computes the string that needs to be hashed as a file id
     * @return string to hash into the final file id
     */
    protected String computedFileIDStringToHash(){
        String ret = "";
        ret += this.referenceChunk + this.computerMetadataHash() + this.senderID;
        return ret;
    }

    /**
     * hashes the file id using the sha-256 encryption algorithm
     * @param fileID string representing the unhashed file id
     * @return sha-256 hash of the fileID
     */
    protected String HashFileID(String fileID){
        if(hasher==null) {
            hasher = new SHA256Hash();
        }
        return hasher.Hash(fileID);
    }

    /**
     * junction of the fileID string calculation and hashing
     * @return sha-256 hashed file id
     */
    protected String calculateFinalFileID(){
        String fileID = this.computedFileIDStringToHash();
        return this.HashFileID(fileID);
    }

    /**
     * Calculates the file ID using relevant file metadata that has been supplied,
     * the send ID and a small chunk of the file, then hashes this using sha-256
     * @return the FileID of this file hashed with sha-256
     */
    public String calculateFileID(){
        String fileID = this.calculateFinalFileID();
        this.hashedID = fileID;
        return fileID;
    }

    /**
     * Obtains the present file ID if available
     * @return the file ID of the specified file
     */
    public String getFileID(){
        if(this.hashedID!=null){
            return this.hashedID;
        }
        return "";
    }

    /**
     * Constructor that takes the supplied data and requests the calculation of the file ID
     * @param startChunk string containing small chunk of the beggining of the file
     * @param filepath filepath to the file whom´s file id we are calculating
     * @param senderID the sender id of this initiator peer
     * @throws IOException when the filepath supplied doesnt allows us to find the requested file
     */
    public FileIDCalculator(String startChunk,String filepath,int senderID) throws IOException {
        hasher = new SHA256Hash();
        this.senderID = senderID;
        this.referenceChunk = startChunk;
        this.meta = new FileMetadataObtainer(filepath);
        this.calculateFileID();
    }    

}
