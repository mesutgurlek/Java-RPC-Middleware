/*
 * Created on Nov 1, 2004
 * 
 * @author Nathan Balon 
 * Middleware Application 
 * CIS 578 - Advanced Operating Systems 
 * U 0f M Dearborn
 * 
 * @version 1.0
 */

package nathan.middleware;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.lang.reflect.*;

/**
 * Naming is used to communicate with the Registry.
 * The main features of the class is to lookup and bind remote object.
 * 
 * @author  Nathan Balon<br />
 * Advance Operating Systems CIS 578<br />
 * University of Michigan Dearborn<br />
 * Remote Method Invocation Middleware Project<br />
 *
 */
public class Naming {
    
	/**
	 * Lookup looks an object up in the registry and the creates a new stub.
	 * @param name The name of the object to lookup.
	 * @param ip The ip address of the registry
	 * @param port The port number of the registry
	 * @return Object A stub object to use by the client.
	 */
	public static synchronized Object lookup(String name, String ip,int port) {
	    RemoteObjectReference ror = null;
	    Object stubObject = null;
	    try {
	        // create a new connection to the registry to locate an object
			Socket socket = new Socket(ip, port);
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            // create the message to send
            RegistryMessage message = new RegistryMessage(RegistryMessage.LOOKUP, name);
            // send the message to the registry
            out.writeObject(message);
            // get the remote object reference from the registry
            ror = (RemoteObjectReference)in.readObject();
            // get the name of the stub class to create
            String impClassName = ror.getImplementationClass();
            String stubClassName = impClassName + "Stub";
            // get a class object for the stub class
            Class stubClass = Class.forName(stubClassName);
            // get the constructor for the stub
            Constructor stubConstructor = stubClass.getConstructor(
                      new Class[]{Class.forName("nathan.middleware.RemoteObjectReference")});
            // create the arguments for the constructor
            Object [] constructorArgs = new Object[]{ror};
            // invoke the stub constructor
            stubObject = stubConstructor.newInstance(constructorArgs);
            System.out.println("Naming succesfully looked up: " + ror.getName() + "\n");
            out.close();
            in.close();
            socket.close();
		}
	    catch(java.net.ConnectException e){
            System.err.println("Unable to connect to Registry");
            System.exit(1);
        }
		catch(Exception e){
			System.err.println("Unable to lookup: " + name);
//			e.printStackTrace();
			System.exit(1);
		}
		// return the remote object reference
        return stubObject;
	}
	
	/**
	 * Binds an object to the registry then creates a skeleton object.
	 * @param objectName The name to be used to lookup the object.
	 * @param registryIP The IP address of the registry used to bind the object.
	 * @param registryPort The port number of the registry.
	 * @param portToBind The port number of the server that the object will be bound to.
	 * @return RemoteObjectReference The RemoteObjectReference used to access a remote object.
	 */
	public static synchronized Object rebind(String objectName, Object objectToBind,
	                             String registryIP, int registryPort, int portToBind){
	    Object skeletonObject = null;
		try {
		    // create a socket to communicate with the registry
			Socket socket = new Socket(registryIP, registryPort);
			// get the input and output streams
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            // send a message to the registry
            RegistryMessage message = new RegistryMessage(RegistryMessage.BIND, objectName, objectToBind, portToBind);
            out.writeObject(message);
            // get the remote object reference from the registry
            RemoteObjectReference ror = (RemoteObjectReference)in.readObject();
            String impClassName = ror.getImplementationClass();
            String skeletonClassName = impClassName + "Skeleton";
            // get a class object for the skeleton class
            Class skeletonClass = Class.forName(skeletonClassName);
            // get the constructor for the skelton class
            Constructor skeletonConstructor = skeletonClass.getConstructor(
                      new Class[]{Class.forName("nathan.middleware.RemoteObjectReference"),
                                  Class.forName("java.lang.Object")});
            // create the arguments for the constructor
            Object [] constructorArgs = new Object[]{ror, objectToBind};
            // invoke the skelton constructor
            skeletonObject = skeletonConstructor.newInstance(constructorArgs);
            System.out.println("Bound new object to registry: " + ror.getName());
            // close the connections
            out.close();
            in.close();
            socket.close();
		}
	    catch(java.net.ConnectException e){
            System.err.println("Unable to connect to Registry");
            System.exit(1);
        }
		catch(Exception e){
			System.err.println("Error unable to bind object");
			e.printStackTrace();
		}	 
		// return the remote object reference
        return skeletonObject;
	}
	
	/**
	 * Remove an entry from the registry when no longer needed.
	 * @param registryIP The IP address of the registry.
	 * @param registryPort The port number of the registry.
	 * @param ror The RemoteObjectReference to be removed from the registry.
	 */
	public static void remove(String registryIP, int registryPort, RemoteObjectReference ror){
		try {
		    // create a socket to communicate with the registry
			Socket socket = new Socket(registryIP, registryPort);
			// get the input and output streams
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            RegistryMessage message = new RegistryMessage(RegistryMessage.REMOVE_BINDING, ror);
            out.writeObject(message);
            out.close();
            in.close();
            socket.close();
		} 
		catch(IOException e){
		    System.err.println("Unable to create a connection to the registry");
		    System.exit(1);
		}
		catch(Exception e){
		    System.err.println("Error Removing Object");
		    e.printStackTrace();
		    System.exit(1);
		}	    
	    
	}
	
	/**
	 * Create an entry in the registry for the active 
	 * replication front end server.
	 * @param name The name to be used to look up the servie
	 * @param registryIP The IP address of the registry.
	 * @param registryPort The port number of the registry.
	 * @param portToBind The port to bind the Front End to.
	 * @return The RemoteObjectMapping to be used with the FrontEnd.
	 */
	public static synchronized RemoteObjectMapping createFrontEnd(
				String name, String registryIP,int registryPort, int portToBind){
	    RemoteObjectMapping mapping = null;
		try {
		    // create a socket to communicate with the registry
			Socket socket = new Socket(registryIP, registryPort);
			// get the input and output streams
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            RegistryMessage message = new RegistryMessage(RegistryMessage.NEW_FRONT_END, name, portToBind);
            out.writeObject(message);
            // get the mapping for the replicated objects
            mapping = (RemoteObjectMapping)in.readObject();
            in.close();
            out.close();
            socket.close();
		} 
		catch(IOException e){
		    System.err.println("Unable to create a connection to the registry");
		    System.exit(1);
		}
		catch(Exception e){
		    System.err.println("Error creating replica");
		    e.printStackTrace();
		    System.exit(1);
		}
		return mapping;
	}
	
}