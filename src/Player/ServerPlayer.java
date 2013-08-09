package Player;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created with IntelliJ IDEA.
 * User: Евгений
 * Date: 08.08.13
 */
public class ServerPlayer extends Human {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private final int PORT = 8484;

    public class RemotePlayer extends Human {

        public RemotePlayer(int mark) {
            super(mark);
        }

        @Override
        public void play(byte[][] board) {
            try {
                System.out.println("Ожидание хода противника...");
                byte[] buf = new byte[2];
                InputStream in = clientSocket.getInputStream();
                in.read(buf);
                x = buf[0];
                y = buf[1];
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }

        }
    }

    public RemotePlayer getRemotePlayer(int mark) {
        return new RemotePlayer(mark);
    }

    public ServerPlayer(int mark) {
        super(mark);
    }

    @Override
    public void play(byte[][] board) {
        readFromConsole(board);
        try {
            byte[] buf = new byte[]{(byte) x, (byte) y};
            OutputStream out = clientSocket.getOutputStream();
            out.write(buf);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public void startServer() {
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Ожидание клиента....");
            clientSocket = serverSocket.accept();
            System.out.println("Соединнение установлено");
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public void stopServer() {
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}
