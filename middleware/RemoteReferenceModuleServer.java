/*
 * ServerRemoteReference
 * Created on Nov 8, 2004 at 12:03:49 PM
 *
 * Nathan Balon
 * University of Michigan Dearborn
 * CIS 578
 * Advanced Operating Systems
 * 
 */
package nathan.middleware;
import java.util.*;


/**
 * ServerRemoteReference maps remote references to local references.
 * 
 * @author  Nathan Balon<br />
 * Advance Operating Systems CIS 578<br />
 * University of Michigan Dearborn<br />
 * Remote Method Invocation Middleware Project<br />
 *
 */

public class RemoteReferenceModuleServer {    
    
    /**
     * Gets an instance of the remote reference module
     * @return RemoteRefernceModuleServer 
     */
    public static synchronized RemoteReferenceModuleServer getServerRemoteReference(){
        if(referenceLookup == null){
            referenceLookup = new RemoteReferenceModuleServer();
        }
        return referenceLookup;
    }

    
    private RemoteReferenceModuleServer(){
        lookupReference = new HashMap<RemoteObjectReference, Object>();
    }
    
    /**
     * Add a remote reference to the table.
     * @param ror The RemoteObjectReference for an object.
     * @param o The object that methods will be called on.
     */
    public synchronized void addReference(RemoteObjectReference ror, Object o){
        lookupReference.put(ror, o);
    }
    
    /**
     * Remove an object from the table.
     * @param ror The RemoteObjectReference of the object to be removed.
     */
    public synchronized void removeReference(RemoteObjectReference ror){
        lookupReference.remove(ror);

    }
    
	/**
	 * Lookup the object which has specific RemoteObjectReference.
	 * @param ror The RemoteObjectReference of the object to be looked up.
	 * @return The local object.
	 */
	public synchronized Object getReference(RemoteObjectReference ror){   
	    Object o = this.lookupReference.get(ror);
	    return o;
	}
	
	/**
	 * Returns a string representation of the objects and references contained
	 * in the ReferenceModule.
	 * @return String a String representation of the object contain in the table.
	 * @see java.lang.Object#toString()
	 */
	public String toString(){
	    return lookupReference.toString();
	}
	
	private static RemoteReferenceModuleServer referenceLookup;
	private HashMap <RemoteObjectReference, Object> lookupReference;
	
	
}
