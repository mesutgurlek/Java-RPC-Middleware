import java.util.*;
import java.net.*;
public class CalculatorInterfaceStub implements CalculatorInterface {

	/*     Private Variables          */

	public ClientCommunicationModule comm;

	public RemoteObjectReference ror;

	public CalculatorInterface stub;


	public CalculatorInterfaceStub(RemoteObjectReference ror){
		this.ror = ror;
		InetAddress remoteObjectAddress = ror.getAddress();
		int remoteObjectPort = ror.getPort();
		comm = new ClientCommunicationModule();
	}

	public CalculatorInterface CalculatorInterfaceStubFactory(){
		return stub;
	}

	/*       Declared Methods Generated         */

	public int add( int param0 , int param1 ){

		// vector of args to pass
		Vector <Object> vec = new Vector<Object>();
		vec.add(param0);
		vec.add(param1);
		// Create a message to send
		Message message = new Message();

		// Build the message to send
		message.setMethodName("add");
		message.setArguments(vec);
		message.setRemoteObjectReference(ror);

		// Pass the message to the communication module
		comm.sendMessage(message);

		// Get the message returned from the Server
		message = comm.receiveMessage();
		Vector returnedFromServer = message.getArguments();
		Integer returnType = (Integer) returnedFromServer.get(0);
		return returnType.intValue();
	}

}