package com.melahn.util.agile;

import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

public abstract class StorySheetsHandler {

    ArrayList<String> roles = new ArrayList<String>();
    ArrayList<String> goals = new ArrayList<String>();
    ArrayList<String> benefits = new ArrayList<String>();
    ArrayList<String> criteria = new ArrayList<String>();
    private StorySelectionsHandler storySelectionsHandler;

    /**
     *
     * Reads the rows of a sheet into an array
     *
     * @param a     An array into which to read the rows
     * @param sh    The Sheet whose rows are to be read
     */
    void readRows(ArrayList<String> a, Sheet sh) {
        int rc = sh.getLastRowNum();
        for (int i = 0; i <= rc; i++) {
            Row r = sh.getRow(i);
            if (r != null) {
                Cell c = r.getCell(0);
                if (c != null) {
                    String s = c.toString().trim();
                    if (!s.isEmpty()) {
                        a.add(c.toString());
                    }
                }
            }
        }
    }

    /**
     *
     * Reads a column of a sheet into an array
     *
     * @param a     An array into which to read the column
     * @param sh    The Sheet whose rows are to be read
     *  @param c    The column whose rows are to be read
     */
    void readColumn(ArrayList<String> a, Sheet sh, int c) {
        Row r;
        for (int i = 0; i <= sh.getLastRowNum(); i++) {
            r = sh.getRow(i);
            if (r != null) {
                Cell cell = r.getCell(c);
                if (cell != null) {
                    a.add(cell.getStringCellValue().trim());
                }
            }
        }
    }

    /**
     * Factory method to generate the right kind of a Sheet Handler based on whether
     * the data is normalized or not
     *
     * @param selectionsFilename    The file name of the selections file
     * @return Either an instance of a DenormalizedStorySheetsHandler or a NormalizedStorySheetsHandler
     */
    public static StorySheetsHandler createStorySheetsHandler(String selectionsFilename) {
        StorySheetsHandler storySheetsHandler;
        if (selectionsFilename != null) {
            StorySelectionsHandler ssh = new StorySelectionsHandler(selectionsFilename);
            storySheetsHandler = new NormalizedStorySheetsHandler(ssh);
        } else {
            storySheetsHandler = new DenormalizedStorySheetsHandler();
        }
        return storySheetsHandler;
    }

    abstract void readData(String dataFilename);

    public ArrayList<String> getRoles() {
        return roles;
    }

    public ArrayList<String> getGoals() {
        return goals;
    }

    public ArrayList<String> getBenefits() {
        return benefits;
    }

    public ArrayList<String> getCriteria() {
        return criteria;
    }

    public StorySelectionsHandler getStorySelectionsHandler() {
        return storySelectionsHandler;
    }

    public void setStorySelectionsHandler(StorySelectionsHandler storySelectionsHandler) {
        this.storySelectionsHandler = storySelectionsHandler;
    }
}

