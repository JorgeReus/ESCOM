package client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class Client {

    private static final int BUFFER_SIZE = 512;

    public static void main(String[] args) throws IOException {
        int num = Integer.parseInt(args[2]);
        InetSocketAddress myAddress
                = new InetSocketAddress(args[0], Integer.parseInt(args[1]));
        DatagramChannel datagramChannel = DatagramChannel.open();
        datagramChannel.bind(null);

        ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
        Double y = 0d;
        for (Double x = -Math.PI; x <= Math.PI; x += .01) {
            for (int n = 0; n < num; n++) {
                if (n != 0 && x !=0) {
                    y += (4 / Math.PI) * (1 / n) * (Math.sin(n * x));
                    System.out.println("X : " + x);
                    System.out.println("Y : " + y);
                    String res = 
                    buffer.put(x.toString() + y.toString());
                    buffer.put(y.byteValue());
                    buffer.flip();
                    datagramChannel.send(buffer, myAddress);
                    buffer.clear();                  
                }
            }
        }
    }
}
