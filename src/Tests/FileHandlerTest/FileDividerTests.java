package Tests.FileHandlerTest;

import FileHandler.FileDivider;
import javafx.util.Pair;
import org.junit.Test;

import static org.junit.Assert.*;

public class FileDividerTests extends FileDivider {

    public FileDividerTests() {
        super(5);
    }

    @Test
    public void chunkSizeRequirementCalculatorAllLargeTest() {
        int testSize = 10;

        Pair<Integer, Integer> result =
                this.calculateFileChunkSizeRequirement(testSize);

        assertEquals(2, result.getKey().intValue());
        assertEquals(0, result.getValue().intValue());
    }

    @Test
    public void chunkSizeRequirementCalculatorLargeAndSmallTest() {
        int testSize = 13;

        Pair<Integer, Integer> result =
                this.calculateFileChunkSizeRequirement(testSize);

        assertEquals(2, result.getKey().intValue());
        assertEquals(1, result.getValue().intValue());
    }

    @Test
    public void chunkSizeRequirementCalculatorAllSmallTest() {
        int testSize = 3;

        Pair<Integer, Integer> result =
                this.calculateFileChunkSizeRequirement(testSize);

        assertEquals(0, result.getKey().intValue());
        assertEquals(1, result.getValue().intValue());
    }

    @Test
    public void chunkContainerGeneratorAllLargeTest() {
        int testSize = 10;

        String[] result = this.fileChunkContainerGenerator(testSize);

        assertEquals(2, result.length);
    }

    @Test
    public void chunkContainerGeneratorAllSmallTest() {
        int testSize = 3;

        String[] result = this.fileChunkContainerGenerator(testSize);

        assertEquals(1, result.length);
    }

    @Test
    public void chunkContainerGeneratorSmallAndLargeTest() {
        int testSize = 13;

        String[] result = this.fileChunkContainerGenerator(testSize);

        assertEquals(3, result.length);
    }
    
    @Test
    public void fileDivisionAllLargeTest(){
        int testSize = 15;

        String[] result = this.fileChunkContainerGenerator(testSize);

        assertEquals(3, result.length);
    }

    @Test
    public void divideFileIntoChunksAllSmallTest(){
        String testStr = "test";
        String[] result = this.divideFileIntoChunks(testStr);

        assertEquals(testStr,result[0]);
    }

    @Test
    public void divideFileIntoChunksSmallAndLargeTest(){
        String testStr = "test1Test2test";
        String[] result = this.divideFileIntoChunks(testStr);
        assertEquals("test1",result[0]);
        assertEquals("Test2",result[1]);
        assertEquals("test",result[2]);
    }

    @Test
    public void divideFileIntoChunksAllLargeTest(){
        String testStr = "test1test2test3";
        String[] result = this.divideFileIntoChunks(testStr);

        assertEquals("test1",result[0]);
        assertEquals("test2",result[1]);
        assertEquals("test3",result[2]);
    }
}
