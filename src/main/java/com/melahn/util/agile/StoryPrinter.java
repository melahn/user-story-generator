package com.melahn.util.agile;

import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * 
 * Prints a set of user stories from a set of roles, goals, benefits and selections.
 *
 */
public class StoryPrinter {

    private  String outputFilename;
    private static PrintWriter writer = null;
    private StorySheetsHandler storySheetsHandler;
    private StorySelectionsHandler storySelectionsHandler;

    StoryPrinter(StorySheetsHandler storySheetsHandler, String outputFilename) {
        this.storySheetsHandler = storySheetsHandler;
        this.outputFilename = outputFilename;
        this.storySelectionsHandler = storySheetsHandler.getStorySelectionsHandler();
    }

    /**
     * Applies the selections array to print stories
     *
     * @param a An array of selections read from a selections file.
     *
     * @throws Exception
     */
    private void applySelections(ArrayList<String> a) throws Exception {
        for (int l = 0; l < a.size(); l++) {
            String[] m = a.get(l).split(",");
            int[] i = new int[m.length];
            for (int j = 0; j < i.length; j++) {
                i[j] = Integer.parseInt(m[j]) - 1;
            }
            validateSelections(i, l + 1);
            printStory(i);
        }
    }

    /**
     * Prints user stories based on a simple iteration of the data file, i.e.
     * not using a selections file.
     */
    private void printAllRows() {
        for (int i = 0 ; i < storySheetsHandler.getRoles().size(); i++){
            printStory(i);
        }
    }

    /**
     * Makes sure that the selections the user provided make sense by checking the
     * limits of the data in the spreadsheet.
     *
     * @param i The selections for a given line
     * @param l The line number (used for error messages)
     * @throws Exception
     */
    void validateSelections(int[] i, int l) throws Exception {
        String s = "Error on line " + l + " in selections file " + storySelectionsHandler.getSelectionsFilename() + ": ";
        String e = null;
        if (i.length < 3) {
            e = s + " too few selections";
        } else if (i[0] > storySheetsHandler.getRoles().size()) {
            e = s + " role index is too large";
        } else if (i[1] > storySheetsHandler.getGoals().size()) {
            e = s + " goal index is too large";
        } else if (i[2] > storySheetsHandler.getBenefits().size()) {
            e = s + " benefits index is too large";
        }
        if (i.length > 3) {
            for (int j = 3; j < i.length; j++) {
                if (i[j] > storySheetsHandler.getCriteria().size()) {
                    e = s + " criteria index " + i[j] + " is too large";
                    break;
                }
            }
        }
        if (e != null) {
            throw new Exception(e);
        }
    }
    /**
     * Prints a single user story based on a selection list
     *
     * @param i The index into an array of roles
     *
     * @throws Exception
     */
    private void printStory(int[] i) {
        String s = "As a " + storySheetsHandler.getRoles().get(i[0]) + " I want to " + storySheetsHandler.getGoals().get(i[1]) + " so that I can " + storySheetsHandler.getBenefits().get(i[2]) + ".\n";
        s += "\tAcceptance Criteria:";
        if (i.length == 3) {
            s += "\n\t\tNone specified";
        } else {
            for (int j = 3; j < i.length; j++) {
                s += "\n\t\t" + storySheetsHandler.getCriteria().get(i[j]);
            }
        }
        s += "\n\n";
        if (outputFilename == null) {
            System.out.print(s);
        } else {
            writer.write(s);
        }
    }


    /**
     * Prints a user story based on the data in a single line in the spreadsheet
     * @param i The line to be used for the story
     */
    private void printStory(int i) {
        String s = "As a " + storySheetsHandler.getRoles().get(i) + " I want to " + storySheetsHandler.getGoals().get(i) + " so that I can " + storySheetsHandler.getBenefits().get(i) + ".\n";
        s += "\tAcceptance Criteria:";
        String criteria = storySheetsHandler.getCriteria().get(i);
        s += "\n\t\t" + criteria.replaceAll("\n","\n\t\t");;
        s += "\n\n";
        if (outputFilename == null) {
            System.out.print(s);
        } else {
            writer.write(s);
        }
    }

    /**
     * Generates the stories
     *
     * @throws Exception
     */
    protected void printStories() throws Exception {
        if (outputFilename != null) {
            writer = new PrintWriter(outputFilename, "UTF-8");
        }
        if (storySheetsHandler instanceof NormalizedStorySheetsHandler) {
            applySelections((storySelectionsHandler.getSelections()));
        }
        else {
            printAllRows();
        }
        if (writer != null) {
            writer.close();
            System.out.println("Stories generated to file " + outputFilename);
        }
    }
}
