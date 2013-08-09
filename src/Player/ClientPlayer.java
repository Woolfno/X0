package Player;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created with IntelliJ IDEA.
 * User: Евгений
 * Date: 08.08.13
 */
public class ClientPlayer extends Human {
    private String serverIP;
    private Socket socket;
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
                InputStream in = socket.getInputStream();
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

    public ClientPlayer(int mark) {
        super(mark);
    }

    @Override
    public void play(byte[][] board) {
        readFromConsole(board);
        try {
            OutputStream out = socket.getOutputStream();
            byte[] buf = new byte[]{(byte) x, (byte) y};
            out.write(buf);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public void setServerIP(String serverIP) {
        this.serverIP = serverIP;
    }

    public void startClient() {
        try {
            System.out.println("Подключение к серверу....");
            socket = new Socket(serverIP, PORT);
            System.out.println("Соединнение установлено");
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public void stopClient() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}
