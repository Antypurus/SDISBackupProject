package Tests.FileHandlerTest;

import FileHandler.FileMetadataObtainer;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;

public class FileIDCalculatorTests extends FileMetadataObtainer{

    private File file;
    private String testFile = "ReferenceTestFile";
    private String testFileStartChunk;
    private BasicFileAttributes fileAttribs;
    private Path filePath;

    private FileIDCalculatorTests() throws IOException{
        this.file = new File(this.testFile);
        FileInputStream file = new FileInputStream(this.file);
        byte[] bytes = new byte[100];
        int read = file.read(bytes,0,100);
        if(read==0){
            throw new IOException();
        }
        this.testFileStartChunk = new String(bytes);
        this.filePath = Paths.get(this.testFile);
        this.fileAttribs = Files.readAttributes(this.filePath,BasicFileAttributes.class);
    }

    @Test
    public void computeMetadataHashTest(){

    }
}
