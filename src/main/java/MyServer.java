import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class MyServer {
    public static void main(String[] args) throws IOException {

        final ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        serverSocketChannel.bind(new InetSocketAddress("localhost", 9999));

        while (serverSocketChannel.isOpen()) {
            try (SocketChannel socketChannel = serverSocketChannel.accept()) {
                final ByteBuffer byteBuffer = ByteBuffer.allocate(2 << 10);

                while (socketChannel.isConnected()) {
                    int bytes = socketChannel.read(byteBuffer);
                    if (bytes == -1) break;
                    final String message = new String(byteBuffer.array(), 0, bytes, StandardCharsets.UTF_8);
                    byteBuffer.clear();
                    socketChannel.write(ByteBuffer.wrap(("ИТОГ: " + deleter(message)).getBytes(StandardCharsets.UTF_8)));
                }

                serverSocketChannel.close();
            }
        }
    }

    public static String deleter(String message) {
        return message.trim().replace(" ", "");
    }

}