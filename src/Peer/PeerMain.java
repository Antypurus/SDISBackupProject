package Peer;

import java.io.IOException;
import java.nio.file.FileStore;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.NumberFormat;

public class PeerMain {
    public static void main(String args[]){
        NumberFormat nf = NumberFormat.getNumberInstance();
        for (Path root : FileSystems.getDefault().getRootDirectories()) {

            System.out.print(root + ": ");
            try {
                FileStore store = Files.getFileStore(root);
                double available = (store.getUsableSpace()/(Math.pow(10f,9f)));
                double totalS = (store.getTotalSpace()/(Math.pow(10f,9f)));
                System.out.println("available=" + available
                        + ", total=" + totalS);
            } catch (IOException e) {
                System.out.println("error querying space: " + e.toString());
            }
        }
    }
}
