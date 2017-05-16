import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by cagri on 16.05.2017.
 */
public class ClientTest {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Scanner scan = new Scanner(System.in);
        int port = Integer.parseInt(args[0]);
        while(true) {
            String name = scan.next();
            String o = new String();
            Socket socket = new Socket("localhost", port);
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            RegistryMessage message = new RegistryMessage(MessageType.REBIND, name, o, 500);
            out.writeObject(message);
            // get the remote object reference from the registry
            RemoteObjectReference ror = (RemoteObjectReference)in.readObject();

        }
    }
}
