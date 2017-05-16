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

//        // Create skeleton
//        StubSkeletonGenerator skel = new StubSkeletonGenerator();
//        skel.generateSkeleton(interf);
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
        generateStubFactory(filename);
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
        generatedCode.append(" class " + this.interfaceName + fileType);
        generatedCode.append(" {\n\n");
    }

    // Generates stub variables
    private void generateStubVariables() {
        generatedCode.append("\t/*     Private Variables          */\n\n");
        generatedCode.append("\tprivate ClientCommunicationModule comm;\n\n");
        generatedCode.append("\tprivate RemoteObjectReference ror;\n\n");
//        generatedCode.append("\tprivate RemoteReferenceModuleClient clientModule;\n\n");
        generatedCode.append("\tprivate " + interfaceName + " stub;\n\n");
        generatedCode.append("\n\n");
    }

    // Generates stub constructor
    private void generateStubConstructor(String filename){
        generatedCode.append("\tpublic " + filename + "(RemoteObjectReference ror){\n");
        generatedCode.append("\t\tthis.ror = ror;\n");
        generatedCode.append("\t\tInetAddress remoteObjectAddress = ror.getAddress();\n");
        generatedCode.append("\t\tint remoteObjectPort = ror.getPort();\n");
        generatedCode.append("\t\tcomm = new ClientCommunicationModule(remoteObjectAddress, remoteObjectPort);\n");
//        generatedCode.append("\t\tclientModule = RemoteReferenceModuleClient.getClientRemoteReference();\n");
//        generatedCode.append("\t\tclientModule.addReference(ror, stub);\n");
        generatedCode.append("\t}\n\n");
    }

    // Generates a method to return a stub instance.
    private void generateStubFactory(String filename){
        generatedCode.append("\tpublic " + interfaceName + " "+ filename + "Factory(){\n");
        generatedCode.append("\t\treturn stub;\n");
        generatedCode.append("\t}\n\n");
    }

    // Generates the Stub Methods
    private void generateStubMethods() {
        // Get methods using Java Reflection
        generatedCode.append("\t/*       Declared Methods Generated         */\n\n");
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

            // create all the parameters passes to the stub
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
                if (exceptions.length != 0) {
                    if (e == 0) {
                        generatedCode.append(" throws ");
                    }
                    generatedCode.append(exceptions[e].getName());
                    if (i < (exceptions.length - 1)) {
                        generatedCode.append(", ");
                    }
                }
            }
            generatedCode.append("{\n\n");
            // Method body
            generatedCode.append("\t\t// vector of args to pass\n" +
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
                generatedCode.append("\t\tmessage = comm.getMessage();\n");
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
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

}
