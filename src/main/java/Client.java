
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException {

        int waitTime = 2000;

        InetSocketAddress socketAddress = new InetSocketAddress("localhost", 9999);

        final SocketChannel socketChannel = SocketChannel.open();

        try (socketChannel; Scanner scanner = new Scanner(System.in)) {
            socketChannel.connect(socketAddress);

            final ByteBuffer inputBuffer = ByteBuffer.allocate(2 << 10);

            String message;

            while (true) {
                System.out.println("Введите текст или введите end для выхода");
                message = scanner.nextLine();
                if ("end".equals(message)) break;
                socketChannel.write(ByteBuffer.wrap(message.getBytes(StandardCharsets.UTF_8)));
                Thread.sleep(waitTime);
                int bytesCount = socketChannel.read(inputBuffer);
                System.out.println(new String(inputBuffer.array(), 0, bytesCount, StandardCharsets.UTF_8).trim());
                inputBuffer.clear();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}