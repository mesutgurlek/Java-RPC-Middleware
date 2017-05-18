/**
*
*   StudentListImpl
*
*   @author Nathan Balon
*   Advanced Operating Systems - CIS 550
*   University of Michigan Dearborn
*
*   Proxy created by Java reflection for the Interface nathan.middleware.StudentListImpl.java
*/

package nathan.middleware;

import java.util.*;
import java.net.*;
public class StudentListImplStub  extends java.lang.Object implements nathan.middleware.StudentList, java.io.Serializable {

	public StudentListImplStub(RemoteObjectReference ror){
		this.ror = ror;
		InetAddress remoteObjectAddress = ror.getAddress();
		int remoteObjectPort = ror.getPort();
		comm = new ClientCommunication(remoteObjectAddress, remoteObjectPort);
		clientModule = RemoteReferenceModuleClient.getClientRemoteReference();
		clientModule.addReference(ror, stub);
	}

	public nathan.middleware.StudentList StudentListImplFactory(){
		return stub;
	}

	/*       Declared Methods Generated         */

	public boolean addStudent( nathan.middleware.Student param0 ){

		Vector <Object> vec = new Vector<Object>();         // vector of args to pass
		vec.add(param0);
		// Create a message to send
		Message message = new Message();

		// Build the message to send
		message.setMethodName("addStudent");
		message.setArguments(vec);
		message.setRemoteObjectReference(ror);

		// Pass the message to the communication module
		comm.sendMessage(message);

		// Get the message returned from the Server
		message = comm.getMessage();
		Vector returnedFromServer = message.getArguments();
		Boolean returnValue = (Boolean) returnedFromServer.get(0);
		return returnValue.booleanValue();
	}

	public boolean delStudent( java.lang.String param0 ){

		Vector <Object> vec = new Vector<Object>();         // vector of args to pass
		vec.add(param0);
		// Create a message to send
		Message message = new Message();

		// Build the message to send
		message.setMethodName("delStudent");
		message.setArguments(vec);
		message.setRemoteObjectReference(ror);

		// Pass the message to the communication module
		comm.sendMessage(message);

		// Get the message returned from the Server
		message = comm.getMessage();
		Vector returnedFromServer = message.getArguments();
		Boolean returnValue = (Boolean) returnedFromServer.get(0);
		return returnValue.booleanValue();
	}

	public java.util.Vector listAll( ){

		Vector <Object> vec = new Vector<Object>();         // vector of args to pass
		// Create a message to send
		Message message = new Message();

		// Build the message to send
		message.setMethodName("listAll");
		message.setArguments(vec);
		message.setRemoteObjectReference(ror);

		// Pass the message to the communication module
		comm.sendMessage(message);

		// Get the message returned from the Server
		message = comm.getMessage();
		Vector returnedFromServer = message.getArguments();
		java.util.Vector returnValue = (java.util.Vector) returnedFromServer.get(0);
		return returnValue;
	}

	public boolean setGrade( java.lang.String param0 , nathan.middleware.Grade param1 ){

		Vector <Object> vec = new Vector<Object>();         // vector of args to pass
		vec.add(param0);
		vec.add(param1);
		// Create a message to send
		Message message = new Message();

		// Build the message to send
		message.setMethodName("setGrade");
		message.setArguments(vec);
		message.setRemoteObjectReference(ror);

		// Pass the message to the communication module
		comm.sendMessage(message);

		// Get the message returned from the Server
		message = comm.getMessage();
		Vector returnedFromServer = message.getArguments();
		Boolean returnValue = (Boolean) returnedFromServer.get(0);
		return returnValue.booleanValue();
	}

	public nathan.middleware.Grade getGrade( java.lang.String param0 ){

		Vector <Object> vec = new Vector<Object>();         // vector of args to pass
		vec.add(param0);
		// Create a message to send
		Message message = new Message();

		// Build the message to send
		message.setMethodName("getGrade");
		message.setArguments(vec);
		message.setRemoteObjectReference(ror);

		// Pass the message to the communication module
		comm.sendMessage(message);

		// Get the message returned from the Server
		message = comm.getMessage();
		Vector returnedFromServer = message.getArguments();
		nathan.middleware.Grade returnValue = (nathan.middleware.Grade) returnedFromServer.get(0);
		return returnValue;
	}

	public java.util.Vector getReplicatedObject( ){

		Vector <Object> vec = new Vector<Object>();         // vector of args to pass
		// Create a message to send
		Message message = new Message();

		// Build the message to send
		message.setMethodName("getReplicatedObject");
		message.setArguments(vec);
		message.setRemoteObjectReference(ror);

		// Pass the message to the communication module
		comm.sendMessage(message);

		// Get the message returned from the Server
		message = comm.getMessage();
		Vector returnedFromServer = message.getArguments();
		java.util.Vector returnValue = (java.util.Vector) returnedFromServer.get(0);
		return returnValue;
	}

	public void setReplicatedObject( java.util.Vector param0 ){

		Vector <Object> vec = new Vector<Object>();         // vector of args to pass
		vec.add(param0);
		// Create a message to send
		Message message = new Message();

		// Build the message to send
		message.setMethodName("setReplicatedObject");
		message.setArguments(vec);
		message.setRemoteObjectReference(ror);

		// Pass the message to the communication module
		comm.sendMessage(message);
	}

	public nathan.middleware.Student find( java.lang.String param0 ){

		Vector <Object> vec = new Vector<Object>();         // vector of args to pass
		vec.add(param0);
		// Create a message to send
		Message message = new Message();

		// Build the message to send
		message.setMethodName("find");
		message.setArguments(vec);
		message.setRemoteObjectReference(ror);

		// Pass the message to the communication module
		comm.sendMessage(message);

		// Get the message returned from the Server
		message = comm.getMessage();
		Vector returnedFromServer = message.getArguments();
		nathan.middleware.Student returnValue = (nathan.middleware.Student) returnedFromServer.get(0);
		return returnValue;
	}

	/*     private data members          */

	private ClientCommunication comm;

	private RemoteObjectReference ror;

	private RemoteReferenceModuleClient clientModule;

	private nathan.middleware.StudentList stub;

}