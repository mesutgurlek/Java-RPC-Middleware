/*
 * Created on Oct 27, 2004 
 * @author Nathan Balon Middleware Application CIS 578 -
 * Advanced Operating Systems U 0f M Dearborn
 * @version 1.0 
 * 
 * The Proxy Generator accepts an arguement of a the name of Java
 * interface. The program then generates a stub from the interface to be used on
 * the client side of the middleware system.
 */

package nathan.middleware;
import java.lang.reflect.*;

import java.io.*;

/**
 * 
 * The program then generates a stub and a skeleton for the
 * middleware system.<br /><br />
 * 
 * To run the application: SubSkeletonGenerator [Class Name] [Interface Name]<br /><br />
 * 
 * Class name is the name of the class to generate the stubs and skeletons for.<br />
 * Interface name is the name of the Interface that the stub will implement.<br /><br />
 * 
 * A sample of how run the StubSkeletonGenerator is:<br /><br />
 * java nathan/middleware/StubSkeletonGenerator nathan.middleware.StudentListImpl
 *  nathan.middleware.StudentList<br />
 * 
 * @author  Nathan Balon<br />
 * Advance Operating Systems CIS 578<br />
 * University of Michigan Dearborn<br />
 * Remote Method Invocation Middleware Project<br />
 *
 */

public class StubSkeletonGenerator {
    /**
     * main entry point to the StubSkeletonGenerator apllication
     * @param args The command line arguments required are the
     * class name and interface name of the the stub and skeletons
     * to be generated
     */
    public static void main(String args[]) {

        // check the an interface was suplied as an argument
        if (args.length != 2) {
            System.err.println("Usage: SubSkeletonGenerator [Class Name] [Interface Name]");
            System.exit(1);
        }
        // create the stub for the class
        StubSkeletonGenerator stub = new StubSkeletonGenerator();
        stub.createStub(args[0], args[1]);
        // create the skeleton for the class
        StubSkeletonGenerator skel = new StubSkeletonGenerator();
        skel.createSkeleton(args[0], args[1]);
    }
    
    //  move the file to the correct place in the filesystem
    private static void moveFile(String fileToMove, String destinationDirectory){
        try{
            File file = new File(fileToMove);    
            // Destination directory
            File dir = new File("nathan/middleware");   
            // Move file to new directory
            boolean success = file.renameTo(new File(dir, file.getName()));
            if (!success) {
                System.err.println("Error unbale to move file");
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    // constructor that creates a new sting buffer
    private StubSkeletonGenerator() {
        fileName = null;
        st = new StringBuffer();
    }

    // add the heading information to the file
    private void addHeadings(String fileName) {
        st.append("/**\n*\n*   " + fileName + "\n");
        st.append("*\n*   @author Nathan Balon\n"
                + "*   Advanced Operating Systems - CIS 550\n");
        st.append("*   University of Michigan Dearborn\n*\n");
        st.append("*   Proxy created by Java reflection for the Interface "
                + c.getName() + ".java\n*/\n\n");
        this.packageName = c.getPackage().getName();
        st.append("package " + packageName + ";\n\n");
        // packages to import
        st.append("import java.util.*;\n");
        st.append("import java.net.*;\n");
    }

    // create the class declaration for the skeleton
    private void createClassDeclarationSkeleton(String type) {
        // generate the class declaration

        int mod = c.getModifiers();
        if (Modifier.isInterface(mod)) {
            mod = mod - Modifier.INTERFACE;
        }
        if (Modifier.isAbstract(mod)) {
            mod = mod - Modifier.ABSTRACT;
        }
        st.append(Modifier.toString(mod));
        // remove full class name

        st.append(" class " + fileName + type + " ");

        // get the name of the superclass
        supClass = c.getSuperclass();
        if (supClass != null) {
            st.append(" extends " + supClass.getName());
        }

        // get the interfaces the class implements
        in = c.getInterfaces();
        st.append(" {\n\n");
    }
    
    // create the class declaration for the stub
    private void createClassDeclarationStub(String type) {
        // generate the class declaration

        int mod = c.getModifiers();
        if (Modifier.isInterface(mod)) {
            mod = mod - Modifier.INTERFACE;
        }
        if (Modifier.isAbstract(mod)) {
            mod = mod - Modifier.ABSTRACT;
        }
        st.append(Modifier.toString(mod));
        // remove full class name

        st.append(" class " + fileName + type + " ");

        // get the name of the superclass
        supClass = c.getSuperclass();
        if (supClass != null) {
            st.append(" extends " + supClass.getName());
        }

        // get the interfaces the class implements
        in = c.getInterfaces();
        if (in.length != 0) {
            st.append(" implements ");
            for (int i = 0; i < in.length; i++) {
                st.append(in[i].getName());
                if (i < (in.length - 1)) {
                    st.append(", ");
                }
            }
        } 
        st.append(" {\n\n");
    }
    
    // create the skeletons constructor
    private void createSkeletonContructor(){
        st.append("\tpublic " + fileName + "Skeleton(RemoteObjectReference ror, Object remoteObject){\n");
        st.append("\t\tthis.remoteObject = (" + interfaceName + ")remoteObject;\n");
        st.append("\t\tint remoteObjectPort = ror.getPort();\n");
        st.append("\t\tserverModule = RemoteReferenceModuleServer.getServerRemoteReference();\n");
        st.append("\t\tserverModule.addReference(ror, this);\n");
        st.append("\t\tcomm = new ServerCommunication(remoteObjectPort);\n");
        st.append("\t}\n\n");
    }
    

    /**
     * Creates a new skeleton file for a class to be used to remotely 
     * access a remote object on the server.
     * @param className The name of the class that is to have a skeleton created
     * for it.
     * @param interfaceName The name of the interface that the remote object
     * will implement.
     */
    public void createSkeleton(String className, String interfaceName) {
        try {
            System.out.println(StubSkeletonGenerator.class);
            c = Class.forName(className);

        }
        catch (ClassNotFoundException e) {
            System.err.println("Could not find " + className);
            System.exit(1);
        }
        this.interfaceName = interfaceName;
        // print the comments for the proxy file
        int spot = c.getName().lastIndexOf('.');
        fileName = c.getName().substring(++spot);
        this.addHeadings(fileName);
        this.createClassDeclarationSkeleton("Skeleton");
        this.createSkeletonContructor();
        this.createSkeletonMethod();
        this.createSkeletonDataMembers();
        this.writeToFile(fileName + "Skeleton.java");
    }

    // create the data memeber that the skeleton will use
    private void createSkeletonDataMembers() {
        // data members for the proxy
        st.append("\t/*     private data members          */\n\n");
        st.append("\tprivate ServerCommunication comm;\n\n");
        st.append("\tprivate RemoteObjectReference ror;\n\n");
        st.append("\tprivate RemoteReferenceModuleServer serverModule;\n\n");
        st.append("\tprivate " +  interfaceName + " remoteObject;\n");
        st.append("}");

    }

    // create the methods that will be contained in the skeleton.
    private void createSkeletonMethod() {
        // get the methods for the proxy to implement
        st.append("\t/*       Declared Methods Generated         */\n\n");
        // get all declared methods
        mm = c.getDeclaredMethods();
        // create a skeleton method for each method of the class
        for (int i = 0; i < mm.length; i++) {
            String ret;
            int mod = mm[i].getModifiers();
            st.append("\t");
            if (Modifier.isAbstract(mod)) {
                mod = mod - Modifier.ABSTRACT;
            }
            st.append(Modifier.toString(mod));
            st.append(" synchronized Message ");
            String methodName = mm[i].getName();
            st.append(mm[i].getName());
            st.append("(Message message)");
            Class exceptionsThrown[] = mm[i].getExceptionTypes();
            for (int ex = 0; ex < exceptionsThrown.length; ex++) {
                if (exceptionsThrown.length != 0) {
                    if (ex == 0) {
                        st.append(" throws ");
                    }
                    st.append(exceptionsThrown[ex].getName());
                    if (i < (exceptionsThrown.length - 1)) {
                        st.append(", ");
                    }
                }
            }
            st.append("{\n\n");
            // method body
            // add parameters to a vector
            st.append("\t\tVector vec = message.getArguments();\n");
            if(mm[i].getReturnType().getName().equals("void")){
                st.append("\t\tremoteObject." + methodName + "(");
            } else {
                st.append("\t\t"); 
                if(mm[i].getReturnType().getName().equals("boolean")){
                    st.append("Boolean returnValue;\n");
                    st.append("\t\treturnValue = Boolean.valueOf(remoteObject." + methodName + "(");
                } else if (mm[i].getReturnType().getName().equals("int")){
                    st.append("Integer returnValue;\n");
                    st.append("\t\treturnValue = Integer.valueOf(remoteObject." + methodName + "(");
                } else if (mm[i].getReturnType().getName().equals("double")){
                    st.append("Double returnValue;\n");
                    st.append("\t\treturnValue = Double.valueOf(remoteObject." + methodName + "(");
                } else if (mm[i].getReturnType().getName().equals("char")){
                    st.append("Character returnValue;\n");
                    st.append("\t\treturnValue = Character.valueOf(remoteObject." + methodName + "(");
                } 
                else {
                    st.append(mm[i].getReturnType().getName() + " returnValue;\n");
                    st.append("\t\treturnValue = remoteObject." + methodName + "(");
                }

            }
            Class param[] = mm[i].getParameterTypes();
            int numberOfParameters = param.length;
            for (int j = 0; j < param.length; j++) {
                st.append(" ");
                if (param[j].getName().equals("int")) {
                    st.append("(Integer)");
                } else if(param[j].getName().equals("double")){
                    st.append("(Double)");               
                } else if(param[j].getName().equals("char")){
                    st.append("(Character)");                   
                } else if(param[j].getName().equals("boolean")){
                    st.append("(Boolean)");                
                } else {
                    st.append("(" + param[j].getName() + ")");
                }
                st.append("vec.get(" + j + ")");
                st.append(" ");
                if (j < (param.length - 1)) {
                    st.append(", ");
                }
            }     
            if(mm[i].getReturnType().getName().equals("boolean")){
                st.append("));\n");
            } else if(mm[i].getReturnType().getName().equals("int")){
                st.append("));\n");
            }else if(mm[i].getReturnType().getName().equals("double")){
                st.append("));\n");
            }else if(mm[i].getReturnType().getName().equals("char")){
                st.append("));\n");
            }else {
                st.append(");\n");
            }
            st.append("\t\tVector <Object> returnVector = new Vector<Object>();         "
                    + "// vector of args to pass back\n");            
            if(!mm[i].getReturnType().getName().equals("void")){
                st.append("\t\treturnVector.add(returnValue);\n");
            }
            st.append("\t\tmessage.setArguments(returnVector);\n");
            st.append("\n\t\t// return the message to the dispatcher module\n");
            st.append("\t\treturn message;\n");
            st.append("\t}\n\n");
        }
    }
    
    /**
     * Creates the stub file that the client wil use to access the
     * remote object on the server.  The stub acts as a proxy forwarding
     * all calls to the server.
     * @param className The name of the class to generate the stub for.
     * @param interfaceName The name of the interface to use to access the
     * stub object.
     */
    public void createStub(String className, String interfaceName) {
        try {
            System.out.println(StubSkeletonGenerator.class);
            c = Class.forName(className);
        }
        catch (ClassNotFoundException e) {
            System.err.println("Could not find " + className);
            System.exit(1);
        }
        this.interfaceName = interfaceName;
        // print the comments for the proxy file
        int spot = c.getName().lastIndexOf('.');
        fileName = c.getName().substring(++spot);
        this.addHeadings(fileName);
        this.createClassDeclarationStub("Stub");
        this.createStubConstructor();
        this.createStubFactory();
        this.createStubMethod();
        this.createStubDataMembers();
        this.writeToFile(fileName + "Stub.java");
    }
    
    // Creates a method to return a stub instance.
    private void createStubFactory(){
        st.append("\tpublic " + interfaceName + " "+ fileName + 
                  "Factory(){\n");
        st.append("\t\treturn stub;\n");
        st.append("\t}\n\n");
    }
    

    // Creates the constructor for the stub
    private void createStubConstructor(){
        st.append("\tpublic " + fileName + "Stub(RemoteObjectReference ror){\n");
        st.append("\t\tthis.ror = ror;\n");
        st.append("\t\tInetAddress remoteObjectAddress = ror.getAddress();\n");
        st.append("\t\tint remoteObjectPort = ror.getPort();\n");
        st.append("\t\tcomm = new ClientCommunication(remoteObjectAddress, remoteObjectPort);\n");
        st.append("\t\tclientModule = RemoteReferenceModuleClient.getClientRemoteReference();\n");
        st.append("\t\tclientModule.addReference(ror, stub);\n");
        st.append("\t}\n\n");
    }
    
    // Creates the data members that the stub will use.
    private void createStubDataMembers() {
        // data members for the proxy
        st.append("\t/*     private data members          */\n\n");
        st.append("\tprivate ClientCommunication comm;\n\n");
        st.append("\tprivate RemoteObjectReference ror;\n\n");
        st.append("\tprivate RemoteReferenceModuleClient clientModule;\n\n");
        st.append("\tprivate " + interfaceName + " stub;\n\n");
        st.append("}");
    }
   
    // Creates the methods that will be used by the stub
    private void createStubMethod() {
        // get the methods for the proxy to implement
        st.append("\t/*       Declared Methods Generated         */\n\n");
        mm = c.getDeclaredMethods();
        // create a stub method for each method declared in the class
        for (int i = 0; i < mm.length; i++) {
            String ret;
            int mod = mm[i].getModifiers();
            st.append("\t");
            if (Modifier.isAbstract(mod)) {
                mod = mod - Modifier.ABSTRACT;
            }
            st.append(Modifier.toString(mod));
            st.append(" ");
            ret = mm[i].getReturnType().getName();
            st.append(ret);
            st.append(" ");
            st.append(mm[i].getName());
            st.append("( ");
            Class param[] = mm[i].getParameterTypes();
            int numberOfParameters = param.length;

            // create all the parameters passes to the stub
            for (int j = 0; j < param.length; j++) {
                st.append(param[j].getName());
                st.append(" param");
                st.append(j);
                st.append(" ");
                if (j < (param.length - 1)) {
                    st.append(", ");
                }
            }
            st.append(")");
            Class exceptionsThrown[] = mm[i].getExceptionTypes();
            // create all exceptions for the stub
            for (int ex = 0; ex < exceptionsThrown.length; ex++) {
                if (exceptionsThrown.length != 0) {
                    if (ex == 0) {
                        st.append(" throws ");
                    }
                    st.append(exceptionsThrown[ex].getName());
                    if (i < (exceptionsThrown.length - 1)) {
                        st.append(", ");
                    }
                }
            }
            st.append("{\n\n");
            // method body
            st.append("\t\tVector <Object> vec = new Vector<Object>();         "
                            + "// vector of args to pass\n");
            // add parameters to a vector
            for (int j = 0; j < param.length; j++) {
                st.append("\t\tvec.add(");
                     if(param[j].getName().equals("char")){
                         st.append("(Character)");
                     }
                st.append("param" + j + ");\n");
            }
            st.append("\t\t// Create a message to send\n");
            st.append("\t\tMessage message = new Message();\n");
            st.append("\n\t\t// Build the message to send\n");
            //			st.append("\t\tmessage.setRemoteObjectReference();\n");
            st.append("\t\tmessage.setMethodName(\"" + mm[i].getName()
                    + "\");\n");
            st.append("\t\tmessage.setArguments(vec);\n");
            st.append("\t\tmessage.setRemoteObjectReference(ror);\n");
            st.append("\n\t\t// Pass the message to the communication module\n");
            st.append("\t\tcomm.sendMessage(message);\n");
            if (!mm[i].getReturnType().getName().equals("void")) {
                st.append("\n\t\t// Get the message returned from the Server\n");
                st.append("\t\tmessage = comm.getMessage();\n");
                st.append("\t\tVector returnedFromServer = message.getArguments();\n");
                if (mm[i].getReturnType().getName().equals("int")) {
                    st.append("\t\tInteger returnValue = " + "(Integer) "
                            + "returnedFromServer.get(0);\n");
                    st.append("\t\treturn returnValue.intValue();\n");
                } else if(mm[i].getReturnType().getName().equals("double")){
                    st.append("\t\tDouble returnValue = " + "(Double) "
                            + "returnedFromServer.get(0);\n");
                    st.append("\t\treturn returnValue.doubleValue();\n");
                } else if(mm[i].getReturnType().getName().equals("char")){
                    st.append("\t\t Character returnValue = " + "(Character) "
                            + "returnedFromServer.get(0);\n"); 
                    st.append("\t\treturn returnValue.charValue();\n");
                } else if(mm[i].getReturnType().getName().equals("boolean")){
                    st.append("\t\tBoolean returnValue = " + "(Boolean) "
                            + "returnedFromServer.get(0);\n");
                    st.append("\t\treturn returnValue.booleanValue();\n");
                } else {
                    st.append("\t\t" + ret + " returnValue = ("
                            + mm[i].getReturnType().getName() + ") "
                            + "returnedFromServer.get(0);\n");
                    st.append("\t\treturn returnValue;\n");
                }

            }
            st.append("\t}\n\n");
        }
    }

    // writes the stub or skeleton to a file.
    private void writeToFile(String fileName) {
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(fileName));
            out.write(st.toString());
            String compileResult = "";
            out.flush();
            out.close();
            System.out.println("Generating file: " + fileName);
            
            // compile the the new file created
            String command = "javac " + fileName;
            Process proc = 
                Runtime.getRuntime().exec("javac " + fileName);
            InputStream inputstream =
                proc.getInputStream();
            InputStreamReader inputstreamreader =
                new InputStreamReader(inputstream);
            BufferedReader bufferedreader =
                new BufferedReader(inputstreamreader);    
            // read the output    
            String line;
            while ((line = bufferedreader.readLine()) 
                      != null) {
                System.out.println(line);
            }
        
            // check for ls failure
    
            try {
                if (proc.waitFor() != 0) {
                    System.err.println("exit value = " +
                        proc.exitValue());
                    if(proc.exitValue() > 0){
                        System.err.println("Failed to compile " + fileName);
                        System.exit(1);
                    }
                }
            }
            catch (InterruptedException e) {
                System.err.println(e);
            }
            // move the file to the correct location in the directory
            String dir = packageName.replace('.', '/');
            StubSkeletonGenerator.moveFile(fileName, dir);
            String classOut = fileName.replace(".java", ".class");
            StubSkeletonGenerator.moveFile(classOut, dir);            
        }

        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Class c; 				// class

    private String fileName; 		// the name of the file

    private Class in[]; 			// interfaces implemented
    
    private String interfaceName;	// the name of the interface

    private Method mm[]; 			// the methods

    private String packageName;		// the package that the stub or skeleton belongs to
    
    private StringBuffer st;		// buffer used to construct the .java file

    private Class supClass; 		// super class
}