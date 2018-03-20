package Tests.UtilTests;

import Utils.SHA256Hash;
import org.junit.Test;
import static org.junit.Assert.*;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class SHA256Tests extends SHA256Hash {

    private  MessageDigest digest =  MessageDigest.getInstance("SHA-256");

    public SHA256Tests() throws NoSuchAlgorithmException {
    }

    @Test
    public void byteEncodingTest() {
        String toTest = "testString";

        byte[] bytemsg = digest.digest(toTest.getBytes());
        byte[] result = this.stringToSHA256Bytes(toTest);

        assertEquals(Arrays.toString(bytemsg),Arrays.toString(result));
    }

    @Test
    public void encodedByteToStringHashTest() {
        String toTest = "testString";
        String sha256equiv = "4acf0b39d9c4766709a3689f553ac01ab550545ffa4544dfc0b2cea82fba02a3";

        byte[] bytemsg = digest.digest(toTest.getBytes());
        String encodedTestString = this.SHA256ByteArrayToString(bytemsg);

        assertEquals(sha256equiv,encodedTestString);
    }

    @Test
    public void stringHashTest(){
        String toTest = "testString";
        String sha256equiv = "4acf0b39d9c4766709a3689f553ac01ab550545ffa4544dfc0b2cea82fba02a3";

        String result = this.hashToSHA256(toTest);

        assertEquals(sha256equiv,result);
    }

    @Test
    public void completeStringSha256HashTest(){
        String toTest = "testString";
        String sha256equiv = "4acf0b39d9c4766709a3689f553ac01ab550545ffa4544dfc0b2cea82fba02a3";

        String result = this.Hash(toTest);
        assertEquals(sha256equiv,result);
    }

}
