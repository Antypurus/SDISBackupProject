package Tests.UtilTests;

import Utils.SHA256Hash;
import org.junit.Test;
import static org.junit.Assert.*;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class SHA256Tests extends SHA256Hash {

    @Test
    public void byteEncodingTest() throws NoSuchAlgorithmException {
        String toTest = "testString".trim();
        MessageDigest digest = null;
        digest = MessageDigest.getInstance("SHA-256");
        digest.update(toTest.getBytes());
        byte[] bytemsg = digest.digest();
        assertEquals(Arrays.toString(bytemsg),Arrays.toString(stringToSHA256Bytes(toTest)));
    }

}
