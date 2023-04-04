package utilities;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelFileUtil {
		
	Workbook wb;
	//Constructor for reading Excel Path
	public ExcelFileUtil(String excelpath) throws Throwable {
		FileInputStream fi= new FileInputStream(excelpath);
		wb= WorkbookFactory.create(fi);
	}
	
	//get row count from sheet
	public int rowCount(String sheetname) {
		return wb.getSheet(sheetname).getLastRowNum();
	} 
	//method for reading cell data
	public String getCellData(String sheetname, int row, int column) {
		String data="";
		if(wb.getSheet(sheetname).getRow(row).getCell(column).getCellType()==Cell.CELL_TYPE_NUMERIC) {
			//read integer type cell and store
			int celldata=(int) wb.getSheet(sheetname).getRow(row).getCell(column).getNumericCellValue();
			//convert int type data to String
			data= String.valueOf(celldata);
			
		}else {
			data=wb.getSheet(sheetname).getRow(row).getCell(column).getStringCellValue();
		}
		return data;
	} 
	public void setCellData(String sheetName,int row,int column, String status, String writeExcel) throws Throwable {
		
		//get sheet from xlfile
		Sheet ws =wb.getSheet(sheetName);
		//get row from sheet
		Row rowNum=ws.getRow(row);
		//create cell
		Cell cell=rowNum.createCell(column);
		//write status
		cell.setCellValue(status);
		if(status.equalsIgnoreCase("Pass")) {
			CellStyle style=wb.createCellStyle();
			Font font=wb.createFont();
			font.setColor(IndexedColors.GREEN.getIndex());
			font.setBold(true);
			font.setBoldweight(Font.BOLDWEIGHT_BOLD);
			style.setFont(font);
			rowNum.getCell(column).setCellStyle(style);
		}else if(status.equalsIgnoreCase("Fail")){
			CellStyle style=wb.createCellStyle();
			Font font=wb.createFont();
			font.setColor(IndexedColors.RED.getIndex());
			font.setBold(true);
			font.setBoldweight(Font.BOLDWEIGHT_BOLD);
			style.setFont(font);
			rowNum.getCell(column).setCellStyle(style);
		}else if(status.equalsIgnoreCase("Blocked")) {
			CellStyle style=wb.createCellStyle();
			Font font=wb.createFont();
			font.setColor(IndexedColors.BLUE.getIndex());
			font.setBold(true);
			font.setBoldweight(Font.BOLDWEIGHT_BOLD);
			style.setFont(font);
			rowNum.getCell(column).setCellStyle(style);
		}
	FileOutputStream fo= new FileOutputStream(writeExcel);
	wb.write(fo);
	}
/*	public static void main(String[] args) throws Throwable {
//		ExcelFileUtil xl=new ExcelFileUtil("E:/Software Testing/Project_By_Ranga_Reddy/Projects/Sample.xlsx");
//		int rc=xl.rowCount("EmpData");
//		System.out.println("No of rows are: "+rc);
//		for(int i=1;i<=rc;i++) {
//			String fname=xl.getCellData("EmpData", i, 0);
//			String mname=xl.getCellData("EmpData", i, 1);
//			String lname=xl.getCellData("EmpData", i, 2);
//			String eid=xl.getCellData("EmpData", i, 3);
//			System.out.println(fname+"  "+mname+"  "+lname+"  "+eid);
//			xl.setCellData("EmpData", i, 4, "Pass", "E:/Software Testing/Project_By_Ranga_Reddy/Projects/Results.xlsx");
//	//		xl.setCellData("EmpData", i, 4, "Fail", "E:/Software Testing/Project_By_Ranga_Reddy/Projects/Results.xlsx");
//	//		xl.setCellData("EmpData", i, 4, "Blocked", "E:/Software Testing/Project_By_Ranga_Reddy/Projects/Results.xlsx");
//		}
	}
	
*/	
	
	
	
	
	
	
	
}
