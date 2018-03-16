package Utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA256Hash {

    protected static MessageDigest digest;

    static {
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public SHA256Hash(){
    }

    /**
     * hashes a given string to an byte array with the string in sha256 format
     * @param string string to hash
     * @return byte array with the hashed string
     */
    protected byte[] stringToSHA256Bytes(String string) {
        return digest.digest(string.getBytes());
    }

    /**
     * converts a byte array representing a string encoded in sha256 to a string representation
     * @param encodedhash byte array
     * @return string representation of the sha256 byte array
     */
    protected String SHA256ByteArrayToString(byte[] encodedhash){
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
    protected String hashToSHA256(String string){
        byte[] bytes    = this.stringToSHA256Bytes(string);
        String sha256String   = this.SHA256ByteArrayToString(bytes);
        return sha256String;
    }

    /**
     * hashes a string using sha256
     * @param string string to be hashed
     * @return string with the resulting hash
     */
    public String Hash(String string) {
        return this.hashToSHA256(string);
    }

}
