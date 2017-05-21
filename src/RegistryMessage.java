import java.io.Serializable;

/**
 * Created by cagri on 15.05.2017.
 */
public class RegistryMessage implements Serializable {
    private RegistryMessageType type;
    private String name;
    private Object object;
    private int port;

    // constructor to create binding message
    public RegistryMessage(RegistryMessageType type, String name, Object object, int port) {
        this.setType(type);
        this.setName(name);
        this.setObject(object);
        this.setPort(port);
    }

    // constructor to create lookup message
    public RegistryMessage(RegistryMessageType type, String name) {
        this.setType(type);
        this.setName(name);
    }

    public RegistryMessageType getType() {
        return type;
    }

    public void setType(RegistryMessageType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
