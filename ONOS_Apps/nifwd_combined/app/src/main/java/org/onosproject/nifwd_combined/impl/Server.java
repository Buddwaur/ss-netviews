
package org.onosproject.nifwd_combined.impl;

import java.io.*;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import org.onosproject.net.intent.IntentService;
import org.onosproject.net.intent.IntentState;
import org.onosproject.net.intent.Intent;
import org.onosproject.cli.AbstractShellCommand;
import org.onosproject.nifwd_combined.impl.PolicyEngine;


import gov.nist.csd.pm.exceptions.PMException;


public class Server {

    public static void connectToServer() {
        //Try to create a socket server on an unused port eg 9191. A successful connection will return a socket
        try(ServerSocket serverSocket = new ServerSocket(9191)) {
            while (true) {
            
            	//Accept connnections to the server
            	Socket connectionSocket = serverSocket.accept();

            	//Create Input&Outputstreams for the connection (decided we didn't really need the input stream)
            	OutputStream outputFromServer = connectionSocket.getOutputStream();

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
			PolicyEngine.getInstance().createPolicyGraph("/[PATH-TO-NETVIEWS]/ss-netviews/input-files/[POLICY-INFO-FILE.json]");
			
		} catch (Exception e) {
			
			serverPrintOut.println(e.getLocalizedMessage());
		};
		
            }
        } catch (IOException e) {
            e.printStackTrace();
            
        }
    }
    
}

