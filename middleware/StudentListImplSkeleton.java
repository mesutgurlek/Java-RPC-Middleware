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
public class StudentListImplSkeleton  extends java.lang.Object {

	public StudentListImplSkeleton(RemoteObjectReference ror, Object remoteObject){
		this.remoteObject = (nathan.middleware.StudentList)remoteObject;
		int remoteObjectPort = ror.getPort();
		serverModule = RemoteReferenceModuleServer.getServerRemoteReference();
		serverModule.addReference(ror, this);
		comm = new ServerCommunication(remoteObjectPort);
	}

	/*       Declared Methods Generated         */

	public synchronized Message addStudent(Message message){

		Vector vec = message.getArguments();
		Boolean returnValue;
		returnValue = Boolean.valueOf(remoteObject.addStudent( (nathan.middleware.Student)vec.get(0) ));
		Vector <Object> returnVector = new Vector<Object>();         // vector of args to pass back
		returnVector.add(returnValue);
		message.setArguments(returnVector);

		// return the message to the dispatcher module
		return message;
	}

	public synchronized Message delStudent(Message message){

		Vector vec = message.getArguments();
		Boolean returnValue;
		returnValue = Boolean.valueOf(remoteObject.delStudent( (java.lang.String)vec.get(0) ));
		Vector <Object> returnVector = new Vector<Object>();         // vector of args to pass back
		returnVector.add(returnValue);
		message.setArguments(returnVector);

		// return the message to the dispatcher module
		return message;
	}

	public synchronized Message listAll(Message message){

		Vector vec = message.getArguments();
		java.util.Vector returnValue;
		returnValue = remoteObject.listAll();
		Vector <Object> returnVector = new Vector<Object>();         // vector of args to pass back
		returnVector.add(returnValue);
		message.setArguments(returnVector);

		// return the message to the dispatcher module
		return message;
	}

	public synchronized Message setGrade(Message message){

		Vector vec = message.getArguments();
		Boolean returnValue;
		returnValue = Boolean.valueOf(remoteObject.setGrade( (java.lang.String)vec.get(0) ,  (nathan.middleware.Grade)vec.get(1) ));
		Vector <Object> returnVector = new Vector<Object>();         // vector of args to pass back
		returnVector.add(returnValue);
		message.setArguments(returnVector);

		// return the message to the dispatcher module
		return message;
	}

	public synchronized Message getGrade(Message message){

		Vector vec = message.getArguments();
		nathan.middleware.Grade returnValue;
		returnValue = remoteObject.getGrade( (java.lang.String)vec.get(0) );
		Vector <Object> returnVector = new Vector<Object>();         // vector of args to pass back
		returnVector.add(returnValue);
		message.setArguments(returnVector);

		// return the message to the dispatcher module
		return message;
	}

	public synchronized Message getReplicatedObject(Message message){

		Vector vec = message.getArguments();
		java.util.Vector returnValue;
		returnValue = remoteObject.getReplicatedObject();
		Vector <Object> returnVector = new Vector<Object>();         // vector of args to pass back
		returnVector.add(returnValue);
		message.setArguments(returnVector);

		// return the message to the dispatcher module
		return message;
	}

	public synchronized Message setReplicatedObject(Message message){

		Vector vec = message.getArguments();
		remoteObject.setReplicatedObject( (java.util.Vector)vec.get(0) );
		Vector <Object> returnVector = new Vector<Object>();         // vector of args to pass back
		message.setArguments(returnVector);

		// return the message to the dispatcher module
		return message;
	}

	public synchronized Message find(Message message){

		Vector vec = message.getArguments();
		nathan.middleware.Student returnValue;
		returnValue = remoteObject.find( (java.lang.String)vec.get(0) );
		Vector <Object> returnVector = new Vector<Object>();         // vector of args to pass back
		returnVector.add(returnValue);
		message.setArguments(returnVector);

		// return the message to the dispatcher module
		return message;
	}

	/*     private data members          */

	private ServerCommunication comm;

	private RemoteObjectReference ror;

	private RemoteReferenceModuleServer serverModule;

	private nathan.middleware.StudentList remoteObject;
}