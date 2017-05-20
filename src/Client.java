import java.io.Serializable;

public class Client implements ClientInterface, Serializable
{ 
	private String name;
	private int timeout;
	private boolean handshake;
	public Client(String name, int timeout) {
		this.name = name;
		this.timeout = timeout;
		handshake = false;
	}
	@Override
	public void handshake() {
		handshake = true;
	} 
	
	public String getName() {
		return name;
	}
	public boolean getHandshake() {
		return handshake;
	}

	public void setName(String name) {
		this.name = name;
		
	}

	public int getTimeout() {
		return timeout;
	}
	

	

} 