package com.qind.common.util.jxl;

import java.io.File;
import java.io.IOException;

import jxl.Workbook;
import jxl.format.UnderlineStyle;
import jxl.write.Colour;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class JexcelUtil {

	public static void main(String[] args) {
		
		String targetFile = "E:\\TempFile\\zhihu.xls";
		
		//字体设置
		WritableCellFormat cf1 = ExcelCell.cellTitle;
		WritableCellFormat cf2 = ExcelCell.cellContent;
		
		ExcelCell cellTitle00 = new ExcelCell(0, 0, "柜员号", cf1);
		ExcelCell cellTitle01 = new ExcelCell(0, 1, "附加1", cf1);
		ExcelCell cellTitle02 = new ExcelCell(0, 2, "附加2", cf1);
		
		ExcelCell cellTitle10 = new ExcelCell(1, 0, "内容1", cf2);
		ExcelCell cellTitle11 = new ExcelCell(1, 1, "内容2", cf2);
		ExcelCell cellTitle12 = new ExcelCell(1, 2, "内容3", cf2);
		
		ExcelCell[][] allCell = new ExcelCell[2][3];
		allCell[0][0] = cellTitle00;
		allCell[0][1] = cellTitle01;
		allCell[0][2] = cellTitle02;
		allCell[1][0] = cellTitle10;
		allCell[1][1] = cellTitle11;
		allCell[1][2] = cellTitle12;
		
		ExcelSheet excelSheet = new ExcelSheet();
		excelSheet.setSheetName("爬取知乎用户内容");
		excelSheet.setSheetRowNum(2);
		excelSheet.setSheetColNum(3);
		excelSheet.setExcelCell(allCell);
		
		ExcelSheet[] allSheet = new ExcelSheet[1];
		allSheet[0] = excelSheet;
		
		Excel excel = new Excel();
		excel.setUrlPath(targetFile);
		excel.setExcelSheet(allSheet);
		
		writeExcel(excel);
		
	}
	
	/**
	 * 写入Excel
	 * @param excel
	 * @return
	 */
	public static void writeExcel(Excel excel) {

		WritableWorkbook workbook = null;
		try {
			// 构建 Workbook 对象 , 只读 Workbook 对象
			workbook = Workbook.createWorkbook(new File(excel.getUrlPath()));
			ExcelSheet[] allSheet = excel.getExcelSheet();
			
			for(int k = 0; k<allSheet.length; k++){
				ExcelSheet excelSheet = allSheet[k];
				// 创建 Excel 工作表
				WritableSheet wSheet = workbook.createSheet(excelSheet.getSheetName(), k);
				//获取当前sheet所有单元格对象
				ExcelCell[][] allCell = excelSheet.getExcelCell();
				//写入所有单元格内容
				eachCell(allCell,wSheet);
			}
			workbook.write();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			//关闭 Excel工作薄对象
			try {
				if (workbook != null)
					workbook.close();
			} catch (WriteException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 写入Excel Sheet单元格内容
	 * @param excel
	 * @param writableSheet
	 * @return
	 */
	public static void eachCell(ExcelCell[][] allCell, WritableSheet writableSheet) {
		try {
			for(int i = 0; i<allCell.length; i++){
				for(int j = 0; j<allCell[i].length; j++){
					ExcelCell eachCell = allCell[i][j];
					//添加Label对象
					Label label = new Label(eachCell.cellColNum, eachCell.cellRowNum, (String) eachCell.cellValue, eachCell.cellFormat);
					writableSheet.addCell(label);
				}
			}
		} catch (RowsExceededException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (WriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
