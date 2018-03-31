package Subprotocols;

import Utils.threadRegistry;
import javafx.util.Pair;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class Dispatcher implements Runnable{

    private threadRegistry              registry = null;
    private MulticastSocket             socket = null;
    private InetAddress                 address = null;
    private int                         port;
    private int                         senderId;

    public Dispatcher(MulticastSocket socket, threadRegistry registry, InetAddress address,int port,int senderId){
        this.socket = socket;
        this.registry = registry;
        this.address = address;
        this.port = port;
        this.senderId = senderId;
    }

    @Override
    public void run() {
        System.out.println("Started Reading Socket");
        while(true){
            byte[] bytes = new byte[64*1024];
            DatagramPacket packet = new DatagramPacket(bytes,bytes.length);
            if(socket!=null){
                try {
                    socket.receive(packet);
                    bytes = packet.getData();
                    String msg = new String(bytes);
                    dispatcherMessageHandler handler = new dispatcherMessageHandler(msg,this.registry,this.socket,this.address,this.port,this.senderId);
                    Thread thread = new Thread(handler);
                    thread.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Number of Registered Putchun Threads: "+registry.getRegisteredPutchunkThreadCount());
        }
    }
}
