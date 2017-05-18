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

            serverSocket = new Socket(address, port);
            outputStream = new ObjectOutputStream(serverSocket.getOutputStream());
            inputStream = new ObjectInputStream(serverSocket.getInputStream());

            message.setMessageType(MessageType.REQUEST);
            outputStream.writeObject(message);

        } catch (Exception e) {
            System.out.println("Connection erorr");
            e.printStackTrace();
        }
    }
    public Message receiveMessage() {
        Message message = null;
        try {
            message = (Message)inputStream.readObject();

            if(message.getMessageType() == MessageType.CLOSE){
                System.out.println("No more active objects: All server down");
                System.exit(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                inputStream.close();
                outputStream.close();
                serverSocket.close();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        return message;
    }

}
