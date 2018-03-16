package FileHandler;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;

public class FileMetadataObtainer {

    private String filepath = "";
    private Path file = null;
    private BasicFileAttributes attributes = null;

    /**
     *
     * @param filepath
     * @throws IOException
     */
    public FileMetadataObtainer(String filepath) throws IOException {
        this.filepath = filepath;
        this.file = Paths.get(filepath);
        this.attributes = Files.readAttributes(this.file,BasicFileAttributes.class);
    }

    /**
     *
     * @param filepath
     * @throws IOException
     */
    public void changeFilePath(String filepath) throws IOException {
        if(this.filepath!=filepath) {
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
}
