import java.util.*;
import java.net.*;
public class CalculatorInterfaceSkeleton {

	private ServerCommunicationModule comm;
	private RemoteObjectReference ror;
	private RemoteReferenceModuleServer serverModule;
	private CalculatorInterface remoteObject;


	public CalculatorInterfaceSkeleton(RemoteObjectReference ror, Object remoteObject){
		this.remoteObject = (CalculatorInterface)remoteObject;
		serverModule = RemoteReferenceModuleServer.getServerRemoteReference();
		serverModule.addObjectReference(ror, this);
		comm = new ServerCommunicationModule(ror.getPort());
	}

	public Message add(Message message){

		Vector vec = message.getArguments();
		Integer returnValue;
		returnValue = Integer.valueOf(remoteObject.add( (Integer)vec.get(0) ,  (Integer)vec.get(1) ));
		Vector <Object> returnVector = new Vector<Object>();         // vector of args to pass back
		returnVector.add(returnValue);
		message.setArguments(returnVector);

		// return the message to the dispatcher module
		return message;
	}

}