package com.qind.common.util.jxl;

public class ExcelSheet {

	public String getSheetName() {
		return sheetName;
	}
	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}
	public int getSheetRowNum() {
		return sheetRowNum;
	}
	public void setSheetRowNum(int sheetRowNum) {
		this.sheetRowNum = sheetRowNum;
	}
	public int getSheetColNum() {
		return sheetColNum;
	}
	public void setSheetColNum(int sheetColNum) {
		this.sheetColNum = sheetColNum;
	}
	public ExcelCell[][] getExcelCell() {
		return excelCell;
	}
	public void setExcelCell(ExcelCell[][] excelCell) {
		this.excelCell = excelCell;
	}
	
	private String sheetName;
	private int sheetRowNum;
	private int sheetColNum;
	private ExcelCell[][] excelCell;
	
}
