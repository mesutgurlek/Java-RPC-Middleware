/*
 * Created on Nov 1, 2004
 * 
 * @author  Nathan Balon
 * Middleware Application
 * CIS 578 - Advanced Operating Systems
 * U 0f M Dearborn
 * @version 1.0
 */

package nathan.middleware;
import java.lang.reflect.*;

/**
 * Dispatcher is used call call the correct method on
 * the skeleton of the remote object.  By using reflection
 * the correct method is located on the skeleton object.
 * 
 * @author  Nathan Balon<br />
 * Advance Operating Systems CIS 578<br />
 * University of Michigan Dearborn<br />
 * Remote Method Invocation Middleware Project<br />
 */
public class Dispatcher {
	
	/**
	 * dispatch the message calls the correct method of the skeleton class.
	 * @param message The message to be passed to the skeleton.
	 * @return Message The message returned from the skeleton.
	 */
	public Message dispatchMessage(Message message){
	    // get the remote object reference.
		RemoteObjectReference ror = message.getRemoteObjectReference();
		RemoteReferenceModuleServer referenceLookup = RemoteReferenceModuleServer.getServerRemoteReference();
		
		// get the skeleton to handle the message
		Object skeleton = referenceLookup.getReference(ror);
		Class c = skeleton.getClass();
		Method [] declaredMethods = c.getMethods();

		// find the correct method to call
		for(int i = 0; i < declaredMethods.length; i++){
		   // get all the methods for the skeleton
		   String methodName = declaredMethods[i].getName();
		   // check if the method names match
		   if (methodName.equals(message.getMethodName())){
		       Object [] args = new Object[1];
		       args[0] = message;
		       try{
		           // invoke the method on the skeleton
		           message = (Message)declaredMethods[i].invoke((Object)skeleton, args);
		           System.out.println("Executed Method: " + message.getMethodName());
		       }
		       catch(Exception e){
		           System.err.println("Failed to execute skeleton method");
		          // e.printStackTrace();
		       }
		   }
		}
		// return the message to the communication module
		return message;
	}
}
