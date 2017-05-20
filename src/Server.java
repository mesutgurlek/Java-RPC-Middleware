
import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;


public class Server implements ServerInterface, Serializable
{ 
	private String prevName1, prevName2;
	private final ReentrantLock  lock1 = new ReentrantLock ();
	private final ReentrantLock  lock2 = new ReentrantLock ();
	private final String  generalLock = new String ();
    public Server(){
    	prevName1 = prevName2 = null;
    }

    public String match(String name, int timeoutSecs) {
    	System.out.println("Incoming match request: "  + "name: " + name + " timeout: " + timeoutSecs);
    	
    	while(lock1.isLocked());
		if (lock1.isLocked() == false && prevName1 == null) {
			lock1.lock();
			prevName1 = name;
			lock1.unlock();
			synchronized (generalLock) {
				try {
					generalLock.wait(1000 * timeoutSecs);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			lock2.lock();
			String val = prevName2;
			prevName2 = null; // clear two
			lock2.unlock();
			// clear one
			lock1.lock();
			prevName1 = null;
			lock1.unlock();
			
			return val;					
    	}
		else {
				lock2.lock();
	    		prevName2 = name;
	    		lock2.unlock();
	    		
	    		synchronized (generalLock) {
	    			generalLock.notify();
	    		}
	    		
	    		lock1.lock();
	    		String val = prevName1;
	    		prevName1 = null;
	    		lock1.unlock();	   		
	    		return val;
			}
	}
    
    
    public synchronized  void assign1(String name) {
    	prevName1 = name;
    }
    
    public synchronized  void assign2(String name) {
    	prevName2 = name;
    }
  
}
