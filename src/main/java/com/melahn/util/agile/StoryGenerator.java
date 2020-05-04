package com.melahn.util.agile;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class StoryGenerator {

    private static String version = "1.0.0";
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
        String help = "\nUsage:\n\n"
                .concat("java -jar UserStoryGenerator-")
                .concat(version)
                .concat(".jar")
                .concat("\nFlags:\n")
                .concat("\t-d\t<filename>\tThe location of a spreadsheet file containing the user story data\n")
                .concat("\t-o\t<filename>\tThe location of a file to which the stories will be written\n")
                .concat("\t-s\t<filename>\tThe location of a csv file containing the selection data\n")
                .concat("\nSee https://github.com/melahn/user-story-generator for more information\n");
        System.out.print(help);
        System.exit(0);
    }
}
