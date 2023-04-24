package netviews.netviewscli;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

@Command(name = "setup", description = "command to setup NetViews with different SDN Controllers", mixinStandardHelpOptions = true)
public class Setup implements Runnable {
    // D:\NCSU Related\Cybersecurity
    // Projects\NetViewsLocal\NVCLI\netviewsCLI\netviewscli\src\main\java\netviews\netviewscli\Setup.java
    // private final String pathToOnosSetup =
    // "./netviewscli/src/main/java/netviews/netviewscli/testls.sh";
    private final String pathToOnosSetup = "../../../../../scripts/setup_original.sh";

    @Parameters(paramLabel = "<controller>", defaultValue = "onos", description = "name of the SDN controller")
    private final String[] controllers = { "default" };

    @Override
    public void run() {

        String controller = controllers[0];

        if (controller.length() == 0) {
            // If no parameter is passed in, then the default is set here.
            controller = "onos";
        }

        // If a default value passed
        if (controller.equals("onos")) {

            final Runtime runTimeObj = Runtime.getRuntime();
            // Setup onos
            try {
                System.out.println(pathToOnosSetup);
                Process process = runTimeObj.exec(pathToOnosSetup);
                BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));

                // Read the output from the command
                System.out.println("Here is the standard output of the command:\n");
                String s = null;
                while ((s = stdInput.readLine()) != null) {
                    System.out.println(s);
                }
            } catch (final IOException e) {
                e.printStackTrace();
            }

        } else if (controllers[0].length() > 1) {
            // For non-default values
        } else {
            // Print usage
            System.out.println("Controller unavailable");
        }

    }
}
