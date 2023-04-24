package netviews.netviewscli;

import models.NVWrapper;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

@Command(name = "AddAssignment", description = "Appends an assignment to the policy JSON file", mixinStandardHelpOptions = true)
public class AddAssignment implements Runnable {

    @Parameters(paramLabel = "<Source>", index = "0", description = "Source node of the assignment")
    private String source;

    @Parameters(paramLabel = "<Target>", index = "1", description = "Target node of the assignment.")
    private String target;

    @Override
    public void run() {
        if (source == null || target == null || source.length() == 0
                || target.length() == 0) {
            // Missing parameters
            System.out.println("Assignment Source, or Target is empty");
        }

        NVWrapper wrap = Utilities.deserialize();

        try {
        	wrap.addAssignment(source, target);
            Utilities.serialize(wrap);
        } catch (IllegalArgumentException e) {
        	System.out.println(e.getMessage());
        }
        
    }

}
