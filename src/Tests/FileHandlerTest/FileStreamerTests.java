package Tests.FileHandlerTest;

import FileHandler.FileStreamer;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileStreamerTests extends FileStreamer{

    private String                  testFile = "ReferenceTestFile.tst";

    public FileStreamerTests() throws IOException {
        this.filename = this.testFile;
        FileOutputStream f = new FileOutputStream(this.testFile);
        String toWrite = "1234567890t";
        f.write(toWrite.getBytes());
        f.close();
    }

    @Test
    public void readTest() throws IOException {
        this.chunkSize = 2;
        assertEquals("12",this.read());
        assertEquals("34",this.read());
        assertEquals("56",this.read());
        assertEquals("78",this.read());
        assertEquals("90",this.read());
        this.close();
    }

    @Test
    public void closeFileFileNULLTest() throws IOException {
        this.file = null;
        this.close();
        assertEquals(null,this.file);
    }


    @Test
    public void closeFileFileNotNULLTest() throws IOException {
        this.file = new FileInputStream(this.filename);
        this.close();
        assertEquals(null,this.file);
    }

}
