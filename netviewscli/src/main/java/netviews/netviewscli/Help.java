package netviews.netviewscli;

import picocli.CommandLine.Command;

@Command(name = "help", description = "Help", mixinStandardHelpOptions = true)
public class Help implements Runnable {

    @Override
    public void run() {
        final StringBuilder output = new StringBuilder();

        String commands[] = { "help", "setup", "overwritepolicy", "addnode", "removenode", "addassociation",
                "removeassociation", "addassignment", "removeassignment", "addoperations", "removeoperations" };

        output.append("The list of commands are:\n\n");
        output.append(String.join("\n", commands));
        output.append("\n\nTo see documentation and usage, type the command with the option \"-h\" or \"--help\"\n");
        System.out.println(output.toString());
    }

}
