package Utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA256Hash {

    /**
     *
     * @param string
     * @return
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
     *
     * @param encodedhash
     * @return
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
     *
     * @param string
     * @return
     */
    private static String hashToSHA256(String string){
        byte[] bytes    = stringToSHA256Bytes(string);
        String sha256String   = SHA256ByteArrayToString(bytes);
        return sha256String;
    }

    /**
     *
     * @param string
     * @return
     */
    public static String hashWithSHA256(String string) {
        return hashToSHA256(string);
    }

}
