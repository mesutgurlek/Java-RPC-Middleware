import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.UUID;

/**
 * Created by cagri on 16.05.2017.
 */
public class ClientCommunicationModule {
    private Socket serverSocket;
    private InetAddress serverAddress;
    private int portNo;

    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;

    // TODO add message id system as timestamp

    public ClientCommunicationModule() {

    }

    public void sendMessage(Message message) {
        try {
            RemoteObjectReference remoteObjectReference = message.getRemoteObjectReference();
            InetAddress address = remoteObjectReference.getAddress();
            int port = remoteObjectReference.getPort();

            Socket socket = new Socket(address, port);


        } catch (Exception e) {

        }
    }
    public Message receiveMessage() {

    }

}
