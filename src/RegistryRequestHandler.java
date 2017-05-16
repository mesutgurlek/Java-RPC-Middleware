import java.net.InetAddress;
import java.net.Socket;
import java.util.HashMap;

public class RegistryRequestHandler extends Thread {
    private Socket inSocket;

    public RegistryRequestHandler(Socket inSocket) {
        this.inSocket = inSocket;
    }

    public synchronized RemoteObjectReference bind(RegistryMessage message) {
        InetAddress clientAddr = inSocket.getInetAddress();
        RemoteObjectReference remoteObjectReference = new RemoteObjectReference(message, clientAddr);
        HashMap<String, RemoteObjectReference> table = Registry.getLookupTable();
        String name = message.getName();
        // check the name is unique or not
        if (table.containsKey(name)) {
            System.out.println("There is already an object with same name!");
        }
        // if unique, add remote reference to table
        else {
            table.put(name, remoteObjectReference);
            System.out.println("Object:" + name + " is binded to the registry!");
        }
        return remoteObjectReference;
    }

    public synchronized RemoteObjectReference lookup(RegistryMessage message) {

    }

}