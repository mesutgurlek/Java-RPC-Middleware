import java.util.HashMap;

/**
 * Created by aeakdogan on 17/05/2017.
 * ${CLASS}
 */
public class RemoteReferenceModuleServer {
    private static RemoteReferenceModuleServer remoteReferenceModuleServer;
    public HashMap<RemoteObjectReference, Object> remoteObjectReferenceObjectHashMap;

    //SINGLETON PATTERN
    public static synchronized RemoteReferenceModuleServer getServerRemoteReference(){
        if(remoteReferenceModuleServer != null)
            return remoteReferenceModuleServer;
        return remoteReferenceModuleServer = new RemoteReferenceModuleServer();
    }

    public RemoteReferenceModuleServer() {
        remoteObjectReferenceObjectHashMap = new HashMap<>();
    }


    public synchronized void addObjectReference(RemoteObjectReference remoteObjectReference, Object object){
        remoteObjectReferenceObjectHashMap.put(remoteObjectReference, object);
    }

    public synchronized void removeObjectReference(RemoteObjectReference remoteObjectReference){
        remoteObjectReferenceObjectHashMap.remove(remoteObjectReference);
    }

    public synchronized Object getObjectReference(RemoteObjectReference remoteObjectReference){
        return remoteObjectReferenceObjectHashMap.get(remoteObjectReference);
    }

}
