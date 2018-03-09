package FileHandler;

import javafx.util.Pair;
import java.util.ArrayList;

public class FileDivider {

    protected int maxFileSize = 0;

    public FileDivider(int maxFileSize){
        this.maxFileSize = maxFileSize;
    }

    /**
     *
     * @param fileSize
     * @return
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
        return ret;
    }

    protected String[] fileChunkContainerGenerator(int fileSize){
        Pair<Integer,Integer> chunkSizeRequirements = this.calculateFileChunkSizeRequirement(fileSize);

        //returns a string array with enough indices for the ammount of chunks required
        return new String[chunkSizeRequirements.getKey()+chunkSizeRequirements.getValue()];
    }

    protected String[] divideFileIntoChunks(String file){
        String[] fileChunks     = this.fileChunkContainerGenerator(file.length());//the array that will contain the file chunks
        int nChunks             = fileChunks.length;//the number of chunks

        //copies the first chunks of the file
        for(int i=0;i<nChunks;++i){
           fileChunks[i] = file.substring(i*this.maxFileSize, i*this.maxFileSize+this.maxFileSize);
        }
        //copies the smaller chunk fo the file
        fileChunks[nChunks-1] = file.substring(nChunks*this.maxFileSize,nChunks*this.maxFileSize+(file.length()-nChunks*this.maxFileSize));
        return fileChunks;
    }

}
