package com.qind.common.util.jxl;

public class Excel {

	public String getUrlPath() {
		return urlPath;
	}
	public void setUrlPath(String urlPath) {
		this.urlPath = urlPath;
	}
	public ExcelSheet[] getExcelSheet() {
		return excelSheet;
	}
	public void setExcelSheet(ExcelSheet[] excelSheet) {
		this.excelSheet = excelSheet;
	}
	
	private String urlPath;//文档路径
	private ExcelSheet[] excelSheet;//sheet数组
}
