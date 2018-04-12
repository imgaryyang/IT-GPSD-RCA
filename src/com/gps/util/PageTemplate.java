package com.gps.util;

import java.util.ArrayList;
import java.util.Hashtable;

public class PageTemplate {

  private int pageIndex;
  private int startRow;
  private int startCol;
  private int endRow;
  private int endCol;
  private Hashtable<String,Hashtable<String,String>> hashMaps;
  private Hashtable<String,ArrayList<String>> concatCellQuestions;
  private Hashtable<String,ArrayList<String>> concatCellAnswers;
  private Hashtable<Integer, String> rowFilters;
  
  //stored by row / values in cell
  private Hashtable<Integer,String[]> colorCols;
  private Hashtable<Integer,String[]> colorFuncs;
  private Hashtable<Integer,String[]> colorMaps;
  private Hashtable<Integer,String[]> colorValues;
  private int firstMapRow;
  private int lastMapRow;
  boolean rowEndUsed = false;
  
  /**
   * 
   */
  public PageTemplate() {
    pageIndex = -1;
    startRow = -1;
    startCol = -1;
    endRow = -1;
    endCol = -1;
    firstMapRow = 100;
    lastMapRow = -1;
    hashMaps = new Hashtable<String,Hashtable<String,String>>();
    concatCellQuestions = new Hashtable<String,ArrayList<String>>();
    concatCellAnswers = new Hashtable<String,ArrayList<String>>();
    rowFilters = new Hashtable<Integer,String>();
    colorCols = new Hashtable<Integer,String[]>();
    colorFuncs = new Hashtable<Integer,String[]>();
    colorMaps = new Hashtable<Integer,String[]>();
    colorValues = new Hashtable<Integer,String[]>();
  }
  
  /**
   * Set the index of the page in the spreadsheet (we expect pages to start at 0)
   * @param index the location of the page
   */
  public void setPageIndex(int index) {
    pageIndex = index;
  }
  
  /**
   * @return the index of the page
   */
  public int getPageIndex() {
    return this.pageIndex;
  }
  
  /**
   * Set the starting cell of the table
   * @param r the row number
   * @param c the column number
   */
  public void setStartCell(int r, int c) {
    startRow = r;
    startCol = c;
  }
  
  /**
   * Set the ending cell of the table
   * @param r the row number
   * @param c the column number
   */
  public void setEndCell(int r, int c) {
    endRow = r;
    endCol = c;
  }
  
  /**
   * Get the starting row
   * @return the row number
   */
  public int getStartRow() {
    return startRow;
  }
  
  /**
   * Get the starting column
   * @return the starting column
   */
  public int getStartCol() {
    return startCol;
  }
  
  /**
   * Get the ending row
   * @return the row number
   */
  public int getEndRow() {
    return endRow;
  }
  
  /**
   * Get the ending column
   * @return the column number
   */
  public int getEndCol() {
    return endCol;
  }
  
  /**
   * Add a hashmap with the given name and key / value pair
   * @param name the name of the 'variable'
   * @param map the key / value pairing
   */
  public void addHashMap(String name, Hashtable<String,String> map) {
    hashMaps.put(name,map);
  }
  
  /**
   * Get the hashmap with a given name
   * @param name the name
   * @return the key / value pair mappings
   */
  public Hashtable<String,String> getHashMap(String name) {
    return hashMaps.get(name);
  }
  
  /**
   * Add the information relating to the concat cell
   * @param name the name of the concat cell configuration
   * @param questions the questions text
   * @param answers the answer variables to look for
   */
  public void addConcatCell(String name, ArrayList<String> questions, ArrayList<String> answers) {
    concatCellQuestions.put(name, questions);
    concatCellAnswers.put(name, answers);
  }
  
  /**
   * Get the questions related to the given name of the concatCell
   * @param name the name of the question list to retrieve
   * @return the question list
   */
  public ArrayList<String> getConcatCellQuestions(String name) {
    return concatCellQuestions.get(name);
  }
  
  /**
   * Gets the answers related to the given name of concatCell
   * @param name the name of the answer list to retrieve
   * @return the answer list
   */
  public ArrayList<String> getConcatCellAnswers(String name) {
    return concatCellAnswers.get(name);
  }
  
  /**
   * Set the row filter
   * @param row the row for the filter
   * @param filterVal the expression for the filter
   */
  public void setRowFilter(int row, String filterVal) {
    rowFilters.put(row, filterVal);
  }
  
  /**
   * Get the row filter for a given row
   * @param row the row
   * @return the expression that will be tested
   */
  public String getRowFilter(int row) {
    return rowFilters.get(row);
  }

  /**
   * Set a color map for a given row
   * @param row the row the color map is to be used for
   * @param cCols the columns to use this color mapping for
   * @param cFuncs the functions to use to determine the color mapping
   * @param cMaps the results of the color functions to care about
   * @param cVals the colors to use for the corresponding cMaps value
   */
  public void setColorMap(int row, String[] cCols, String[] cFuncs, 
                          String[] cMaps, String[] cVals) {	  
    colorCols.put(row,cCols);
    colorFuncs.put(row,cFuncs);
    colorMaps.put(row,cMaps);
    colorValues.put(row,cVals);
    if(row<firstMapRow)
    	firstMapRow = row;
    if(row>lastMapRow)
    	lastMapRow = row;    	
  }
  
  public String[] getColorCols(int row) {
    return colorCols.get(row);
  }
  
  public String[] getColorFunctions(int row) {
    return colorFuncs.get(row);
  }
  
  public String[] getColorMaps(int row) {
    return colorMaps.get(row);
  }
  
  public String[] getColorValues(int row) {
    return colorValues.get(row);
  }

	public int getFirstMapRow() {
		return firstMapRow;
	}
	
	public void setFirstMapRow(int firstMapRow) {
		this.firstMapRow = firstMapRow;
	}
	
	public int getLastMapRow() {
		return lastMapRow;
	}
	
	public void setLastMapRow(int lastMapRow) {
		this.lastMapRow = lastMapRow;
	}

	public boolean isRowEndUsed() {
		return rowEndUsed;
	}

	public void setRowEndUsed(boolean rowEndUsed) {
		this.rowEndUsed = rowEndUsed;
	}
}
