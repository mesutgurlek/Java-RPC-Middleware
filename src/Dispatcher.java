import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by aeakdogan on 16/05/2017.
 * ${CLASS}
 */
public class Dispatcher {
    public Message dispatch(Message message){
        RemoteObjectReference remoteObjectReference = message.getRemoteObjectReference();
        System.out.println(remoteObjectReference.getClassName());
        RemoteReferenceModuleServer remoteReferenceModuleServer = RemoteReferenceModuleServer.getServerRemoteReference();
        System.out.println(remoteReferenceModuleServer.remoteObjectReferenceObjectHashMap.toString());
        System.out.println(remoteObjectReference);

        Object skeleton = remoteReferenceModuleServer.getObjectReference(remoteObjectReference);
        System.out.println(skeleton);
        System.out.println("dispatch: " + skeleton.getClass().toString());
        for(Method method: skeleton.getClass().getMethods()){
            if(method.getName().equals(message.getMethodName())){
                try {
                    message = (Message)method.invoke(skeleton, message);
                    System.out.println(message.getMethodName() + " Method Invoked");
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        return message;
    }
}
