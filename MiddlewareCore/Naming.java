import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Constructor;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by cagri on 16.05.2017.
 */
public class Naming {

    public static synchronized Object lookup(String name, String IP, int portNo) {
        ObjectOutputStream outputStream = null;
        ObjectInputStream inputStream = null;
        Socket registrySocket = null;
        Object stubObject = null;
        try {
            registrySocket = new Socket(IP, portNo);
            // create streams for registry communication
            outputStream = new ObjectOutputStream(registrySocket.getOutputStream());
            inputStream = new ObjectInputStream(registrySocket.getInputStream());

            // create a msg to send to registry server
            RegistryMessage message = new RegistryMessage(RegistryMessageType.LOOKUP, name);
            // send msg
            outputStream.writeObject(message);
            // receive msg (reference)
            RemoteObjectReference remoteObjectReference = (RemoteObjectReference)inputStream.readObject();

            if(remoteObjectReference == null) {
                System.out.println("Object is not found");
                return null;
            }
            else {
                String className = remoteObjectReference.getClassName(); // CalculatorInterface
                /* TODO create a stub with using this class name
                 TODO use parameter of remoteObjRef to connect stup to server
                 TODO then return that stub */
                String stubName = className + "Stub";
                Class stubClass = Class.forName(stubName);

                Constructor stubConst = stubClass.getConstructor(RemoteObjectReference.class);
                stubObject = stubConst.newInstance(remoteObjectReference);
                System.out.println("Lookup is succesful:" + name);

            }


        } catch (Exception e) {

        }
        finally {
            try {
                inputStream.close();
                outputStream.close();
                registrySocket.close();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        return stubObject;
    }

    public static Object bind(Object obj, String name, String registryIP, int registryPort, int objPort) {
       Object skeletonObj = null;
        ObjectOutputStream outputStream = null;
        ObjectInputStream inputStream = null;
        Socket registrySocket = null;
        try {
            registrySocket = new Socket(registryIP, registryPort);
            // create streams for registry communication
            outputStream = new ObjectOutputStream(registrySocket.getOutputStream());
            inputStream = new ObjectInputStream(registrySocket.getInputStream());

            // create a msg to send to registry server
            RegistryMessage message = new RegistryMessage(RegistryMessageType.BIND, name, obj, objPort);
            // send msg
            outputStream.writeObject(message);
            // receive msg (reference)
            RemoteObjectReference remoteObjectReference = (RemoteObjectReference)inputStream.readObject();

            if(remoteObjectReference == null) {
                System.out.println("Binding error (null return)");
                return null;
            }
            else {
                String className = remoteObjectReference.getClassName(); // EX. CalculatorInterface
                System.out.println("Binding: " + className);
                /* TODO create a skeleton with using this class name
                 TODO use parameter of remoteObjRef to connect skeleton to server
                 TODO then return that skeleton */
                String skeletonName = className + "Skeleton";
                Class skeletonClass = Class.forName(skeletonName);
                Class[] constArgTypes = {RemoteObjectReference.class, Object.class};
                Constructor skeletonConst = skeletonClass.getConstructor(constArgTypes);
                Object[] skeletonConstArgs = {remoteObjectReference, obj};

                skeletonObj = skeletonConst.newInstance(skeletonConstArgs);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                inputStream.close();
                outputStream.close();
                registrySocket.close();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        return skeletonObj;
    }

    public static void remove(String name, String IP, int portNo) {
        ObjectOutputStream outputStream = null;
        ObjectInputStream inputStream = null;
        Socket registrySocket = null;

        try {
            registrySocket = new Socket(IP, portNo);
            // create streams for registry communication
            outputStream = new ObjectOutputStream(registrySocket.getOutputStream());
            inputStream = new ObjectInputStream(registrySocket.getInputStream());

            // create a msg to send to registry server
            RegistryMessage message = new RegistryMessage(RegistryMessageType.REMOVE, name, null, 0);
            // send msg
            outputStream.writeObject(message);
            boolean isRemoved = (boolean)inputStream.readObject();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


}
