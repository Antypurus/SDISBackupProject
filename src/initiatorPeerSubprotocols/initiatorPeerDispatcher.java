package initiatorPeerSubprotocols;

import Utils.threadRegistry;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.MulticastSocket;

public class initiatorPeerDispatcher implements Runnable{

    private threadRegistry          registry = null;
    private MulticastSocket         socket = null;

    public initiatorPeerDispatcher(MulticastSocket socket,threadRegistry registry){
        this.socket = socket;
        this.registry = registry;
    }

    @Override
    public void run() {
        while(true){
            byte[] bytes = new byte[64*1024];
            DatagramPacket packet = new DatagramPacket(bytes,bytes.length);
            if(socket!=null){
                try {
                    socket.receive(packet);
                    bytes = packet.getData();
                    String msg = new String(bytes);
                    dispatcherMessageHandler handler = new dispatcherMessageHandler(msg,this.registry);
                    Thread thread = new Thread(handler);
                    thread.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
