package FileHandler;

import javafx.util.Pair;
import java.util.ArrayList;

/**
 *  ATTENTION: THIS CLASS SHOULD ONLY BE USED FOR RELATIVELY SMALL FILES
 *  IF THE INTENDED FILE IS ANYWHERE NEAR TO THE PROTOCOL MAX OF 64GB THIS
 *  CLASS MUST NOT BE USED, IN THAT CASE CHUNKS SHOULD BE READ IN 64k CHUNKS
 *  AND STREEAMED THEN ,NOT PRELOADED INTO MEMORY.
 */
public class FileDivider {

    protected int maxFileSize = 0;
    private Pair<Integer,Integer>SpacialRequirements = null;

    public FileDivider(int maxFileSize){
        this.maxFileSize = maxFileSize;
    }

    /**
     * calculates the number of chunks of max size and of smaller size required to store the file
     * @param fileSize the size of the file to be split
     * @return a pair where the key is the number of max size chunks and the value is the number of smaller sized chunks
     */
    protected Pair<Integer,Integer> calculateFileChunkSizeRequirement(int fileSize){
        int fullChunks  = 0;    //the number of max sized chunks
        int smChunks    = 0;    //the number of smaller chunks is either 0 or 1

        //  calculates the number of max sized chunks that are needed
        fullChunks = Math.floorDiv(fileSize, this.maxFileSize);

        /*
        *   calculates whether a smaller sized chunk is required or not
        *   at most there can only be 1 smaller chunk all other must be
        *   of max size
        */
        if((fileSize-(fullChunks*this.maxFileSize))!=0){
            smChunks++;
        }

        Pair<Integer,Integer> ret = new Pair<Integer,Integer>(fullChunks,smChunks);
        this.SpacialRequirements = ret;
        return ret;
    }

    /**
     * creates an array of strings used to keep the various chunks of the file
     * @param fileSize size of the file to be divied
     * @return array to contain the file chunks
     */
    protected String[] fileChunkContainerGenerator(int fileSize){
        Pair<Integer,Integer> chunkSizeRequirements = this.calculateFileChunkSizeRequirement(fileSize);

        //returns a string array with enough indices for the ammount of chunks required
        return new String[chunkSizeRequirements.getKey()+chunkSizeRequirements.getValue()];
    }

    /**
     * divides the file into chunks of the max size specified
     * @param file the file to be split
     * @return array of strings with the various chunks
     */
    protected String[] divideFileIntoChunks(String file){
        String[] fileChunks                         = this.fileChunkContainerGenerator(file.length());//the array that will contain the file chunks
        Pair<Integer, Integer> spacialReqs = null;
        if(this.SpacialRequirements==null) {
            spacialReqs = this.calculateFileChunkSizeRequirement(file.length());//the number of chunks
        }else{
            spacialReqs = this.SpacialRequirements;
        }
        int nChunks                                 = fileChunks.length;

        //copies the first chunks of the file
        for(int i=0;i<spacialReqs.getKey();++i){
            int startIndex = i*this.maxFileSize;
            fileChunks[i] = file.substring(startIndex,i*this.maxFileSize+this.maxFileSize);
        }
        //copies the smaller chunk fo the file
        if(spacialReqs.getValue()!=0) {
            int indexToread = (spacialReqs.getKey()) * this.maxFileSize;
            if (indexToread < 0) {
                indexToread = 0;
            }
            fileChunks[nChunks - 1] = file.substring(indexToread, file.length());
        }
        return fileChunks;
    }

    /**
     *  splits the file into chunks of the specified max size
     * @param file file to be split
     * @return array with the various chunks in order
     */
    public String[] splitFile(String file){
        return this.divideFileIntoChunks(file);
    }

    public int getMaxFileSize(){
        return this.maxFileSize;
    }

    public void setMaxFileSize(int maxFileSize){
        this.maxFileSize = maxFileSize;
    }

}
