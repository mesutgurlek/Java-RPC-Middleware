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
import java.util.*;

/** 
 * The remote reference module for the client application.  The
 * module contains the mapping of locale object to the 
 * RemoteObjectReferences of the object.
 *
 * @author  Nathan Balon<br />
 * Advance Operating Systems CIS 578<br />
 * University of Michigan Dearborn<br />
 * Remote Method Invocation Middleware Project<br />
 */

public class RemoteReferenceModuleClient {
 
    /**
     * Creates a new instance of the clients 
     * remote reference module.
     */
    private RemoteReferenceModuleClient(){
        objectReferences = new HashMap<RemoteObjectReference, Object>();
    }
    
    /**
     * Adds a local reference to the reference module.
     * @param ror The RemoteObjectReference of the local object.
     * @param o The local object.
     */
    public void addReference(RemoteObjectReference ror, Object o){
        objectReferences.put(ror, o);
    }
    
    /**
     * Remove a local reference from the table of objects.
     * @param ror The RemoteObjectReference of the object to remove.
     */
    public void removeReference(RemoteObjectReference ror){
        objectReferences.remove(ror);
    }
    
    /**
     * Get the remote reference module.
     * @return RemoteReferenceModuleClient an instance of the reference module.
     */
    public static synchronized RemoteReferenceModuleClient getClientRemoteReference(){
        if(referenceLookup == null){
            referenceLookup = new RemoteReferenceModuleClient();
        }
        return referenceLookup;
    }
    
	/**
	 * Returns the local object.
	 * @return Returns the lookupLocalReference.
	 */
	public Object getReference(RemoteObjectReference ror){
	    Object o = objectReferences.get(ror);
	   return o;
	}
	
	private static RemoteReferenceModuleClient referenceLookup;
	private HashMap <RemoteObjectReference, Object> objectReferences;
}
