package Tests.FileHandlerTest;

import FileHandler.FileIDCalculator;
import FileHandler.FileMetadataObtainer;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;

public class FileIDCalculatorTests extends FileIDCalculator{

    private File file;
    private String testFile = "ReferenceTestFile.tst";
    private String testFileStartChunk = "testing12456";
    private BasicFileAttributes fileAttribs;
    private Path filePath;

    public FileIDCalculatorTests() throws IOException{

        FileOutputStream f = new FileOutputStream(this.testFile);
        f.write("testing12456".getBytes());
        f.close();

        this.file = new File(this.testFile);

        this.filePath = Paths.get(this.testFile);
        this.fileAttribs = Files.readAttributes(this.filePath,BasicFileAttributes.class);
    }

    @Test
    public void computeMetadataHashTest() throws IOException{
        int val = this.fileAttribs.creationTime().hashCode() * this.fileAttribs.lastModifiedTime().hashCode();
        val+= this.fileAttribs.size();

        this.meta = new FileMetadataObtainer(this.testFile);
        assertEquals(val,this.computerMetadataHash());
    }
}
