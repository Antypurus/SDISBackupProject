package FileHandler;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;

public class FileMetadataObtainer {

    private String                  filepath;
    private Path                    file;
    private BasicFileAttributes     attributes;

    /**
     * FileMetadataObtainer constructor for testing purposes
     */
    protected FileMetadataObtainer() {
    }

    /**
     * FileMetadataObtainer constructor obtains the attributes of a file using the filepath
     * @param filepath filepath to file
     * @throws IOException in case the file is not found
     */
    public FileMetadataObtainer(String filepath) throws IOException {
        this.filepath = filepath;
        this.file = Paths.get(filepath);
        this.attributes = Files.readAttributes(this.file,BasicFileAttributes.class);
    }

    /**
     * Changes the active file, to do this it will search for the new file and read its attributes
     * @param filepath filepath to the file
     * @throws IOException in case the file is not found
     */
    public void changeFilePath(String filepath) throws IOException {
        if(!this.filepath.equals(filepath)) {
            this.filepath = filepath;
            this.file = Paths.get(filepath);
            this.attributes = Files.readAttributes(this.file, BasicFileAttributes.class);
        }
    }

    /**
     * Obtains the creation time of the currently set file
     * @return FileTime Object with the creation date of the file
     */
    public FileTime getFileCreationTime(){
        return this.attributes.creationTime();
    }

    /**
     * Obtains the last time the file was altered
     * @return FileTime object time with the last alter time
     */
    public FileTime getFileAlterTime(){
        return this.attributes.lastModifiedTime();
    }

    /**
     * Obtains the last time the file was accessed
     * @return FileTime object with the last time the file was accessed
     */
    public FileTime getFileLastAccessTime(){
        return this.attributes.lastAccessTime();
    }

    /**
     * Obtains the size of the currently set file
     * @return long with the size of the file
     */
    public long getFileSize(){
        return this.attributes.size();
    }

    /**
     *  THIS METHODS RETURN IS CAUSING PROBLEMS AS SUCH IS HAS BEEN DISABLED FOR EXTERNAL USE
     *  Obtains the unique file key of the file
     * @return Object object that servers as file key
     */
    private Object getFileKey(){
        return this.attributes.fileKey();
    }
}
