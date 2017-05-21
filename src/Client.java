import java.io.Serializable;

public class Client implements ClientInterface, Serializable {
    private String name;
    private int timeout;
    private boolean handshake;
    private int port;

    public Client(String name, int timeout, int port) {
        this.name = name;
        this.timeout = timeout;
        handshake = false;
        this.setPort(port);

    }

    @Override
    public void handshake() {
        handshake = true;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;

    }

    public boolean getHandshake() {
        return handshake;
    }

    public int getTimeout() {
        return timeout;
    }


    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}