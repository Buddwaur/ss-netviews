package netviews.netviewscli;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

import picocli.CommandLine;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

public class NetviewsCLI {

    public static void main(final String[] args) {

        final Scanner scan = new Scanner(System.in);

        String input;

        final String exit = "exit";

        printTitle();

        // Get the policy file as a NVWrapper

        // final TestCommand command = new TestCommand();
        int exitCode = 0;

        do {
            System.out.print(">>> ");
            input = scan.nextLine();

            final String[] parsedInput = input.split(" ");

            final String command = parsedInput[0];

            String arguments[] = Arrays.copyOfRange(parsedInput, 1, parsedInput.length);
            // for (int i = 1; i < parsedInput.length; i++) {

            // arguments = arguments.concat(parsedInput[i]);
            // if (i != parsedInput.length - 1) {
            // arguments = arguments.concat(" ");
            // }

            // }
            // System.out.println( Arrays.toString( parsedInput ) );
            // System.out.println( command );
            // System.out.println( arguments );

            try {
                switch (command.toLowerCase()) {
                    case "help":
                        exitCode = new CommandLine(new Help()).execute(arguments);
                        break;
                    case "setup":
                        exitCode = new CommandLine(new Setup()).execute(arguments);
                        break;
                    case "overwritepolicy":
                        exitCode = new CommandLine(new OverwritePolicy()).execute();
                        break;
                    case "addnode":
                        exitCode = new CommandLine(new AddNode()).execute(arguments);
                        break;
                    case "removenode":
                        exitCode = new CommandLine(new RemoveNode()).execute(arguments);
                        break;
                    case "addassociation":
                    	exitCode = new CommandLine(new AddAssociation()).execute(arguments);
                        break;
                    case "removeassociation":
                        exitCode = new CommandLine(new RemoveAssociation()).execute(arguments);
                        break;
                    case "addassignment":
                        exitCode = new CommandLine(new AddAssignment()).execute(arguments);
                        break;
                    case "removeassignment":
                        exitCode = new CommandLine(new RemoveAssignment()).execute(arguments);
                        break;
                    case "addoperations":
                        exitCode = new CommandLine(new AddOperations()).execute(arguments);
                        break;
                    case "removeoperations":
                        exitCode = new CommandLine(new RemoveOperations()).execute(arguments);
                        break;
                    case "exit":
                        scan.close();
                        System.exit(0);
                    default:
                }
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
                exitCode = 1;
            }
            System.out.println("Command exited with code: " + exitCode);

        } while (input.compareTo(exit) != 0);
        scan.close();
    }

    private static void printTitle() {
        System.out.println();
        int width = 100;
        int height = 50;
        Random rand = new Random();
        String symbols[] = { "$", "&", "'", "\"", "#", "X", "o" };
        int symbolIndex = rand.nextInt(symbols.length);
        String symbol = symbols[symbolIndex];

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics graphics = image.getGraphics();
        Graphics2D graphics2D = (Graphics2D) graphics;
        graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        graphics2D.drawString("Net VIewsCLI", 12, 24);

        for (int y = 0; y < height; y++) {
            StringBuilder stringBuilder = new StringBuilder();

            for (int x = 0; x < width; x++) {
                stringBuilder.append(symbol);
            }

            if (stringBuilder.toString().trim().isEmpty()) {
                continue;
            }
        }

        for (int y = 0; y < height; y++) {
            StringBuilder stringBuilder = new StringBuilder();

            for (int x = 0; x < width; x++) {
                stringBuilder.append(image.getRGB(x, y) == -16777216 ? symbol : " ");
            }

            if (stringBuilder.toString().trim().isEmpty()) {
                continue;
            }
        }

        for (int y = 0; y < height; y++) {
            StringBuilder stringBuilder = new StringBuilder();

            for (int x = 0; x < width; x++) {
                stringBuilder.append(image.getRGB(x, y) == -16777216 ? " " : symbol);
            }

            if (stringBuilder.toString().trim().isEmpty()) {
                continue;
            }

            System.out.println(stringBuilder);
        }
        System.out.println();
    }
}
