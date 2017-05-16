import java.io.Serializable;
import java.net.InetAddress;
import java.util.UUID;

/**
 * Created by cagri on 15.05.2017.
 */


public class RemoteObjectReference implements Serializable {
    private String name;
    private int port;
    private String className;
    private UUID uniqueID;
    private InetAddress address;

    public RemoteObjectReference(RegistryMessage message, InetAddress addr) {
        this.name = message.getName();
        this.className = message.getObject().getClass().getCanonicalName();
        // TODO skeloton logic -- if(impName.endsWith("Skeleton")){
        //if(this.className.contains("Skeleton")) {
        //   this.className = this.className.replace("Skeleton", "");
        //}
        this.port = message.getPort();
        this.address = addr;
        uniqueID = UUID.randomUUID(); // create unique ID for every reference
    }

    public RemoteObjectReference(String name, String className, int port, InetAddress addr) {
        this.name = name;
        this.className = className;
        this.port = port;
        this.address = addr;
        uniqueID = UUID.randomUUID(); // create unique ID for every reference
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public UUID getUniqueID() {
        return uniqueID;
    }

    public void setUniqueID(UUID uniqueID) {
        this.uniqueID = uniqueID;
    }

    public InetAddress getAddress() {
        return address;
    }

    public void setAddress(InetAddress address) {
        this.address = address;
    }
}
