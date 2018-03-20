package FileHandler;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FileStreamer {
    private int chunkSize;
    private FileInputStream file = null;
    private String filename;

    /**
     * Constructor fot the file streamer class object , this constructor open the file to stream read and
     * as such can throw io exception more specifically file not found errors
     *
     * @param maxChunkSize the max size to read at a time
     * @param filename     the file to read
     * @throws IOException when the file is not found
     */
    public FileStreamer(int maxChunkSize, String filename) throws IOException {
        this.chunkSize = maxChunkSize;
        this.filename = filename;
        this.file = new FileInputStream(this.filename);
    }

    /**
     * Reads the next chunk of the file, automatically closes the file in case there is nothing else to read
     * @return the chunk read as a string
     * @throws IOException in case there is an error while reading
     */
    public String read() throws IOException {
        if(this.file==null){
            this.setNewFile(this.filename);
            if(this.file==null){
                return null;
            }
        }
        byte[] bytes = new byte[this.chunkSize];
        int read = this.file.read(bytes);
        if (read == -1) {
            this.close();
            return null;
        }
        return new String(bytes);
    }

    /**
     * Closes the currently open file
     *
     * @throws IOException in case the file is not properly closed
     */
    public void close() throws IOException {
        this.file.close();
    }

    /**
     * Sets a new file to read and attempts to open it, logs if an error occurs and sets the file to null,also closes the
     * previously opened file and logs if an error occurs when closing
     *
     * @param filename the file to open
     * @return 0 if the file is found and opened or -1 if the file is not found and not opened
     */
    public int setNewFile(String filename) {
        this.filename = filename;
        try {
            this.close();
        } catch (IOException e) {
            System.out.println("[ERROR] @FileStreamer:setNewFile:closeOldFile - Failed to close file");
        }
        try {
            this.file = new FileInputStream(this.filename);
        } catch (FileNotFoundException e) {
            System.out.println("[ERROR] @FileStreamer:setNewFile:openNewFile - Failed to find specified file:" + filename);
            this.file = null;
            return -1;
        }
        return 0;
    }

    /**
     * Sets the max size of the chunk to read per read
     *
     * @param size the size of the new max read amount
     */
    public void setMaxChunkSize(int size) {
        this.chunkSize = size;
    }

    /**
     * returns the current max chunk read size
     *
     * @return the chunk read size
     */
    public int getChunkSize() {
        return this.chunkSize;
    }

    /**
     * returns the current files filename
     *
     * @return the filename of the currently opened file
     */
    public String getCurrentFile() {
        return this.filename;
    }

}
