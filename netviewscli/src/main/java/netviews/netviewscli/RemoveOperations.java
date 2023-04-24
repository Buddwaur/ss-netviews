package netviews.netviewscli;

import models.NVWrapper;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

@Command(name = "RemoveOperations", description = "Removes the operations from an association", mixinStandardHelpOptions = true)
public class RemoveOperations implements Runnable {

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
            throw new IllegalArgumentException("Association Source, Target, or Operations is empty");
        }

        NVWrapper wrap = Utilities.deserialize();

        wrap.removeOperations(source, target, operations);

        Utilities.serialize(wrap);
    }

}
