import java.net.InetAddress;
import java.net.Socket;

public class RegistryRequestHandler extends Thread {
    private Socket inSocket;

    public RegistryRequestHandler(Socket inSocket) {
        this.inSocket = inSocket;
    }

    public synchronized RemoteObjectReference bind(RegistryMessage message) {
        InetAddress clientAddr = inSocket.getInetAddress();
        RemoteObjectReference remoteObjectReference = new RemoteObjectReference(message, clientAddr);

    }

}