import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by aeakdogan on 16/05/2017.
 * ${CLASS}
 */
public class ServerCommunication {
    public ServerCommunication() {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(6000);

            System.out.println("Server Communication Started");
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Server Communication Handler Thread is creating");
                new ServerCommunicationHandler(clientSocket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
