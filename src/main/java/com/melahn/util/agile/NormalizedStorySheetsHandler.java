package com.melahn.util.agile;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class NormalizedStorySheetsHandler extends StorySheetsHandler {

    private ArrayList<String> selections = new ArrayList<String>();

    NormalizedStorySheetsHandler (StorySelectionsHandler storySelectionsHandler) {
        this.setStorySelectionsHandler(storySelectionsHandler);
    }

    /**
     * Reads the data from a set of sheets from a normalized data file.
     *
     * @param dataFilename  The name of a file containing the sheets
     *                      to be read.
     */
    void readData(String dataFilename) {
        System.out.println("NormalizedStorySheetsHandler");
        InputStream is = null;
        try {
            is = new FileInputStream(dataFilename);
            Workbook wb = WorkbookFactory.create(is);
            Sheet rolesSheet = wb.getSheet("Role");
            Sheet goalsSheet = wb.getSheet("Goal");
            Sheet benefitsSheet = wb.getSheet("Benefit");
            Sheet criteriaSheet = wb.getSheet("Criterion");
            readRows(roles, rolesSheet);
            readRows(goals, goalsSheet);
            readRows(benefits, benefitsSheet);
            readRows(criteria, criteriaSheet);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public ArrayList<String> getSelections() {
        return selections;
    }

}
