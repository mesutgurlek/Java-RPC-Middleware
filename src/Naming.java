import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by cagri on 16.05.2017.
 */
public class Naming {

    public static synchronized Object lookup(String name, String IP, int portNo) {
        try {
            Socket registrySocket = new Socket(IP, portNo);
            // create streams for registry communication
            ObjectOutputStream outputStream = new ObjectOutputStream(registrySocket.getOutputStream());
            ObjectInputStream inputStream = new ObjectInputStream(registrySocket.getInputStream());

            // create a msg to send to registry server
            RegistryMessage message = new RegistryMessage(MessageType.LOOKUP, name);
            // send msg
            outputStream.writeObject(message);
            // receive msg (reference)
            RemoteObjectReference remoteObjectReference = (RemoteObjectReference)inputStream.readObject();

            if(remoteObjectReference == null) {
                System.out.println("Object is not found");
                return null;
            }
            else {
                String className = remoteObjectReference.getClassName();
                /* TODO create a stub with using this class name
                 TODO use parameter of remoteObjRef to connect stup to server
                 TODO then return that stub */
            }


        } catch (Exception e) {

        }
    }

    public static RemoteObjectReference bind(Object obj, String name, String IP, int portNo) {
        try {
            Socket registrySocket = new Socket(IP, portNo);
            // create streams for registry communication
            ObjectOutputStream outputStream = new ObjectOutputStream(registrySocket.getOutputStream());
            ObjectInputStream inputStream = new ObjectInputStream(registrySocket.getInputStream());

            // create a msg to send to registry server
            RegistryMessage message = new RegistryMessage(MessageType.BIND, name, obj, portNo);
            // send msg
            outputStream.writeObject(message);
            // receive msg (reference)
            RemoteObjectReference remoteObjectReference = (RemoteObjectReference)inputStream.readObject();

            if(remoteObjectReference == null) {
                System.out.println("Object is not found");
                return null;
            }
            else {
                String className = remoteObjectReference.getClassName();
                /* TODO create a stub with using this class name
                 TODO use parameter of remoteObjRef to connect stup to server
                 TODO then return that stub */
            }


        } catch (Exception e) {

        }
    }

}