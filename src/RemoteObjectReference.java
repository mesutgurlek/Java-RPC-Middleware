import java.io.Serializable;
import java.net.InetAddress;

/**
 * Created by cagri on 15.05.2017.
 */


public class RemoteObjectReference implements Serializable {
    private String name;
    private int port;
    private String className;
    private InetAddress address;

    public RemoteObjectReference(RegistryMessage message, InetAddress addr) {
        this.name = message.getName();
        this.className = message.getObject().getClass().getInterfaces()[0].getName();
        // TODO skeloton logic -- if(impName.endsWith("Skeleton")){
        //if(this.className.contains("Skeleton")) {
        //   this.className = this.className.replace("Skeleton", "");
        //}
        this.port = message.getPort();
        this.address = addr;
    }

    public RemoteObjectReference(String name, String className, int port, InetAddress addr) {
        this.name = name;
        this.className = className;
        this.port = port;
        this.address = addr;
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

    public InetAddress getAddress() {
        return address;
    }

    public void setAddress(InetAddress address) {
        this.address = address;
    }

    public int hashCode() {
        int hashCode = 17;
        hashCode = 37 * hashCode + name.hashCode();
        hashCode = 37 * hashCode + address.hashCode();
        hashCode = 37 * hashCode + port;
        return hashCode;
    }

    /**
     * Compares if two RemoteObjectReferences are equal.
     *
     * @param o the object to compare for equality.
     * @return boolean Returns true if two RemoteObjectReferences are equal.
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object o) {
        if ((o != null) && (this.getClass() == o.getClass())) {
            RemoteObjectReference ror = (RemoteObjectReference) o;
            return (port == ror.getPort()) &&
                    (address.equals(ror.getAddress()) &&
                            (name.equals(ror.getName())));
        } else {
            return false;
        }
    }
}
