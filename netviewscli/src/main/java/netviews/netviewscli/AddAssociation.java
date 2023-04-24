package netviews.netviewscli;

import models.NVWrapper;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

@Command(name = "AddAssociation", description = "Appends an association to the policy JSON file", mixinStandardHelpOptions = true)
public class AddAssociation implements Runnable {

    @Parameters(paramLabel = "<Source>", index = "0", description = "Source node of the assocation")
    private String source;

    @Parameters(paramLabel = "<Target>", index = "1", description = "Target node of the association.")
    private String target;

    @Parameters(paramLabel = "<Operations>", index = "2..*", description = "The protocols and ports the source can use communcate with the target")
    private String[] operations;

    @Override
    public void run() {
        if (source == null || target == null || operations.length == 0 || source.length() == 0
                || target.length() == 0) {
            // Missing parameters
            System.out.println("Association Source, Target, or Operations is empty");
        }
        else {
        	NVWrapper wrap = Utilities.deserialize();
            try {
            	wrap.addAssociation(source, target, operations);
            	Utilities.serialize(wrap);
            }
            catch (IllegalArgumentException e) {
            	System.out.println(e.getMessage());
            }
        }

        
        
        
        

        
    }

}
