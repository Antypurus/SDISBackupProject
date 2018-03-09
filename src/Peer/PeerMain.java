package Peer;

import java.io.IOException;
import java.nio.file.FileStore;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PeerMain {
  public static void main(String args[]) throws NoSuchAlgorithmException {
    for (Path root : FileSystems.getDefault().getRootDirectories()) {
      System.nanoTime();
      System.out.print(root + ": ");
      try {
        FileStore store = Files.getFileStore(root);
        double available = (store.getUsableSpace() / (Math.pow(10f, 9f)));
        double totalS = (store.getTotalSpace() / (Math.pow(10f, 9f)));
        System.out.println("available=" + available + ", total=" + totalS);
      } catch (IOException e) {
        System.out.println("error querying space: " + e.toString());
      }
    }
      String str = "test";
      MessageDigest digest = MessageDigest.getInstance("SHA-256");
      byte[] encodedhash =
              digest.digest(str.getBytes());

      StringBuffer hexString = new StringBuffer();
      for (int i = 0; i < encodedhash.length; i++) {
          String hex = Integer.toHexString(0xff & encodedhash[i]);
          if(hex.length() == 1) hexString.append('0');
          hexString.append(hex);
      }

      System.out.print("SHA-256 Hash: "+hexString.toString());

  }
}
