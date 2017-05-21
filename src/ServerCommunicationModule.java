import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by aeakdogan on 16/05/2017.
 * ${CLASS}
 */
public class ServerCommunicationModule extends Thread {
    int port;
    private ServerSocket serverSocket;

    // run server for every skeleton
    public ServerCommunicationModule(int port) {
        this.port = port;
        this.start();
    }

    @Override
    public void run() {
        this.runServer();
    }

    public void runServer() {
        serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);

            System.out.println("Server Communication Started at port:" + port);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Server Communication Handler Thread is creating");
                new ServerCommunicationHandler(clientSocket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            this.closeServer();
        }
    }

    public void closeServer() {
        try {
            serverSocket.close();
            this.stop();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
