package netviews.netviewscli;

import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

@Command ( name = "TestCommand", description = "Test function to help understand picoCLI",
        mixinStandardHelpOptions = true )
public class TestCommand implements Runnable {

    @Parameters ( paramLabel = "<word>", defaultValue = "default",
            description = "Message to append to print statement" )
    private final String[] message = { "default" };

    @Override
    public void run () {
        System.out.println( "Command Test ran " + String.join( " ", message ) );
    }

    // public static void main ( final String[] args ) {
    // final int exitCode = new CommandLine( new TestCommand() ).execute( args
    // );
    // System.exit( exitCode );
    // }
}
