import java.lang.reflect.Method;

/**
 * Created by aeakdogan on 16/05/2017.
 * ${CLASS}
 */
public class Dispatcher {
    public Message dispatch(Message message){
        RemoteObjectReference remoteObjectReference = message.getRemoteObjectReference();

        //TODO use referenceLookup for getting methods
//        RemoteReferenceModuleServer referenceLookup = RemoteReferenceModuleServer.getServerRemoteReference();
//
//        Object skeleton = referenceLookup.getReference(ror);
//        Class c = skeleton.getClass();
//        Method[] declaredMethods = c.getMethods();
        Method[] methods = new Method[10];


        for(Method method: methods){
            if(method.getName().equals(message.getMethodName())){
                Object[] arguments = new Object[]{message};
                //TODO insert skeleton
//                message = (Message)method.invoke((Object)skeleton, arguments);
            }
        }
        return message;
    }
}
