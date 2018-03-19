package FileHandler;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FileStreamer {
    protected int chunkSize;
    protected FileInputStream file= null;
    protected String filename;

    /**
     *
     * @param maxChunkSize
     * @param filename
     * @throws IOException
     */
    public FileStreamer(int maxChunkSize,String filename) throws IOException{
        this.chunkSize = maxChunkSize;
        this.filename = filename;
        this.file = new FileInputStream(this.filename);
    }

    /**
     *
     * @return
     * @throws IOException
     */
    public String read() throws IOException {
        if(this.file!=null){
            String ret ="";
            byte[] bytes = new byte[this.chunkSize];
            int read = this.file.read(bytes);
            if(read==-1){
                return null;
            }
            ret = new String(bytes);
            return ret;
        }
        return null;
    }

    /**
     *
     * @throws IOException
     */
    public void close() throws IOException {
        this.file.close();
    }

    /**
     *
     * @param filename
     * @throws FileNotFoundException
     */
    public void setNewFile(String filename) throws FileNotFoundException {
        this.filename = filename;
        this.file = new FileInputStream(this.filename);
    }

    /**
     *
     * @param size
     */
    public void setMaxChunkSize(int size){
        this.chunkSize = size;
    }

    /**
     *
     * @return
     */
    public int getChunkSize(){
        return this.chunkSize;
    }

    /**
     *
     * @return
     */
    public String getCurrentFile(){
        return this.filename;
    }

}
