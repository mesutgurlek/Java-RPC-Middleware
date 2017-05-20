import java.util.*;
import java.net.*;
public class ClientInterfaceSkeleton {

	/*     private data members          */

	private ServerCommunicationModule comm;

	private RemoteObjectReference ror;

	private RemoteReferenceModuleServer serverModule;

	private ClientInterface remoteObject;
	public ClientInterfaceSkeleton(RemoteObjectReference ror, Object remoteObject){
		this.remoteObject = (ClientInterface)remoteObject;
		int remoteObjectPort = ror.getPort();
		serverModule = RemoteReferenceModuleServer.getServerRemoteReference();
		serverModule.addObjectReference(ror, this);
		comm = new ServerCommunicationModule(remoteObjectPort);
	}

	/*       Declared Methods Generated         */

	public synchronized Message handshake(Message message){

		Vector vec = message.getArguments();
		remoteObject.handshake();
		Vector <Object> returnVector = new Vector<Object>();         // vector of args to pass back
		message.setArguments(returnVector);

		// return the message to the dispatcher module
		return message;
	}

}