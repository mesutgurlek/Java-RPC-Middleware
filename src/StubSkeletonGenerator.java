import java.io.*;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * Created by mesutgurlek on 5/16/17.
 *
 * Stub and skeleton generator for the RPC system
 *
 * Usage is as the following:
 *      java StubSkeletonGenerator 'Class Name' 'Class Interface Name'
 */


public class StubSkeletonGenerator {
    private Class stubInterface;
    private Class skeletonInterface;
    private Method methods[];
    private StringBuffer generatedCode;
    private String interfaceName;

    public static void main(String args[]){

        // Take the interface name that we are going to use for generating stub and skeleton
        if (args.length != 1) {
            System.err.println("Usage: StubSkeletonGenerator <Interface Name>");
            System.exit(1);
        }
        String interf = args[0];

        //Create stub
        StubSkeletonGenerator stub = new StubSkeletonGenerator();
        stub.generateStub(interf);

        // Create skeleton
        StubSkeletonGenerator skel = new StubSkeletonGenerator();
        skel.generateSkeleton(interf);
    }

    //  move the file to the correct place in the filesystem
    private static void moveFile(String fileToMove, String destinationDirectory){
        try{
            File file = new File(fileToMove);
            // Destination directory
            File dir = new File(destinationDirectory);
            // Move file to new directory
            boolean success = file.renameTo(new File(dir, file.getName()));
            if (!success) {
                System.err.println("Error while moving file");
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    private StubSkeletonGenerator() {
        this.interfaceName = null;
        this.generatedCode = new StringBuffer();
    }

    private void generateStub(String interfaceName){
        this.interfaceName = interfaceName;
        String filename = interfaceName + "Stub";
        generateHeader();
        generateStubClassDefinition("Stub");
        generateStubVariables();
        generateStubConstructor(filename);
        generateStubMethods();
        writeToFile(filename + ".java");
    }

    // Generates header information and libraries
    private void generateHeader() {
        // Import libraries
        generatedCode.append("import java.util.*;\n");
        generatedCode.append("import java.net.*;\n");
    }

    // Generate class definition
    // Uses Java Reflection
    private void generateStubClassDefinition(String fileType){
        try{
            stubInterface = Class.forName(this.interfaceName);
        }catch (ClassNotFoundException e){
            System.err.println("Got Error: " + e.toString());
        }

        int stubModifier = stubInterface.getModifiers();
        stubModifier = stubModifier - Modifier.INTERFACE - Modifier.ABSTRACT;
        generatedCode.append(Modifier.toString(stubModifier));
        generatedCode.append(" class " + this.interfaceName + fileType + " implements " + interfaceName);
        generatedCode.append(" {\n\n");
    }

    // Generates stub variables
    private void generateStubVariables() {
        generatedCode.append("\tprivate ClientCommunicationModule comm;\n");
        generatedCode.append("\tprivate RemoteObjectReference ror;\n");
        generatedCode.append("\n\n");
    }

    // Generates stub constructor
    private void generateStubConstructor(String filename){
        generatedCode.append("\tpublic " + filename + "(RemoteObjectReference ror){\n");
        generatedCode.append("\t\tthis.ror = ror;\n");
        generatedCode.append("\t\tthis.comm = new ClientCommunicationModule();\n");
        generatedCode.append("\t}\n\n");
    }

    // Generates the Stub Methods
    private void generateStubMethods() {
        // Get methods using Java Reflection
        methods = stubInterface.getDeclaredMethods();

        // Generate a stub method for all methods in interface
        for (int i = 0; i < methods.length; i++) {
            String returnType;
            int modifiers = methods[i].getModifiers();
            generatedCode.append("\t");
            if (Modifier.isAbstract(modifiers)) {
                modifiers = modifiers - Modifier.ABSTRACT;
            }
            generatedCode.append(Modifier.toString(modifiers));
            generatedCode.append(" ");
            returnType = methods[i].getReturnType().getName();
            generatedCode.append(returnType);
            generatedCode.append(" ");
            generatedCode.append(methods[i].getName());
            generatedCode.append("( ");

            Class parameters[] = methods[i].getParameterTypes();
            int numOfParams = parameters.length;

            // Create all the parameters that passes to the stub
            for (int j = 0; j < numOfParams; j++) {
                generatedCode.append(parameters[j].getName());
                generatedCode.append(" param");
                generatedCode.append(j);
                generatedCode.append(" ");
                if (j < (numOfParams - 1)) {
                    generatedCode.append(", ");
                }
            }
            generatedCode.append(")");

            Class exceptions[] = methods[i].getExceptionTypes();
            // Generate exceptions for the stub method
            for (int e = 0; e < exceptions.length; e++) {
                if (e == 0) {
                    generatedCode.append(" throws ");
                }
                generatedCode.append(exceptions[e].getName());
                if (i < (exceptions.length - 1)) {
                    generatedCode.append(", ");
                }
            }
            generatedCode.append("{\n\n");
            // Method body
            generatedCode.append("\t\t// Vector of args to pass\n" +
                    "\t\tVector <Object> vec = new Vector<Object>();\n");
            // Adding parameters to a vector
            for (int j = 0; j < parameters.length; j++) {
                generatedCode.append("\t\tvec.add(");
                if(parameters[j].getName().equals("char")){
                    generatedCode.append("(Character)");
                }
                generatedCode.append("param" + j + ");\n");
            }
            generatedCode.append("\t\t// Create a message to send\n");
            generatedCode.append("\t\tMessage message = new Message();\n");
            generatedCode.append("\n\t\t// Build the message to send\n");
            generatedCode.append("\t\tmessage.setMethodName(\"" + methods[i].getName() + "\");\n");
            generatedCode.append("\t\tmessage.setArguments(vec);\n");
            generatedCode.append("\t\tmessage.setRemoteObjectReference(ror);\n");
            generatedCode.append("\n\t\t// Pass the message to the communication module\n");
            generatedCode.append("\t\tcomm.sendMessage(message);\n");
            if (!methods[i].getReturnType().getName().equals("void")) {
                generatedCode.append("\n\t\t// Get the message returned from the Server\n");
                generatedCode.append("\t\tmessage = comm.receiveMessage();\n");
                generatedCode.append("\t\tVector returnedFromServer = message.getArguments();\n");
                if (methods[i].getReturnType().getName().equals("int")) {
                    generatedCode.append("\t\tInteger returnType = " + "(Integer) "
                            + "returnedFromServer.get(0);\n");
                    generatedCode.append("\t\treturn returnType.intValue();\n");
                } else if(methods[i].getReturnType().getName().equals("double")){
                    generatedCode.append("\t\tDouble returnType = " + "(Double) "
                            + "returnedFromServer.get(0);\n");
                    generatedCode.append("\t\treturn returnType.doubleValue();\n");
                } else if(methods[i].getReturnType().getName().equals("char")){
                    generatedCode.append("\t\t Character returnType = " + "(Character) "
                            + "returnedFromServer.get(0);\n");
                    generatedCode.append("\t\treturn returnType.charValue();\n");
                } else if(methods[i].getReturnType().getName().equals("boolean")){
                    generatedCode.append("\t\tBoolean returnType = " + "(Boolean) "
                            + "returnedFromServer.get(0);\n");
                    generatedCode.append("\t\treturn returnType.booleanValue();\n");
                } else {
                    generatedCode.append("\t\t" + returnType + " returnType = ("
                            + methods[i].getReturnType().getName() + ") "
                            + "returnedFromServer.get(0);\n");
                    generatedCode.append("\t\treturn returnType;\n");
                }

            }
            generatedCode.append("\t}\n\n");
        }
        generatedCode.append("}");
    }

    // writes the stub or skeleton to a file.
    private void writeToFile(String fileName) {
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(fileName));
            out.write(generatedCode.toString());
            out.flush();
            out.close();
            System.out.println("Generating file: " + fileName);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        String dir = "src/";
        moveFile(fileName, dir);
    }

    private void generateSkeleton(String interfaceName){
        generatedCode = new StringBuffer();
        this.interfaceName = interfaceName;
        String filename = interfaceName + "Skeleton";
        generateHeader();
        generateSkeletonClassDefinition("Skeleton");
        generateSkeletonVariables();
        generateSkeletonConstructor(filename);
        generateSkeletonMethods();
        writeToFile(filename + ".java");
    }

    private void generateSkeletonClassDefinition(String fileType){
        try{
            skeletonInterface = Class.forName(this.interfaceName);
        }catch (ClassNotFoundException e){
            System.err.println("Got Error: " + e.toString());
        }

        int skeletonModifier = skeletonInterface.getModifiers();
        skeletonModifier = skeletonModifier - Modifier.INTERFACE - Modifier.ABSTRACT;
        generatedCode.append(Modifier.toString(skeletonModifier));
        generatedCode.append(" class " + this.interfaceName + fileType);
        generatedCode.append(" {\n\n");
    }

    private void generateSkeletonVariables(){
        generatedCode.append("\tprivate ServerCommunicationModule comm;\n");
        generatedCode.append("\tprivate RemoteObjectReference ror;\n");
        generatedCode.append("\tprivate RemoteReferenceModuleServer serverModule;\n");
        generatedCode.append("\tprivate " +  interfaceName + " remoteObject;\n");
        generatedCode.append("\n\n");
    }

    private void generateSkeletonConstructor(String filename){
        generatedCode.append("\tpublic " + filename + "(RemoteObjectReference ror, Object remoteObject){\n");
        generatedCode.append("\t\tthis.remoteObject = (" + interfaceName + ")remoteObject;\n");
        generatedCode.append("\t\tserverModule = RemoteReferenceModuleServer.getServerRemoteReference();\n");
        generatedCode.append("\t\tserverModule.addObjectReference(ror, this);\n");
        generatedCode.append("\t\tcomm = new ServerCommunicationModule(ror.getPort());\n");
        generatedCode.append("\t}\n\n");
    }

    private void generateSkeletonMethods() {
        // get the methods for the proxy to implement
        // get all declared methods
        methods = skeletonInterface.getDeclaredMethods();
        // create a skeleton method for each method of the class
        for (int i = 0; i < methods.length; i++) {
            String ret;
            int mod = methods[i].getModifiers();
            generatedCode.append("\t");
            if (Modifier.isAbstract(mod)) {
                mod = mod - Modifier.ABSTRACT;
            }
            generatedCode.append(Modifier.toString(mod));
            generatedCode.append(" Message ");
            String methodName = methods[i].getName();
            generatedCode.append(methods[i].getName());
            generatedCode.append("(Message message)");
            Class exceptionsThrown[] = methods[i].getExceptionTypes();
            for (int ex = 0; ex < exceptionsThrown.length; ex++) {
                if (ex == 0) {
                    generatedCode.append(" throws ");
                }
                generatedCode.append(exceptionsThrown[ex].getName());
                if (i < (exceptionsThrown.length - 1)) {
                    generatedCode.append(", ");
                }
            }
            generatedCode.append("{\n\n");
            // method body
            // add parameters to a vector
            generatedCode.append("\t\tVector vec = message.getArguments();\n");
            if (methods[i].getReturnType().getName().equals("void")) {
                generatedCode.append("\t\tremoteObject." + methodName + "(");
            } else {
                generatedCode.append("\t\t");
                if (methods[i].getReturnType().getName().equals("boolean")) {
                    generatedCode.append("Boolean returnValue;\n");
                    generatedCode.append("\t\treturnValue = Boolean.valueOf(remoteObject." + methodName + "(");
                } else if (methods[i].getReturnType().getName().equals("int")) {
                    generatedCode.append("Integer returnValue;\n");
                    generatedCode.append("\t\treturnValue = Integer.valueOf(remoteObject." + methodName + "(");
                } else if (methods[i].getReturnType().getName().equals("double")) {
                    generatedCode.append("Double returnValue;\n");
                    generatedCode.append("\t\treturnValue = Double.valueOf(remoteObject." + methodName + "(");
                } else if (methods[i].getReturnType().getName().equals("char")) {
                    generatedCode.append("Character returnValue;\n");
                    generatedCode.append("\t\treturnValue = Character.valueOf(remoteObject." + methodName + "(");
                } else {
                    generatedCode.append(methods[i].getReturnType().getName() + " returnValue;\n");
                    generatedCode.append("\t\treturnValue = remoteObject." + methodName + "(");
                }

            }
            Class param[] = methods[i].getParameterTypes();
            int numbOfParams = param.length;
            for (int j = 0; j < numbOfParams; j++) {
                generatedCode.append(" ");
                if (param[j].getName().equals("int")) {
                    generatedCode.append("(Integer)");
                } else if (param[j].getName().equals("double")) {
                    generatedCode.append("(Double)");
                } else if (param[j].getName().equals("char")) {
                    generatedCode.append("(Character)");
                } else if (param[j].getName().equals("boolean")) {
                    generatedCode.append("(Boolean)");
                } else {
                    generatedCode.append("(" + param[j].getName() + ")");
                }
                generatedCode.append("vec.get(" + j + ")");
                generatedCode.append(" ");
                if (j < (numbOfParams - 1)) {
                    generatedCode.append(", ");
                }
            }
            if (methods[i].getReturnType().getName().equals("boolean")) {
                generatedCode.append("));\n");
            } else if (methods[i].getReturnType().getName().equals("int")) {
                generatedCode.append("));\n");
            } else if (methods[i].getReturnType().getName().equals("double")) {
                generatedCode.append("));\n");
            } else if (methods[i].getReturnType().getName().equals("char")) {
                generatedCode.append("));\n");
            } else {
                generatedCode.append(");\n");
            }
            generatedCode.append("\t\tVector <Object> returnVector = new Vector<Object>();         "
                    + "// vector of args to pass back\n");
            if (!methods[i].getReturnType().getName().equals("void")) {
                generatedCode.append("\t\treturnVector.add(returnValue);\n");
            }
            generatedCode.append("\t\tmessage.setArguments(returnVector);\n");
            generatedCode.append("\n\t\t// return the message to the dispatcher module\n");
            generatedCode.append("\t\treturn message;\n");
            generatedCode.append("\t}\n\n");
        }
        generatedCode.append("}");
    }

}
