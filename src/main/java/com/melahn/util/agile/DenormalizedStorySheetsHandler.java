package com.melahn.util.agile;

import org.apache.poi.ss.usermodel.*;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;

public class DenormalizedStorySheetsHandler extends StorySheetsHandler {

    /**
     * Reads the data from a sheet from a file.
     *
     * @param dataFilename  The name of a file containing the sheet
     *                      to be read.
     */
    void readData(String dataFilename) {
        System.out.println("DenormalizedStorySheetsHandler");
        InputStream is = null;
        try {
            is = new FileInputStream(dataFilename);
            Workbook wb = WorkbookFactory.create(is);
            Sheet sheet  = wb.getSheetAt(0);
            readColumn(roles, sheet, 0);
            readColumn(goals, sheet, 1);
            readColumn(benefits, sheet, 2);
            readColumn(criteria, sheet, 3);
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
}
