import java.util.*;
import java.net.*;
public class ServerInterfaceStub implements ServerInterface {

	/*     Private Variables          */

	private ClientCommunicationModule comm;

	private RemoteObjectReference ror;

	private ServerInterface stub;



	public ServerInterfaceStub(RemoteObjectReference ror){
		this.ror = ror;
		InetAddress remoteObjectAddress = ror.getAddress();
		int remoteObjectPort = ror.getPort();
		comm = new ClientCommunicationModule();
	}

	public ServerInterface ServerInterfaceStubFactory(){
		return stub;
	}

	/*       Declared Methods Generated         */

	public java.lang.String match( java.lang.String param0 , int param1 ){

		// vector of args to pass
		Vector <Object> vec = new Vector<Object>();
		vec.add(param0);
		vec.add(param1);
		// Create a message to send
		Message message = new Message();

		// Build the message to send
		message.setMethodName("match");
		message.setArguments(vec);
		message.setRemoteObjectReference(ror);

		// Pass the message to the communication module
		comm.sendMessage(message);

		// Get the message returned from the Server
		message = comm.receiveMessage();
		Vector returnedFromServer = message.getArguments();
		java.lang.String returnType = (java.lang.String) returnedFromServer.get(0);
		return returnType;
	}

}