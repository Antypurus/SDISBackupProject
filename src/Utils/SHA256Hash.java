package Utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA256Hash {

    /**
     * hashes a given string to an byte array with the string in sha256 format
     * @param string string to hash
     * @return byte array with the hashed string
     */
    private static byte[] stringToSHA256Bytes(String string) {
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return digest.digest(string.getBytes());
    }

    /**
     * converts a byte array representing a string encoded in sha256 to a string representation
     * @param encodedhash byte array
     * @return string representation of the sha256 byte array
     */
    private static String SHA256ByteArrayToString(byte[] encodedhash){
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < encodedhash.length; i++) {
            String hex = Integer.toHexString(0xff & encodedhash[i]);
            if(hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }

    /**
     * hashes a string using sha256
     * @param string string to be hashed
     * @return string with the resulting hash
     */
    private static String hashToSHA256(String string){
        byte[] bytes    = stringToSHA256Bytes(string);
        String sha256String   = SHA256ByteArrayToString(bytes);
        return sha256String;
    }

    /**
     * hashes a string using sha256
     * @param string string to be hashed
     * @return string with the resulting hash
     */
    public static String hashWithSHA256(String string) {
        return hashToSHA256(string);
    }

}
