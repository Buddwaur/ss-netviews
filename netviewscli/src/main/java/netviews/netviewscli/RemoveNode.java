package netviews.netviewscli;

import models.NVWrapper;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

@Command(name = "RemoveNode", description = "Takes the name of a Node and removes it from the policy JSON file", mixinStandardHelpOptions = true)
public class RemoveNode implements Runnable {

    @Parameters(paramLabel = "<Name>", description = "Name of the node to be removed.")
    private String name[];

    @Override
    public void run() {
        if (name == null || name.length == 0) {
            // Missing parameters
            System.out.println("NodeName is empty");
        }

        String fullName = String.join(" ", name);

        // String fullType = String.join(" ", type);

        NVWrapper wrap = Utilities.deserialize();
        try {
        	wrap.removeNode(fullName);
            Utilities.serialize(wrap);
        } catch(IllegalArgumentException e) {
        	System.out.println(e.getMessage());
        }
        
    }

}
