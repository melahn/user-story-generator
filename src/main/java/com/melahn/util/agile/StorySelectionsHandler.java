package com.melahn.util.agile;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class StorySelectionsHandler {

    private String selectionsFilename = null;
    private StorySheetsHandler storySheetsHandler;

    private ArrayList<String> selections = new ArrayList<String>();

    StorySelectionsHandler(String selectionsFilename) {
        this.selectionsFilename = selectionsFilename;
        readSelections();
    }

    /**
     * Reads a selections file
     */
    void readSelections() {
        FileInputStream is = null;
        try {
            is = new FileInputStream(selectionsFilename);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String l = null;
            while ((l = br.readLine()) != null) {
                selections.add(l);
            }
            br.close();
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

    public String getSelectionsFilename() {
        return selectionsFilename;
    }

    ArrayList<String> getSelections() {
        return selections;
    }

}
