package netviews.netviewscli;

import picocli.CommandLine.Command;
//import picocli.CommandLine.Parameters;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

/*
 * The SOAP client code in this command was used from a stackoverflow
 * forum response at:
 * https://stackoverflow.com/questions/15940234/how-to-do-a-soap-web-service-call-from-java-class
 */
@Command(name = "OverwritePolicy", description = "Takes filepath of a new policy json file to overwrite current policy graph.", mixinStandardHelpOptions = true)
public class OverwritePolicy implements Runnable {

    private Socket sock;

    @Override
    public void run() {
        // Connect to Server with Socket (doesn't do anything with the
    	// socket besides connect and close because connecting automatically
    	// triggers the code to reload the policy from the policy file)
        try {
            sock = new Socket("127.0.0.1", 9191);
            PrintStream outputToServer = new PrintStream(sock.getOutputStream());
            outputToServer.println("Change");
            outputToServer.flush();
            
            //BufferedReader output = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            //output.println("Change!");
            //String s = output.readLine();
            //while(s != null) {
            	//System.out.println(s);
            	//s = output.readLine();
            //}
            //output.close();
            sock.close();
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
