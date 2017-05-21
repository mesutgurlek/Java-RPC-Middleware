# Java-RPC-Middleware

## Registry System
![Alt text](../master/figures/figure1.png)

For the registry system, a hashmap is used to store each entry. This systems maps remote object names to remote object references. When an object is binded to the registry, a remote object references is created with using port, address, name and class name of the object which is binded to the registry. After the binding is done, clients who want to use the binded objects could access the remote objects with using RMI system with simple lookup. So, with this system clients could access remote objects by just knowing the name of the entry and address of the registry.

## General System Architecture Overview with Calculator Example

![Alt text](../master/figures/figure2.png)

At this phase, client calls add method of Calculator Stub. Then, even the client doesn’t realize the underlying structure, a message is created at the stub and sent to Server Communication Module with using Client Communication Module. Then, server module propagates this message to the dispatcher. It checks the method name field and sees it is “add” method. Then, it calls the add method of the related skeleton with using this message as a parameter. Calculator Skeleton unmarshalls the arguments (3 and 4), calls the add method of actual Calculator object. Then, it calculates the result which is 7, and sends the return value back to the client with using same path.

### How to Run Calculator Example

First, compile all the code using following command inside the SimpleCalculatorExample folder:

  > javac *
  
Then run StubSkeletonGenerator using following command:

  > java StubSkeletonGenerator CalculatorInterface
  
After that, to run registry use the following command:

  > java Registry 'registryPortNo'

Start the CalculatorServer:

  > java CalculatorServer 'objectName' 'registryIPAddress' 'registryPortNo' 'objectPortNo'
  
Finally, run the ClientTest to test Calculator operations:

  > java ClientTest 'objectName' 'registryIPAddress' 'registryPortNo'
  
Now you can enter 2 numbers to call add function of the object which is located at the server. 
