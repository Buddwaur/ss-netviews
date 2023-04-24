package netviews.netviewscli;

import models.NVWrapper;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

@Command(name = "AddNode", description = "Appends a Node to the policy JSON file", mixinStandardHelpOptions = true)
public class AddNode implements Runnable {

    @Parameters(paramLabel = "<Name>", index = "1..*", description = "Name of the node to be added.")
    private String name[];

    @Parameters(paramLabel = "<Type>", index = "0", description = "Type of the node to be added. EX. 'O', 'OA'")
    private String type;

    @Override
    public void run() {
        if (type == null || name == null || name.length == 0 || type.length() == 0) {
            // Missing parameters
            System.out.println("Node Type or Name is empty");
        }

        String fullName = String.join(" ", name);

        // String fullType = String.join(" ", type);

        NVWrapper wrap = Utilities.deserialize();
        
        try {
        	wrap.addNode(fullName, type, null);
            Utilities.serialize(wrap);
        } catch(IllegalArgumentException e) {
        	System.out.println(e.getMessage());
        }

        
    }

}
