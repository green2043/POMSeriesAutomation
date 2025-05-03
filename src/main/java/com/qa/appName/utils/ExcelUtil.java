package com.qa.appName.utils;

// Import necessary classes from Apache POI for reading Excel files
import java.io.FileInputStream; // Used to read file input streams
import java.io.FileNotFoundException; // Used to handle file not found exceptions
import java.io.IOException; // Used to handle input/output exceptions

import org.apache.poi.EncryptedDocumentException; // Handles encrypted Excel files
import org.apache.poi.ss.usermodel.Sheet; // Represents an Excel sheet
import org.apache.poi.ss.usermodel.Workbook; // Represents an Excel workbook
import org.apache.poi.ss.usermodel.WorkbookFactory; // Used to create workbook instances from files

/*=================================================================================================================================
**Important Note**: DON'T CHANGE THIS CODE AGAIN, THIS IS A VERY GENERIC CODE TO FETCH THE DATA FROM EXCEL SHEET, IT'S NOT THAT MUCH IMPORTANT FOR INTERVIEW, IT'S ONLY ONETIME DESIGN 
=================================================================================================================================*/

public class ExcelUtil {

    // Define the path to the Excel file containing test data
    public static final String TEST_DATA_SHEET_PATH = "./src/test/resources/testdata/OpenCartAppTestData.xlsx";
    
    public static Workbook book; //imported from the Apache POI library: org.apache.poi.ss.usermodel.Workbook
    public static Sheet sheet; //imported from the Apache POI library: org.apache.poi.ss.usermodel.Sheet

    // Method to fetch test data from the specified Excel sheet
    public static Object[][] getTestdata(String sheetName) {
        // Initialize a 2D Object array to store data from the Excel sheet
        Object data[][] = null;
        
        try {
            // Create a FileInputStream to read the Excel file at the specified path
            FileInputStream ip = new FileInputStream(TEST_DATA_SHEET_PATH);
            
            // Create a Workbook object using the WorkbookFactory to read the Excel file
            book = WorkbookFactory.create(ip);
            
            // Access the specified sheet by its name from the workbook
            sheet = book.getSheet(sheetName);
            
            // Initialize the 2D array 'data' based on the number of rows and columns in the sheet
            // getLastRowNum() returns the total number of rows
            // getRow(0).getLastCellNum() returns the total number of columns in the first row
            data = new Object[sheet.getLastRowNum()][sheet.getRow(0).getLastCellNum()];
            
            // Loop through all rows in the sheet, 
            for (int i = 0; i < sheet.getLastRowNum(); i++) {
                
                // Loop through all columns in the first row to fetch cell values
                // We use sheet.getRow(0).getLastCellNum() to get the total number of columns
                for (int j = 0; j < sheet.getRow(0).getLastCellNum(); j++) {
                    
                    // Fetch the cell at position (i+1, j) where i+1 skips the header row (row 0)
                	// starting from row 1 (skipping the header row)
                    // and j refers to the column index
                    // We use toString() to convert the cell value to a string format and store it in the data array
                    data[i][j] = sheet.getRow(i + 1).getCell(j).toString();
                }
            }
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (EncryptedDocumentException e) {
            // Handle exceptions for encrypted Excel documents
            e.printStackTrace();
        } catch (IOException e) {
            // Handle general IO exceptions like file reading issues
            e.printStackTrace();
        }
        
        // Return the populated data array containing the Excel sheet data
        return data;
    }

    
}
