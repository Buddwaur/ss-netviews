
package org.onosproject.nifwd_combined.impl;

import java.io.*;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import org.onosproject.net.intent.IntentService;
import org.onosproject.net.intent.IntentState;
import org.onosproject.net.intent.Intent;
import org.onosproject.cli.AbstractShellCommand;


import gov.nist.csd.pm.exceptions.PMException;


public class Server {

    public static void connectToServer() {
        //Try to create a socket server on an unused port eg 9191. A successful connection will return a socket
        try(ServerSocket serverSocket = new ServerSocket(9191)) {
            while (true) {
            
            	//Accept connnections to the server
            	Socket connectionSocket = serverSocket.accept();

            	//Create Input&Outputstreams for the connection
            	InputStream inputToServer = connectionSocket.getInputStream();
            	OutputStream outputFromServer = connectionSocket.getOutputStream();

            	Scanner scanner = new Scanner(inputToServer, "UTF-8");
            	PrintWriter serverPrintOut = new PrintWriter(new OutputStreamWriter(outputFromServer, "UTF-8"), true);

            
            	serverPrintOut.println("Server has been created on port 9191");

                
                //trigger a recreation of the policy and delete all intnets in the system
                try {
                	
                	//get the intentservice to get all intents
                	IntentService service = AbstractShellCommand.get(IntentService.class);
                	
                	//get an iterable instance of intents
                	Iterable<Intent> intents = service.getIntents();
                	
                	//go through all the intents and withdraw them if the state is installed or install_req
                	for (Intent intent : intents) {
                		IntentState state = service.getIntentState(intent.key());
                		if (state == IntentState.INSTALLED || state == IntentState.INSTALL_REQ) {
                			service.withdraw(intent);
                		}
                	}
                	
                	//Get an instance of the PolicyEngine and create a new policy graph
			PolicyEngine.getInstance().createPolicyGraph("/home/noah/netviews/ss-netviews/input-files/med-topo-ref/med-topo-policy.json");
			
		} catch (Exception e) {
			
			serverPrintOut.println(e.getLocalizedMessage());
		};
		
            }
        } catch (IOException e) {
            e.printStackTrace();
            
        }
    }
    
}

