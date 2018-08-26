package com.melahn.util.agile;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class StoryGenerator {

    private final static String DEFAULT_DATA_FILE = "data.xslx";
    private static String dataFilename = DEFAULT_DATA_FILE;
    private static String selectionsFilename = null;
    private static String outputFilename = null;
    private static StorySheetsHandler storySheetsHandler;

    public static void main(String[] args) {
        try {
            parseArgs(args);
            StorySheetsHandler storySheetsHandler = StorySheetsHandler.createStorySheetsHandler(selectionsFilename);
            storySheetsHandler.readData(dataFilename);
            new StoryPrinter(storySheetsHandler, outputFilename).printStories();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Parse the command line arguments
     *
     * @param args
     */
    private static void parseArgs(String[] args) {
        Options options = new Options();
        options.addOption("d", true, "The Data Filename");
        options.addOption("s", true, "The Selection Filename");
        options.addOption("o", true, "The Output Filename");
        options.addOption("h", false, "help");
        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine cmd = parser.parse(options, args);
            if (cmd.hasOption("h")) {
                showHelp();
            } else {
                if (cmd.hasOption("d")) {
                    dataFilename = cmd.getOptionValue("d");
                }
                if (cmd.hasOption("s")) {
                    selectionsFilename = cmd.getOptionValue("s");
                }
                if (cmd.hasOption("o")) {
                    outputFilename = cmd.getOptionValue("o");
                }
            }
        } catch (ParseException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Show the help
     */
    private static void showHelp() {
        System.out.println("Usage: java -jar UserStoryGenerator.jar -d <userstorydataFilename> -s <userstoryselectionFilename> -o <outputfile>");
        System.out.println("Defaults:");
        System.out.println("\tuserstorydataFilename=" + DEFAULT_DATA_FILE);
        System.out.println("\tuserstoryselectionFilename=<none>");
        System.out.println("\toutputfile=stdout");
        System.exit(0);
    }
}
