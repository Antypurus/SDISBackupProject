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

    private File                    file;
    private String                  testFile = "ReferenceTestFile.tst";
    private String                  testFileStartChunk = "testing12456";
    private BasicFileAttributes     fileAttribs;
    private Path                    filePath;
    private long                     metadataHashVal = 0;

    public FileIDCalculatorTests() throws IOException{
        FileOutputStream f = new FileOutputStream(this.testFile);
        f.close();

        this.file = new File(this.testFile);
        this.filePath = Paths.get(this.testFile);
        this.fileAttribs = Files.readAttributes(this.filePath,BasicFileAttributes.class);

        this.meta = new FileMetadataObtainer(this.testFile);
    }

    @Test
    public void computeMetadataHashTest() {
        long val = this.fileAttribs.creationTime().hashCode() * this.fileAttribs.lastModifiedTime().hashCode();
        val+= this.fileAttribs.size();
        this.metadataHashVal = val;
        assertEquals(val,this.computerMetadataHash());
    }

    @Test
    public void computerFileIDStringToHashTest(){
        int testSenderID = 1;
        if(this.metadataHashVal==0){
            int val = this.fileAttribs.creationTime().hashCode() * this.fileAttribs.lastModifiedTime().hashCode();
            val+= this.fileAttribs.size();
            this.metadataHashVal = val;
        }
        String str = this.testFileStartChunk + this.metadataHashVal + testSenderID;
        this.senderID = testSenderID;
        this.referenceChunk = this.testFileStartChunk;
        assertEquals(str,this.computedFileIDStringToHash());
    }
}
