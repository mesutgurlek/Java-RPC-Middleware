import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by aeakdogan on 16/05/2017.
 * ${CLASS}
 */
public class Message implements Serializable {
    int id;
    MessageType messageType;
    RemoteObjectReference remoteObjectReference;
    String methodName;
    ArrayList<Object> arguments;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public RemoteObjectReference getRemoteObjectReference() {
        return remoteObjectReference;
    }

    public void setRemoteObjectReference(RemoteObjectReference remoteObjectReference) {
        this.remoteObjectReference = remoteObjectReference;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public ArrayList<Object> getArguments() {
        return arguments;
    }

    public void setArguments(ArrayList<Object> arguments) {
        this.arguments = arguments;
    }
}
