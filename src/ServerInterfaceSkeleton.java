import java.util.Vector;

public class ServerInterfaceSkeleton {

	/*     private data members          */

    private ServerCommunicationModule comm;

    private RemoteObjectReference ror;

    private RemoteReferenceModuleServer serverModule;

    private ServerInterface remoteObject;

    public ServerInterfaceSkeleton(RemoteObjectReference ror, Object remoteObject) {
        this.remoteObject = (ServerInterface) remoteObject;
        int remoteObjectPort = ror.getPort();
        serverModule = RemoteReferenceModuleServer.getServerRemoteReference();
        serverModule.addObjectReference(ror, this);
        comm = new ServerCommunicationModule(remoteObjectPort);
    }

	/*       Declared Methods Generated         */

    public Message match(Message message) {

        Vector vec = message.getArguments();
        java.lang.String returnValue;
        returnValue = remoteObject.match((java.lang.String) vec.get(0), (Integer) vec.get(1));
        Vector<Object> returnVector = new Vector<Object>();         // vector of args to pass back
        returnVector.add(returnValue);
        message.setArguments(returnVector);

        // return the message to the dispatcher module
        return message;
    }

}