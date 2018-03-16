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
        this.filepath = filepath;
        this.file = Paths.get(filepath);
        this.attributes = Files.readAttributes(this.file,BasicFileAttributes.class);
    }

    /**
     *
     * @return
     */
    public FileTime getFileCreationTime(){
        return this.attributes.creationTime();
    }

}
