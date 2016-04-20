package com.qind.common.util.jxl;

import jxl.format.*; 
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WriteException;

public class ExcelCell {

	//静态常量
	public static WritableCellFormat cellTitle;
	public static WritableCellFormat cellContent;
	
	static {
		
		try {
			cellTitle = new WritableCellFormat(new WritableFont(WritableFont.ARIAL, 10,
					WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE,
					Colour.BLACK));
			cellTitle.setBackground(Colour.BLUE);
			cellContent = new WritableCellFormat(new WritableFont(WritableFont.ARIAL, 8));
		} catch (WriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public ExcelCell(int cellRowNum,int cellColNum,Object cellValue,CellFormat cellFormat){
		this.cellRowNum = cellRowNum;
		this.cellColNum = cellColNum;
		this.cellValue = cellValue;
		this.cellFormat = cellFormat;
	}
	
	public int cellRowNum;
	public int cellColNum;
	public Object cellValue;
	public CellFormat cellFormat;
}
