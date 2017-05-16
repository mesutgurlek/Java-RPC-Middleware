import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.HashMap;

public class RegistryRequestHandler extends Thread {
    private Socket inSocket;
    private HashMap<String, RemoteObjectReference> table;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    public RegistryRequestHandler(Socket inSocket) {
        table = Registry.getLookupTable();
        this.inSocket = inSocket;
    }

    public synchronized RemoteObjectReference bind(RegistryMessage message) {
        InetAddress clientAddr = inSocket.getInetAddress();
        RemoteObjectReference remoteObjectReference = new RemoteObjectReference(message, clientAddr);
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
        String name = message.getName();
        RemoteObjectReference remoteObjectReference;
        if(table.containsKey(name)) {
            remoteObjectReference = table.get(name);
        }
        else {
            System.out.println("Object:" + name + " is not in the registry!");
            remoteObjectReference = null;
        }

        return remoteObjectReference;
    }

    public synchronized void listAllObjects() {
        System.out.println(table.toString());
    }

    public void run() {
        try {
            inputStream = new ObjectInputStream(inSocket.getInputStream());
            outputStream = new ObjectOutputStream(inSocket.getOutputStream());

            RegistryMessage message = (RegistryMessage) inputStream.readObject();

            switch (message.getType()) {
                case REBIND:
                    RemoteObjectReference remoteObjectReference = bind(message);
                    outputStream.writeObject(remoteObjectReference);
                    break;
                case LOOKUP:
                    RemoteObjectReference remoteObjectReference1 = lookup(message);
                    outputStream.writeObject(remoteObjectReference1);
                    break;
                default:
                    System.out.println("Invalid request!");
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                inputStream.close();
                outputStream.close();
                inSocket.close();
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}