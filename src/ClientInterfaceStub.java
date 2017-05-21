import java.net.InetAddress;
import java.util.Vector;

public class ClientInterfaceStub implements ClientInterface {

	/*     Private Variables          */

    private ClientCommunicationModule comm;

    private RemoteObjectReference ror;

    private ClientInterface stub;


    public ClientInterfaceStub(RemoteObjectReference ror) {
        this.ror = ror;
        InetAddress remoteObjectAddress = ror.getAddress();
        int remoteObjectPort = ror.getPort();
        comm = new ClientCommunicationModule();
    }

    public ClientInterface ClientInterfaceStubFactory() {
        return stub;
    }

	/*       Declared Methods Generated         */

    public void handshake() {

        // vector of args to pass
        Vector<Object> vec = new Vector<Object>();
        // Create a message to send
        Message message = new Message();

        // Build the message to send
        message.setMethodName("handshake");
        message.setArguments(vec);
        message.setRemoteObjectReference(ror);

        // Pass the message to the communication module
        comm.sendMessage(message);
    }

}